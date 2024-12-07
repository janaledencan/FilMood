import React from "react";
import { Container, Button, Row, Col } from "react-bootstrap";
import styled from "styled-components";

function Mood() {
    const moods = [
        { icon: "💪", label: "Brave" },
        { icon: "💡", label: "Motivated" },
        { icon: "🥰", label: "In Love" },
        { icon: "😢", label: "Sad" },
        { icon: "🧐", label: "Curious" },
        { icon: "🤩", label: "Excited" },
    ];

    return (
        <Container>
            <Title>How are you feeling today?</Title>
            <Row className="justify-content-center">
                {moods.map((mood, index) => (
                    <Col key={index} xs={6} sm={4} md={2} className="text-center my-3">
                        <StyledButton variant="outline-secondary">
                            <span className="emoji">{mood.icon}</span>
                        </StyledButton>
                        <Description>{mood.label}</Description>
                    </Col>
                ))}
            </Row>
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
  color: --var(--text-color);
`;
