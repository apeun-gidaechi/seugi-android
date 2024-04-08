package com.apeun.gidaechi.network.core.response

import com.apeun.gidaechi.common.exception.BadRequestException
import com.apeun.gidaechi.common.exception.ConflictException
import com.apeun.gidaechi.common.exception.FailedRequestException
import com.apeun.gidaechi.common.exception.ForbiddenException
import com.apeun.gidaechi.common.exception.InternalServerException
import com.apeun.gidaechi.common.exception.NotFoundException
import com.apeun.gidaechi.common.exception.TooManyRequestsException
import com.apeun.gidaechi.common.exception.UnauthorizedException

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
