package com.springboot.board.web;

import com.springboot.board.domain.posts.Posts;
import com.springboot.board.domain.posts.PostsRepository;
import com.springboot.board.web.dto.PostsSaveRequestDto;
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
    public void Posts_수정하기()throws Exception{
        //given
        Posts posts = new Posts();
        List<Posts> postsUpdate = postsRepository.findAllById(posts.getId());




    }

    @Test

    public void Posts_상세(){
        //given

//        Order savedOrder = orderRepository.findById(order.getId())
//                .orElseThrow(EntityNotFoundException::new);


        //when

        //then
    }
}