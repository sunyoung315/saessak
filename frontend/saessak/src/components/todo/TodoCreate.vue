<template>
    <div class="flex mt-4">
        <input class="shadow appearance-none border rounded w-full py-2 px-3 mr-4 text-grey-darker" placeholder="Add Todo"
            v-model="content" type="text">
        <button class="flex-no-shrink p-2 border-2 rounded text-teal border-teal hover:text-white hover:bg-teal"
            @click="createTodo">Add</button>
    </div>
</template>


<script setup>
import { postTodo, getTodoList } from '@/api/todo';
import { ref } from 'vue'
const content = ref("")
const emits = defineEmits(["createTodo"])
const createTodo = async () => {
    // console.log(content.value)
    postTodo(content.value, (response) => {
        emits("createTodo")
        content.value = ''
    },
        (error) => {
            console.log(error)
        })

}

const username = ref("")




</script>

<script scope>

</script>