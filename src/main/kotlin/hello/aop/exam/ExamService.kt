package hello.aop.exam

import org.springframework.stereotype.Service

@Service
class ExamService constructor( val examRepository: ExamRepository){
    fun request(itemId: String){
        examRepository.save(itemId)
    }
}