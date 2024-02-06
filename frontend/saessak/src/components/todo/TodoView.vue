<template>
    <div>todoView</div>
    <TodoCreate />
    <div v-for="todo in todoList" :key="todo.todoId">
        <TodoDetail v-bind:todo=todo />

    </div>
    <button @click="downloadPhoto">사진 다운로드</button>
</template>

<script setup>
import { BaseTransitionPropsValidators, onMounted, ref, watch } from 'vue'
import { getTodoList } from '@/api/todo'
import TodoDetail from '@/components/todo/TodoDetail.vue'
import TodoCreate from '@/components/todo/TodoCreate.vue'
import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3'
// import { Readable } from 'stream';

const s3Client = new S3Client({
    region: 'ap-northeast-2', // AWS 리전 (예: us-east-1)
    credentials: {
        accessKeyId: 'AKIAUZRXT2PHA3NJOO76',
        secretAccessKey: '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',
    },
});

const bucketName = 'saessack-photo-album'
const key = 'class/37ac2e50-b91e-4bbe-af34-2b94dc23ae71..png'


const downloadPhoto = async () => {
    try {
        // S3 객체 다운로드 명령 생성
        const getObjectCommand = new GetObjectCommand({
            Bucket: bucketName,
            Key: key,
        });

        // S3 객체 다운로드
        const { Body, ContentType } = await s3Client.send(getObjectCommand);
        console.log(ContentType)

        const blob = new Blob([Body], { type: ContentType })

        const downloadLink = document.createElement('a')
        downloadLink.href = URL.createObjectURL(blob)
        downloadLink.download = 'replaced.' + ContentType.split('/')[1]
        downloadLink.click()
        downloadLink.remove()
        URL.revokeObjectURL(downloadLink.href);

        // 다운로드 받은 데이터를 Buffer로 읽어온다
        // const dataBuffer = await Readable.from(Body).toBuffer();
        // console.log(Body)
        // 여기에서 dataBuffer를 원하는 방식으로 처리하면 됩니다.

        console.log(`S3 객체 다운로드 성공: ${key}`);
    } catch (error) {
        console.error('S3 객체 다운로드 실패:', error);
    }
};
onMounted(() => {
    getTodoList((response) => {
        console.log(response)
        todoList.value = response.data.data
        // console.log("success")
    },
        (error) => {
            console.log("fail")
        })

})

const todoList = ref([])


</script>

<style scoped></style>
