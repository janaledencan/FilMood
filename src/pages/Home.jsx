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
                        gap: "2rem",
                        breakpoints: {
                            800: { perPage: 2 },
                            450: { perPage: 1 },
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

