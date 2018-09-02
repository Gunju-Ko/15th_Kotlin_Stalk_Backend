# Stalk

## 15th_Kotlin_Stalk_Backend
15기 슬립스터디 프로젝트

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

비공개 키가 존재하지 않으면, 어플리케이션 실행시 예외가 발생합니다.

> 비공개 키 파일은 서비스 계정의 비공개 암호화 키와 같은 민감한 정보를 포함합니다. 항상 기밀로 유지하고 공개 저장소에는 저장하지 마세요

#### gradlew
```bash
./gradlew bootRun 
```

## Test

#### gradlew
```bash
./gradlew test 
```

#### Test API

어플리케이션 실행 후 SAMPLE API를 호출해보세요.

##### curl

``` 
curl -X GET http://localhost:8080/members/1
```

##### response

```json
{
    "memberId": "gunju",
    "password": "test",
    "name": "고건주",
    "email": "gunjuko92@gmail.com"
}
```