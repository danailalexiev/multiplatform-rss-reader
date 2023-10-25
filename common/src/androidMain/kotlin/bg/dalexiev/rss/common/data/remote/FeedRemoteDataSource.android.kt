package bg.dalexiev.rss.common.data.remote

import android.util.Xml
import arrow.core.Either
import arrow.core.left
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import bg.dalexiev.rss.common.core.DomainError
import bg.dalexiev.rss.common.data.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.StringReader
import java.util.logging.XMLFormatter

internal actual fun String.parseToFeed(): Either<DomainError.FeedError.FeedNotParsed, Feed> = catch({ createParser(this).readRss() }) {
    when (it) {
        is XmlPullParserException,
        is IOException -> DomainError.FeedError.FeedNotParsed("Exception while parsing feed").left()
        else -> throw it
    }
}

private fun createParser(input: String): XmlPullParser =
    Xml.newPullParser().apply {
        setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        setInput(StringReader(input))
        nextTag()
    }

private fun XmlPullParser.readRss(): Either<DomainError.FeedError.FeedNotParsed, Feed> = either {
    var feed: Feed? = null

    require(XmlPullParser.START_TAG, null, "rss")

    forEachChildTag {
        when (name) {
            "channel" -> feed = readFeed().bind()
            else -> skip()
        }
    }

    ensureNotNull(feed) { DomainError.FeedError.FeedNotParsed("Channel missing") }
}

private fun XmlPullParser.readFeed(): Either<DomainError.FeedError.FeedNotParsed, Feed> = either {
    var title: Title? = null
    var link: Link? = null
    var description: Description? = null
    val items = mutableListOf<Item>()

    require(XmlPullParser.START_TAG, null, "channel")

    forEachChildTag {
        when (name) {
            "title" -> title = read("title" to ::Title).bind()
            "link" -> link = read("link" to ::Link).bind()
            "description" -> description = read("description" to ::Description).bind()
            "item" -> items.add(readItem().bind())
            else -> skip()
        }
    }

    Feed(
        title = ensureNotNull(title) { DomainError.FeedError.FeedNotParsed("Title missing") },
        link = ensureNotNull(link) { DomainError.FeedError.FeedNotParsed("Link missing") },
        description = ensureNotNull(description) { DomainError.FeedError.FeedNotParsed("Description missing") },
        items = items
    )
}

private fun XmlPullParser.readItem(): Either<DomainError.FeedError.FeedNotParsed, Item> = either {
    var guid: Guid? = null
    var title: Title? = null
    var link: Link? = null
    var author: Author? = null
    var description: Description? = null

    require(XmlPullParser.START_TAG, null, "item")

    forEachChildTag {
        when (name) {
            "guid" -> guid = read("guid" to ::Guid).bind()
            "title" -> title = read("title" to ::Title).bind()
            "link" -> link = read("link" to ::Link).bind()
            "dc:creator" -> author = read("dc:creator" to ::Author).bind()
            "description" -> description = read("description" to ::Description).bind()
            else -> skip()
        }
    }

    Item(
        guid = ensureNotNull(guid) { DomainError.FeedError.FeedNotParsed("Item guid missing") },
        title = ensureNotNull(title) { DomainError.FeedError.FeedNotParsed("Item title missing") },
        link = ensureNotNull(link) { DomainError.FeedError.FeedNotParsed("Item link missing") },
        author = ensureNotNull(author) { DomainError.FeedError.FeedNotParsed("Item author missing") },
        description = ensureNotNull(description) { DomainError.FeedError.FeedNotParsed("Item description missing") }
    )
}

private inline fun XmlPullParser.forEachChildTag(crossinline block: (String) -> Unit) {
    while (next() != XmlPullParser.END_TAG) {
        if (eventType != XmlPullParser.START_TAG) {
            continue
        }

        block(name)
    }
}

private inline fun <reified T> XmlPullParser.read(tagToBlock: Pair<String, (String) -> T>): Either<DomainError.FeedError.FeedNotParsed, T> =
    either {
        require(XmlPullParser.START_TAG, null, tagToBlock.first)
        val text = readText()
        require(XmlPullParser.END_TAG, null, tagToBlock.first)
        tagToBlock.second(text)
    }

private fun XmlPullParser.readText(): String =
    if (next() == XmlPullParser.TEXT) {
        val text = text
        nextTag()
        text
    } else {
        ""
    }

private fun XmlPullParser.skip() {
    if (eventType != XmlPullParser.START_TAG) {
        throw IllegalStateException()
    }

    var depth = 1
    while (depth != 0) {
        when (next()) {
            XmlPullParser.END_TAG -> depth--
            XmlPullParser.START_TAG -> depth++
        }
    }
}