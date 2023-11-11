import {useEffect, useState} from "react";
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Link} from "react-router-dom";
import Logout from "../pages/user/Logout";

const Header = () => {
    const [isLogin, setIsLogin] = useState(false);

    useEffect(() => {
        setIsLogin(localStorage.getItem("token") !== null);
    }, [])

    return (
        <>
            <Navbar bg="dark" data-bs-theme="dark">
                <Container>
                    <Link to="/" className={"navbar-brand"}>홈</Link>
                    <Nav className="me-auto">
                        {!isLogin && <Link to="/join" className="nav-link">회원가입</Link>}
                        {!isLogin && <Link to="/login" className="nav-link">로그인</Link>}
                        {isLogin  && <Link to="/save" className="nav-link">글 쓰기</Link>}
                        {isLogin && <Link to="/" className="nav-link">글 목록</Link>}
                        {isLogin && <Logout />}
                    </Nav>
                </Container>
            </Navbar>
        </>
    );
};

export default Header;