package hello.aop.order.aop

import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class AspectV3 {
    private val log = logger()

    // hello.aop.order 패키지와 그 하위 패키지(..) 를 지정하는 AspectJ 포인트컷 표현식
    @Pointcut("execution(* hello.aop.order..*(..))")
    fun allOrder() {} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    fun allService(){}

    @Around("allOrder()")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        log.info("[log] {}", joinPoint.signature) // join point 시그니쳐
        return joinPoint.proceed() // 이렇게 해야 타겟 호출됨
    }

    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    fun doTransaction(joinPoint: ProceedingJoinPoint): Any? {
            try {
                log.info("[트랜잭션 시작] ${joinPoint.signature}")
                val result = joinPoint.proceed()
                log.info("[트랜잭션 커밋] ${joinPoint.signature}")
                return result
            }catch (e: Exception){
                log.info("[트랜잭션 롤백] ${joinPoint.signature}")
                throw e
            }finally {
                log.info("[리소스 릴리즈] ${joinPoint.signature}")
            }
    }
}