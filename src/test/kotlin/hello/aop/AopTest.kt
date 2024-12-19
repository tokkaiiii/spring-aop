package hello.aop

import hello.aop.order.OrderRepository
import hello.aop.order.OrderService
import hello.aop.util.logger
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AopTest(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val orderService: OrderService
) {
    private val log =logger()

    @Test
    fun aopInfo(){
        log.info("isAopProxy, orderService=${AopUtils.isAopProxy(orderService)}")
        log.info("isAopProxy, orderRepository=${AopUtils.isAopProxy(orderRepository)}")
    }

    @Test
    fun success(){
        orderService.orderItem("itemA")
    }

    @Test
    fun exception(){
        Assertions.assertThatThrownBy { orderService.orderItem("ex") }
            .isInstanceOf(IllegalStateException::class.java)
    }
}