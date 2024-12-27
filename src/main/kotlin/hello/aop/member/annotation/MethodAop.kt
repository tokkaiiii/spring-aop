package hello.aop.member.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@Target(FUNCTION)
@Retention(RUNTIME)
annotation class MethodAop (val value: String)