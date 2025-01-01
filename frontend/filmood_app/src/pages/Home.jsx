import React, { useState } from 'react';
import Container from 'react-bootstrap/Container';
import Pagination from 'react-bootstrap/Pagination';
import styled from "styled-components";
import MovieCard from '../components/MovieCard';
import { useLocation, useNavigate } from 'react-router-dom';
import { handleCategoryChange } from '../components/Navigation'

function Home({ category, movies, page, setMovies, setPages, setCategory }) {
    const location = useLocation();

    const locationCategory = location.state?.locationCategory;
    const locationMovies = location.state?.locationMovies;
    const locationPage = location.state?.pageNumbers;

    const displayCategory = category || locationCategory;
    const displayMovies = movies || locationMovies;
    const displayPages = page || locationPage || 0;
    const navigate = useNavigate();

    // Pagination state
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 20;  // 20 movies per page

    if (!displayMovies || !displayCategory) {
        return <div>Loading...</div>;
    }

    // Calculate paginated movies
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentMovies = displayMovies.slice(indexOfFirstItem, indexOfLastItem);
console.log("Current Movies to display:", currentMovies);
    const totalPages = Math.min(20, displayPages.totalPages); // Limit the number of pages to 20

    // const handlePageChange = (pageNumber) => {
    //     setCurrentPage(pageNumber);
    //     //setCat
    //     handleCategoryChange(displayCategory, pageNumber,displayMovies, displayPages, displayCategory, navigate);
    //     //handleCategoryChange(displayCategory, pageNumber)
    // };

    // const handlePageChange = async (pageNumber) => {
    //     setCurrentPage(pageNumber);
    //     try {
    //         await handleCategoryChange(displayCategory, pageNumber, setMovies, setPages, setCategory, navigate);
    //     } catch (error) {
    //         console.error('Error during page change:', error);
    //     }
    // };
    const handlePageChange = async (pageNumber) => {
        setCurrentPage(pageNumber);
        try {
            const response = await handleCategoryChange(displayCategory, pageNumber, setMovies, setPages, navigate);
            console.log("Fetched page num:", pageNumber);
            console.log("Fetched data from API:", response);
            //console.log("Movies: ", movies);
           
        } catch (error) {
            console.error('Error during page change:', error);
        }
    };
    

    return (
        <Container>
            <Wrapper>
                <h2>{displayCategory.replace('_', ' ').toUpperCase()}</h2>
                <MoviesGrid>
                    {currentMovies.map((movie) => (
                        <MovieCard key={movie.movieId} movie={movie} />
                    ))}
                </MoviesGrid>
                <PaginationWrapper>
                    <Pagination>
                        <Pagination.First
                            onClick={() => handlePageChange(1)}
                            disabled={currentPage === 1}
                        />
                        <Pagination.Prev
                            onClick={() => handlePageChange(currentPage - 1)}
                            disabled={currentPage === 1}
                        />
                        {Array.from({ length: totalPages }).map((_, index) => {
                            const pageNum = index + 1;
                            return (
                                <Pagination.Item
                                    key={pageNum}
                                    active={pageNum === currentPage}
                                    onClick={() => handlePageChange(pageNum)}
                                >
                                    {pageNum}
                                </Pagination.Item>
                            );
                        })}
                        <Pagination.Next
                            onClick={() => handlePageChange(currentPage + 1)}
                            disabled={currentPage === totalPages}
                        />
                        <Pagination.Last
                            onClick={() => handlePageChange(totalPages)}
                            disabled={currentPage === totalPages}
                        />
                    </Pagination>
                </PaginationWrapper>
            </Wrapper>
        </Container>
    );
}

export default Home;

const Wrapper = styled.div`
    margin: 4rem 0rem;
`;

const MoviesGrid = styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 2rem;
    margin-bottom: 2rem;
`;

const PaginationWrapper = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 2rem;
`;
