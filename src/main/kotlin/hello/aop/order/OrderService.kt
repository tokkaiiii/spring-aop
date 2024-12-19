package hello.aop.order

import hello.aop.util.logger
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    private val log = logger()
    fun orderItem(itemId: String){
        log.info("[orderService] 실행")
        orderRepository.save(itemId)
    }
}