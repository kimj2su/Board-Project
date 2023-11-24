import React, {useEffect, useState} from 'react';
import PostItem from "../../components/PostItem";
import {Table} from "react-bootstrap";
import {fetchApi} from "../../components/FetchApi";
import PaginationBar from "../../components/PaginationBar";

const Home = () => {
    const [posts, setPosts] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [paginationBar, setPaginationBar] = useState([]);

    const fetchPosts = (currentPage) => {
        fetchApi(`/posts?page=${currentPage}`)
        .then(res => {
            setPosts(res.data.posts);
            setCurrentPage(res.data.currentPageNumber);
            setTotalPages(res.data.totalPages);
            setPaginationBar(res.data.paginationBarNumbers)
        });
    }
    // 함수 실행시 최초 한번 실행
    useEffect(() => {
        fetchPosts(currentPage);
    }, [currentPage])

    return (
        <div>
            <Table striped="columns">
                <thead>
                    <tr>
                        <th>글 번호</th>
                        <th>제목</th>
                        <th>좋아요</th>
                        <th>작성자</th>
                    </tr>
                </thead>
                <tbody>
                    {posts.map((post) => (
                        <PostItem key={post.id} post={post}/>
                    ))}
                </tbody>
            </Table>
            <PaginationBar
                currentPage={currentPage}
                totalPages={totalPages}
                setCurrentPage={setCurrentPage}
                paginationBar={paginationBar}
            />
        </div>
    );
};

export default Home;