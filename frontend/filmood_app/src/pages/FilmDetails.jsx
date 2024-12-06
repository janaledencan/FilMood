import React from "react";
import { useLocation } from "react-router-dom";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import styled from "styled-components";

function FilmDetails() {
    const location = useLocation();
    const movie = location.state.movie; // Pass movie data via state

    return (
        <Container>
            <Row className="my-5">
                <Col md={4}>
                    <MovieImage src={movie.image} style={{ height: '75vh', objectFit: 'cover' }} alt={movie.title} />
                </Col>
                <Col md={8}>
                    <Details className="ms-4">
                        <h2 className="movie-title">{movie.name}</h2>
                        <p><strong>Year:</strong> {movie.year}</p>
                        <p><strong>Duration:</strong> {movie.duration} mins</p>
                        <p><strong>Rating:</strong> {movie.rating}/10</p>
                        <p><strong>Genre:</strong> {movie.genre}</p>
                        <p><strong>Synopsis:</strong> {movie.synopsis}</p>
                    </Details>
                </Col>
            </Row>
        </Container>
    );
}

export default FilmDetails;

const MovieImage = styled.img`
  width: 100%;
  border-radius: 1rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
`;

const Details = styled.div`
  h2 {
    font-size: 2rem;
    margin-bottom: 1rem;
  }
  p {
    font-size: 1.1rem;
    margin-bottom: 0.5rem;
  }
`;

const AdditionalDetails = styled.div`
  p {
    font-size: 1rem;
    margin-bottom: 0.5rem;
  }
`;
