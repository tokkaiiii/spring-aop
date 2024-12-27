package hello.aop.pointcut

import hello.aop.member.MemberService
import hello.aop.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

/**
 * application.properties
 * spring.aop.proxy-target-class=true CGLIB
 * spring.aop.proxy-target-class=false JDK 동적 프록시
 */
//@SpringBootTest(properties = ["spring.aop.proxy-target-class=false"])
@SpringBootTest
@Import(ThisTargetTest.ThisTargetAspect::class)
class ThisTargetTest(
    @Autowired val memberService: MemberService
) {
    private val log = logger()

    @Test
    fun success(){
        log.info("memberService Proxy=${memberService.javaClass}")
        memberService.hello("helloA")
    }

    @Aspect
    class ThisTargetAspect {
        private val log = logger()

        // 부모 타입 허용
        @Around("this(hello.aop.member.MemberService)")
        fun doThisInterface(joinPoint: ProceedingJoinPoint): Any{
            log.info("[this-interface] ${joinPoint.signature}")
            return joinPoint.proceed()
        }

        // 부모 타입 허용
        @Around("target(hello.aop.member.MemberService)")
        fun doTargetInterface(joinPoint: ProceedingJoinPoint): Any{
            log.info("[target-interface] ${joinPoint.signature}")
            return joinPoint.proceed()
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        fun doThis(joinPoint: ProceedingJoinPoint): Any{
            log.info("[this-impl] ${joinPoint.signature}")
            return joinPoint.proceed()
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        fun doTarget(joinPoint: ProceedingJoinPoint): Any{
            log.info("[target-impl] ${joinPoint.signature}")
            return joinPoint.proceed()
        }
    }
}