import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Container from 'react-bootstrap/Container';
import { Link } from 'react-router-dom';

function Navigation({ isLoggedIn, handleLogout }) {
    return (
        <Navbar className="navigation py-3" expand="lg">
            <Container>
                <Navbar.Brand as={Link} to="/">FilMood</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        {!isLoggedIn ? (  //Treba biti bez !, ovo je trenutno dok nemamo backend
                            <>
                                <Nav.Link as={Link} to="/">Home</Nav.Link>
                                <Nav.Link as={Link} to="/mood">Mood</Nav.Link>
                                <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
                                <Nav.Link as={Link} to="/wishlist">Wishlist</Nav.Link>
                            </>
                        ) : (
                            <Nav.Link as={Link} to="/login">Login/Signup</Nav.Link>
                        )}
                    </Nav>
                    {isLoggedIn && (
                        <Nav className="ms-auto">
                            <Nav.Link onClick={handleLogout}>
                                Logout
                            </Nav.Link>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Navigation;
