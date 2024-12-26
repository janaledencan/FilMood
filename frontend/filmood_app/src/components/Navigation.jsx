import React, { useState } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Container from 'react-bootstrap/Container';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../logo.svg';

function Navigation({ isLoggedIn, setIsLoggedIn }) {
    const [category, setCategory] = useState('');
    const [movies, setMovies] = useState([]);
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/auth/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error('Failed to log out');
            }

            setIsLoggedIn(false);
            navigate("/login");

        } catch (error) {
            console.error('Logout Error:', error.message);
        }
    };

    const handleCategoryChange = async (newCategory) => {
        setCategory(newCategory);
    
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/${newCategory}?number=1`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json', 
                },
                credentials: 'include',
            });
    
            if (!response.ok) {
                throw new Error('Failed to fetch movies');
            }
    
            const data = await response.json();
            setMovies(data.content); 
            navigate('/home', { state: { locationMovies: data.content, locationCategory: newCategory } })
    
        } catch (error) {
            console.error('Error fetching movies:', error.message);
        }
    };

    return (
        <Navbar className="navigation py-3" expand="lg">
            <Container>
                <Navbar.Brand as={Link} to="/">
                    <img
                        src={logo}
                        alt="FilMood Logo"
                        style={{ height: '40px', width: 'auto' }}
                    />
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        {isLoggedIn ? (
                            <>
                                <NavDropdown title="Home" id="home-dropdown">
                                    <NavDropdown.Item onClick={() => handleCategoryChange('now-playing')}>Now Playing</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('popular')}>Popular</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('top-rated')}>Top Rated</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('upcoming')}>Upcoming</NavDropdown.Item>
                                </NavDropdown>
                                <Nav.Link as={Link} to="/mood">Mood</Nav.Link>
                                <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
                                <Nav.Link as={Link} to="/library">My Library</Nav.Link>
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
