## ๐ Comment_Board

### ๐ ํ๋ก์ ํธ ๋ชฉ์  
<strong><a href="http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788965402602">์คํ๋ง ๋ถํธ์ AWS๋ก ํผ์ ๊ตฌํํ๋ ์น์๋น์ค - ์ด๋์ฑ ์ ์๋</a></strong> ์ฑ์ ์ฝ๊ณ  ๊ฐ์ธ ํ๋ก์ ํธ๋ฅผ ์ค์ต์ ํด๋ณด๋ ค๊ณ ํฉ๋๋ค. </br>
์ฃผ์ ๋ ๋๊ธ ๊ฒ์ํ ๋ง๋ค๊ธฐ ์๋๋ค. ๋จ์ํ CRUD ๊ฒ์ํ๋ณด๋ค๋ ์ฌ์ฉ์๋ค์ด ์๊ตฌ์ฌํญ์ด ๋ฌด์์ธ์ง ์๊ธฐ ์ํด์ ๋๊ธ๊ธฐ๋ฅ์ ์ฌ์ฉํ๋ ค๊ณ  ํฉ๋๋ค. 

### ๐ ๊ธฐ๋ฅ์ค๋ช
+ JpaRepository ์ธํฐํ์ด์ค ์ด์ฉํ์ฌ CRUD ๊ฒ์ํ ๊ตฌํํ๊ธฐ
+ SpringSecurity ๋ก๊ทธ์ธ API ์ธ์ฆ๊ตฌํํ๊ธฐ (ex ๋ค์ด๋ฒ, ๊ตฌ๊ธ)
+ ๋๊ธ ๊ธฐ๋ฅ ๊ตฌํํ๊ธฐ

### ๐ ์ ์๊ธฐ๊ฐ / ์ฐธ์ฌ์ธ์
+ ์ ์๊ธฐ๊ฐ: 2022-07-08 ~ ์งํ
+ ์ฐธ์ฌ์ธ์: ๊ฐ์ธ ํ๋ก์ ํธ

### ๐  ์ฌ์ฉ ๊ธฐ์ (๊ธฐ์ ์คํ)
#### Back-End
+ Java 8
+ SpringBoot 2.7.1
+ SpringSecurity
+ QueryDSL
+ Gradle
+ H2 Database
+ MySQL
+ TDD

### ๐ ERD ์ค๊ณ
๋ฏธ์์ฑ
### ๐ ํต์ฌ๊ธฐ๋ฅ
<details>
<summary>JPA Auditing ๋ ์ง ์๋ํ</summary>
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
    public void BaseTimeEntity_๋ฑ๋ก(){
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
  
### ๐ ํต์ฌํธ๋ฌ๋ธ์ํ ๊ฒฝํ
<details>
<summary>๊ฒ์๊ธ ์์ ๋ฒํผ ํด๋ฆญ ์ค๋ฅ</summary>
<div markdown="1">
- java.lang.IllegalArgumentException: ํด๋น ๊ฒ์๊ธ์ด ์์ต๋๋ค. id=1

### ํด๊ฒฐ๋ฐฉ๋ฒ
+ ์์ธ: posts-update.mustache ์ค๋ฅ
+ posts-update.mustache ์์ ๋งจ ๋ฐ์ ํ๋จ์ {{>layout/footer}} ์ฝ๋ ์ถ๊ฐ ํ index.js ๊ฒฝ๋ก ํตํด์ ํด๋น id ๊ฐ ๋ณด๋ด ๊ฐ์   


<details>
<summary>๊ธฐ์กด ์ฝ๋</summary>
<div markdown="1">

~~~
{{>layout/header}}

<h3>๊ฒ์๊ธ ์์ ํ๊ธฐ</h3>

<div class="container">
    <div class="col-md-12">
        <div class="col-md-4">
            <form>
                <div class="form-group">
                    <label for="id">๊ธ ๋ฒํธ</label>
                    <input type="text" class="form-control" id="id" value="{{post.id}}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="title">์ ๋ชฉ</label>
                    <input type="text" class="form-control" id="title" value="{{post.title}}">
                </div>
                
                <div class="form-group">
                    <label for="author">์์ฑ์</label>
                    <input type="text" class="form-control" id="author" value="{{post.author}}" readonly>
                </div>

                <div class="form-group">
                    <label for="content">๋ด์ฉ</label>
                    <textarea class="form-control" id="content">{{post.content}}</textarea>
                </div>
            </form>
            <a href="/" role="button" class="btn btn-secondary">์ทจ์</a>
            <button type="button" class="btn btn-primary" id="btn-update">์์ </button>
        </div>
    </div>
</div>

~~~

</div>
</details>  
  
<details>
<summary>๊ฐ์  ์ฝ๋</summary>
<div markdown="1">

~~~
{{>layout/header}}

<h3>๊ฒ์๊ธ ์์ ํ๊ธฐ</h3>

<div class="container">
    <div class="col-md-12">
        <div class="col-md-4">
            <form>
                <div class="form-group">
                    <label for="id">๊ธ ๋ฒํธ</label>
                    <input type="text" class="form-control" id="id" value="{{post.id}}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="title">์ ๋ชฉ</label>
                    <input type="text" class="form-control" id="title" value="{{post.title}}">
                </div>
                
                <div class="form-group">
                    <label for="author">์์ฑ์</label>
                    <input type="text" class="form-control" id="author" value="{{post.author}}" readonly>
                </div>

                <div class="form-group">
                    <label for="content">๋ด์ฉ</label>
                    <textarea class="form-control" id="content">{{post.content}}</textarea>
                </div>
            </form>
            <a href="/" role="button" class="btn btn-secondary">์ทจ์</a>
            <button type="button" class="btn btn-primary" id="btn-update">์์ </button>
        </div>
    </div>
</div>

{{>layout/footer}} //์ฝ๋ ์ถ๊ฐ ํ ๊ฐ์ 
~~~
  
  
</div>
</details>  
  
  
  
</div>
</details>  

### ๐ ํธ๋ฌ๋ธ์ํ ๊ฒฝํ
<details>
<summary>Hello Controller ํ์คํธ ์ฝ๋ ์ค๋ฅ</summary>
<div markdown="1">
- Execution failed for task ':test'.
  
  ### ํด๊ฒฐ๋ฐฉ๋ฒ
- ์์ธ: InteliJ ์ค๋ฅ
- Intelij > Ctrl+Alt+S > Setting > BuildTools > Gradle > Run tests using : InteliJ IDEA  
<img src="https://user-images.githubusercontent.com/58936137/178106276-a84c7c23-7b77-4cdd-9ccb-5836f9e0abba.png" width="600px" height="500px">
 
</div>
</details>
  
<details>
<summary>๊ฒ์๊ธ ์กฐํํ๊ธฐ ์ค๋ฅ</summary>
<div markdown="1">
- Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'postsApiController' method <br>
- Execution failed for task ':CommentBoardApplication.main()'.

### ํด๊ฒฐ๋ฐฉ๋ฒ
+ ์์ธ: @GetMapping("/") ์ด๋ธํ์ด์ ์ค๋ณต๊ฒฝ๋ก ์ค๋ฅ 
+ PostsApiController ํด๋์ค Index ๋ฉ์๋ ์ฃผ์ ์ฒ๋ฆฌ   
<details>
<summary>๊ธฐ์กด ์ฝ๋</summary>
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
<summary>๊ฐ์  ์ฝ๋</summary>
<div markdown="1">

PostsApiController.java
~~~
//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }
~~~
  
</div>
</details>  
  
  
</div>
</details>  

<details>
<summary>๊ฒ์๊ธ ์์ ํ๊ธฐ ์ค๋ฅ</summary>
<div markdown="1">
- com.samskivert.mustache.MustacheException$Context: No method or field with name 'post.author' on line 21

### ํด๊ฒฐ๋ฐฉ๋ฒ
+ ์์ธ: PostsResponseDto.java ์์ author ๋ฉค๋ฒ ๋ณ์์ ์ธ๊ณผ ์์ฑ์ ์ ์ธ์ ์ํ์ฌ ์ค๋ฅ


<details>
<summary>๊ธฐ์กด ์ฝ๋</summary>
<div markdown="1">

~~~

import com.springboot.board.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String email;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
    }

}
~~~

</div>
</details>  

<details>
<summary>๊ฐ์  ์ฝ๋</summary>
<div markdown="1">

~~~


import com.springboot.board.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author; // ๋ฉค๋ฒ๋ณ์ ์ ์ธ ํ ๊ฐ์ 
    private String email;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor(); //์์ฑ์ ์ถ๊ฐํ์ฌ ๊ฐ์ 
        this.email = entity.getEmail();
    }

}

~~~

</div>
</details>  

  
</div>
</details>  


<details>
<summary>๊ฒ์๊ธ ์์ ํ๊ธฐ ํ์คํธ ์ค๋ฅ</summary>
<div markdown="1">
- java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0 </br>
- org.springframework.web.client.RestClientException:
  
### ํด๊ฒฐ๋ฐฉ๋ฒ
+ ์์ธ: Posts_์์ ํ๊ธฐ() ๋ฉ์๋ ๊ตฌํ๋ถ ํ์คํธ ์ฝ๋ ์ค๋ฅ

<details>
<summary>๊ธฐ์กด ์ฝ๋</summary>
<div markdown="1">
  
~~~
    @Test
    public void Posts_์์ ํ๊ธฐ(){
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
<summary>๊ฐ์  ์ฝ๋</summary>
<div markdown="1">
  
~~~
    @Test
    public void Posts_์์ ํ๊ธฐ() throws Exception{
        //given
        String title = "์ ๋ชฉ";
        String content = "๋ด์ฉ";
        String email = "์ด๋ฉ์ผ";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .email(email)
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();
        List<Posts> posts = postsRepository.findAllById(postsList.get(0).getId());

        Posts modify = new Posts("์ ๋ชฉ ์์ ","๋ด์ฉ ์์ ","์ด๋ฉ์ผ ์์ ");
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
  
</div>
</details> 

<details>
<summary>์์ ํ๊ธฐ 'PUT' ์๋จ </summary>
<div markdown="1">
 - ์ค๋ฅ๋ฅผ ๋์ง ์์์ง๋ง, PUT ์๋๋ ์์ธ์ ๋ชจ๋ฅด๊ฒ ์
 
 ### ํด๊ฒฐ ๋ฐฉ๋ฒ
+ ์์ธ: Service ๊ณ์ธต update ๋ฉ์๋ ์์ @Transactional ์ด๋ธํ์ด์ ์ ์ธ์ ์ํ์ฌ PUT ์ ์ฉ์ด ์๋จ 


<details>
<summary>๊ธฐ์กด ์ฝ๋</summary>
<div markdown="1">

##### PostsService.java
~~~

public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ํด๋น ๊ฒ์๊ธ์ด ์์ต๋๋ค. id = " + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());

     return id;
}

~~~

</div>
</details>  

<details>
<summary>๊ฐ์  ์ฝ๋</summary>
<div markdown="1">

##### PostsService.java
~~~

@Transactional // ํธ๋์ญ์ ์ ์ธํ์ฌ PUT ์ ์ฉํ  ์ ์๊ฒ ๊ฐ์ 
public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ํด๋น ๊ฒ์๊ธ์ด ์์ต๋๋ค. id = " + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());

     return id;
}

~~~

</div>
</details> 

</div>
</details>  


### ๐ ํ๋ก์ ํธ ์ค๋ช
+ ๊ฐ์ธ ํ๋ก์ ํธ ์ค๋ช: <a href="https://pan2468.tistory.com/category/Toy%20Project/%EB%8C%93%EA%B8%80%20%EA%B2%8C%EC%8B%9C%ED%8C%90">๊ฐ์ธ ํ๋ก์ ํธ ๋ธ๋ก๊ทธ</a>
  






