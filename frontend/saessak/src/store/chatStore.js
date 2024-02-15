import { defineStore } from 'pinia'
import { ref } from 'vue'

export const chatStore = defineStore(
  'chatStore',
  () => {
    const chatName = ref('')
    const chatRoom = ref(-1)
    
    const userId = ref(-1)
    const isOpen = ref(false) // 채팅방에서 메뉴 버튼 클릭 여부 판단을 위한 flag

    function setChatname(chatname) {
      chatName.value = chatname
    }

    function setChatroom(chatroom) {
      chatRoom.value = chatroom
    }

    function setIsopen(flag) {
        isOpen.value = flag
    }

    return { chatName, chatRoom, isOpen, setChatname, setChatroom, setIsopen }
  },
  { persist: true, strategies: [{ storage: sessionStorage }] }
)
