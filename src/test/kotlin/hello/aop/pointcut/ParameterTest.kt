package hello.aop.pointcut

import hello.aop.member.MemberService
import hello.aop.member.annotation.ClassAop
import hello.aop.member.annotation.MethodAop
import hello.aop.util.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(ParameterTest.ParameterAspect::class)
class ParameterTest(
    @Autowired val memberService: MemberService
) {
    private val log = logger()

    @Test
    fun success(){
        log.info("memberService={}", memberService.javaClass)
        memberService.hello("helloA")
    }

    @Aspect
    open class ParameterAspect {
        private val log = logger()
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        protected fun allMember(){
        }

        @Around("allMember()")
        fun logArgs1(joinPoint: ProceedingJoinPoint): Any {
            val arg1 = joinPoint.args[0]
            log.info("[logArgs1]${joinPoint.signature} $arg1")
            return joinPoint.proceed()
        }

        @Around("allMember() && args(arg, ..)")
        fun logArgs2(joinPoint: ProceedingJoinPoint, arg: Any): Any {
            log.info("[logArgs2]${joinPoint.signature} $arg")
            return joinPoint.proceed()
        }

        @Before("allMember() && args(arg, ..)")
        fun logArgs3(arg: String) {
            log.info("[logArgs3] arg=${arg}")
        }

        @Before("allMember() && this(obj)")
        fun thisArgs(joinPoint: JoinPoint, obj: MemberService) {
            log.info("[this]${joinPoint.signature} obj=${obj.javaClass}")
        }

        @Before("allMember() && target(obj)")
        fun targetArgs(joinPoint: JoinPoint, obj: MemberService) {
            log.info("[target]${joinPoint.signature} obj=${obj.javaClass}")
        }

        @Before("allMember() && @target(annotation)")
        fun atTarget(joinPoint: JoinPoint, annotation: ClassAop) {
            log.info("[@target]${joinPoint.signature} obj=${annotation.javaClass}")
        }

        @Before("allMember() && @within(annotation)")
        fun atWithin(joinPoint: JoinPoint, annotation: ClassAop) {
            log.info("[@within]${joinPoint.signature} obj=${annotation.javaClass}")
        }

        @Before("allMember() && @annotation(annotation)")
        fun atAnnotation(joinPoint: JoinPoint, annotation: MethodAop) {
            log.info("[@annotation]${joinPoint.signature} annotationValue=${annotation.value}")
        }
    }
}