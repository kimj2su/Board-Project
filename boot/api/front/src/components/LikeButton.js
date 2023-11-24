import React, { useState, useEffect } from 'react';
import {Button} from "react-bootstrap";
import {authUser} from "../actions/UserAction";
import {fetchApi} from "./FetchApi";
import {useDispatch} from "react-redux";
import {useParams} from "react-router-dom";

const LikeButton = () => {
    const {id} = useParams();
    const dispatch = useDispatch();
    const [isLogin, setIsLogin] = useState(false);
    const [isLiked, setIsLiked] = useState(false);

    useEffect(() => {
        setIsLogin(dispatch(authUser()).payload)
    }, [isLogin])

    useEffect(() => {
        fetchLikeStatus();
    }, [isLiked]);

    const fetchLikeStatus = () => {
        fetchApi("/likes/" + id, "GET", null, true)
            .then(res => {
                if (res.result === "SUCCESS") {
                    setIsLiked(res.data.isLike)
                } else {
                    setIsLiked(false)
                }
            })
    };

    const increaseLike = () => {
        if (isLogin) {
            fetchApi("/likes/" + id, "POST", null, true)
                .then(res => {
                    if (res.result === "SUCCESS") {
                        alert("좋아요가 추가되었습니다.")
                        setIsLiked(true)
                    } else {
                        alert(res.error.data);
                    }
                });
        } else {
            alert("로그인이 필요합니다.");
        }
    }

    const decreaseLike = () => {
        fetchApi("/likes/" + id, "DELETE", null, true)
            .then(res => {
                if (res.result === "SUCCESS") {
                    alert("좋아요가 취소되었습니다.")
                    setIsLiked(false)
                } else {
                    alert(res.error.data);
                }
            });
    }

    return (
        <div>
            {isLogin ?
                (!isLiked ?
                    <Button onClick={increaseLike} className="btn btn-primary" style={{ marginRight: "100px" }}>좋아요</Button> :
                    <Button onClick={decreaseLike} className="btn btn-primary" style={{ marginRight: "100px" }}>좋아요 취소 </Button>
                ) :
                <Button onClick={increaseLike} className="btn btn-primary" style={{ marginRight: "100px" }}>좋아요</Button>
            }
        </div>
    );
};

export default LikeButton;