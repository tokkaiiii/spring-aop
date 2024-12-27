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

@SpringBootTest
@Import(AtAnnotationTest.AtAnnotationAspect::class)
class AtAnnotationTest(
    @Autowired val memberService: MemberService
) {
    private val log = logger()

    @Test
    fun success(){
        log.info("memberService Proxy=${memberService.javaClass}")
        memberService.hello("helloA")
    }

    @Aspect
    class AtAnnotationAspect{
        private val log = logger()
        @Around("@annotation(hello.aop.member.annotation.MethodAop)")
        fun doAtAnnotation(joinPoint: ProceedingJoinPoint): Any?{
            log.info("[@annotation] ${joinPoint.signature}")
            return joinPoint.proceed()
        }
    }
}