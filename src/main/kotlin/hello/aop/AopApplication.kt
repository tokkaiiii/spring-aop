package hello.aop

import hello.aop.order.aop.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
//@Import(AspectV4Pointcut::class)
@Import(AspectV5Order.LogAspect::class,AspectV5Order.TxAspect::class)
class AopApplication

fun main(args: Array<String>) {
    runApplication<AopApplication>(*args)
}
