import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import {useState} from "react";
const Logout = () => {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const logout = () => {
        localStorage.removeItem("token")
        setShow(false)
        window.location.replace("/")
    }

    return (
        <>
            <Button className="nav-link" variant="primary" onClick={handleShow}>로그아웃</Button>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>로그아웃 하기</Modal.Title>
                </Modal.Header>
                <Modal.Body>로그아웃 하시겠습니까?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        창 닫기
                    </Button>
                    <Button variant="primary" onClick={logout}>
                        확인
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default Logout;