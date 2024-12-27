package hello.aop.pointcut

import hello.aop.order.OrderService
import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(BeanTest.BeanAspect::class)
class BeanTest (@Autowired val orderService: OrderService) {

    @Test
    fun success(){
        orderService.orderItem("itemA")
    }

    @Aspect
    class BeanAspect{
        private val log = logger()
        @Around("bean(orderService) || bean(*Repository)")
        fun doLog(joinPoint: ProceedingJoinPoint): Any?{
            log.info("[bean] ${joinPoint.signature}")
            return joinPoint.proceed()
        }
    }
}