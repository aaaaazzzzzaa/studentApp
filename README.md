# 응답 에러 모델 만들기 (StudentApp)

---

## 요구사항
1. Controller에서 응답 모델로 만들어 주어야 한다.
   1. ApiResponse<T> : \
      여러 가지 데이터 타입(클래스)를 result로 넣을 수 있도록 제네릭 사용
   2. makeResponse(T result), makeResponse(List<T> results) : \
      결과를 응답 객체로 만들어주기 위한 메서드 (단건, 복구건 모두 구현)
2. 에러 응답을 만들기 위해 @ExceptionHandler를 사용하여 exception의 데이터를 이용해야 한다.
3. exceptionHandler에서 응답 모델을 만들 때 필요한 데이터가 포함시킬 수 있는 customException을 구현해야 한다.
    1. ex, CustomException(ErrorCode, message, data) - ErrorCode는 enum으로 정의

---

## API
```
    POST /student?name=kim&grade=1 # 생성 (2000)
    POST /student?name=kim&grade=7 # 에러 (5000)
    GET  /students                 # 전체 조회
    GET  /students/{grade}         # 특정 성적 조회
```

```json
# 생성 (2000)
# localhost:8080/student?name=test&grade=3
# localhost:8080/student?name=kim&grade=1
{
  "status": {
    "code": 2000,
    "message": "OK"
  },
  "results": [
    {
      "name": "kim",
      "grade": 1
    },
    {
      "name": "test",
      "grade": 3
    }
  ],
  "metadata": {
      "resultCount": 2
  }
}
```

```json
# 전체 조회
# localhost:8080/students
{
  "status": {
    "code": 2000,
    "message": "OK"
  },
  "results": [
    {
      "name": "kim",
      "grade": 1
    },
    {
      "name": "test",
      "grade": 3
    }
  ],
  "metadata": {
    "resultCount": 2
  }
}
```

```json
# 생성 에러(5000)
# localhost:8080/student?name=test&grade=9
{
  "status": {
    "code": 5000,
    "message": "BAD REQUEST"
  },
  "data": {
    "InputRestriction": {
      "maxGrade": 6
    }
  }
}
```

