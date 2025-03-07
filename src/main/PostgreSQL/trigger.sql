CREATE OR REPLACE FUNCTION update_available_books()
RETURNS TRIGGER AS $$
BEGIN
  IF (TG_OP = 'INSERT') THEN
    UPDATE books
    SET available = available - NEW.num_of_books
    WHERE book_id = NEW.book_id;
  ELSIF (TG_OP = 'UPDATE') THEN
    UPDATE books
    SET available = available + OLD.num_of_books - NEW.num_of_books
    WHERE book_id = NEW.book_id;
  ELSIF (TG_OP = 'DELETE') THEN
    UPDATE books
    SET available = available + OLD.num_of_books
    WHERE book_id = OLD.book_id;
  END IF;

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS books_in_order_trigger ON books_in_order;
CREATE TRIGGER books_in_order_trigger
AFTER INSERT OR UPDATE OR DELETE
ON books_in_order
FOR EACH ROW
EXECUTE FUNCTION update_available_books();