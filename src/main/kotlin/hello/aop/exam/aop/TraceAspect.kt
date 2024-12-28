package hello.aop.exam.aop

import hello.aop.util.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Aspect
class TraceAspect {
    private val log =logger()

    @Before("@annotation(hello.aop.exam.annotation.Trace)")
    fun doTrace(joinPoint: JoinPoint){
        val args = joinPoint.args
        log.info("[trace] ${joinPoint.signature} args=${args.toList()}")
    }
}