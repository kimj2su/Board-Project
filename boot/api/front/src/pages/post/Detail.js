import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import {fetchApi} from "../../components/FetchApi";
import {useDispatch} from "react-redux";
import {verifiedUser} from "../../actions/UserAction";
import LikeButton from "../../components/LikeButton";

const Detail = (props) => {

  const {id} = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [post, setPost] = useState({
    id: "",
    title: "",
    content: "",
  });

  // sse
  let eventSource = undefined;
  const [postEvent, setPostEvent] = useState(undefined);

  const fetch = () => {
    fetchApi("/posts/" + id, "GET", null, true)
    .then(res => {
      setPost(res.data);
    });
  };

  useEffect(() => {
    fetch();
    // fetch("/posts/" + id, {
    //     method: "GET",
    // })
    // .then(res => res.json())
    // .then(res => {
    //     setPost(res.data);
    // });
    eventSource = new EventSource(
        "/posts/" + id + "/subscribe?token=" + localStorage.getItem('token'));
    setPostEvent(eventSource);

    eventSource.addEventListener("open", function (event) {
      console.log("connection opened");
    });

    eventSource.addEventListener("post", function (event) {
      console.log(event.data);
      fetch();
    });

    eventSource.addEventListener("error", function (event) {
      console.log(event.target.readyState);
      if (event.target.readyState === EventSource.CLOSED) {
        console.log("eventsource closed (" + event.target.readyState + ")");
      }
      eventSource.close();
    });
  }, [])

  const updatePost = () => {
    dispatch(verifiedUser(id))
    .then(res => {
      if (res.payload.result === "SUCCESS") {
        navigate("/updateForm/" + id);
      } else {
        if (res.payload.error.data === null) {
          alert("로그인이 필요합니다.")
        } else {
          alert(res.payload.error.data);
        }
      }
    });
  }

  const deletePost = (id) => {
    dispatch(verifiedUser(id))
    .then(res => {
      if (res.payload.result === "SUCCESS") {
        fetchApi("/posts/" + id, "DELETE", null, true)
        .then(res => {
          if (res.result === 'SUCCESS') {
            navigate("/");
          } else {
            alert("게시글 삭제에 실패하였습니다.");
          }
        });
      } else {
        if (res.payload.error.data === null) {
          alert("로그인이 필요합니다.")
        } else {
          alert(res.payload.error.data);
        }
      }
    });
  }

  return (
      <div>
        <div style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center"
        }}>
          <div>
            <h1>{post.title}</h1>
            <h3>{post.content}</h3>
          </div>
          <LikeButton/>
        </div>
        <div className="mt-3">
          <Link onClick={updatePost} to={""} className="btn btn-primary"
                style={{marginRight: "10px"}}>수정</Link>
          <Button variant="danger"
                  onClick={() => deletePost(post.id)}>삭제</Button>
        </div>
      </div>

  );
};

export default Detail;