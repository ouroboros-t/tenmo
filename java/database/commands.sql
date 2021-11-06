SELECT * FROM accounts WHERE account_id = 2001;

BEGIN TRANSACTION;
UPDATE accounts SET balance = balance + 500 WHERE account_id = 2001;
ROLLBACK;


INSERT INTO transfers(transfer_type_id,transfer_status_id, account_from, account_to,amount) VALUES(2,2,2002,2001,25);

UPDATE accounts SET balance = balance - 25 WHERE account_id = 2002;

SELECT * FROM transfers AS t JOIN accounts AS a ON t.account_from = a.account_id JOIN users AS u ON a.user_id = u.user_id;


SELECT account_id, user_id, balance FROM accounts WHERE user_id = 1002;

--Everything for a transfer detail:
SELECT t1.transfer_id, 
t1.amount,
userFrom.user_id AS user_from_id, userFrom.username AS user_from_username,
userTo.user_id AS user_to_id, userTo.username AS user_to_username,
ts.transfer_status_id, ts.transfer_status_desc,
tt.transfer_type_id, tt.transfer_type_desc
FROM transfers AS t1
JOIN accounts AS acctFrom ON t1.account_from = acctFrom.account_id
JOIN users AS userFrom ON acctFrom.user_id = userFrom.user_id
JOIN accounts AS acctTo ON t1.account_to = acctTo.account_id
JOIN users AS userTo ON acctTo.user_id = userTo.user_id
JOIN transfer_statuses AS ts ON t1.transfer_status_id = ts.transfer_status_id
JOIN transfer_types AS tt ON t1.transfer_type_id = tt.transfer_type_id;
