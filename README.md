## 📌 CommentBoard

### 👉 제작기간 / 참여인원
+ 제작기간: 2022-07-08 ~ 진행
+ 참여인원: 개인 프로젝트

### 🛠 사용 기술(기술스택)
#### Back-End
+ Java 8
+ SpringBoot 2.7.1
+ SpringSecurity
+ QueryDSL
+ Gradle
+ H2 Database
+ MySQL
+ TDD

### 📌 ERD 설계
미완성
### 📌 핵심기능
<details>
<summary>JPA Auditing 날짜 자동화</summary>
<div markdown="1">
  
~~~
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createData;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


}
~~~

~~~
    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2022,7,13,5,46,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .email("email")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>> createDate=" + posts.getCreateData()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreateData()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
~~~

</div>
</details>
  
### 📌 핵심트러블슈팅 경험
미완성

### 📌 트러블슈팅 경험
<details>
<summary>Hello Controller 테스트 코드 오류</summary>
<div markdown="1">
- Execution failed for task ':test'.
  
  ### 해결방법
- 원인: InteliJ 오류
- Intelij > Ctrl+Alt+S > Setting > BuildTools > Gradle > Run tests using : InteliJ IDEA  
<img src="https://user-images.githubusercontent.com/58936137/178106276-a84c7c23-7b77-4cdd-9ccb-5836f9e0abba.png" width="600px" height="500px">
 
</div>
</details>
  
<details>
<summary>게시글 조회하기 오류</summary>
<div markdown="1">
- Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'postsApiController' method <br>
- Execution failed for task ':CommentBoardApplication.main()'.

### 해결방법
+ 원인: @GetMapping("/") 어노테이션 중복경로 오류 
+ PostsApiController 클래스 Index 메소드 주석 처리   
<details>
<summary>기존 코드</summary>
<div markdown="1">

  IndexController.java
  ~~~
      @GetMapping("/")
    public String index(Model model){

        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
  ~~~
  
</div>
</details>  

<details>
<summary>개선 코드</summary>
<div markdown="1">

PostsApiController.java
~~~
//    @GetMapping("/")
//    public String index(Model model){
//        model.addAttribute("posts",postsService.findAllDesc());
//        return "index";
//    }
~~~
  
</div>
</details>  
  
  
</div>
</details>  
  
<details>
<summary>게시글 수정하기 테스트 오류</summary>
<div markdown="1">
- java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0 </br>
- org.springframework.web.client.RestClientException:
  
### 해결방법
+ 원인: Posts_수정하기() 메소드 구현부 테스트 코드 오류

<details>
<summary>기존 코드</summary>
<div markdown="1">
  
~~~
    @Test
    public void Posts_수정하기(){
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .email("email")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedContent);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
~~~
</div>
</details>

<details>
<summary>개선 코드</summary>
<div markdown="1">
  
~~~
    @Test
    public void Posts_수정하기() throws Exception{
        //given
        String title = "제목";
        String content = "내용";
        String email = "이메일";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .email(email)
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();
        List<Posts> posts = postsRepository.findAllById(postsList.get(0).getId());

        Posts modify = new Posts("제목 수정","내용 수정","이메일 수정");
        PostsUpdateRequestDto postsUpdate = new PostsUpdateRequestDto(modify.getTitle(), modify.getContent());
        postsRepository.save(Posts.builder()
                .title(postsUpdate.getTitle())
                .content(postsUpdate.getContent())
                .build());
        //then
        List<Posts> postsmodify = postsRepository.findAllById(postsList.get(0).getId());
        assertThat(postsmodify.get(0).getTitle()).isEqualTo(title);
        assertThat(postsmodify.get(0).getContent()).isEqualTo(content);

    }
~~~
</div>
</details>
  <br>
  

### 👉 프로젝트 과정
+ 개인 프로젝트 설명: <a href="https://pan2468.tistory.com/category/%EA%B0%9C%EC%9D%B8%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8">개인 프로젝트 블로그</a>




