package com.andres.nyuzuk.domain

sealed class Failure {
    object NetworkConnection : Failure()
    object ApiError : Failure()
    object EmptyResult : Failure()
    object MappingError : Failure()
    object NotFoundError : Failure()
    object UnknownError : Failure()
}