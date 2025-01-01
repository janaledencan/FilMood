import React, { useState, useEffect } from "react";
import { Form, Button, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Alert from 'react-bootstrap/Alert'; 

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
    const [passwordError, setPasswordError] = useState("");
    const [emailError, setEmailError] = useState("");
    const [alert, setAlert] = useState({ show: false, message: "", variant: "" });
    const navigate = useNavigate();

    const toggleMode = () => setIsSignup(!isSignup);

    const [isVisible, setIsVisible] = useState(false);

     useEffect(() => {
        if (alert.show) {
            setIsVisible(true);
            const timer = setTimeout(() => {
                setIsVisible(false); 
                setTimeout(() => {
                    setAlert((prev) => ({ ...prev, show: false }));
                }, 300); 
            }, 4000);

            return () => clearTimeout(timer);
        }
    }, [alert.show]);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "password") {
            validatePassword(value);
        }

        if (name === "email") {
            validateEmail(value);
        }

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const validatePassword = (password) => {

        const minLength = 6;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasNumber = /\d/.test(password);

        if (password.length < minLength) {
            setPasswordError("Password must be at least " + minLength +" characters long.");
        } else if (!hasUpperCase) {
            setPasswordError("Password must contain at least one uppercase letter.");
        } else if (!hasLowerCase) {
            setPasswordError("Password must contain at least one lowercase letter.");
        } else if (!hasNumber) {
            setPasswordError("Password must contain at least one number.");
        } else {
            setPasswordError("");
        }
    };

    const validateEmail = (email) => {
        const EMAIL_REGEX = /^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/;
              
        if (!email || email.trim() === "") {
          setEmailError("Email address cannot be empty.");
        } else if (!email.includes("@")) {
          setEmailError("Email address must contain '@'.");
        } else if (/^[.@]|[.@]$/.test(email)) {
          setEmailError("Email cannot start or end with '.' or '@'.");
        } else if (email.includes("..")) {
          setEmailError("Email address cannot contain consecutive dots.");
        } else if (!EMAIL_REGEX.test(email)) {
          setEmailError("Email format is invalid. Please check the characters and domain.");
        } else {
            setEmailError("");
        }
    }
      

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (passwordError) {
            console.error("Fix password errors before submitting.");
            setAlert({
                show: true,
                message: `Failed to ${isSignup ? "sign up" : "log in"}`,
                variant: "danger",
            });
            return;
        }

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
                credentials: "include",
            });

            if (!response.ok) {
                setAlert({
                    show: true,
                    message: `Failed to ${isSignup ? "sign up" : "log in"}`,
                    variant: "danger",
                });
                return;
            }

            if (!isSignup) {
                onLogin();
                navigate("/mood");
            } else {
                setIsSignup(false);
                setAlert({
                    show: true,
                    message: "Sign-up successful! You can now log in.",
                    variant: "success",
                });
            }
        } catch (error) {
            console.error("Error:", error.message);
            setAlert({
                show: true,
                message: "An unexpected error occurred. Please try again.",
                variant: "danger",
            });
        }
    };

    return (
        <>
           {alert.show && (
                <Alert
                    variant={alert.variant}
                    dismissible
                    onClose={() => {
                        setIsVisible(false);
                        setTimeout(() => setAlert({ ...alert, show: false }), 300);
                    }}
                    className={`text-center alert-animation ${
                        isVisible ? "fade-in" : "fade-out"
                    }`}
                >
                    {alert.message}
                </Alert>
            )} 
        
        
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
                                className="my-2 fixed-width"
                            />
                        </Form.Group>
                        <Form.Group controlId="email">
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                className="my-2 fixed-width"
                            />
                        </Form.Group>
                        <Form.Group controlId="firstName">
                            <Form.Control
                                type="text"
                                placeholder="First Name"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                className="my-2 fixed-width"
                            />
                        </Form.Group>
                        <Form.Group controlId="lastName">
                            <Form.Control
                                type="text"
                                placeholder="Last Name"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                className="my-2 fixed-width"
                            />
                        </Form.Group>
                        <Form.Group controlId="gender">
                            <Form.Control
                                as="select"
                                name="gender"
                                value={formData.gender}
                                onChange={handleChange}
                                className="my-2 fixed-width"
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
                                className="my-2 fixed-width"
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
                            className="my-2 fixed-width"
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
                        className="my-2 fixed-width"
                    />
                </Form.Group>
                {isSignup && (
                    <div className={`error-container ${!(passwordError || emailError) ? 'hidden' : ''}`}>
                        {passwordError && (
                            <div className="text-danger error-item">{passwordError}</div>
                        )}
                        {emailError && (
                            <div className="text-danger error-item">{emailError}</div>
                        )}
                    </div>
                )}
                
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
    </>
    );
}

export default LoginSignUp;
