package com.springboot.board.web.dto;

import com.springboot.board.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListReponseDto {
    private Long id;
    private String title;
    private String author;
    private String email;
    private LocalDateTime modifiedDate;

    public PostsListReponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.email = entity.getEmail();
        this.modifiedDate = entity.getModifiedDate();
    }
}
