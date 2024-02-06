<template>
    <div>
        <input v-model="content" type="text"
            class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-200">
        <button @click="createTodo">add</button>
    </div>
</template>


<script setup>
import { postTodo } from '@/api/todo';
import { ref } from 'vue'

const content = ref("")

const createTodo = () => {
    console.log(content.value)
    postTodo(content.value, (response) => {
        console.log(response)
    },
        (error) => {
            console.log(error)
        })
}

const username = ref("")




</script>

<script scope>

</script>