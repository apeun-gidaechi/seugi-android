package com.seugi.network.core.response

import com.seugi.common.exception.BadRequestException
import com.seugi.common.exception.ConflictException
import com.seugi.common.exception.FailedRequestException
import com.seugi.common.exception.ForbiddenException
import com.seugi.common.exception.InternalServerException
import com.seugi.common.exception.NotFoundException
import com.seugi.common.exception.TooManyRequestsException
import com.seugi.common.exception.UnauthorizedException

fun <T> BaseResponse<T>.safeResponse(): T = when (status) {
    400 -> throw BadRequestException(message)
    401 -> throw UnauthorizedException(message)
    403 -> throw ForbiddenException(message)
    404 -> throw NotFoundException(message)
    409 -> throw ConflictException(message)
    429 -> throw TooManyRequestsException(message)
    500 -> throw InternalServerException(message)
    else -> {
        if (!success) {
            throw FailedRequestException(message)
        }
        this.data
    }
}

fun Response.safeResponse(): Boolean = when (status) {
    400 -> throw BadRequestException(message)
    401 -> throw UnauthorizedException(message)
    403 -> throw ForbiddenException(message)
    404 -> throw NotFoundException(message)
    409 -> throw ConflictException(message)
    429 -> throw TooManyRequestsException(message)
    500 -> throw InternalServerException(message)
    else -> {
        if (!success) {
            throw FailedRequestException(message)
        }
        this.success
    }
}
