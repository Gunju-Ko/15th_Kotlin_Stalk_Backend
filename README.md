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