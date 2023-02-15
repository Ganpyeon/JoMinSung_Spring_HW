Spring 입문 주차

유스케이스 다이어 그램

![유스케이스 과제1](https://user-images.githubusercontent.com/121671967/218910687-4e78dba7-8806-4f50-b700-86057e917ec7.png)

API 명세서

|기능|Method|URL|request|response|
|:---|:---|:---|:---|:---|
|전체 글 조회|GET|/api/boards|-|{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|게시물 작성|POST|/api/boards|{<br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"password" = "비밀번호"<br>}|{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|선택한 게시물 조회|GET|/api/boards/{id}|-|{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|게시물 수정|PUT|/api/boards/{id}|{<br>"title" = "수정 제목",<br> <br>"contents" = "수정 작성내용",<br> <br>"username" = "수정 작성자"<br>}|{<br>"id" = 1,<br> <br>"title" = "수정 제목",<br> <br>"contents" = "수정 내용",<br> <br>"username" = "수정 작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|게시물 삭제|DELETE|/api/boards/{id}|{<br>"password" = "비밀번호"<br>}|{<br>String = "게시물 삭제 완료"<br>}|


Spring 숙련 주차

유스케이스 다이어 그램

![유스케이스](https://user-images.githubusercontent.com/121671967/218906591-34697b9b-1ea5-49b0-9835-75eab88874a4.png)


ERD

![ERD](https://user-images.githubusercontent.com/121671967/218906603-ddd543f6-b944-4e1c-8f6f-ecf091bcedd6.png)

API 명세서

|기능|Method|URL|request_header|request|response|response_header|
|:---|:---|:---|:---|:---|:---|:---|
|전체 글 조회|GET|/api/boards|-|-|{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|-|
|게시물 작성|POST|/api/boards|-|{<br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> |{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|선택한 게시물 조회|GET|/api/boards/{id}|-|{<br>"id" = 1,<br> <br>"title" = "제목",<br> <br>"contents" = "작성내용",<br> <br>"username" = "작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|-|
|게시물 수정|PUT|/api/boards/{id}|-|{<br>"title" = "수정 제목",<br> <br>"contents" = "수정 작성내용",<br>}|{<br>"id" = 1,<br> <br>"title" = "수정 제목",<br> <br>"contents" = "수정 내용",<br> <br>"username" = "수정 작성자",<br> <br>"createdAt" = "작성 날짜",<br> <br>"modifiedAt" = "수정 날짜"<br>}|
|게시물 삭제|DELETE|/api/boards/{id}|-|-|{<br>String = "게시물 삭제 완료"<br>}|-|
|회원 가입|POST|/api/user/signup|-|<br>"username" = "u1234",<br> <br>"password" = "user1234",<br>|{<br>statuscode = "200"<br>, <br>msg = "로그인이 완료 되었습니다."<br>}|-|
|로그인|POST|/api/user/login|-|{<br>"username" = "u1234",<br> <br>"password" = "user1234",<br>}|{<br>statuscode = "200"<br>, <br>msg = "로그인이 완료 되었습니다."<br>}|-|
