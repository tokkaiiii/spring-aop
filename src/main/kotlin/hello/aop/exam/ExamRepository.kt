package hello.aop.exam

import hello.aop.exam.annotation.Trace
import org.springframework.stereotype.Repository

@Repository
class ExamRepository {
    companion object {
        var seq = 0
    }
    /**
     * 5번에 1번 실패하는 요청
     */
    @Trace
    fun save(itemId: String): String{
        seq++
        if (seq % 5 == 0){
            throw IllegalStateException("에외 발생")
        }
        return "ok"
    }
}