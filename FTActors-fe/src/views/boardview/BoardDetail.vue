<template>
  <div class="container my-5">
    <div class="profileheader text-center mb-4">
        <h1><b>BOARD</b></h1>
    </div>
    <div class="contents d-flex flex-row justify-content-evenly">
        <div class="col-md-8 img-container">
            <img :src="recruitment.image" alt="" class="img-fit">
        </div>
        <div class="col-md-8">
          <div class="profilelist">
            <ul class="list-group list-group-flush">
              <li class="list-group-item flex-shrink">
                <div class="d-flex flex-col justify-content-center columnthings">
                  <label>
                    <h2><b>{{ recruitment.title }}</b></h2>
                  </label>
                  <!-- <div v-if="checkPermission()"> -->
                    <div class="button-container ps-5">
                      <button type="button" class="btn btn-dark-outlined" @click="boardUpdate">공고 변경</button>
                      <button type="button" class="btn btn-dark" @click="confirmDelete">공고 삭제</button>
                      <button type="button" class="btn btn-danger" @click="goToEdit">지원영상 편집</button>
                    </div>
                  <!-- </div> -->
                  <!-- <div v-else> -->
                    <div class="button-container flex-column flex-sm-row flex-fill ">
                      <button v-if="recruitment.apply === 1" class="btn btn-secondary">이미 지원하였습니다</button>
                      <ApplyCreate />
                      <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#applyModal" @click="apply">
                        지원하기
                      </button>
                    </div>
                  <!-- </div> -->
                </div>
              </li>
                <li class="list-group-item p-3 fs-4"><b>담당자:</b> {{ recruitment.postMember }}</li>
                <li class="list-group-item p-3 fs-4"><b>공고분류:</b> {{ recruitment.category }}</li>
                <li class="list-group-item p-3 fs-4"><b>지원시작일자:</b> {{ recruitment.startDate }}</li>
                <li class="list-group-item p-3 fs-4"><b>지원마감일자:</b> {{ recruitment.endDate }}</li>
              
              </ul>
          <div class="detailboardpage mt-5 ml-">
            <h3 class="mb-2"><b>공고 내용</b></h3>
            <h4 class=""><p>{{ recruitment.content }}</p></h4>
            <h3 class="mt-5"><b>첨부파일</b></h3>
            <a :href="recruitment.file" download="recruitment_file" class="fs-5">대사 스크립트 다운로드</a>
            <p class="mt-4"> 영상을 올릴 때 꼭 [이름] 배역이름으로 파일명을 지정해주세요.<br>ex) [배역이름]실제이름</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<!-- 
<template>
  <div class="profileheader">
    <h1> <b>PROFILE</b></h1>
    </div>
    <div class="profilepage">
      <img :src="recruitment.image" alt="" class="img-fit">
      <div class="profilelist">
        <ul class="list-group list-group-flush">
          <li class="list-group-item"><label><b>이름</b></label> 
            <div class="profiletab">
            <button type="button" class="btn btn-dark" id="updatebtn" @click="goToProfileUpdate">수정하기</button>
            신고 버튼
            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#reportModal">
              <img src="@/assets/icons/Siren.png" alt="">
            </button>
            <ReportModal />
          </div>
          배사람 </li>
          <li class="list-group-item"><label><b>닉네임</b></label>춤추는 산양 </li>
          <li class="list-group-item"><label><b>이메일</b></label> bausa@gmail.com </li>
          <li class="list-group-item"><label><b>전화번호</b></label> 010-5454-5454 </li>
          <li class="list-group-item"><label><b>이름</b></label> 배사람 </li>
          <li class="list-group-item"><label><b>닉네임</b></label>춤추는 산양 </li>
          <li class="list-group-item"><label><b>이메일</b></label> bausa@gmail.com </li>
          <li class="list-group-item"><label><b>전화번호</b></label> 010-5454-5454 </li>
          <li class="list-group-item"><label><b>이름</b></label> 배사람 </li>
          <li class="list-group-item"><label><b>닉네임</b></label>춤추는 산양 </li>
          <li class="list-group-item"><label><b>이메일</b></label> bausa@gmail.com </li>
          <li class="list-group-item"><label><b>전화번호</b></label> 010-5454-5454 </li>
        </ul>
      </div>
    </div>
    <div class="detailprofilepage">
      <h4><b>자기소개</b></h4>
      <p>안녕하세요 배우 안기영입니다 :)
        항상 맡은 바 책임을 다하는 배우가 되겠습니다.
  
        이름 : 안기영
        나이 : 27세 (1996.12.08)
        신장 : 180cm / 65kg
        학력 : 서울예대 연기전공 졸업
        특기 : 액션, 춤, 악기연주(기타/플룻), 노래
        연락처 : 01076777064 / gi0miso@naver.com /
        https://www.instagram.com/hug.0a/
  
        연기영상 : https://youtu.be/R0tEXCYsBt4
        https://youtu.be/R0tEXCYsBt4</p>
  
      <h4><b>학력</b></h4>
      <p>서울예술대학교 연기전공</p>
      <h4><b>경력</b></h4>
      <p></p>
      <h4><b>추가 이미지</b></h4>
      <p></p>
      <h4><b>연기영상</b></h4>
      <p></p>
    </div>
  
  
  </template> -->
  

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { recruitmentApi } from '@/util/axios';
import { useMemberStore } from "@/stores/member-store.js";
import ApplyCreate from '../applyview/ApplyCreate.vue';
const MemberStore = useMemberStore();
const loginMember = ref(null);
loginMember.value = MemberStore.memberInfo;
const adminId = 11;
const router = useRouter();
const recruitment = ref({});

const goToEdit = () => {
  router.push({ name: 'edit', params : {id: router.currentRoute.value.params.id} });
};
const fetchRecruitmentDetail = async () => {
  const recruitmentId = router.currentRoute.value.params.id; // 현재 라우트의 파라미터 사용
  if (loginMember.value == "" || loginMember.value == null || loginMember.value == undefined) {
    const response = await recruitmentApi.getDetail(recruitmentId, adminId);
    recruitment.value = response.data.data;
  } else {
    const memberId = loginMember.value;
    const response = await recruitmentApi.getDetail(recruitmentId, memberId);
    recruitment.value = response.data.data;
    console.log(recruitment)
  }
};

onMounted(fetchRecruitmentDetail);

const checkPermission = () => {
  return loginMember.value == recruitment.value.postMemberId;
};

const boardUpdate = () => {
  const boardId = recruitment.value.id;
  router.push({ name: 'boardUpdate', state: { id: boardId } });
};

const confirmDelete = () => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    deleteRecruitment();
  }
};

const apply = () => {
  if (loginMember.value == "" || loginMember.value == null) {
    alert("로그인이 필요합니다")
    router.push({ name: 'login' });
  } else {
    const recruitmentId = recruitment.value.id;
    router.push({ name: 'applyCreate', params: { recruitmentId: recruitmentId, memberId: loginMember.value } });
  }
};

const deleteRecruitment = async () => {
  const recruitmentId = recruitment.value.id;
  try {
    const response = await recruitmentApi.remove(recruitmentId);
    if (response.status === 200) {
      alert("삭제되었습니다.");
    } else {
      alert("삭제 실패했습니다.");
    }
    router.push({ name: 'board' });
  } catch (error) {
    console.error("Error deleting recruitment:", error);
  }
};

</script>

<style scoped>
h1 {
  font-size: 4rem;
}
.container {
  display: flex;
  flex-direction: column;
}

.profileheader {
  display: flex;
  align-items: center;
  font-size: 4rem;
}

.profileheader {
  display: flex;
  justify-content: center;
}

.columnthings{
  display: flex;
  flex-direction: column;
}
.profiletab img {
  width: 24px;
  height: 24px;

}
.btn{
  min-width: 100px;
}
.profilepage {
  display: flex;
}

.profilelist {
  width: 30rem;
}

.detailprofilepage {
  width: 50%;
}

.uploader {
  display: flex;
  flex-direction: row;
}

#title {
  display: flex;
}

#title button {
  margin-left: auto;
}

.btn-dark {
  position: relative;
  right: 0;
  min-width: 100px;
}

.list-group-item {
  display: flex;
  justify-content: space-between;
  /* 항목을 양 끝으로 분산 */
  align-items: center;
  /* 세로 중앙 정렬 */
}

/* 버튼 컨테이너 스타일링 */
.button-container {
  display: flex;
  padding-top: 2rem;
  justify-content: end;
  /* 버튼들을 오른쪽 끝으로 정렬 */
}

.img-container {
  height: 80vh; /* 이미지 컨테이너의 높이 설정 */
  overflow: hidden; /* 비율이 맞지 않아 이미지가 컨테이너보다 클 경우 잘리도록 설정 */
}

.img-fit {
  height: 100%; /* 이미지를 컨테이너의 높이에 맞춤 */
  width: auto; /* 너비는 자동으로 설정되어 비율 유지 */
  object-fit: cover; /* 이미지가 컨테이너를 가득 채우도록 하며, 비율이 맞지 않을 경우 잘림 */
  object-position: center; /* 이미지가 컨테이너 중앙에 위치하도록 설정 */
}
.row {
  display: flex;
  align-items: flex-start; /* 컨텐츠를 상단에서 정렬 */
}
.contents {
  display: flex;
  flex-direction: column;
  width: 100vw;
  
}
.img-container {
  height: 80vh; /* 이미지 컨테이너의 높이 설정 */
  width: 60vh; /* 이미지 컨테이너의 너비 설정 (필요한 경우 조정) */
  overflow: hidden;
}

.img-fit {
  height: 100%;
  object-fit: cover;
  object-position: center;
}

/* 사용자 정의 CSS */

</style>
