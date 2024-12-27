package hello.aop.pointcut

import hello.aop.member.MemberServiceImpl
import hello.aop.util.logger
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import java.lang.reflect.Method

class ExecutionTest {

    private val log = logger()
    val pointcut = AspectJExpressionPointcut()
    private var helloMethod: Method? = null

    @BeforeEach
    fun init(){
        // execution(* ..package..Class.)
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
         helloMethod = MemberServiceImpl::class.java.getMethod("hello",String::class.java)
    }

    @Test
    fun printMethod(){
        log.info("helloMethod={}", helloMethod)
    }

    @Test
    fun exactMatch(){
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.expression = "execution(public String hello.aop.member.MemberServiceImpl.hello(String))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    // 가장 많이 생략한 포인트컷
    @Test
    fun allMatch(){
        pointcut.expression = "execution(* *(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun nameMatch(){
        pointcut.expression = "execution(* hello(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun nameMatchStar1(){
        pointcut.expression = "execution(* hel*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun nameMatchStar2(){
        pointcut.expression = "execution(* *el*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun nameMatchFalse(){
        pointcut.expression = "execution(* nono(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isFalse()
    }

    @Test
    fun packageExactMatch1(){
        pointcut.expression = "execution(* hello.aop.member.MemberServiceImpl.hello(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun packageExactMatch2(){
        pointcut.expression = "execution(* hello.aop.member.*.*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun packageExactFalse(){
        pointcut.expression = "execution(* hello.aop.*.*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isFalse()
    }

    @Test
    fun packageMatchSubPackage1(){
        pointcut.expression = "execution(* hello.aop.member..*.*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun packageMatchSubPackage2(){
        pointcut.expression = "execution(* hello.aop..*.*(..))"
        assertThat(pointcut.matches(helloMethod!!,MemberServiceImpl::class.java)).isTrue()
    }



}