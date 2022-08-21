set schema 'kurly_schema';

DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
                      user_id VARCHAR(20) PRIMARY KEY,
                      password VARCHAR(20),
                      gender VARCHAR(1),
                      skin_type VARCHAR(50),
                      age_group INT,
                      skin_info VARCHAR(50),
                      skin_tone VARCHAR(50)
);

DROP TABLE IF EXISTS "review";
CREATE TABLE "review" (
                        review_id SERIAL PRIMARY KEY,
                        item_id VARCHAR(20),
                        purchase_id VARCHAR(20),
                        user_rating INT,
                        user_id VARCHAR(20),
                        gender VARCHAR(1),
                        skin_type VARCHAR(50),
                        age_group INT,
                        skin_info VARCHAR(50),
                        skin_tone VARCHAR(50),
                        review TEXT,
                        is_sample BOOLEAN,
                        reordered BOOLEAN,
                        dt TIMESTAMP DEFAULT now()
);

DROP TABLE IF EXISTS "item";
CREATE TABLE "item" (
                      item_id VARCHAR(20) PRIMARY KEY,
                      category_id VARCHAR(10),
                      item_img VARCHAR(200),
                      brand_name VARCHAR(50),
                      item_name VARCHAR(200),
                      price INT,
                      has_sample BOOLEAN
);

DROP TABLE IF EXISTS "category";
CREATE TABLE "category" (
                          category_id VARCHAR(10) PRIMARY KEY,
                          category_name VARCHAR(20)
);

DROP TABLE IF EXISTS "bag";
CREATE TABLE "bag" (
                     bag_id SERIAL PRIMARY KEY,
                     user_id VARCHAR(20),
                     item_id VARCHAR(20),
                     purchase_id VARCHAR(20),
                     is_sample BOOLEAN,
                     status VARCHAR(20),
                     count INT,
                     dt DATE DEFAULT now()
);

DROP TABLE IF EXISTS "purchase";
CREATE TABLE "purchase" (
                          purchase_id VARCHAR(20) PRIMARY KEY,
                          user_id VARCHAR(20),
                          purchase_dt TIMESTAMP DEFAULT now(),
                          delivery_fee INT,
                          payment_amount INT
);

INSERT INTO "category" VALUES ('CTG001', '스킨케어');
INSERT INTO "category" VALUES ('CTG002', '메이크업');
INSERT INTO "category" VALUES ('CTG003', '네일&향수');
INSERT INTO "category" VALUES ('CTG004', '생활용품');
INSERT INTO "category" VALUES ('CTG005', '소품&도구');
INSERT INTO "category" VALUES ('CTG006', '뷰티푸드');
INSERT INTO "category" VALUES ('CTG007', '남성');
INSERT INTO "category" VALUES ('CTG008', '베이비');