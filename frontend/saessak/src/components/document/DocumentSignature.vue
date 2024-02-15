<template>
	<div>
		<canvas
			ref="canvas"
			width="400"
			height="200"
			style="border: 1px solid #000"
			@mousedown="startDrawing"
			@mousemove="draw"
			@mouseup="stopDrawing"
			@mouseout="stopDrawing"
		></canvas>
		<div class="flex justify-end mt-1 mr-1">
			<button class="mr-8 text-black text-lg" @click="clearCanvas">
				다시 쓰기
			</button>
			<button @click="saveSignature" class="text-black text-lg">
				서명 저장
			</button>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 전자서명
const canvas = ref(null);
let ctx = null;
let drawing = ref(false);
let lastX = 0;
let lastY = 0;

onMounted(() => {
	ctx = canvas.value.getContext('2d');
});

const emit = defineEmits(['signature-saved']);

const startDrawing = e => {
	drawing.value = true;
	lastX = e.clientX - canvas.value.getBoundingClientRect().left;
	lastY = e.clientY - canvas.value.getBoundingClientRect().top;
};

const draw = e => {
	if (!drawing.value) return;
	ctx.beginPath();
	ctx.moveTo(lastX, lastY);
	ctx.lineTo(
		e.clientX - canvas.value.getBoundingClientRect().left,
		e.clientY - canvas.value.getBoundingClientRect().top,
	);
	ctx.stroke();
	lastX = e.clientX - canvas.value.getBoundingClientRect().left;
	lastY = e.clientY - canvas.value.getBoundingClientRect().top;
};

const stopDrawing = () => {
	if (!drawing.value) return;
	drawing.value = false;
};

const dataURItoBlob = dataURI => {
	var binary = atob(dataURI.split(',')[1]);
	var array = [];
	for (var i = 0; i < binary.length; i++) {
		array.push(binary.charCodeAt(i));
	}
	return new Blob([new Uint8Array(array)], { type: 'image/png' });
};

const saveSignature = () => {
	const dataUrl = canvas.value.toDataURL();
	// 서명 데이터 이벤트 전달
	// emit('signature-saved', dataUrl);
	// console.log(dataUrl);
	const blobData = dataURItoBlob(dataUrl);
	// console.log(blobData);
	emit('signature-saved', blobData);

	// File Download
	const image = canvas.value.toDataURL('image/png');
	// const link = document.createElement('a');
	// link.href = image;
	// link.download = 'signature';
	// link.click();

	// const svgData =
	// 	'<svg xmlns="http://www.w3.org/2000/svg" width="500" height="200">' +
	// 	'<path d="' +
	// 	canvas.value.toDataURL('image/svg+xml') +
	// 	'" stroke="#000" stroke-width="2" fill="none"/>' +
	// 	'</svg>';
	// console.log(svgData);
};
const clearCanvas = () => {
	ctx.clearRect(0, 0, canvas.value.width, canvas.value.height);
};
</script>
