import axios from "axios";
import Cookies from "universal-cookie";
import {requestRefreshToken, saveToken} from "./api.js";

const jwtAxios = axios.create();

const cookies = new Cookies(null,{path:'/',maxAge:2592000});


const beforeRequest = (config) =>{
    console.log('before')
    const accessToken = cookies.get("accessToken");
    if(!accessToken)
    {
        throw Error("Not Token");
    }
    config.headers["Authorization"] = "Bearer "+accessToken;
    return config;
}
const beforeResponse = (response)=>{
    console.log('errorResponse');
    console.log(response);
return response;
}


const errorResponse = (error)=>{
    const status = error.response.status;
    const res = err.response.data;
    const errMsg  = res.error;


    const refreshFn = async ()=>{
        console.log("Refresh Token");
        const data = await requestRefreshToken();
        saveToken("accessToken",data.accessToken);
        saveToken("refreshToken",data.refreshToken);

        error.config.headers['Authorization'] = "Bearer "+ data.accessToken;


        return  await axios(error.config)
    }

    if(errMsg.indexOf("expired") > 0){
        return refreshFn();
    }else {
        return Promise.reject(error);
    }
}




jwtAxios.interceptors.request.use(beforeRequest);
jwtAxios.interceptors.response.use(beforeResponse,errorResponse)

export default jwtAxios;