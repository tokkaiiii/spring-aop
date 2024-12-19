package hello.aop.order.aop

import hello.aop.util.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*

@Aspect
class AspectV6Advice {
    private val log = logger()

    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    fun doTransaction(joinPoint: ProceedingJoinPoint): Any? {
            try {
                // @Before
                log.info("[트랜잭션 시작] ${joinPoint.signature}")
                val result = joinPoint.proceed()
                // @AfterReturning
                log.info("[트랜잭션 커밋] ${joinPoint.signature}")
                return result
            }catch (e: Exception){
                // @AfterThrowing
                log.info("[트랜잭션 롤백] ${joinPoint.signature}")
                throw e
            }finally {
                // @after
                log.info("[리소스 릴리즈] ${joinPoint.signature}")
            }
    }

    // joinPoint 실행되기전 로직
    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    fun doBefore(joinPoint: JoinPoint){
        log.info("[before] ${joinPoint.signature}")
    }

    // returning 에 리턴되는 값 이름 써줘야 힘
    @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndService()",returning = "result")
    fun doReturn(joinPoint: JoinPoint, result: Any?) {
        log.info("[return] ${joinPoint.signature}] return=$result")
    }

    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.orderAndService()",throwing = "ex")
    fun doThrowing(joinPoint: JoinPoint, ex: Exception?) {
        log.info("[ex] $ex")
    }

    @After(value = "hello.aop.order.aop.PointCuts.orderAndService()")
    fun doAfter(joinPoint: JoinPoint) {
        log.info("[after] ${joinPoint.signature}")
    }
}