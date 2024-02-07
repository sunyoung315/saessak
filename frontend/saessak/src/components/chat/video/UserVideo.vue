<template>
    <div v-if="streamManager">
      <ov-video :stream-manager="streamManager"/>
      <!-- {{ clientData }} : 현재 userName -->
      <!-- <div><p>{{ clientData }}</p></div> -->
    </div>
  </template>
  
  <script>
    export default {
      name: 'UserVideo',
    }
  </script>
  
  <script setup>
    import { computed } from 'vue';
    import OvVideo from './OvVideo.vue';
  
  
    const props = defineProps({
      streamManager: Object,
    })

    // clientData는 computed로 진행됨
    const clientData = computed(() => {
      const { clientData } = getConnectionData();
      return clientData;
    });
  
    function getConnectionData() {
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    }
  </script>