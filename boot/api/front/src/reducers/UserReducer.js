import {AUTH_USER, LOGIN_USER, VERIFIED_USER} from "../actions/Types";
export default function UserReducer(state = {}, action) {
    switch (action.type) {
        case LOGIN_USER:
            return {...state, loginSuccess: action.payload};
        case VERIFIED_USER:
            return {...state, verifiedSuccess: action.payload};
        case AUTH_USER:
            return {...state, isLogin: action.payload};
        default:
            return state;
    }
}
