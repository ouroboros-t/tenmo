SELECT * FROM accounts WHERE account_id = 2001;

BEGIN TRANSACTION;
UPDATE accounts SET balance = balance + 500 WHERE account_id = 2001;
ROLLBACK;


INSERT INTO transfers(transfer_type_id,transfer_status_id, account_from, account_to,amount) VALUES(2,2,2002,2001,25);

UPDATE accounts SET balance = balance - 25 WHERE account_id = 2002;

SELECT * FROM transfers AS t JOIN accounts AS a ON t.account_from = a.account_id JOIN users AS u ON a.user_id = u.user_id;


SELECT account_id, user_id, balance FROM accounts WHERE user_id = 1002;

--Everything for a transfer detail:

SELECT t.transfer_id, t.account_from, t.account_to, t.amount, 
ts.transfer_status_id, ts.transfer_status_desc, 
tt.transfer_type_id, tt.transfer_type_desc,
a1.account_id,a1.balance,
u.user_id, u.username
FROM transfers AS t
JOIN accounts AS a1 ON t.account_from = a1.account_id
JOIN users AS u ON a1.user_id = u.user_id
JOIN accounts AS a2 ON a2.user_id = u.user_id
JOIN transfer_statuses AS ts ON t.transfer_status_id = ts.transfer_status_id
JOIN transfer_types AS tt ON t.transfer_type_id = tt.transfer_type_id;
