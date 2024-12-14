INSERT INTO movie (id, title, genres,
                          movie_id, user_id, year, vote_average, user_rating, poster_path,
                          version, created_at, modified_at)
VALUES
    ('c30b81f2-6761-4b32-a4c9-464ab0cd7e40', 'Something4', 'Action,Drama',
     1, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 5,
     '/existing.jpg', 1, '2024-05-29 10:00:00', CURRENT_TIMESTAMP),
    ('8b5ecc07-ad92-4a4c-97e1-b63021d25952', 'Something3', 'Action,Drama',
     2, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 5,
     '/existing.jpg', 1, '2024-05-29 08:00:00', CURRENT_TIMESTAMP),
    ('bca52141-2809-42e9-925c-9ce73da29b9e', 'Something2', 'Action,Drama',
     4, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 3,
     '/existing.jpg', 1, '2024-05-29 09:00:00', CURRENT_TIMESTAMP),
    ('21288885-5c28-41ac-a97d-97309bf484d4', 'Something1', 'Action,Drama',
     3, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 5,
     '/existing.jpg', 1, '2024-05-29 07:00:00', CURRENT_TIMESTAMP),
    ('9e44cf21-6142-4c66-adb4-6d61bc626339', 'Something0', 'Action,Drama',
     5, '0571c9b5-6212-40b2-8d2b-51b3cba72a77', 2022, 6.8, 0,
     '/existing.jpg', 1, '2024-05-29 07:00:00', CURRENT_TIMESTAMP)