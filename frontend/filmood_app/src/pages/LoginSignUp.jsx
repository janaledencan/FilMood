import React, { useState } from "react";
import { Form, Button, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom"; 

function LoginSignUp({ onLogin }) {
    const [isSignup, setIsSignup] = useState(false);
    const [formData, setFormData] = useState({
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        email: "",
        age: "",
        gender: "Male",
    });

    const navigate = useNavigate(); 

    const toggleMode = () => setIsSignup(!isSignup);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const dataToSend = isSignup
            ? formData
            : { username: formData.username, password: formData.password };

        try {
            const url = isSignup
                ? "http://localhost:8080/api/v1/auth/signup"
                : "http://localhost:8080/api/v1/auth/login";

            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(dataToSend),
                credentials: "include"
            });

            if (!response.ok) {
                throw new Error("Failed to " + (isSignup ? "sign up" : "log in"));
            }

            onLogin();
            navigate("/mood");
        } 
        catch (error) 
        {
            console.error("Error:", error.message);
        }
    };

    return (
        <Container className="d-flex flex-column justify-content-center align-items-center vh-100">
            <h2 className="text-center mb-4">{isSignup ? "Sign Up" : "Login"}</h2>

            <Form className="min-vw-75" onSubmit={handleSubmit}>
                {isSignup && (
                    <>
                        <Form.Group controlId="username">
                            <Form.Control
                                type="text"
                                placeholder="Username"
                                name="username"
                                value={formData.username}
                                onChange={handleChange}
                                className="my-2"
                            />
                        </Form.Group>
                        <Form.Group controlId="email">
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                className="my-2"
                            />
                        </Form.Group>
                        <Form.Group controlId="firstName">
                            <Form.Control
                                type="text"
                                placeholder="First Name"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                className="my-2"
                            />
                        </Form.Group>
                        <Form.Group controlId="lastName">
                            <Form.Control
                                type="text"
                                placeholder="Last Name"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                className="my-2"
                            />
                        </Form.Group>
                        <Form.Group controlId="gender">
                            <Form.Control
                                as="select"
                                name="gender"
                                value={formData.gender}
                                onChange={handleChange}
                                className="my-2"
                            >
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </Form.Control>
                        </Form.Group>
                        <Form.Group controlId="age">
                            <Form.Control
                                type="number"
                                placeholder="Age"
                                name="age"
                                value={formData.age}
                                onChange={handleChange}
                                className="my-2"
                            />
                        </Form.Group>
                    </>
                )}

                {!isSignup && (
                    <Form.Group controlId="username">
                        <Form.Control
                            type="text"
                            placeholder="Username"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            className="my-2"
                        />
                    </Form.Group>
                )}

                <Form.Group controlId="password">
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        className="my-2"
                    />
                </Form.Group>

                <Button variant="primary" type="submit" className="w-100 mt-2">
                    {isSignup ? "Sign Up" : "Login"}
                </Button>
            </Form>

            <Button variant="link" className="mt-3 w-100" onClick={toggleMode}>
                {isSignup
                    ? "Already have an account? Login"
                    : "Don't have an account? Sign Up"}
            </Button>
        </Container>
    );
}

export default LoginSignUp;
