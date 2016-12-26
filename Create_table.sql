CREATE TABLE tbl_notes
  (
	_id INTEGER PRIMARY KEY, 
	body TEXT,
	subject TEXT,
	datak TEXT
  )
  
CREATE TRIGGER t_tbl_note_i AFTER INSERT ON tbl_notes
FOR EACH ROW
BEGIN
  UPDATE tbl_notes SET datak = CURRENT_TIMESTAMP
    WHERE _id = new._ID;
END