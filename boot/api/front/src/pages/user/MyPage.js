import React, {useEffect, useState} from 'react';
import {Button, Stack} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {fetchApi} from "../../components/FetchApi";

const MyPage = () => {
    const navigate = useNavigate();

    const [member, setMember] = useState({
        id: "",
        name: "",
        email: "",
        level: "",
    });

    const fetchPosts = () => {
        fetchApi(`/auth/myPage`, "GET", null, true)
            .then(res => {
                setMember(res.data);
            });
    }

    // 함수 실행시 최초 한번 실행
    useEffect(() => {
        fetchPosts();
    }, [])

    const historyBack = () => {
        navigate(-1);
    }

    return (
        <div>
            <h1>MyPage</h1>
            <Stack gap={3}>
                <div className="p-2"> Name : {member.name}</div>
                <div className="p-2"> Email : {member.email}</div>
                <div className="p-2"> Level : {member.level}</div>
            </Stack>

            <Button variant="primary" type="button" onClick={historyBack}>
                뒤로가기
            </Button>
        </div>
    );
};

export default MyPage;