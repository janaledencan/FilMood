import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";

function Library() {
    const [library, setLibrary] = useState([
        {
            id: 1,
            name: "Inception",
            image: "https://via.placeholder.com/150",
            year: 2010,
            genre: "Sci-Fi",
            rating: 0,
        },
        {
            id: 2,
            name: "The Dark Knight",
            image: "https://via.placeholder.com/150",
            year: 2008,
            genre: "Action",
            rating: 0,
        },
    ]);

    const [filter, setFilter] = useState("all");

    // Load library from localStorage when the component mounts
    useEffect(() => {
        const savedLibrary = JSON.parse(localStorage.getItem("library"));
        if (savedLibrary) setLibrary(savedLibrary);
    }, []);

    // Save library to localStorage whenever it changes
    useEffect(() => {
        localStorage.setItem("library", JSON.stringify(library));
    }, [library]);

    const handleRating = (id, stars) => {
        const updatedLibrary = library.map((movie) =>
            movie.id === id ? { ...movie, rating: stars } : movie
        );
        setLibrary(updatedLibrary);
    };

    const filteredLibrary = library.filter((movie) => {
        if (filter === "rated") return movie.rating > 0;
        if (filter === "notRated") return movie.rating === 0;
        return true; // "all" filter
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
                <Button className="btn-notRaited" onClick={() => setFilter("notRated")}>
                    Not Rated
                </Button>
            </ButtonGroup>

            <Row>
                {filteredLibrary.map((movie) => (
                    <Col key={movie.id} xs={12} sm={6} md={4} lg={3} className="my-3">
                        <Card>
                            <Card.Img variant="top" src={movie.image} />
                            <Card.Body>
                                <Card.Title>{movie.name}</Card.Title>
                                <Card.Text>
                                    <strong>Year:</strong> {movie.year} <br />
                                    <strong>Genre:</strong> {movie.genre}
                                </Card.Text>
                                {/* Star Rating */}
                                <div>
                                    <strong>Grade: </strong>
                                    {[1, 2, 3, 4, 5].map((star) => (
                                        <span
                                            key={star}
                                            style={{
                                                fontSize: "1.5rem",
                                                color: movie.rating >= star ? "gold" : "gray",
                                                cursor: "pointer",
                                            }}
                                            onClick={() => handleRating(movie.id, star)}
                                        >
                                            ★
                                        </span>
                                    ))}
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

export default Library;