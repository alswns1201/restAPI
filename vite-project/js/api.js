import axios from "axios";
import Cookies from "universal-cookie";

export  const testApi = ()=>{
    console.log("test api");
}

const url ="http://localhost:8080/api/v1/";

export const makeToken = async (mid,mpw)=>{
    const path = url +"token/make"

    const data = {mid,mpw}

    const res = await axios.post(path,data);

    return res.data;
}

const cookies = new Cookies(null, {path:'/',maxAge :2592000});

export const saveToken =(tokenName,tokenValue)=>{
    cookies.set(tokenName,tokenValue);
};