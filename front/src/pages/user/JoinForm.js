import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import {fetchApi} from "../../components/FetchApi";

const JoinForm = () => {
    const navigate = useNavigate();

    const [user, setUser] = useState({
        email: "",
        name: "",
        password: "",
    });

    const changeValue = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        })
    };

    const submitPost = (e) => {
        e.preventDefault();
        fetchApi("/members", "POST", user)
        .then(res => {
            if (res.result === "SUCCESS") {
                navigate("/loginForm")
            } else {
                alert(res.error.message)
            }
        });
    };

    return (
        <Form onSubmit={submitPost}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="Enter email" onChange={changeValue} name="email"/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Name</Form.Label>
                <Form.Control type="text" placeholder="Enter name" onChange={changeValue} name="name"/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Enter password" onChange={changeValue} name="password"/>
            </Form.Group>
            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
    );
};

export default JoinForm;