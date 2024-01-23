<template>
	<div
		class="container mx-16 mb-10 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
	>
		<div class="flex justify-end items-center">
			<button
				type="button"
				@click="goBoardList()"
				class="mt-8 mr-8 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
			>
				목록
			</button>
		</div>
		<!-- datepicker -->
		<div class="block mb-5">
			<span class="text-gray-700 ml-36 text-xl font-bold">날짜</span>
			<div class="block mt-1 ml-32 mb-10">
				<VDatePicker
					v-model="date"
					:select-attribute="selectAttribute"
					:disabled-dates="disabledDates"
				>
					<template #default="{ inputValue, inputEvents }">
						<div class="relative max-w-sm">
							<div
								class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
							>
								<svg
									class="w-4 h-4 text-gray-900 dark:text-gray-400"
									aria-hidden="true"
									xmlns="http://www.w3.org/2000/svg"
									fill="currentColor"
									viewBox="0 0 20 20"
								>
									<path
										d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
									/>
								</svg>
							</div>
							<input
								:value="inputValue"
								v-on="inputEvents"
								class="border border-gray-300 text-gray-900 text-sm font-bold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5"
							/>
						</div>
					</template>
				</VDatePicker>
			</div>
		</div>
		<div>
			<label class="block mt-2 mb-5">
				<span class="text-gray-700 ml-36 text-xl font-bold">내용</span>
				<textarea
					id="contents"
					class="block mt-1 ml-32 mb-10 w-9/12 rounded-md border border-neutral-300 shadow focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
					rows="6"
					readonly
				></textarea>
			</label>
		</div>
		<span class="text-gray-700 ml-36 text-xl font-bold">건강기록</span>
		<div
			class="block ml-32 mb-0 mt-1 bg-white w-9/12 border border-neutral-300 rounded-lg shadow"
		>
			<div>
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>체온 체크</span
				>
				<div class="inline-flex rounded-md shadow-sm h-12" role="group">
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						정상
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						미열
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						고열
					</button>
				</div>
			</div>
			<div class="flex">
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>수면 시간
				</span>
				<div class="inline-block relative flex items-center max-w-[8rem]">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						id="quantity-input"
						data-input-counter
						aria-describedby="helper-text-explanation"
						class="bg-gray-50 border-x-0 border-gray-300 h-11 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-full py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
						readonly
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
							/>
						</svg>
					</button>
				</div>
			</div>
			<div>
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>배변 상태</span
				>
				<div class="inline-flex rounded-md shadow-sm h-12" role="group">
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						보통
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						묽음
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
						disabled
					>
						딱딱함
					</button>
				</div>
			</div>

			<div class="flex items-center">
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>키/몸무게</span
				>
				<div class="inline-block relative flex items-center max-w-[8rem]">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						id="quantity-input"
						data-input-counter
						aria-describedby="helper-text-explanation"
						class="bg-gray-50 border-x-0 border-gray-300 h-11 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-full py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
						readonly
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
							/>
						</svg>
					</button>
				</div>
				<span class="inline-block">&nbsp;&nbsp;cm&nbsp;&nbsp;</span>
				<div class="inline-block relative flex items-center max-w-[8rem]">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						id="quantity-input"
						data-input-counter
						aria-describedby="helper-text-explanation"
						class="bg-gray-50 border-x-0 border-gray-300 h-11 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-full py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
						readonly
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-11 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
					>
						<svg
							class="w-3 h-3 text-gray-900 dark:text-white"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
							/>
						</svg>
					</button>
				</div>
				<span class="inline-block">&nbsp;&nbsp;kg</span>
			</div>
		</div>
		<br /><br />
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const goBoardList = () => {
	router.push({ name: 'BoardList' });
};

const date = ref(new Date());

const selectAttribute = ref({ highlight: 'yellow' });

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);
const disabledDates = ref([
	{
		start: tomorrow,
		end: null,
	},
]);
</script>

<style scoped></style>
