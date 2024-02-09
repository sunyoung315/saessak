import { kakaoLogin } from "@/api/oauth";
import { defineStore } from "pinia";
import { ref, computed } from 'vue';
import createPersistedState from 'pinia-plugin-persistedstate';

export const loginStore = defineStore("loginStore", () => {
    const isLogin = ref(false);
    const isAlarm = ref(false)
    const isTeacher = ref(false)
    const teacherName = ref("")
    const kidList = ref([])
    const curKid = ref(-1)
    const userId = ref(0)
    const profile = ref("")

    function setlogin(){
        isLogin.value = true;
    }

    function setlogout(){
        isLogin.value = false;
    }

    function setAlarmFlag(input) {
        isAlarm.value = input;
    }

    function setTeacherFlag(input){
        isTeacher.value = input;
    }

    function setTeachername(name){
        teacherName.value = name;
    }

    function setKidlist(list){
        kidList.value = list;
    }

    function setUserid(id){
        userId.value = id;
    }

    function setCurkid(kid){
        curKid.value = kid;
    }

    function setProfile(url){
        profile.value = url;
    }

    return {isLogin, isAlarm, isTeacher, teacherName, kidList, curKid, userId, profile,
        setCurkid, setKidlist, setAlarmFlag, setTeacherFlag, setTeachername, setUserid, setlogin, setlogout, setProfile};
}, {persist : true,
    strategies: [{ storage: localStorage }],
});