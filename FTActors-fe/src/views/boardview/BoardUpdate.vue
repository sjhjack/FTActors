<template>
  <div class="boardheader">
    <h1><b>BOARD Update</b></h1>
  </div>
  <div class="boardpage">
    <div class="boardlist">
      <ul class="list-group list-group-flush">
        <li class="list-group-item" id="update-form">
          <label><b>공고명</b></label>
          <input v-model="editedRecruitment.title" type="text" class="form-control">
        </li>
        <li class="list-group-item" id="update-form">
          <label><b>공고분류</b></label>
          <select id="category" v-model="editedRecruitment.category" class="form-control">
            <option value="">카테고리를 선택하세요</option>
            <option value="장편영화">장편영화</option>
            <option value="단편영화">단편영화</option>
            <option value="뮤비/CF">웹드라마</option>
            <option value="뮤비/CF">뮤비/CF</option>
            <option value="뮤비/CF">유튜브/기타</option>
          </select>
        </li>
        <li class="list-group-item" id="update-form">
          <label><b>담당자  {{editedRecruitment.postMemberName }}</b></label>
        </li>
        <li class="list-group-item" id="update-form">
          <label><b>지원시작일자</b></label>
          <input v-model="editedRecruitment.startDate" type="date" class="form-control">
        </li>
        <li class="list-group-item" id="update-form">
          <label><b>지원마감일자</b></label>
          <input v-model="editedRecruitment.endDate" type="date" class="form-control">
        </li>

        <li class="list-group-item" id="update-form">
          <label for="image"><b>이미지</b></label>
          <input type="file" id="image" @change="onImageChange" class="input-field" />
          <div v-if="selectedImage">
            <span @click="clearSelectedImage"> X</span>
          </div>
          <img :src="selectedImage" v-if="selectedImage" width="300px" height="200px">
        </li>

        <li class="list-group-item" id="update-form">
          <label for="file"><b>파일</b></label>
          <input type="file" id="script" class="input-field" @change="onScriptChange">
          <div v-if="selectedFile">
            <span @click="clearSelectedFile"> X</span>
          </div>
        </li>
      </ul>
      <button @click="updateRecruitment" class="btn btn-dark">수정</button>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { recruitmentApi } from '@/util/axios';
import { useMemberStore } from "@/stores/member-store.js";

const MemberStore = useMemberStore();
const loginMember = ref(null);
loginMember.value = MemberStore.memberInfo;

const router = useRouter();
const editedRecruitment = ref({});

const selectedImage = ref(null);
const selectedFile = ref(null);

let image = null;
let script = null;

let imageReader = new FileReader();
let fileReader = new FileReader(); // FileReader 변수를 함수 외부에서 정의

const onImageChange = (e) => {
  const file = e.target.files[0];
  if (file != null) {
    imageReader.onload = () => {
      selectedImage.value = imageReader.result;
    };
    imageReader.readAsDataURL(file);
    image = file;
    console.log(imageReader)
  }
  else {
    image = null;
    imageReader.onload = null; // reader 초기화
    imageReader = new FileReader(); // 새로운 FileReader 객체 생성
    selectedImage.value = null; // 이미지 데이터 초기화
  }
};
const clearSelectedImage = () => {
  selectedImage.value = null
  const input = document.getElementById('image');
  input.value = ''; // input 요소의 값을 초기화하여 파일 이름을 지움
  image = null;
};

const clearSelectedFile = () => {
  selectedFile.value = null
  const input = document.getElementById('script');
  input.value = '';
  script = null;
};

const onScriptChange = (e) => {
  const file = e.target.files[0];
  if (file != null) {
    fileReader.onload = () => {
      selectedFile.value = fileReader.result;
    };
    fileReader.readAsDataURL(file);
    script = file;
  }
  else {
    script = null;
    fileReader.onload = null; // reader 초기화
    fileReader = new FileReader(); // 새로운 FileReader 객체 생성
    selectedFile.value = null; // 이미지 데이터 초기화
  }
};


const fetchRecruitmentDetail = async () => {
  try {
    const recruitmentId = history.state.id;
    const response = await recruitmentApi.getDetail(recruitmentId, loginMember.value);
    editedRecruitment.value = response.data.data;
    // 초기 이미지 설정
    if (response.data.data.image != null) {
      selectedImage.value = response.data.data.image;
    }
    if (response.data.data.file != null) {
      selectedFile.value = response.data.data.file;
    }
    console.log(response.data.data)
  } catch (error) {
    console.error('Error fetching recruitment detail:', error);
  }
};

onMounted(fetchRecruitmentDetail);

const updateRecruitment = async () => {
  let formData = new FormData();
  if (image != null) {
    formData.append("image", image);
  };

  formData.append("id", editedRecruitment.value.id);
  formData.append("title", editedRecruitment.value.title);
  formData.append("content", editedRecruitment.value.content);
  formData.append("postMemberId", loginMember.value);
  formData.append("category", editedRecruitment.value.category);
  formData.append("startDate", editedRecruitment.value.startDate);
  formData.append("endDate", editedRecruitment.value.endDate);
  formData.append("memberId", loginMember.value);

  try {
    console.log(formData)
    const response = await recruitmentApi.update(formData);
    if (response.status === 200) {
      alert("변경 성공");
    } else {
      // 등록 실패 시 처리
      alert("변경 실패");
    }
    router.push({ name: 'board' });
  } catch (error) {
    console.error("Error registering recruitment:", error);
    // 오류 처리
  }
};

</script>

<style>
.boardheader {
  display: flex;
  justify-content: center;
}

h1 {
  font-size: 4rem;

}

.boardpage {
  display: flex;
  justify-content: center;
}

.boardlist {
  width: 30rem;
}

.imageFile {
  display: flex;
  flex-direction: column;
}
label {
  width: 500px;

}
#update-form {
  display: flex;
  flex-direction: row;
}
</style>
