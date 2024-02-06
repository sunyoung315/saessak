<template>
	<div>
		<p>선생님 메인 페이지</p>
		<p class="text-4xl">새싹반</p>
		<div class="container p-6 flex flex-col border">
			<div class="flex flex-row">
				<div class="basis-2/3 m-8 bg-slate-100">
					<div class="flex flex-row items-center p-8">
						<img src="@/assets/noteIcon.png" alt="메모" />
						<span class="ml-4 text-2xl font-bold">오늘의 할 일</span>
					</div>
					<div class="ml-8" v-for="index in 5" :key="index">
						<input class="m-2" id="memo" type="checkbox" />
						<label for="memo">오늘의 할 일 리스트</label>
					</div>
				</div>
				<div class="basis-1/3 m-8 bg-slate-200">
					<div class="flex flex-row items-center p-8">
						<p class="mr-4 text-xl font-bold">2월 1일 생일자</p>
						<!-- <img src="@/assets/happyBirthday.png" alt="생일" /> -->
					</div>
					<div>
						<div class="flex flex-row justify-center items-center p-8">
							<!-- <img src="@/assets/image1.png" alt="생일" class="birthday" /> -->
							<p class="ml-4 text-xl font-bold">박나은</p>
						</div>
						<div class="flex flex-row justify-center items-center p-8">
							<!-- <img src="@/assets/image1.png" alt="생일" class="birthday" /> -->
							<p class="ml-4 text-xl font-bold">박나은</p>
						</div>
					</div>
				</div>
			</div>
			<div class="bg-slate-200 m-8">
				식단표
				<p>구분</p>
				<p>음식 사진</p>
			</div>
		</div>
	</div>
</template>

<script setup></script>

<style scoped>
.birthday {
	width: 100px;
	height: 50px;
}
</style>
