import React, { useState, useEffect } from "react";
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert'; 
import  Card  from "react-bootstrap/Card";

function Profile() {
    const [user, setUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        age: 0,
        gender: "",
    });

    const [showModal, setShowModal] = useState(false);
    const [formData, setFormData] = useState({ ...user });
    const [successMessage, setSuccessMessage] = useState(""); 

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/v1/auth/profile-info", {
                    method: "GET",
                    credentials: "include", 
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch user data");
                }

                const data = await response.json();
                setUser(data);
                setFormData(data); 
            } catch (error) {
                console.error("Error fetching user data:", error.message);
            }
        };

        fetchUserData();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSave = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/v1/auth/update", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
                credentials: "include",
            });

            if (!response.ok) {
                throw new Error("Failed to update user data");
            }

            setUser(formData);
            setFormData(formData);
            setShowModal(false); 
            setSuccessMessage("Profile updated successfully!"); 

            setTimeout(() => {
                setSuccessMessage("");
            }, 3000);
        } catch (error) {
            console.error("Error saving user data:", error.message);
        }
    };

    return (
        <Container 
        fluid 
        className="d-flex justify-content-center align-items-center vh-100 "
    >
        <Card style={{ maxWidth: "500px", width: "100%", padding: "1.5rem",  boxShadow: "0 4px 8px rgba(255, 255, 0, 0.6)", }} >
            <h2 className="mb-4 text-center">Profile</h2>

            {successMessage && (
                <Alert
                    variant="success"
                    onClose={() => setSuccessMessage("")}
                    dismissible
                    className="text-center"
                >
                    {successMessage}
                </Alert>
            )}

            <p className="mb-2">
                <strong>First Name:</strong> {user.firstName}
            </p>
            <p className="mb-2">
                <strong>Last Name:</strong> {user.lastName}
            </p>
            <p className="mb-2">
                <strong>Email:</strong> {user.email}
            </p>
            <p className="mb-2">
                <strong>Gender:</strong> {user.gender}
            </p>
            <p className="mb-2">
                <strong>Age:</strong> {user.age}
            </p>

            <div className="text-center mt-4">
                <Button
                    variant="primary"
                    onClick={() => setShowModal(true)}
                    className="px-4"
                >
                    Edit Profile
                </Button>
            </div>
        </Card>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Profile</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>First Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Last Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Gender</Form.Label>
                            <Form.Select
                                name="gender"
                                value={formData.gender}
                                onChange={handleChange}
                            >
                                <option>Male</option>
                                <option>Female</option>
                                <option>Other</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Age</Form.Label>
                            <Form.Control
                                type="number"
                                name="age"
                                value={formData.age}
                                onChange={handleChange}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleSave}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
}

export default Profile;
