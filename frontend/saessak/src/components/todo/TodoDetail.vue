<template>
    <div>
        <div class="grid grid-cols-12 gap-2">
            <div class="col-span-1"></div>
            <div class="col-span-7">{{ todo.content }}</div>
            <div class="col-span-2"><button @click="toggle">{{ todo.completed }}</button></div>
            <div class="col-span-2"><button @click="delTodo">delete</button></div>

        </div>

    </div>
</template>


<script setup>
import { deleteTodo, toggleTodo, } from '@/api/todo';
import { watch } from 'vue'

const props = defineProps(["todo"])

const todo = props.todo

const delTodo = () => {
    console.log("delete", todo.todoId)
    deleteTodo(todo.Id, (response) => {
        console.log(response)
        emit("deleteSuccess")
    },
        (error) => {
            console.log(error)
        })
}

const toggle = () => {
    console.log("toggle", todo.todoId)
    toggleTodo()
}



</script>

<script scope>

</script>