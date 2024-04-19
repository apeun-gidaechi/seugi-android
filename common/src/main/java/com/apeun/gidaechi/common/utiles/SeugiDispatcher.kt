package com.apeun.gidaechi.common.utiles

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SeugiDispatcher(val dispatcherType: DispatcherType)

enum class DispatcherType {
    Default,
    IO,
    Main,
}
