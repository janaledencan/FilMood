import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";

function Wishlist() {
    const [wishlist, setWishlist] = useState([
        {
            id: 1,
            name: "Inception",
            image: "https://via.placeholder.com/150",
            year: 2010,
            genre: "Sci-Fi",
        },
        {
            id: 2,
            name: "The Dark Knight",
            image: "https://via.placeholder.com/150",
            year: 2008,
            genre: "Action",
        },
    ]);

    return (
        <Container>
            <h3 className="mt-5">Your Wishlist</h3>
            <Row>
                {wishlist.map((movie) => (
                    <Col key={movie.id} xs={12} sm={6} md={4} lg={3} className="my-3">
                        <Card>
                            <Card.Img variant="top" src={movie.image} />
                            <Card.Body>
                                <Card.Title>{movie.name}</Card.Title>
                                <Card.Text>
                                    <strong>Year:</strong> {movie.year} <br />
                                    <strong>Genre:</strong> {movie.genre}
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

export default Wishlist;