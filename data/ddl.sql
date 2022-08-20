DROP TABLE IF EXISTS "USER";
CREATE TABLE "USER" (
                      USER_ID VARCHAR(20) PRIMARY KEY,
                      PASSWORD VARCHAR(20),
                      GENDER VARCHAR(1),
                      SKIN_TYPE VARCHAR(50),
                      AGE_GROUP INT,
                      SKIN_INFO VARCHAR(50),
                      SKIN_TONE VARCHAR(50)
);

DROP TABLE IF EXISTS "REVIEW";
CREATE TABLE "REVIEW" (
                        REVIEW_ID SERIAL PRIMARY KEY,
                        ITEM_ID INT,
                        PURCHASE_ID VARCHAR(20),
                        USER_RATING INT,
                        USER_ID VARCHAR(20),
                        GENDER VARCHAR(1),
                        SKIN_TYPE VARCHAR(50),
                        AGE_GROUP INT,
                        SKIN_INFO VARCHAR(50),
                        SKIN_TONE VARCHAR(50),
                        review TEXT,
                        is_sample BOOLEAN,
                        reordered BOOLEAN,
                        dt TIMESTAMP DEFAULT now()
);

DROP TABLE IF EXISTS "ITEM";
CREATE TABLE "ITEM" (
                      item_id SERIAL PRIMARY KEY,
                      category_id VARCHAR(10),
                      item_img VARCHAR(80),
                      brand_name VARCHAR(30),
                      item_name VARCHAR(100),
                      price INT,
                      has_sample BOOLEAN
);

DROP TABLE IF EXISTS "CATEGORY";
CREATE TABLE "CATEGORY" (
                          category_id VARCHAR(10) PRIMARY KEY,
                          category_name VARCHAR(20)
);

DROP TABLE IF EXISTS "BAG";
CREATE TABLE "BAG" (
                     bag_id SERIAL PRIMARY KEY,
                     user_id VARCHAR(20),
                     is_sample BOOLEAN,
                     status VARCHAR(20),
                     count INT,
                     dt DATE DEFAULT now()
);

DROP TABLE IF EXISTS "PURCHASE";
CREATE TABLE "PURCHASE" (
                          purchase_id SERIAL PRIMARY KEY,
                          user_id VARCHAR(20),
                          purchase_dt DATE DEFAULT now(),
                          delivery_fee INT,
                          payment_amount INT
);