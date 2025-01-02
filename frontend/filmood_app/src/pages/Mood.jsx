import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Container, Button, Row, Col } from "react-bootstrap";
import styled from "styled-components";
import Home from "./Home";

function Mood() {
    const [movies, setMovies] = useState([]);
    const [category, setCategory] = useState("");
    const location = useLocation(); 

    const moods = [
        { icon: "💪", label: "Brave" },
        { icon: "💡", label: "Motivated" },
        { icon: "🥰", label: "In Love" },
        { icon: "😢", label: "Sad" },
        { icon: "🧐", label: "Curious" },
        { icon: "🤩", label: "Excited" },
    ];

    
    useEffect(() => {
        if (location.state && location.state.movies) {
            setMovies(location.state.movies); 
            setCategory(location.state.category);
        }
    }, [location.state]);

    const handleMoodClick = async (moodLabel) => {
        const pageNumber = 1;
        const formattedMoodLabel = moodLabel.toLowerCase().replace(/\s+/g, '-');
        const url = `http://localhost:8080/api/v1/movie/mood/${formattedMoodLabel}?number=${pageNumber}`;

        try {
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include"
            });

            const data = await response.json();
            setMovies(data.content);
            setCategory(moodLabel);
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    return (
        <Container>
            <Title className="mt-5">How are you feeling today?</Title>
            <Row className="justify-content-center">
                {moods.map((mood, index) => (
                    <Col key={index} xs={6} sm={4} md={2} className="text-center my-3">
                        <StyledButton
                            variant="outline-secondary"
                            onClick={() => handleMoodClick(mood.label)} 
                        >
                            <span className="emoji">{mood.icon}</span>
                        </StyledButton>
                        <Description>{mood.label}</Description>
                    </Col>
                ))}
            </Row>

            {movies.length > 0 && (
                <Home
                    category={category}
                    movies={movies}
                    isMood={true}
                />
            )}
        </Container>
    );
}

export default Mood;

const Title = styled.h2`
  text-align: center;
  margin: 2rem 0;
`;

const StyledButton = styled(Button)`
  font-size: 2rem;
  width: 4rem;
  height: 4rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  margin: auto;
`;

const Description = styled.p`
  margin-top: 0.5rem;
  font-size: 1rem;
  color: var(--text-color);
`;
