package hello.aop.exam.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@Target(FUNCTION)
@Retention(RUNTIME)
annotation class Trace()
