import React, { useState } from "react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import styled from "styled-components";

function Profile() {
    // Mock user data
    const [user, setUser] = useState({
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@example.com",
        gender: "Male",
        age: 28,
    });

    // Wishlist data
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

    // Modal visibility state
    const [showModal, setShowModal] = useState(false);

    // Form state for editing user data
    const [formData, setFormData] = useState({ ...user });

    // Handle form inputs
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // Save updated user data
    const handleSave = () => {
        setUser({ ...formData });
        setShowModal(false);
    };

    return (
        <Container>
            <Row className="my-5">
                <Col md={6}>
                    <h2 className="mb-4">Profile</h2>
                    <p><strong>First Name:</strong> {user.firstName}</p>
                    <p><strong>Last Name:</strong> {user.lastName}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Gender:</strong> {user.gender}</p>
                    <p><strong>Age:</strong> {user.age}</p>
                    <Button variant="primary" className="mt-4" onClick={() => setShowModal(true)}>
                        Edit Profile
                    </Button>
                </Col>
            </Row>


            {/* Modal for editing user data */}
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
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={formData.email}
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
