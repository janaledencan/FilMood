import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";
import styled from "styled-components";

function FilmDetails() {
    const location = useLocation();
    const navigate = useNavigate();
    const { movie, movies } = location.state || {};

    const handleBack = () => {
        if (movies) {
            navigate(-1, { state: { movies } });
        } else {
            navigate(-1);
        }
    };

    return (
        <Container>
            <Row className="my-5">
                <Col md={4}>
                    <MovieImage 
                        src={`https://image.tmdb.org/t/p/w780/${movie.posterPath}`} 
                        alt={movie.title} 
                    />
                </Col>
                <Col md={8}>
                    <Details>
                        <h2>{movie.title}</h2>
                        <p><strong>Year:</strong> {movie.releaseYear}</p>
                        <p><strong>Rating:</strong> {movie.voteAverage}/10 ({movie.voteCount} votes)</p>
                        <p><strong>Genre:</strong> {movie.genres.join(', ')}</p>
                        <p><strong>Tagline:</strong> {movie.tagline || "N/A"}</p>
                        <p><strong>Overview:</strong> {movie.overview}</p>
                        <p><strong>Runtime:</strong> {movie.runtime} minutes</p>
                        <p><strong>Budget:</strong> ${movie.budget?.toLocaleString()}</p>
                        <p><strong>Revenue:</strong> ${movie.revenue?.toLocaleString()}</p>
                        <p><strong>Status:</strong> {movie.releaseStatus}</p>
                        <p>
                            <strong>IMDB:</strong>{" "}
                            <a 
                                href={`https://www.imdb.com/title/${movie.imdbId}`} 
                                target="_blank" 
                                rel="noopener noreferrer"
                            >
                                View on IMDB
                            </a>
                        </p>
                        <Button onClick={handleBack} className="mt-3">Back</Button>
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
  a {
    color: #007bff;
    text-decoration: none;
  }
  a:hover {
    text-decoration: underline;
  }
`;