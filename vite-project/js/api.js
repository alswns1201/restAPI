import axios from "axios";

export  const testApi = ()=>{
    console.log("test api");
}

const url ="http://localhost:8080/api/v1/";

export const makeToken =(mid,mpw)=>{
    const path = url +"token/make"

    const data = {mid,mpw}

    axios.post(path,data);
}