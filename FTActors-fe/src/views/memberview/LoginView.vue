<template>
  <div class="container h-100">
    <div class="row justify-content-center align-items-start" style="min-height: 100vh;">
      <div class="col-md-6 col-lg-4">
        <div class="mt-5 pt-5">
          <div class="text-center mb-4">
            <h1><b>로그인</b></h1>
          </div>
          <form>
            <div class="form-group mb-3">
              <label for="id">아이디</label>
              <input type="text" id="id" v-model="id" class="form-control" placeholder="아이디를 입력해주세요">
            </div>
            <div class="form-group mb-3">
              <label for="password">비밀번호</label>
              <input :type="showPassword ? 'text' : 'password'" id="password" v-model="password" class="form-control" placeholder="비밀번호를 입력해주세요">
            </div>
            <button type="button" class="btn btn-dark w-100 mb-2" @click="login">로그인</button>
            <button type="button" class="btn btn-secondary w-100" @click="goToJoin">아직 회원이 아니라면?</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { memberApi } from '@/util/axios';
import { useMemberStore, useJwtStore } from '@/stores/member-store';

const id = ref('');
const password = ref('');
const showPassword = ref(false);

const router = useRouter();
const memberStore = useMemberStore();
const jwtStore = useJwtStore();

const login = async () => {
  try {
    const response = await memberApi.login({ loginId: id.value,
      password: password.value});
    if(response.status === 200 || response.data.member != null){
      const responseData = response.data.data;
      // 로그인 성공 시 사용자 정보와 JWT 토큰을 저장
      console.log(responseData)
      const responseId = responseData.id;
      const responseJWT = responseData.accessToken;
      memberStore.setUser(responseId);
      jwtStore.setToken(responseJWT);
    }
    alert('로그인에 성공하였습니다')
        router.push("/");
    } catch (error) {
      alert('로그인에 실패하였습니다')
      router.push("/");
    }};

const goToJoin = () => {
  router.push({ name: 'join' });
};
</script>


<style scoped>
.profileheader h1 {
  font-size: 2.5rem;
}
</style>


