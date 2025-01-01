import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Navigation from './components/Navigation';
import LoginSignUp from './pages/LoginSignUp';
import Home from './pages/Home';
import Profile from './pages/Profile';
import Mood from './pages/Mood';
import FilmDetails from "./pages/FilmDetails";
import Library from './pages/Library';
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [category, setCategory] = useState('');
  const [movies, setMovies] = useState([]);
  const [page, setPages] = useState({ totalPages: 0 });
  

  useEffect(() => {
    const checkSession = () => {
      const cookies = document.cookie.split(';');
      const jsessionid = cookies.find(cookie => cookie.trim().startsWith('JSESSIONID='));
      
      if (jsessionid) {
        setIsLoggedIn(true); 
      }
    };

    checkSession(); 
  }, []); 

  const handleLogin = () => setIsLoggedIn(true);
  const handleCategoryChange = (newCategory) => setCategory(newCategory);

  return (
    <Router>
      
      <Navigation 
        isLoggedIn={isLoggedIn} 
        setIsLoggedIn={setIsLoggedIn} 
        handleCategoryChange={handleCategoryChange}
        setMovies={setMovies}
        setPages={setPages}
      />
      <Routes>
        <Route path="/" element={<LoginRedirect isLoggedIn={isLoggedIn} />} />
        <Route path="/login" element={<LoginSignUp onLogin={handleLogin} />} />
        {/* <Route path="/home" element={<Home category={category} />} />  */}
        <Route path="/home" element={<Home 
            category={category} 
            movies={movies} 
            page={page} 
            setMovies={setMovies} 
            setPages={setPages} 
            setCategory={setCategory} 
          />}/>
        <Route path="/mood" element={isLoggedIn ? <Mood /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/profile" element={isLoggedIn ? <Profile /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/library" element={isLoggedIn ? <Library /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/details" element={<FilmDetails />} />
      </Routes>
    </Router>
  );
}

function LoginRedirect({ isLoggedIn }) {
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoggedIn || (isLoggedIn && window.location.pathname === '/login')) {
      navigate('/mood'); 
    } else {
      navigate('/login'); 
    }
  }, [isLoggedIn, navigate]);

  return null;
}

export default App;
