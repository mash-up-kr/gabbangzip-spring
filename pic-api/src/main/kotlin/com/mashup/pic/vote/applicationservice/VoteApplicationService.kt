package com.mashup.pic.vote.applicationservice

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteApplicationService {
    @Transactional
    fun uploadMyPic() {
        TODO("Not yet implemented")
    }

    @Transactional
    fun vote() {
        TODO("Not yet implemented")
    }
}
