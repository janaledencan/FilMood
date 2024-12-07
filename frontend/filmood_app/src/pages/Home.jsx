import React, { useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import mockedFilms from '../components/assets/mockedFilms';
import { Splide, SplideSlide } from "@splidejs/react-splide";
import "@splidejs/react-splide/css";
import styled from "styled-components";
import MovieCard from '../components/MovieCard';

function Home() {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        // Fetch movies from the backend
        fetch('/api/movies') // Replace with API endpoint
            .then((res) => res.json())
            .then((data) => setMovies(data))
            .catch((err) => console.error('Error fetching movies:', err));
    }, []);

    return (
        <Container>
            <Wrapper>
                <h2>Movie Recommendations</h2>

                <Splide
                    options={{
                        perPage: 4,
                        arrows: false,
                        drag: "free",
                        gap: "1rem", // Default gap between slides
                        breakpoints: {
                            1400: { perPage: 4, gap: "5rem" }, // For screens <= 1400px
                            1200: { perPage: 3, gap: "2rem" },
                            1000: { perPage: 2, gap: "2rem" },
                            800: { perPage: 2, gap: "2.5rem" },    // For screens <= 800px
                            770: { perPage: 2, gap: "4 rem" },
                            450: { perPage: 1, gap: "3rem" },    // For screens <= 450px
                        },
                    }}
                >
                    {mockedFilms.map((movie) => (
                        <SplideSlide key={movie.id}>
                            <MovieCard movie={movie} />
                        </SplideSlide>
                    ))}
                </Splide>
            </Wrapper>
        </Container>
    );
}

export default Home;

const Wrapper = styled.div`
      margin: 4rem 0rem;
    `;

