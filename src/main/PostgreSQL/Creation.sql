DROP TYPE IF EXISTS type_cover CASCADE;
CREATE TYPE type_cover AS ENUM ('Hardcover','Softcover');
DROP TYPE IF EXISTS order_status CASCADE;
CREATE TYPE order_status AS ENUM ('processed','pending','delivered');

DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE IF NOT EXISTS books(
	book_id serial PRIMARY KEY,
	title text NOT NULL,
	authors text,
	genre varchar(30),
	pub_year int,
	pub_company text,
	pages int CHECK(pages > 0),
	price int NOT NULL CHECK(price > 0),
	available int NOT NULL CHECK (available >= 0),
	cover type_cover NOT NULL,
	add_info text
);

DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE IF NOT EXISTS clients(
	client_id serial PRIMARY KEY,
	full_name text NOT NULL,
	address text NOT NULL,
	email varchar(256),
	phone varchar(20) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE IF NOT EXISTS orders(
	order_id serial PRIMARY KEY,
	creation_time timestamp NOT NULL,
	client_id int REFERENCES clients MATCH FULL,
	address text NOT NULL,
	deliv_time timestamp NOT NULL,
	status order_status NOT NULL,
	total int CHECK (total > 0)
);

DROP TABLE IF EXISTS books_in_order CASCADE;
CREATE TABLE IF NOT EXISTS books_in_order(
	order_id int REFERENCES orders MATCH FULL,
	book_id int REFERENCES books MATCH FULL,
	num_of_books int NOT NULL CHECK (num_of_books > 0)
);