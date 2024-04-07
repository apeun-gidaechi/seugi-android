package com.apeun.gidaechi.common.exception

import java.lang.RuntimeException

class BadRequestException(
    override val message: String?
): RuntimeException()

class UnauthorizedException(
    override val message: String?
): RuntimeException()

class NotFoundException(
    override val message: String?
): RuntimeException()

class ForbiddenException(
    override val message: String?
): RuntimeException()

class ConflictException(
    override val message: String?
): RuntimeException()

class TooManyRequestsException(
    override val message: String?
): RuntimeException()

class InternalServerException(
    override val message: String?
): RuntimeException()

class FailedRequestException(
    override val message: String?
): RuntimeException()