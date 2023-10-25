package bg.dalexiev.rss.common.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import bg.dalexiev.rss.common.core.DomainError
import bg.dalexiev.rss.common.data.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.asList
import org.w3c.dom.get
import org.w3c.dom.parsing.DOMParser

internal actual fun String.parseToFeed(): Either<DomainError.FeedError.FeedNotParsed, Feed> {
    val domParser = DOMParser()
    val doc = domParser.parseFromString(this, "application/xml")

    return doc.querySelector("parsererror")?.let { DomainError.FeedError.FeedNotParsed("error while parsing document").left() }
        ?: parseFeed(doc)
}

private fun parseFeed(doc: Document): Either<DomainError.FeedError.FeedNotParsed, Feed> = either {
    val channel = doc.documentElement ?: raise(DomainError.FeedError.FeedNotParsed("root element not found"))
    Feed(
        title = channel.parse("title" to ::Title).bind(),
        link = channel.parse("link" to ::Link).bind(),
        description = channel.parse("description" to ::Description).bind(),
        items = channel.getElementsByTagName("item").asList()
            .map { parseItem(it).bind() }
    )
}

private fun parseItem(element: Element): Either<DomainError.FeedError.FeedNotParsed, Item> = either {
    Item(
        guid = element.parse("guid" to ::Guid).bind(),
        title = element.parse("title" to ::Title).bind(),
        link = element.parse("link" to ::Link).bind(),
        author = element.parse("dc:creator" to ::Author).bind(),
        description = element.parse("description" to ::Description).bind()
    )
}

private fun <T> Element.parse(nodeToBlock: Pair<String, (String) -> T>): Either<DomainError.FeedError.FeedNotParsed, T> =
    getElementsByTagName(nodeToBlock.first)[0]?.textContent?.let { nodeToBlock.second(it).right() }
        ?: DomainError.FeedError.FeedNotParsed("no element with tag ${nodeToBlock.first} found in $this").left()
