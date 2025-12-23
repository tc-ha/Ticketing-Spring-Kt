DELETE FROM tickets;

-- Initialize Item with 100 tickets in stock
INSERT INTO item (id, stock_count) VALUES (1, 100)
ON DUPLICATE KEY UPDATE stock_count = 100;
