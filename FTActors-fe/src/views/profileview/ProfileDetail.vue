

<template>
  <div class="container my-5">
    <div class="profileheader text-center mb-4">
      <h1><b>PROFILE</b></h1>
    </div>
    <div class="contents d-flex flex-row justify-content-evenly">
      <div class="col-md-6 img-container">
        <img :src="profile.imageLink" alt="" class="img-fluid">
      </div>
      <div class="col-md-6">
        <div class="profilelist">
          <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <button type="button" class="btn btn-dark" id="updatebtn" @click="goToProfileUpdate(profile.id)"
                  v-if="isOwnProfile(profile.memberId)">
                  수정하기
                </button>
                <button type="button" class="btn btn-danger" id="deletebtn" @click="confirmDelete"
                  v-if="isOwnProfile(profile.memberId)">
                  삭제하기
                </button>
                <button type="button" class="btn btn-dark" id="followbtn"
                  @click="changeFollow(loginMember.value, profile.memberId)">
                  <p v-if="following.follow === 1">팔로잉 삭제</p>
                  <p v-else>팔로잉</p>
                </button>
            </li>
            <li class="list-group-item"><label><b>이름</b></label> {{ profile.name }}
            </li>
            <li class="list-group-item"><label><b>예명</b></label> {{ profile.stageName }} </li>
            <li class="list-group-item"><label><b>직업</b></label> <span v-if="profile.type === 'A'">배우</span>
              <span v-else>감독</span>
            </li>
            <li class="list-group-item"><label><b>생일</b></label>{{ profile.birth }} </li>
            <li class="list-group-item"><label><b>자기소개</b></label> {{ profile.content }} </li>
            <li class="list-group-item"><label><b>포트폴리오 링크</b></label>{{ profile.portfolio }} </li>
            <li class="list-group-item"><label><b>생성 날짜</b></label>{{ formatDate(profile.createdTime) }} </li>
          </ul>
        </div>
      </div>

    </div>
  </div>
  
</template>



<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'
import ReportModal from '@/components/modals/ReportModal.vue';
import { useMemberStore } from "@/stores/member-store.js";
import { profileApi, followApi } from '@/util/axios';

const MemberStore = useMemberStore();
const loginMember = ref(null);
loginMember.value = MemberStore.memberInfo;


const router = useRouter();
const profile = ref({});
const following = ref(0);


const formatDate = (dateStr) => {
      const date = new Date(dateStr);
      const year = date.getFullYear().toString().substr(-2); 
      const month = ('0' + (date.getMonth() + 1)).slice(-2); 
      const day = ('0' + date.getDate()).slice(-2); 
      return `${year} / ${month} / ${day}`;
}
const profileDetail = async () => {
  const profileId = router.currentRoute.value.params.id; // 현재 라우트의 파라미터 사용
    let response = await profileApi.getDetailProfile(profileId);
    profile.value = response.data.data;
    if(loginMember.value == profile.value.memberId){
      return; 
    }
      response = await followApi.followDetail(loginMember.value, profileId);
      following.value = response.data.data
      console.log(following)
};

onMounted(profileDetail);

const goToProfileUpdate = (profileId) => {
  console.log(profileId)
  router.push({ name: 'profileUpdate', params: { id: profileId } });
};



const confirmDelete = () => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    deleteProfile();
  }
};

const deleteProfile = async () => {
  const profileId = profile.value.id;
  try {
    const response = await profileApi.removeProfile(profileId);
    if (response.status === 200) {
      alert("삭제되었습니다.");
    } else {
      alert("삭제 실패했습니다.");
    }
    router.push({ name: 'profile' });
  } catch (error) {
    console.error("Error deleting recruitment:", error);
  }
};
const isOwnProfile = (memberId) => {
  console.log(memberId)
  return memberId == loginMember.value;
};

const changeFollow = async (followerId) => {
  try {
      const response = await followApi.following(loginMember.value , followerId);
      if(response.data.status == 200){
        console.log(response)
        if(response.data.data == '팔로우 삭제 성공'){
          alert('팔로잉을 취소하였습니다.')
          following.value = 0;
        }
        else{
          alert('팔로잉을 하였습니다.')
          following.value = 1;
        }
      }
      else {
        alert('팔로잉을 하였습니다.')
        following.value = 1;
      }
    }
    catch (error) {
      console.error('Error toggling like:', error);
    }
};


</script>
<style scoped>
h1 {
  font-size: 4rem;
}

.profileheader {
  display: flex;
  align-items: center;
  font-size: 4rem;
}

.profiletab {
  height: 1rem;
  margin-left: auto;
}

.profiletab p {
  margin-bottom: 0;
}

.profiletab img {
  width: 24px;
  height: 24px;

}

.profilepage {
  display: flex;
  justify-content: center;
}

p{
  margin-bottom: 0;
}

.profilelist {
  width: 30rem;
}

.detailprofilepage {
  width: 50%;
}
</style>