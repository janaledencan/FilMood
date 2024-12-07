import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navigation from './components/Navigation';
import LoginSignUp from './pages/LoginSignUp';
import Home from './pages/Home';
import Profile from './pages/Profile';
import Mood from './pages/Mood';
import FilmDetails from "./pages/FilmDetails";
import Wishlist from './pages/Wishlist';
import logo from './logo.svg';
import './App.css';

function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [category, setCategory] = useState('now_playing'); // Default category

  const handleLogin = () => setIsLoggedIn(true);
  const handleCategoryChange = (newCategory) => setCategory(newCategory);

  return (
    <Router>
      <Navigation isLoggedIn={isLoggedIn} handleCategoryChange={handleCategoryChange} />
      <Routes>
        <Route path="/login" element={<LoginSignUp onLogin={handleLogin} />} />
        <Route path="/" element={<Home category={category} />} />
        <Route path="/mood" element={!isLoggedIn ? <Mood /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/profile" element={!isLoggedIn ? <Profile /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/wishlist" element={!isLoggedIn ? <Wishlist /> : <LoginSignUp onLogin={handleLogin} />} />
        <Route path="/details" element={<FilmDetails />} />
      </Routes>
    </Router>
  );
}

export default App;
