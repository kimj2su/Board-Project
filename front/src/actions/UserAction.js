import {AUTH_USER, LOGIN_USER, VERIFIED_USER} from "./Types";
import {fetchApi} from "../components/FetchApi";
export function loginUser(dataToSubmit) {
    const request = fetchApi("/auth/login", "POST", dataToSubmit);
    return {
        type: LOGIN_USER,
        payload: request
    }
}

export function authUser() {
    const isLogin = !!localStorage.getItem("token");
    return {
        type: AUTH_USER,
        payload: isLogin
    }
}

export function verifiedUser(postId) {
    const request = fetchApi("/posts/" + postId + "/validation", "POST", null, true);
    return {
        type: VERIFIED_USER,
        payload: request
    }
}
