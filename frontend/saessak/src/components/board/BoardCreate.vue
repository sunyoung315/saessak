<template>
	<div
		class="container mx-16 mb-10 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
	>
		<div class="flex justify-end items-center mb-10">
			<button
				type="button"
				@click="registBoard()"
				class="mt-8 mr-6 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
			>
				등록
			</button>
			<button
				type="button"
				@click="goBoardList()"
				class="mt-8 mr-8 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
			>
				목록
			</button>
		</div>
		<div>
			<label class="block mt-2 mb-5">
				<span class="text-gray-700 ml-36 text-xl font-bold">내용</span>
				<textarea
					id="contents"
					class="block mt-1 ml-32 mb-10 w-9/12 rounded-md border border-neutral-300 shadow focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
					rows="6"
				></textarea>
			</label>
		</div>
		<span class="text-gray-700 ml-36 text-xl font-bold">건강기록</span>
		<div
			class="block ml-32 mb-0 mt-1 bg-white w-9/12 border border-neutral-300 rounded-lg shadow"
		>
			<div class="flex items-center">
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>체온 체크
				</span>
				<div class="inline-block relative flex items-center">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
						class="bg-gray-50 border-x-0 border-gray-300 h-10 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-20 py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
				<div class="pl-3 pr-6">°C</div>
			</div>
			<div class="flex items-center">
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>수면 시간
				</span>
				<div class="inline-block relative flex items-center">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
						class="bg-gray-50 border-x-0 border-gray-300 h-10 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-20 py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
				<div class="pl-3 pr-6">시간</div>
			</div>
			<div>
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>배변 상태</span
				>
				<div class="inline-flex rounded-md shadow-sm h-11" role="group">
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
					>
						보통
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
					>
						묽음
					</button>
					<button
						type="button"
						class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100"
					>
						딱딱함
					</button>
				</div>
			</div>
			<div class="flex items-center">
				<span class="inline-block text-gray-700 m-5 text-md font-extrabold"
					>키/몸무게</span
				>
				<div class="inline-block relative flex items-center">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
						class="bg-gray-50 border-x-0 border-gray-300 h-10 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-20 py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
				<div class="pl-3 pr-6">cm</div>
				<div class="inline-block relative flex items-center">
					<button
						type="button"
						id="decrement-button"
						data-input-counter-decrement="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
						class="bg-gray-50 border-x-0 border-gray-300 h-10 text-center text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block w-20 py-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
						placeholder="0"
					/>
					<button
						type="button"
						id="increment-button"
						data-input-counter-increment="quantity-input"
						class="bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:border-gray-600 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 dark:focus:ring-gray-700 focus:ring-2 focus:outline-none"
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
				<div class="pl-3 pr-6">kg</div>
			</div>
		</div>
		<br /><br />
	</div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

const goBoardList = () => {
	router.push({ name: 'BoardList' });
};
</script>

<style scoped></style>
