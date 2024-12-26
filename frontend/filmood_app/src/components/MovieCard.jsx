import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';

function MovieCard({ movie }) {
    const [isInMyLibrary, setIsInMyLibrary] = useState(movie.isInMyLibrary);

    const handleAddToLibrary = async () => {  
        const movieData = {
            title: movie.title,
            genres: movie.genres, 
            movieId: movie.movieId,
            releaseYear: movie.releaseYear,
            voteAverage: movie.voteAverage,
            posterPath: movie.posterPath,
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
                alert("Movie added to your library!");
                setIsInMyLibrary(true);
            } else {
                alert("Failed to add movie to the library.");
            }
        } catch (error) {
            console.error("Error adding movie to library:", error);
            alert("An error occurred while adding the movie to your library.");
        }
    };

    return (
        <motion.div whileHover={{ scale: 1.03 }} whileTap={{ scale: 0.97 }}>
            <Card style={{ width: '18rem' }} className="my-3 mx-2 shadow-lg">
                <Card.Img variant="top" className="movie-image" src={`https://image.tmdb.org/t/p/original/${movie.posterPath}`} />
                <Card.Body>
                    <Card.Title>{movie.title}</Card.Title>
                    <Card.Text>
                        <strong>Year:</strong> {movie.releaseYear} <br />
                        <strong>Rating:</strong> {movie.voteAverage}/10 <br />
                        <strong>Genre:</strong> {movie.genres.join(' ')}
                    </Card.Text>
                    {!isInMyLibrary && (
                        <Button className="btn-primary me-2" onClick={handleAddToLibrary}>
                            Add to Library
                        </Button>
                    )}
                    <Link to={`/details`} state={{ movie }}>
                        <Button className="btn-tertiary" variant="secondary">View Details</Button>
                    </Link>
                </Card.Body>
            </Card>
        </motion.div>
    );
}

export default MovieCard;
