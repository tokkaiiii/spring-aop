package hello.aop.exam

import hello.aop.exam.annotation.Trace
import org.springframework.stereotype.Service

@Service
class ExamService constructor( val examRepository: ExamRepository){
    @Trace
    fun request(itemId: String){
        examRepository.save(itemId)
    }
}