
INSERT INTO user_account (user_account_id,user_name, user_password, user_email,is_active, enabled, account_expired, credentials_expired, account_locked)
VALUES (1,'user1','test','user1@example.com','ACTIVE',1,0,0,0);

INSERT INTO orders(user_account_id,cost)
VALUES(1,10);