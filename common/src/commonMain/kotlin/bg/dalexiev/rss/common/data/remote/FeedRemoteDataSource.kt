package bg.dalexiev.rss.common.data.remote

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.nonFatalOrThrow
import bg.dalexiev.rss.common.core.DomainError
import bg.dalexiev.rss.common.data.Feed
import bg.dalexiev.rss.common.data.Source
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

internal suspend fun HttpClient.fetchRemoteRssFeed(source: Source): Either<DomainError.FeedError, Feed> =
    Either.catch { get(source.url.value).bodyAsText() }
        .mapLeft { throwable -> throwable.nonFatalOrThrow().let { DomainError.FeedError.FeedNotFetched(it) } }
        .flatMap { it.parseToFeed() }

internal expect fun String.parseToFeed(): Either<DomainError.FeedError.FeedNotParsed, Feed>