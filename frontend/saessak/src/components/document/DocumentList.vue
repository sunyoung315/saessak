<template>
  <div>
    <div class="inline-flex rounded-md shadow-sm ml-6 mt-5">
      <button @click="replaceBtn()"
        class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-s-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy">
        귀가동의서
      </button>
      <button @click="foodBtn()"
        class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-e-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy">
        식품 알레르기 조사서
      </button>
    </div>
    <!-- Teacher Version : 귀가 동의서 -->
    <div v-if="isTeacher === true">
      <div v-if="isReplace === true">
        <table class="table-auto">
          <thead>
            <tr>
              <th>분류</th>
              <th>대상 원아</th>
              <th>작성일자</th>
              <th>확인여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="kid in kidReplaceList" :key="kid.replacementId">
              <td>귀가 동의서</td>
              <td><router-link :to="`/document/replacement/${kid.replacementId}`">{{ kid.kidName }}</router-link></td>
              <td>{{ kid.replacementDay }}</td>
              <td>{{ kid.replacementCheck ? '확인 완료' : '미확인' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- Teacher Version : 알레르기 동의서 -->
      <div v-else>
        <table class="table-auto">
          <thead>
            <tr>
              <th>분류</th>
              <th>대상 원아</th>
              <th>작성일자</th>
              <th>확인여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="kid in kidAllgeryList" :key="kid.kidId">
              <td>식품 알레르기 조사서</td>
              <td><router-link :to="`/document/allgery/${kid.kidId}`">{{ kid.kidName }}</router-link></td>
              <td>{{ kid.kidAllergyDate }}</td>
              <td>{{ kid.kidAlleryCheck ? '확인 완료' : '미확인' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- Parents Version : 귀가 동의서 -->
    <div v-else>
      <div v-if="isReplace === true">
        <table class="table-auto">
          <thead>
            <tr>
              <th>분류</th>
              <th>대상 원아</th>
              <th>작성일자</th>
              <th>확인여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="kid in kidReplaceList" :key="kid.replacementId">
              <td>귀가 동의서</td>
              <td><router-link :to="`/document/replacement/${kid.replacementId}`">{{ kid.kidName }}</router-link></td>
              <td>{{ kid.replacementDay }}</td>
              <td>{{ kid.replacementCheck ? '확인 완료' : '미확인' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- Parents Version : 알레르기 동의서 -->
      <div v-else>
        <table class="table-auto">
          <thead>
            <tr>
              <th>분류</th>
              <th>대상 원아</th>
              <th>작성일자</th>
              <th>확인여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="kid in kidAllgeryList" :key="kid.kidId">
              <td>식품 알레르기 조사서</td>
              <td><router-link :to="{ name: 'DocumentAllgeryDetail', params: { id: `${kid.kidId}` } }">{{ kid.kidName
              }}</router-link></td>
              <td>{{ kid.kidAllergyDate }}</td>
              <td>{{ kid.kidAlleryCheck ? '확인 완료' : '미확인' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
const isTeacher = ref(true);

// 동의서 버튼 동작
const isReplace = ref(true);
function replaceBtn() {
  isReplace.value = true;
}
function foodBtn() {
  isReplace.value = false;
}
const goDetail = () => {
  router.push({ name: 'BoardCreate' });
};
// 버튼 끝

// 더미 데이터
const kidReplaceList = ref([
  {
    replacementId: 1,
    replacementDay: "2024-01-12",
    kidName: "김샛별",
    replacementCheck: true,
  },
  {
    replacementId: 2,
    replacementDay: "2024-01-12",
    kidName: "김햇님",
    replacementCheck: false,
  }
]);

const kidAllgeryList = ref([
  {
    kidId: 1,
    kidName: "김샛별",
    kidAllergyDate: "2024-01-14",
    kidAlleryCheck: false,
  },
  {
    kidId: 2,
    kidName: "김햇님",
    kidAllergyDate: "2024-01-24",
    kidAlleryCheck: false,
  }
]);
// 더미 데이터


</script>

<style scoped></style>