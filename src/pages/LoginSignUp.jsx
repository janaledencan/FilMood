import React, { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";

function LoginSignUp({ onLogin }) {
    const [isSignup, setIsSignup] = useState(false);

    const toggleMode = () => setIsSignup(!isSignup);

    return (
        <Container
            className="d-flex flex-column justify-content-center align-items-center vh-100">

            <h2 className="text-center mb-4">{isSignup ? "Sign Up" : "Login"}</h2>

            <Form className="min-vw-75">
                {isSignup && (
                    <>
                        <Form.Group controlId="username">
                            <Form.Control
                                type="text"
                                placeholder="Username"
                                className="my-2"
                            />
                        </Form.Group>
                        <Form.Group controlId="gender">
                            <Form.Label>Gender</Form.Label>
                            <Form.Control as="select" className="my-2">
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </Form.Control>
                        </Form.Group>
                        <Form.Group controlId="age">
                            <Form.Control
                                type="number"
                                placeholder="Age"
                                className="my-2"
                            />
                        </Form.Group>
                    </>
                )}
                <Form.Group controlId="email">
                    <Form.Control
                        type="email"
                        placeholder="Email"
                        className="my-2"
                    />
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        className="my-2"
                    />
                </Form.Group>
                <Button
                    variant="primary"
                    type="submit"
                    onClick={onLogin}
                    className="w-100 mt-2">
                    {isSignup ? "Sign Up" : "Login"}
                </Button>
            </Form>

            <Button
                variant="link"
                className="mt-3 w-100"
                onClick={toggleMode}>
                {isSignup ? "Already have an account? Login" : "Don't have an account? Sign Up"}
            </Button>

        </Container>
    );
}

export default LoginSignUp;
