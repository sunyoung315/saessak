<template>
    <div class="flex mb-4 items-center">
        <p class="w-full" :class="todo.completed ? 'line-through text-green' : 'text-grey-darkest'">{{ todo.content }}</p>
        <button
            class="flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green"
            @click="toggle">check</button>
        <button class="flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red"
            @click="delTodo">Remove</button>
    </div>
</template>


<script setup>
import { deleteTodo, toggleTodo, } from '@/api/todo';
import axios from 'axios';
import { watch } from 'vue'
const config = {
    headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("accessToken")
    }
}
const props = defineProps(["todo"])
const emits = defineEmits(["updatedTodo"])
const todo = props.todo

const delTodo = () => {
    console.log("delete", todo.todoId)
    deleteTodo(todo.todoId, (response) => {
        // console.log(response)
        emits("updatedTodo")
    },
        (error) => {
            // console.log(error)
        })
}

const toggle = () => {

    toggleTodo(todo.todoId, (response) => {
        todo.completed = !todo.completed
    },
        (error) => {
            // console.log(error)
        })
}



</script>

<script scope>

</script>