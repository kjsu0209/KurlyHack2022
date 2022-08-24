# KurlyHack2022
추냥이 프로젝트의 api 서버, 데이터 repository입니다.
개발기간: 2022.08.19 ~ 2022.08.24


### 프로젝트 구조
- api: spring boot api server with JPA
- data: 데이터 크롤링, 덤프용 코드 (추후 배치에 적용 가능)

### 제공 api
- user
```
GET /user?userId={user_id}
POST /user
POST /login
PUT /user
```
- item & recommend
```
GET /item/{item_id}
GET /item/category/{category_id}

GET /recommend/sample/{user_id}
GET /recommend/item/{user_id}
GET /recommend/user/{user_id}
```
- bag
```
POST /bag
PUT /bag
GET /bag/{user_id}
```

- review
```
GET /review/{item_id}
GET /review/user/{user_id}
POST /review
```

- purchase
```
POST /purchase
GET /purchase/{user_id}?start=20220820&end=20220831&sample=true&review=false

```

### api

#### before build

- maven 설치 ([가이드(https://charliecharlie.tistory.com/308)])
- java 8

#### build & run
```
## sh 디렉터리로 이동 
cd /../sh
## build & run
sudo sh run2.sh
```
