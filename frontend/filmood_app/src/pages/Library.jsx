import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import { Splide, SplideSlide } from "@splidejs/react-splide";
import "@splidejs/react-splide/css";
import { useNavigate } from "react-router-dom";
import styled from 'styled-components';

function Library() {
    const [library, setLibrary] = useState([]);
    const [filter, setFilter] = useState("all");
    const navigate = useNavigate();

    const pageNumber = 1;
    const pageSize = 10;
    const sort = "userRating";
    const direction = "ASC";
    const userRating = filter === "rated" ? 5 : 0;

    const onViewDetailsClick = (movie) => {
        navigate(`/details`, { state: { movie } });
    };

    useEffect(() => {
        const fetchLibrary = async () => {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/v1/movie/library?page=${pageNumber}&size=${pageSize}&sort=${sort}&direction=${direction}&userRating=${userRating}`,
                    {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        credentials: "include",
                    }
                );

                if (!response.ok) {
                    throw new Error("Failed to fetch library");
                }

                const data = await response.json();
                setLibrary(data.content);
            } catch (error) {
                console.error("Error fetching library:", error);
            }
        };

        fetchLibrary();
    }, [filter]);

    const handleRating = async (movieId, rating) => {
        setLibrary((prevLibrary) =>
            prevLibrary.map((movie) =>
                movie.movieId === movieId ? { ...movie, userRating: rating } : movie
            )
        );

        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/library/${movieId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ userRating: rating }),
                credentials: "include",
            });

            if (!response.ok) {
                console.error("Failed to update movie rating");
            } else {
                console.log(`Movie with ID ${movieId} rated ${rating}`);
            }
        } catch (error) {
            console.error("Error updating movie rating:", error);
        }
    };

    const filteredLibrary = library.filter((movie) => {
        if (filter === "rated") return movie.userRating > 0;
        if (filter === "notRated") return movie.userRating === 0;
        return true;
    });

    return (
        <Container>
            <h3 className="mt-5 mb-4">Your Movie Library</h3>

            <ButtonGroup className="mb-3">
                <Button className="btn-primary" onClick={() => setFilter("all")}>
                    All
                </Button>
                <Button variant="success" onClick={() => setFilter("rated")}>
                    Rated
                </Button>
                <Button className="btn-notRated" onClick={() => setFilter("notRated")}>
                    Not Rated
                </Button>
            </ButtonGroup>

            <Splide
                options={{
                    perPage: 4,
                    arrows: false,
                    drag: "free",
                    gap: "1rem",
                    breakpoints: {
                        1400: { perPage: 4, gap: "5rem" },
                        1200: { perPage: 3, gap: "2rem" },
                        1000: { perPage: 2, gap: "2rem" },
                        800: { perPage: 2, gap: "2.5rem" },
                        770: { perPage: 2, gap: "4rem" },
                        450: { perPage: 1, gap: "3rem" },
                    },
                }}
            >
                {filteredLibrary.map((movie) => (
                    <SplideSlide key={movie.movieId}>
                        <Card style={{ height: "100%" }}>
                            <Card.Img
                                variant="top"
                                src={`https://image.tmdb.org/t/p/w780/${movie.posterPath}`}
                                style={{
                                    height: "250px", 
                                    objectFit: "cover", 
                                }}
                            />
                            <Card.Body>
                                <Card.Title><EllipsisText>{movie.title}</EllipsisText></Card.Title>
                                <Card.Text>
                                    <strong>Year:</strong> {movie.releaseYear} <br />
                                    <strong>Genre:</strong> <EllipsisText>{movie.genres.join(', ')}</EllipsisText>
                                </Card.Text>
                                <div>
                                    <strong>Grade: </strong>
                                    {[1, 2, 3, 4, 5].map((star) => (
                                        <span
                                            key={star}
                                            style={{
                                                fontSize: "1.5rem",
                                                color: movie.userRating >= star ? "gold" : "gray",
                                                cursor: "pointer",
                                            }}
                                            onClick={() => handleRating(movie.movieId, star)}
                                        >
                                            ★
                                        </span>
                                    ))}
                                </div>
                                <Button className="btn-tertiary mt-4" variant="secondary" onClick={() => onViewDetailsClick(movie)}>View Details</Button>
                            </Card.Body>
                        </Card>
                    </SplideSlide>
                ))}
            </Splide>
        </Container>
    );
}

export default Library;


const EllipsisText = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 250px; 
`;