# Stalk

## 15th_Kotlin_Stalk_Backend
15기 슬립스터디 프로젝트입니다.

백엔드 서버를 위한 저장소이며 아래 링크에 가시면 관련 프로젝트 코드를 보실 수 있습니다.

* [15th_Kotlin_Stalk_Front](https://github.com/RudiaMoon/15th_Kotlin_Stalk_Front)


## 개발 환경
- java 1.8
- spring-boot : 2.0.4

## IDE

#### IntelliJ
1. IntellJ
  * `gradle` 설정 시 `Create seperate module per source set` 옵션을 반드시 off 해주세요. 
2. CodeStyle 설정
  * `Preferences -> Editor -> Code Style -> Scheme 설정 버튼 -> Import Scheme -> IntelliJ IDEA code style XML -> (Project ROot)config/intellij-codestyle.xml 선택`
  
## Build

#### gradlew
```bash
./gradlew build
```

## Run

#### serviceAccountKey.json 파일

어플리케이션을 실행 시키기 위해서는 Firebase Admin SDK 비공개 키가 필요합니다. 비공개키는 github 저장소에 존재하지 않습니다.
만약 어플리케이션을 실행 시키고자 하신다면, 저에게 연락 주세요.

비공개 키는 /src/main/resources 폴더 밑에 위치해야 하며, serviceAccountKey.json 이름이어야 합니다.

비공개 키가 존재하지 않으면, Firebase을 통해 메시지를 전송할 수 없습니다.

> 비공개 키 파일은 서비스 계정의 비공개 암호화 키와 같은 민감한 정보를 포함합니다. 항상 기밀로 유지하고 공개 저장소에는 저장하지 마세요

#### Quick Start in MAC

```
git clone https://github.com/Gunju-Ko/15th_Kotlin_Stalk_Backend.git
cd 15th_Kotlin_Stalk_Backend
./gradlew build -x test
./gradlew bootRun 
```

#### Quick Start in Window

```
git clone https://github.com/Gunju-Ko/15th_Kotlin_Stalk_Backend.git
cd 15th_Kotlin_Stalk_Backend
gradlew.bat build -x test
gradlew.bat bootRun
```

## APIS

### 로그인

##### 요청

```
curl -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{
	"email" : "gunjuko92@gmail.com",
	"password" : "test123",
	"token" : "cvwvUUoYeXM:APA91bH0EnvPXe5u6mb-RDi3ECdQBUZYegIGCGnMup6Ml9BFkzHjAzRD3gPQA8snCg1dqLdVgUnHAZX1xe-NhDU9x_Faqtjj4W9QRZsx35e_kYDH1GGvwpyR-3_emrTcVY9nuDw9U-TAX0gmjJQ5Sau_U_kkcvwjMg"
}'

```

* email : 유저 이메일
* password : 유저 패스워드
* token : Firebase 토큰

##### 응답

* 200 OK : 로그인 성공

```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1NDAxMDA4MTcsImlhdCI6MTU0MDAxNDQxNywic3ViIjoiZ3VuanVrbzkyQGdtYWlsLmNvbSJ9.ksRQuEkhagcwNwmyVB-2tKiThQLRwt1wERRuJp4iaaxr0Ybcxy2cccT7P36DnGb48tE86xsjYnaj8OgbaxG9og"
}
```

* token : JWT 토큰 (이후에 요청은 헤더로 JWT 토큰을 전달해줘야함)

### 로그아웃

##### 요청

```
curl -X POST \
  http://localhost:8080/logout \
  -H 'Authentication: eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1NDAxMDA4MTcsImlhdCI6MTU0MDAxNDQxNywic3ViIjoiZ3VuanVrbzkyQGdtYWlsLmNvbSJ9.ksRQuEkhagcwNwmyVB-2tKiThQLRwt1wERRuJp4iaaxr0Ybcxy2cccT7P36DnGb48tE86xsjYnaj8OgbaxG9og' \
  -H 'Content-Type: application/json' \
  -d '{
	"token" : "cvwvUUoYeXM:APA91bH0EnvPXe5u6mb-RDi3ECdQBUZYegIGCGnMup6Ml9BFkzHjAzRD3gPQA8snCg1dqLdVgUnHAZX1xe-NhDU9x_Faqtjj4W9QRZsx35e_kYDH1GGvwpyR-3_emrTcVY9nuDw9U-TAX0gmjJQ5Sau_U_kkcvwjMg"
}'
```

* token : Firebase 토큰

##### 응답

* 204 NO Content : 로그아웃 성공
