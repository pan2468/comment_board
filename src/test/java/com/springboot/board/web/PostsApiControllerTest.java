package com.springboot.board.web;

import com.springboot.board.domain.posts.Posts;
import com.springboot.board.domain.posts.PostsRepository;
import com.springboot.board.web.dto.PostsSaveRequestDto;
import com.springboot.board.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private PostsRepository postsRepository;

    @After
    public void tearDown()throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_게시글조회(){
        //given
        String title = "제목 조회";
        String content = "내용 조회";
        String email = "이메일 조회";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .email(email)
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getEmail()).isEqualTo(email);
    }


    @Test
    public void Posts_저장하기(){
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .email("email")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

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

        Posts modify = new Posts("제목 수정","내용 수정","작성자 수정","이메일 수정");
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

    @Test
    public void Posts_상세조회()throws Exception{
        //given
        String title = "제목 상세";
        String content = "내용 상세";
        String email = "이메일 상세";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .email(email)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        List<Posts> posts = postsRepository.findAllById(postsList.get(0).getId());
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
        assertThat(posts.get(0).getEmail()).isEqualTo(email);
    }


}