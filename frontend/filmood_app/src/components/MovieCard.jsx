import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { Toast, ToastContainer } from "react-bootstrap";
import styled from 'styled-components';

function MovieCard({ movie }) {
    const [isInMyLibrary, setIsInMyLibrary] = useState(movie.isInMyLibrary);
    const [showToast, setShowToast] = useState(false);
    const navigate = useNavigate();

    const handleAddToLibrary = async () => {  
        const movieData = {
            title: movie.title,
            genres: movie.genres, 
            movieId: movie.movieId,
            releaseYear: movie.releaseYear,
            voteAverage: movie.voteAverage,
            posterPath: movie.posterPath,

            budget: movie.budget,
            overview: movie.overview,
            revenue: movie.revenue,
            runtime: movie.runtime,
            releaseStatus: movie.releaseStatus,
            tagline: movie.tagline,
            voteCount: movie.voteCount

        };
    
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/library`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(movieData), 
                credentials: 'include',
            });
    
            if (response.ok) {
                setIsInMyLibrary(true);
                setShowToast(true); 
            } else {
                console.error("Failed to add movie to the library.");
            }
        } catch (error) {
            console.error("Error adding movie to library:", error);
        }
    };

    const getMovieDetails = async () => {
    
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/details/${movie.movieId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json', 
                },
                credentials: 'include',
            });
    
            if (!response.ok) {
                throw new Error('Failed to fetch movie details');
            }
    
            const data = await response.json();
           navigate('/details', { state: { movie: data } });
    
        } catch (error) {
            console.error('Error fetching movie details:', error.message);
        }
    };


    return (
        <>
            <motion.div whileHover={{ scale: 1.03 }} whileTap={{ scale: 0.97 }}>
                <Card className="my-3 mx-2 shadow-lg">
                    <Card.Img variant="top" className="movie-image" src={`https://image.tmdb.org/t/p/original/${movie.posterPath}`} />
                    <Card.Body>
                        <Card.Title><EllipsisText>{movie.title}</EllipsisText></Card.Title>
                        <Card.Text>
                            <strong>Year:</strong> {movie.releaseYear} <br />
                            <strong>Rating:</strong> {movie.voteAverage}/10 <br />
                            <strong>Genre:</strong> <EllipsisText>{movie.genres.join(', ')}</EllipsisText>
                        </Card.Text>
                        
                        {!isInMyLibrary && (
                            <Button className="btn-primary me-3" onClick={handleAddToLibrary}>
                                Add to Library
                            </Button>
                        )}
                
                        <Button className="btn-tertiary" variant="secondary"  onClick={getMovieDetails}>View Details</Button>
                    </Card.Body>
                </Card>
            </motion.div>

            
            <ToastContainer
                position="top-end" 
                className="p-3"
                style={{
                    position: "fixed", 
                    zIndex: 1050, 
                }}
            >
                <Toast onClose={() => setShowToast(false)} show={showToast} delay={3000} autohide>
                    <Toast.Header style={{ backgroundColor: 'rgba(255, 255, 0, 0.6)' }}>
                    <strong className="me-auto" >
                        Add to library
                    </strong>
                    </Toast.Header>
                    <Toast.Body className='bg-dark'><strong>{movie.title}</strong> added to your library!</Toast.Body>
                </Toast>
            </ToastContainer>
        </>
    );
}

export default MovieCard;


const EllipsisText = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 250px; 
`;
