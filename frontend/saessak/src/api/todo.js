import axios from "axios";
import { localAxios } from "./http";
const local = localAxios();

const url = "/todo";

const config = {
    headers : {
        "Authorization" : "Bearer " + sessionStorage.getItem("accessToken")
    }
}



// todoList 전부 가져오기
async function getTodoList(success, fail){
    local.get(`${url}`, config).then(success).catch(fail)
}
async function postTodo(content ,success, fail){
    local.post(`${url}`,{content},config).then(success).catch(fail)
}
async function deleteTodo(todoId, success, fail){
    local.delete(`${url}/${todoId}`,config).then(success).catch(fail)
}
async function toggleTodo(todoId, success, fail){
    local.patch(`${url}/${todoId}`,null, config).then(success).catch(fail)
}

export {
    getTodoList,
    postTodo,
    deleteTodo,
    toggleTodo,
};