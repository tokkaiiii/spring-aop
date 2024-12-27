package hello.aop.pointcut

import hello.aop.member.annotation.ClassAop
import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootTest
class AtTargetAtWithinTest(
    @Autowired var child: Child
) {
    private val log = logger()

    @Test
    fun success() {
        log.info("child Proxy=${child.javaClass}")
        child.childMethod() // 부모, 자식 모드 있는 메서드
        child.parentMethod() // 부모 클래스만 있는 메서드
    }

    @Configuration
    @EnableAspectJAutoProxy
    class Config {
        @Bean
        fun parent(): Parent = Parent()

        @Bean
        fun child(): Child = Child()

        @Bean
        fun atTargetAtWithinAspect(): AtTargetAtWithinAspect = AtTargetAtWithinAspect()
    }

    open class Parent {
        open fun parentMethod() {} // 부모에만 있는 메서드
    }

    @ClassAop
    open class Child : Parent() {
        open fun childMethod() {}
    }

    @Aspect
     class AtTargetAtWithinAspect {
        private val log = logger()

        // @target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
        fun atTarget(joinPoint: ProceedingJoinPoint): Any? {
            log.info("[@target] ${joinPoint.signature}")
            return joinPoint.proceed()
        }

        // @within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
        fun atWithin(joinPoint: ProceedingJoinPoint): Any? {
            log.info("[@within] ${joinPoint.signature}")
            return joinPoint.proceed()
        }
    }
}