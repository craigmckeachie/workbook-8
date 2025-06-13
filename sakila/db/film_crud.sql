INSERT INTO film
(
`title`,
`language_id`
)
VALUES
(
'MISSION IMPOSSIBLE',
 1
);

update film
set
	title = 'title update',
    release_year = 2025,
    language_id = 2
where film_id = 1004;



