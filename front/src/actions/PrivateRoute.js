import {Navigate, Outlet} from "react-router-dom";

const isLogin = !!localStorage.getItem('token');

const PrivateRoute = () => {
    console.log("isLogin: " , isLogin);
    console.log("localStorage.getItem('token'): " , localStorage.getItem('token'));
    return isLogin ? <Outlet /> : <Navigate to={"/loginForm"} replace={true} />
};

export default PrivateRoute;