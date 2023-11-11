import React, {useState} from 'react';
import {Button, Form} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {fetchApi} from "../../components/FetchApi";
const SaveForm = () => {
    const navigate = useNavigate();

    const [post, setPost] = useState({
        title: "",
        content: ""
    });

    const changeValue = (e) => {
        setPost({
            ...post,
            [e.target.name]: e.target.value
        })
    };

    const submitPost = (e) => {
        e.preventDefault();
        fetchApi("/posts", "POST", post, true)
        .then((res) => {
              if (res !== null) {
                  navigate("/")
              } else {
                  alert("게시글 등록에 실패했습니다.");
              }
          });
    };

    return (
        <Form onSubmit={submitPost}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Title</Form.Label>
                <Form.Control type="text" placeholder="Enter title" onChange={changeValue} name="title"/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Content</Form.Label>
                <Form.Control type="text" placeholder="Content" onChange={changeValue} name="content"/>
            </Form.Group>
            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
    );
};

export default SaveForm;