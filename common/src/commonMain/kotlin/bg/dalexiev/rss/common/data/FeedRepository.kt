package bg.dalexiev.rss.common.data

import arrow.core.Either
import bg.dalexiev.rss.common.Dependencies
import bg.dalexiev.rss.common.core.DomainError
import bg.dalexiev.rss.common.data.remote.fetchRemoteRssFeed
import kotlin.jvm.JvmInline

data class Source(
    val url: SourceUrl,
    val type: SourceType
)

@JvmInline
value class SourceUrl(val value: String)

enum class SourceType {
    RSS, ATOM
}

data class Feed(
    val title: Title,
    val link: Link,
    val description: Description,
    val items: List<Item>
)

data class Item(
    val guid: Guid,
    val title: Title,
    val link: Link,
    val author: Author,
    val description: Description
)

@JvmInline
value class Title(val value: String)

@JvmInline
value class Link(val value: String)

@JvmInline
value class Description(val value: String)

@JvmInline
value class Guid(val value: String)

@JvmInline
value class Author(val name: String)

suspend fun Dependencies.getRssFeed(source: Source): Either<DomainError.FeedError, Feed> = with(httpClient) {
    fetchRemoteRssFeed(source)
}