Spring 입문 주차

유스케이스 다이어 그램

![유스케이스 과제1](https://user-images.githubusercontent.com/121671967/218910687-4e78dba7-8806-4f50-b700-86057e917ec7.png)

API 명세서

|기능|Method|URL|request|response|
|:---|:---|:---|:---|:---|
|전체 글 조회|GET|/api/boards|-|{"id" = 1, "title" = "제목", "contents" = "작성내용", "username" = "작성자", "createdAt" = "작성 날짜", "modifiedAt" = "수정 날짜"}|
|게시물 작성|POST|/api/boards|{"title" = "제목", "contents" = "작성내용", "username" = "작성자", "password" = "비밀번호"}|{"id" = 1, "title" = "제목", "contents" = "작성내용", "username" = "작성자", "createdAt" = "작성 날짜", "modifiedAt" = "수정 날짜"}|
|선택한 게시물 조회|GET|/api/boards/{id}|-|{"id" = 1, "title" = "제목", "contents" = "작성내용", "username" = "작성자", "createdAt" = "작성 날짜", "modifiedAt" = "수정 날짜"}|
|게시물 수정|PUT|/api/boards/{id}|{"title" = "수정 제목", "contents" = "수정 작성내용", "username" = "수정 작성자"}|{"id" = 1, "title" = "수정 제목", "contents" = "수정 내용", "username" = "수정 작성자", "createdAt" = "작성 날짜", "modifiedAt" = "수정 날짜"}|
|게시물 삭제|DELETE|/api/boards/{id}|{"password" = "비밀번호"}|{String = "게시물 삭제 완료"}|


Spring 숙련 주차

유스케이스 다이어 그램

![유스케이스](https://user-images.githubusercontent.com/121671967/218906591-34697b9b-1ea5-49b0-9835-75eab88874a4.png)


ERD

![ERD](https://user-images.githubusercontent.com/121671967/218906603-ddd543f6-b944-4e1c-8f6f-ecf091bcedd6.png)
