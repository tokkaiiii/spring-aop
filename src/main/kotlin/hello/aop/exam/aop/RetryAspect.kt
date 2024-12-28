package hello.aop.exam.aop

import hello.aop.exam.annotation.Retry
import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class RetryAspect {
    private val log = logger()

    @Around("@annotation(retry)")
    fun doRetry(joinPoint: ProceedingJoinPoint, retry: Retry): Any {
        log.info("[retry] ${joinPoint.signature} retry=$retry")
        val maxRetry = retry.value
        var exceptionHolder: Exception? = null
        for (retryCont in 1..maxRetry) {
            try {
                log.info("[retry] try count=${retryCont}/${maxRetry}")
                return joinPoint.proceed()
            } catch (e: Exception) {
                exceptionHolder = e
            }
        }
        throw exceptionHolder!!
    }
}