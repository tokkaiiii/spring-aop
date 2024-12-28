package hello.aop.exam

import hello.aop.util.logger
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExamTest @Autowired constructor(
    val examService: ExamService
) {

    private val log = logger()
    @Test
    fun test(){
        for (i in 0 until 5) {
            log.info("client request i=$i")
            examService.request("data $i")
        }
    }
}