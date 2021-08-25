To do list 

1) 팔로워 구현
2) 인기 페이지 구현
3) 회원가입 루트 다양화
4) 다른 사용자와 접근성 강화
5) 무한 참조 방지

Error list
1) Infinite Refrence

fix) likes - image - user 로 이어지는 무한 참조 발생(user가 image를 가지고 있어서)되어 JsonIgnoreProperties로 예방

2) Jpa column size error

fix) application.yml의 ddl-auto를 create로 바꾸고 update로 바꾸어 해결

3) follwers 구현할 때 알 수 없는 엑박이 생성

not fix) 해결중

4) 좋아요 기능을 구현하고 페이지 새로고침 시 Dynamic 구현이 안됨

fix) ``을 이용하여 jsp파일을 수정하였고 몇 가지 오타가 있었음

5) @OneToMany 기법 사용할 때 FetchType을 Eager방식으로 구현하여 엑박이 뜸

fix) Lazy방식으로 변경하면 먼저 페이지를 받아들여오는 것이 아니라, getImages()를 호출하면 이미지를 가져오게끔 해결

6) 
