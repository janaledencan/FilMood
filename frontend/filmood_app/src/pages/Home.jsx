import React, { useState, useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import Pagination from 'react-bootstrap/Pagination';
import Col from 'react-bootstrap/Col';
import styled from "styled-components";
import MovieCard from '../components/MovieCard';
import { useLocation, useNavigate } from 'react-router-dom';
import { handleCategoryChange } from '../components/Navigation'


function Home({ category, movies, page, setMovies, setPages, setCategory, isMood=false }) {
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

    const handlePageChange = async (pageNumber) => {
        setCurrentPage(pageNumber);
        try {
            const response = await handleCategoryChange(displayCategory, pageNumber, setMovies, setPages, navigate);
           
        } catch (error) {
            console.error('Error during page change:', error);
        }
    };

    useEffect(() => {
        handlePageChange(1);
      }, []); 

    if (!displayMovies || !displayCategory) {
        return <div className='ms-4 mt-4'>Loading...</div>;
    }

    // Calculate paginated movies
    const currentMovies = displayMovies;
    console.log("Current Movies to display:", currentMovies);
    const totalPages = Math.min(20, displayPages.totalPages); // Limit the number of pages to 20

    

    return (
        <Container>
            <Wrapper>
                <h2>{displayCategory.replace('_', ' ').toUpperCase()}</h2>

                <MoviesGrid className="movies-grid">
                    {currentMovies.map((movie, index) => (
                        <Col key={index} className="movie-card">
                            <MovieCard key={movie.movieId} movie={movie} />
                        </Col>
                    ))}
                </MoviesGrid>
                { isMood===false &&
                
                <PaginationWrapper>

                    <Pagination>
                        {/* First and Previous Arrows */}
                        <Pagination.First
                            onClick={() => handlePageChange(1)}
                            disabled={currentPage === 1}
                        />
                        <Pagination.Prev
                            onClick={() => handlePageChange(currentPage - 1)}
                            disabled={currentPage === 1}
                        />

                        {/* Previous Page */}
                        {currentPage > 1 && (
                            <Pagination.Item onClick={() => handlePageChange(currentPage - 1)}>
                            {currentPage - 1}
                            </Pagination.Item>
                        )}

                        {/* Current Page */}
                        <Pagination.Item active>{currentPage}</Pagination.Item>

                        {/* Next Page */}
                        {currentPage < totalPages && (
                            <Pagination.Item onClick={() => handlePageChange(currentPage + 1)}>
                            {currentPage + 1}
                            </Pagination.Item>
                        )}

                        {/* Next and Last Arrows */}
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
                
                }
                
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
    grid-template-columns: repeat(auto-fit, minmax(18rem, 1fr));
    gap: 1rem;
    margin-bottom: 2rem;
`;

const PaginationWrapper = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 2rem;
`;
