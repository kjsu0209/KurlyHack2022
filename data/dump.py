import json
import os
import requests

PATH_DIR = './amore_reviews'
API_HOST = "http://localhost:8080"


def post(host, data):
    header = {
        'Content-type': 'application/json'
    }
    res = requests.post(host, data=json.dumps(data), headers=header)
    res.raise_for_status()


def get_gender(gender):
    if gender == "남성":
        return "m"
    else:
        return "f"


def insert_data(data):
    item = {
        "item_id": data["item_id"],
        "item_img": data["item_img"],
        "brand_name": data["brand_name"],
        "category_id": data["category_id"],
        "item_name": data["item_name"],
        "price": int(''.join(data["price"].split(','))),
        "has_sample": True
    }
    post(API_HOST + "/item", item)

    review_limit = 100
    review_cnt = 1
    for r in data["review"]:
        review = {
            "user_id": r["user_id"],
            "user_rating": int(r["user_rating"]),
            "age_group": int(r["user_attr"]["age_group"]) if "age_group" in r["user_attr"] else None,
            "gender": get_gender(r["user_attr"]["gender"]) if "gender" in r["user_attr"] else None,
            "skin_type": r["user_attr"]["skin_type"] if "skin_type" in r["user_attr"] else None,
            "skin_info": r["user_attr"]["skin_info"] if "skin_info" in r["user_attr"] else None,
            "skin_tone": None,
            "review": r["review"],
            "reordered": False,
            "is_sample": False,
            "item_id": item["item_id"]
        }
        post(API_HOST + "/review", review)

        user = {
            "user_id": r["user_id"],
            "password": "kurly2022",
            "age_group": int(r["user_attr"]["age_group"]) if "age_group" in r["user_attr"] else None,
            "gender": get_gender(r["user_attr"]["gender"]) if "gender" in r["user_attr"] else None,
            "skin_type": r["user_attr"]["skin_type"] if "skin_type" in r["user_attr"] else None,
            "skin_info": r["user_attr"]["skin_info"] if "skin_info" in r["user_attr"] else None,
            "skin_tone": None
        }
        try:
            post(API_HOST + "/user", user)
        except Exception as e:
            print(r["user_id"])
            print(e)

        review_cnt += 1
        if review_limit <= review_cnt:
            break


def main():
    file_list = os.listdir(PATH_DIR)

    for file_name in file_list:
        file_location = './amore_reviews/' + file_name
        with open(file_location, 'r', encoding='utf-8') as fp:
            data = json.load(fp)
            for d in data["data"]:
                insert_data(d)
            print(f"{len(data['data'])} data dumped")


if __name__ == '__main__':
    main()
