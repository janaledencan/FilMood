import React from 'react';
import Container from 'react-bootstrap/Container';
import { Splide, SplideSlide } from "@splidejs/react-splide";
import "@splidejs/react-splide/css";
import styled from "styled-components";
import MovieCard from '../components/MovieCard';
import { useLocation  } from 'react-router-dom';

function Home({ category, movies }) {
    const location = useLocation();

    const locationCategory = location.state?.locationCategory;
    const locationMovies = location.state?.locationMovies;

    const displayCategory = category || locationCategory;
    const displayMovies = movies || locationMovies;
    
    if (!displayMovies || !displayCategory) {
        return <div>Loading...</div>; 
    }

    return (
        <Container>
            <Wrapper>
                <h2>{displayCategory.replace('_', ' ').toUpperCase()}</h2>

                <Splide
                    options={{
                        perPage: 4,
                        arrows: false,
                        drag: "free",
                        gap: "1rem",
                        breakpoints: {
                            1400: { perPage: 4, gap: "5rem" },
                            1200: { perPage: 3, gap: "2rem" },
                            1000: { perPage: 2, gap: "2rem" },
                            800: { perPage: 2, gap: "2.5rem" },
                            770: { perPage: 2, gap: "4 rem" },
                            450: { perPage: 1, gap: "3rem" },
                        },
                    }}
                >
                    {displayMovies.map((movie) => {
                        return (
                            <SplideSlide key={movie.movieId}>
                                <MovieCard movie={movie} />
                            </SplideSlide>
                        );
                    })}
                </Splide>
            </Wrapper>
        </Container>
    );
}

export default Home;

const Wrapper = styled.div`
    margin: 4rem 0rem;
`;
