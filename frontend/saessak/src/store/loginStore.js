import { kakaoLogin } from "@/api/oauth";
import { defineStore } from "pinia";
import { ref, computed } from 'vue';

export const loginStore = defineStore("loginStore", () => {
    const isLogin = ref(false);
    const isTeacher = ref(false)
    const teacherName = ref("")
    const kidList = ref([])
    const curKid = ref(-1)
    const userId = ref(0)

    function setlogin(){
        isLogin.value = true;
    }

    function setlogout(){
        isLogin.value = false;
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

    return {isLogin, isTeacher, teacherName, kidList, curKid, userId,
        setCurkid, setKidlist, setTeacherFlag, setTeachername, setUserid, setlogin, setlogout};
    // state: () => ({
    //     isLogin : false,
    //     isTeacher : false,
    //     teacherName : "",
    //     kidList : [],
    //     curKid : -1, // 학부모의 현재 아이 버전
    //     userId : 0, // 회원가입할때 joinview로 갖고 가기 위한 값
    // }),
    // actions: {
    //     setLogin() {
    //       this.isLogin = true;
    //       console.log("setLogin" + isLogin);
    //     },
    // },
}, {persist : true})