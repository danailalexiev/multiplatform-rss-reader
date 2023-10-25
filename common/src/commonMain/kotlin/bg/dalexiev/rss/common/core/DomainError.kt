package bg.dalexiev.rss.common.core

sealed interface DomainError {

    sealed interface FeedError : DomainError {

        data class FeedNotFetched(val cause: Throwable) : FeedError

        data class FeedNotParsed(val message: String) : FeedError

    }

}