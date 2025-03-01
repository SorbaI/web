
INSERT INTO clients (full_name, contact_info) VALUES
('Alice Smith', '{"alice.smith@email.com", "+1-555-123-4567"}'),
('Bob Johnson', '{"bob.johnson@email.com", "+1-555-987-6543"}'),
('Charlie Brown', '{"charlie.brown@email.com"}'),
('Diana Miller', '{"diana.miller@email.com", "+1-555-246-8013"}'),
('Eve Wilson', '{"eve.wilson@email.com", "+1-555-135-7924"}');

INSERT INTO books (title, authors, pub_year, pub_company, pages, price, available, cover) VALUES
('The Lord of the Rings', 'J.R.R. Tolkien', 1954, 'Allen & Unwin', 1178, 1250, 5, 'Hardcover'),
('Pride and Prejudice', 'Jane Austen', 1813, 'T. Egerton', 432, 675, 10, 'Softcover'),
('1984', 'George Orwell', 1949, 'Secker & Warburg', 328, 400, 3, 'Softcover'),
('The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', 1979, 'Pan Books', 224, 500, 7, 'Softcover'),
('Dune', 'Frank Herbert', 1965, 'Chilton Books', 604, 740, 2, 'Hardcover');

INSERT INTO orders (creation_time, client_id, address, deliv_time, status, total) VALUES
(NOW() - INTERVAL '1 day', 1, '123 Main St, Anytown', NOW() + INTERVAL '2 days', 'assembled', 1925),
(NOW() - INTERVAL '3 hours', 2, '456 Oak Ave, Somecity', NOW() + INTERVAL '1 day', 'assembled', 900),
(NOW() - INTERVAL '30 minutes', 3, '789 Pine Ln, Othertown', NOW() + INTERVAL '12 hours', 'processed', 1250),
(NOW() - INTERVAL '2 days', 4, '101 Elm Rd, Anytown', NOW() - INTERVAL '1 day', 'delivered', 1990),
(NOW() - INTERVAL '5 days', 5, '222 Maple Dr, Somecity', NOW() - INTERVAL '3 days', 'delivered', 2325);

INSERT INTO books_in_order (order_id, book_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 1),
(4, 5),
(5, 2),
(5, 3),
(5, 1),
(4, 1);