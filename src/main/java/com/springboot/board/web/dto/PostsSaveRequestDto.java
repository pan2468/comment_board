package com.springboot.board.web.dto;

import com.springboot.board.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String email;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String email){
        this.title = title;
        this.content = content;
        this.author = author;
        this.email = email;

    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .email(email)
                .build();
    }


}
