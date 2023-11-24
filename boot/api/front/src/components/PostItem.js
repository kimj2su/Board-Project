import React from 'react';
import {Link} from "react-router-dom";

const PostItem = (props) => {
    const {id, title, likeCount, memberResponse} = props.post;
// <Card>
    //     <Card.Body>
    //         <Card.Title>{title}</Card.Title>
    //         <Link to={"/post/" + id} className="btn btn-primary" >
    //             상세 보기
    //         </Link>
    //     </Card.Body>
    // </Card>
    return (
        <tr>
            <td>{id}</td>
            <td>
                <Link to={"/post/" + id}>{title}</Link>
            </td>
            <td>{likeCount}</td>
            <td>{memberResponse.name}</td>
        </tr>
    );
};

export default PostItem;