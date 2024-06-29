package com.mashup.pic.vote.applicationservice

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteApplicationService {
    /**
     * Request: 사진 imageUrl list (1~4)
     * Flow
     * 0. eventID로 이벤트 조회
     * 1. 리스트 돌면서 해당 사진들 이벤트 후보 테이블에 insert
     * 2. 현재 유저 상태 READY -> PIC_FINISHED로 변경
     * @return 현재 그룹 상태 내려주기 (모두 사진을 올렸으면 다음 스텝으로)
     */
    @Transactional
    fun uploadMyPic() {
        TODO("Not yet implemented")
    }

    /**
     * Request: 이벤트 후보 사진 Id랑 좋아요 유무 리스트
     * Flow
     * 1. 리스트 돌면서 이벤트 후보 사진 득표수 +1 & 투표 이력 insert
     * 2. 현재 유저 상태 PIC_FINISHED -> VOTE_FINISHED로 변경
     * 3. 해당 그룹원 돌면서 투표 다 됐는지 체크
     * 4. 모두가 투표 완료했을 경우 득표 1, 2, 3, 4등 뽑아서 4컷 insert
     * @return 현재 그룹 상태 내려주기 (모두 투표를 완료했으면 다음 스텝으로)
     * */
    @Transactional
    fun completeVote() {
        TODO("Not yet implemented")
    }
}
