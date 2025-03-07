INSERT INTO clients (client_id,full_name, address, email, phone) VALUES
(1,'Harry Potter', '4 Privet Drive, Little Whinging, Surrey GU21 4WZ, UK', 'theboywholived@example.com', '+44 7700 900000'),
(2,'Hermione Granger', '14 Heathgate, Hampstead Garden Suburb, London NW11 7AP, UK', 'hermione.granger@example.com', '+44 7700 900001'),
(3,'Ron Weasley', 'The Burrow, Ottery St Catchpole, Devon TQ13 0FF, UK', NULL, '+44 7700 900002'),
(4,'Albus Dumbledore', 'Hogwarts School of Witchcraft and Wizardry, Scottish Highlands, UK', NULL, '+44 7700 900003'),
(5,'Severus Snape', 'Spinner''s End, Cokeworth, UK', 'severus.snape@example.com', '+44 7700 900004'),
(6,'Luna Lovegood', 'Near Ottery St Catchpole, Devon TQ13 0FF, UK', 'loonylovegood@example.com', '+44 7700 900005');


INSERT INTO books (book_id,title, authors, genre, pub_year, pub_company, pages, price, available, cover, add_info) VALUES
(1,'Fantastic Beasts and Where to Find Them', 'Newt Scamander', 'Textbook', 1927, 'Obscurus Books', 144, 450, 10, 'Softcover', 'Hogwarts book, various editions exist'),
(2,'Quidditch Through the Ages', 'Kennilworthy Whisp', 'History', 1952, 'WhizzHard Books', 112, 350, 5, 'Hardcover', 'History and rules of Quidditch'),
(3,'Advanced Potion-Making', 'Libatius Borage', 'Textbook', 1972, 'Little Red Books', 400, 750, 2, 'Hardcover', 'Hogwarts book, annotated by the Half-Blood Prince'),
(4,'The Monster Book of Monsters', 'Edwardus Lima', 'Textbook', 1993, 'Obscurus Books', 150, 900, 0, 'Hardcover', 'Hogwarts book, bites anyone who tries to open it improperly'),
(5,'A History of Magic', 'Bathilda Bagshot', 'History', 1947, 'Little Red Books', 500, 650, 7, 'Hardcover', 'Hogwarts book, used by Harry and his classmates'),
(6,'Magical Drafts and Potions', 'Arsenius Jigger', 'Textbook', 1963, 'Potions Publishing', 350, 600, 3, 'Hardcover', 'Hogwarts book for potions'),
(7,'The Standard Book of Spells, Grade 1', 'Miranda Goshawk', 'Textbook', 1981, 'Standard Spellbooks', 200, 500, 12, 'Softcover', 'Required reading for first-year students'),
(8,'The Standard Book of Spells, Grade 2', 'Miranda Goshawk', 'Textbook', 1986, 'Standard Spellbooks', 220, 800, 8, 'Softcover', 'Required reading for second-year students'),
(9,'Hogwarts: A History', 'Miranda Slytherin', 'History', 1880, 'Hogwarts Educational Office', 800, 1000, 1, 'Hardcover', 'Comprehensive history of the school, read by Hermione Granger'),
(10,'The Tales of Beedle the Bard', 'Beedle the Bard', 'Fairytale', 1522, 'Wizarding Press', 100, 100, 4, 'Hardcover', 'Collection of wizarding fairytales');


INSERT INTO orders (order_id,creation_time, client_id, address, deliv_time, status, total) VALUES
(1,'2023-10-27 10:00:00', 1, '4 Privet Drive, Little Whinging, Surrey GU21 4WZ, UK', '2023-10-28 14:00:00', 'delivered', 800),
(2,'2023-10-27 11:30:00', 1, '14 Heathgate, Hampstead Garden Suburb, London NW11 7AP, UK', '2023-10-28 16:00:00', 'delivered', 2400),
(3,'2023-10-27 13:45:00', 3, 'The Burrow, Ottery St Catchpole, Devon TQ13 0FF, UK', '2023-10-29 11:00:00', 'pending', 900),
(4,'2023-10-28 09:15:00', 4, 'Hogwarts School of Witchcraft and Wizardry, Scottish Highlands, UK', '2023-10-30 10:00:00', 'processed', 1100),
(5,'2023-10-28 12:00:00', 5, 'Spinner''s End, Cokeworth, UK', '2023-10-29 14:00:00', 'delivered', 2250);

INSERT INTO books_in_order (order_id, book_id, num_of_books) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 2),
(2, 4, 1),
(3, 1, 2),
(4, 5, 1),
(4, 1, 1),
(5, 1, 5);
