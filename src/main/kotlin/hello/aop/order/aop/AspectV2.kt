package hello.aop.order.aop

import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class AspectV2 {
    private val log = logger()

    // hello.aop.order 패키지와 그 하위 패키지(..) 를 지정하는 AspectJ 포인트컷 표현식
    @Pointcut("execution(* hello.aop.order..*(..))")
    fun allOrder() {} // pointcut signature

    @Around("allOrder()")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        log.info("[log] {}", joinPoint.signature) // join point 시그니쳐
        return joinPoint.proceed() // 이렇게 해야 타겟 호출됨
    }
}