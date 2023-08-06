INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name1','test_description1', 10,1);

INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name2','test_description2', 10,1);

INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name3','test_description3', 10,1);


INSERT INTO tag (tag_name)
VALUES ('#test1');
INSERT INTO tag (tag_name)
VALUES ('#test2');
INSERT INTO tag (tag_name)
VALUES ('#test3');
INSERT INTO tag (tag_name)
VALUES ('#test4');
INSERT INTO tag (tag_name)
VALUES ('#test5');

INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1,1);
INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1,2);
INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (2,3);

