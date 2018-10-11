/* INSERT MEMBER */
INSERT INTO member (ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_EMAIL) values ((select next value for hibernate_sequence), 'test123', '고건주', 'gunjuko92@gmail.com');
INSERT INTO member (ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_EMAIL) values ((select next value for hibernate_sequence), 'test123', '김민환', 'minhwan@gmail.com');

/* INSERT MESSAGE */
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '점심 먹자', 1);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '저녁 먹자', 1);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '출근함?', 1);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), 'YES', 1);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), 'NO', 1);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '점심 먹자', 2);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '저녁 먹자', 2);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), '출근함?', 2);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), 'YES', 2);
INSERT INTO message (ID, message, member_id) values ((select next value for hibernate_sequence), 'NO', 2);