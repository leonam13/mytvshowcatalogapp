package com.example.leotvshowapp

open class NoMorePagesException : Exception("There's no more pages to load") {
    companion object {
        const val code = 404
    }
}

class TvShowNoMorePagesException : NoMorePagesException()

class PeopleNoMorePagesException : NoMorePagesException()