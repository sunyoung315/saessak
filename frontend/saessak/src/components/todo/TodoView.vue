<template>
    <div>todoView</div>
    <TodoCreate />
    <div v-for="todo in todoList" :key="todo.todoId">
        <TodoDetail v-bind:todo=todo />

    </div>
</template>

<script setup>
import { BaseTransitionPropsValidators, onMounted, ref, watch } from 'vue'
import { getTodoList } from '@/api/todo'
import TodoDetail from '@/components/todo/TodoDetail.vue'
import TodoCreate from '@/components/todo/TodoCreate.vue'

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
