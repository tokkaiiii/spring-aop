package hello.aop.order.aop

import org.aspectj.lang.annotation.Pointcut

object PointCuts {
    // hello.aop.order 패키지와 그 하위 패키지(..) 를 지정하는 AspectJ 포인트컷 표현식
    @Pointcut("execution(* hello.aop.order..*(..))")
    fun allOrder() {} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    fun allService(){}

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    fun orderAndService(){}
}