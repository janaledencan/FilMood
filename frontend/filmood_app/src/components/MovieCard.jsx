import React from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { motion } from 'framer-motion';
import { Link } from "react-router-dom";

function MovieCard({ movie }) {
    return (
        <motion.div whileHover={{ scale: 1.03 }} whileTap={{ scale: 0.97 }}>
            <Card style={{ width: '18rem' }} className="my-3 mx-2 shadow-lg">
                <Card.Img variant="top" className="movie-image" src={movie.image} />
                <Card.Body>
                    <Card.Title>{movie.name}</Card.Title>
                    <Card.Text>
                        <strong>Year:</strong> {movie.year} <br />
                        <strong>Duration:</strong> {movie.duration} mins <br />
                        <strong>Rating:</strong> {movie.rating}/10 <br />
                        <strong>Genre:</strong> {movie.genre}
                    </Card.Text>
                    <Button className="btn-primary me-2">Add to Library</Button>
                    <Link to={`/details`} state={{ movie }}>
                        <Button className="btn-tertiary" variant="secondary">View Details</Button>
                    </Link>
                </Card.Body>
            </Card>
        </motion.div>
    );
}

export default MovieCard;
