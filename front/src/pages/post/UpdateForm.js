import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import {fetchApi} from "../../components/FetchApi";

const UpdateForm = () => {

    const {id} = useParams();

    const navigate = useNavigate();

    const [post, setPost] = useState({
        title: "",
        content: ""
    });

    useEffect(() => {
        fetchApi("/posts/" + id, "GET")
        .then(res => {
            setPost(res.data);
        });
    }, [post])

    const changeValue = (e) => {
        setPost({
            ...post,
            [e.target.name]: e.target.value
        })
    };

    const submitPost = (e) => {
        e.preventDefault();
        fetchApi("/posts/" + id, "PATCH", post, true)
        .then((res) => {
            if (res.result === "SUCCESS") {
                navigate("/")
            } else {
                alert(res.error.data);
            }
        });
    };

    return (
        <Form onSubmit={submitPost}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Title</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="Enter title"
                    onChange={changeValue}
                    name="title"
                    value={post.title}
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Content</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="Content"
                    onChange={changeValue}
                    name="content"
                    value={post.content}
                />
            </Form.Group>
            <Button variant="primary" type="submit">
                수정하기
            </Button>
        </Form>
    );
};

export default UpdateForm;