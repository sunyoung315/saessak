<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Header from "@/components/layout/Header.vue";
import HelloWorld from './components/HelloWorld.vue'
import ChatListView from './views/ChatListView.vue';
import ChatView from './views/ChatView.vue';
</script>

<template>
    <router-view></router-view>
</template>

<style scoped>

</style>
