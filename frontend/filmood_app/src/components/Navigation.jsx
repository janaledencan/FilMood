import React, { useState } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Container from 'react-bootstrap/Container';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../logo.svg';

// export async function handleCategoryChange(newCategory, pageNumber, setMovies, setPages, setCategory, navigate) {
    
// //const handleCategoryChange = async (newCategory, pageNumber) => {,
        
    
//         try {
//             const response = await fetch(`http://localhost:8080/api/v1/movie/${newCategory}?number=${pageNumber}`, {
//                 method: 'GET',
//                 headers: {
//                     'Content-Type': 'application/json', 
//                 },
//                 credentials: 'include',
//             });
    
//             if (!response.ok) {
//                 throw new Error('Failed to fetch movies');
//             }
    
//             const data = await response.json();
//             console.log(data.page);
//             console.log(data.content);
//             setCategory(newCategory);
//             setPages(data.page);
//             setMovies(data.content); 
//             navigate('/home', { state: { locationMovies: data.content, locationCategory: newCategory, pageNumbers: data.page } })
    
//         } catch (error) {
//             console.error('Error fetching movies:', error.message);
//         }
//     };

    export async function handleCategoryChange(newCategory, pageNumber, setMovies, setPages, navigate) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/${newCategory}?number=${pageNumber}`, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            });
    
            if (!response.ok) {
                throw new Error('Failed to fetch movies');
            }
    
            const data = await response.json();
            console.log("Fetched data:", data);
    
            //setCategory(newCategory);
            setPages(data.page);
            setMovies(data.content);
           
            navigate('/home', { state: { locationMovies: data.content, locationCategory: newCategory, pageNumbers: data.page } });
            return data;
        } catch (error) {
            console.error('Error fetching movies:', error.message);
            throw error;
        }
    }

function Navigation({ isLoggedIn, setIsLoggedIn, setMovies, setPages }) {
    // const [category, setCategory] = useState('');
    // const [movies, setMovies] = useState([]);
    // const [page, setPages] = useState([]);
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
                                    {/* <NavDropdown.Item onClick={() => navigate('/home', { state: { category: 'now-playing' } })}>Now Playing</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => navigate('/home', { state: { category: 'popular' } })}>Popular</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => navigate('/home', { state: { category: 'top-rated' } })}>Top Rated</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => navigate('/home', { state: { category: 'upcoming' } })}>Upcoming</NavDropdown.Item> */}
                                    
                                    <NavDropdown.Item onClick={() => handleCategoryChange('now-playing', 1, setMovies, setPages, navigate)}>Now Playing</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('popular', 1, setMovies, setPages, navigate)}>Popular</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('top-rated', 1, setMovies, setPages, navigate)}>Top Rated</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleCategoryChange('upcoming', 1, setMovies, setPages, navigate)}>Upcoming</NavDropdown.Item>
                                </NavDropdown>
                                <Nav.Link as={Link} to="/mood">Mood</Nav.Link>
                                <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
                                <Nav.Link as={Link} to="/library">My Library</Nav.Link>
                            </>
                        ) : (
                            <div></div>
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
