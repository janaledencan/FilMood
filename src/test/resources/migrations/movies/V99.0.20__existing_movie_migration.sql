INSERT INTO movie (id, title, genres,
                          movie_id, user_id, year, vote_average, user_rating, poster_path,
                          version, created_at, modified_at)
VALUES
    ('9e44cf21-6142-4c66-adb4-6d61bc626339', 'Something', 'Action,Drama',
     400, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 0,
     '/existing.jpg', 1, '2024-05-29 07:00:00', CURRENT_TIMESTAMP)