import Pagination from 'react-bootstrap/Pagination';

const PaginationBar = ({ currentPage, totalPages, setCurrentPage, paginationBar}) => {
    const handlerPageChange = (pageNumber) => {
        // 페이지 변경 시 부모 컴포넌트의 setPage 함수 호출
        setCurrentPage(pageNumber);
    }

    return (
        <Pagination>
            <Pagination.First
                disabled={currentPage === 0}
                onClick={() => handlerPageChange(0)}
            />
            <Pagination.Prev
                disabled={currentPage === 0}
                onClick={() => handlerPageChange(currentPage - 1)}
            />
            {/*<Pagination.Ellipsis />*/}


            {paginationBar.map((pageNumber) => (
                <Pagination.Item
                    key={pageNumber}
                    disabled={pageNumber === currentPage}
                    onClick={() => handlerPageChange(pageNumber)}
                >
                    {pageNumber + 1}
                </Pagination.Item>
            ))}


            {/*<Pagination.Ellipsis />*/}
            <Pagination.Next
                disabled={currentPage === totalPages - 1}
                onClick={() => handlerPageChange(currentPage + 1)}
            />
            <Pagination.Last
                disabled={currentPage === totalPages - 1}
                onClick={() => handlerPageChange(totalPages - 1)}
            />
        </Pagination>
    );
};

export default PaginationBar;