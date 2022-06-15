INSERT INTO users (id, email, first_name, last_name, password, password_reset_token, user_name, user_role) VALUES ('9b77aab1-474c-4e32-ae47-7fde4c269264', 'marko.stojakovic17@gmail.com', 'marko', 'markovic', '$2a$10$aWj/FCrxrQbotTEpSeVO7.ooBdCaPwN5dmzfNL8sU8vGoxhq08QJ2', null, 'markoooooo12222', 'ROLE_CLIENT');
INSERT INTO users (id, email, first_name, last_name, password, password_reset_token, user_name, user_role) VALUES ('1319752b-24f1-4094-9896-62d82fc521d4', 'john@gmail.com', 'John', 'Johnson', '$2a$10$n42E3Sc/k2X.0HLQNeMBxejTVsUGnj/1FND2IG8aD77bX3L9Af9Wi', null, 'John', 'ROLE_ADMIN');
INSERT INTO users (id, email, first_name, last_name, password, password_reset_token, user_name, user_role) VALUES ('bef00021-0558-4a59-ac42-0ea45fbffad8', 'mark@gmail.com', 'Mark', 'mark', '$2a$10$Uv5oSpJfzuXkzpEvQbHgSO.L2BQ4fvxDGrnavOn5AZWc9jqXCHnwu', null, 'mark', 'ROLE_CLIENT');

INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('0c54590d-522e-452c-bff2-1e2fe6e869de', true, true, 'testcity', 'test', 2, 2, true, 1000, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('97fd889f-5b34-432e-b8e2-cf4d66dbe4ba', true, true, 'testcity', 'test', 2, 2, true, 1000, true, '1319752b-24f1-4094-9896-62d82fc521d4');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('dea5d005-b3e2-4d43-babc-1b5d516e4984', true, true, 'testcity', 'test', 2, 2, true, 1000, true, '1319752b-24f1-4094-9896-62d82fc521d4');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('9dd392f3-3417-483b-8e2c-5ee80a134e2b', true, true, 'street0', 'property0', 2, 4, false, 100, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('c4465c8b-4c0c-4ad5-8e9c-f2ab5332dab1', true, true, 'street1', 'property1', 2, 4, false, 101, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('b51dd406-4673-4f78-a5e5-aeb48da648fe', true, true, 'street2', 'property2', 2, 4, false, 102, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('356600e1-3686-418a-b6d1-13a9aa76bfb3', true, true, 'street3', 'property3', 2, 4, false, 103, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('e7034ced-013e-4344-9e35-a424fa9ea685', true, true, 'street4', 'property4', 2, 4, false, 104, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('5e4bef3b-b03a-48a9-bd6d-cd8dee3bd34a', true, true, 'street5', 'property5', 2, 4, false, 105, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('c930f9c6-1c1b-4c78-b780-eb2eb9531dd0', true, true, 'street6', 'property6', 2, 4, false, 106, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('98df91a1-9b1f-4bfe-9b27-9f46a02bc06b', true, true, 'street7', 'property7', 2, 4, false, 107, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('b12e7a9f-a762-4ec6-94b3-2f41a08e8d46', true, true, 'street8', 'property8', 2, 4, false, 108, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('35652f99-8076-4a80-ba13-7c9a897d54ae', true, true, 'street9', 'property9', 2, 4, false, 109, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('140b057a-deb3-4a83-8fbb-b5f0c3add8dd', true, true, 'street10', 'property10', 2, 4, false, 110, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('16d80854-bc30-4d2c-b7bb-135cc129abbc', true, true, 'street11', 'property11', 2, 4, false, 111, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('8761b703-27ad-4953-a2af-3eb4bd94fab5', true, true, 'street12', 'property12', 2, 4, false, 112, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('9f092579-717c-456a-9432-11301ca7bb24', true, true, 'street13', 'property13', 2, 4, false, 113, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('9f48cc4b-0fbb-4163-bf7c-d8b7461a9363', true, true, 'street14', 'property14', 2, 4, false, 114, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('e378804d-d63c-4a65-8be2-30f47ef7f552', true, true, 'street15', 'property15', 2, 4, false, 115, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('3a7e9dfb-0a98-4e90-906b-7e86f8140f6e', true, true, 'street16', 'property16', 2, 4, false, 116, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('74990989-a6d8-4498-9a8f-ce04e1ae7391', true, true, 'street17', 'property17', 2, 4, false, 117, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('2f80213e-1387-46fb-a938-9527bb80d947', true, true, 'street18', 'property18', 2, 4, false, 118, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
INSERT INTO property (id, availability, free_parking, location, name, num_of_bedrooms, num_of_sleep_place, pool, price, wifi, owner_id) VALUES ('5e5c1662-8999-44e1-bea5-52b01f9e0245', true, true, 'street19', 'property19', 2, 4, false, 119, true, 'bef00021-0558-4a59-ac42-0ea45fbffad8');
