package com.example.jpa.global;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.service.CommentService;
import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final PostService postService;
    private final CommentService commentService;

    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner() {
        return args -> {
            // 샘플 데이터 3개 생성.
            // 데이터가 3개가 이미 있으면 패스
            if( postService.count() > 0 ) {
                return ;
            }

            postService.write("title1", "body1");
            postService.write("title2", "body2");
            postService.write("title3", "body3");

        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {
        return args -> {

            Post post = postService.findById(1L).get();

            if(commentService.count() > 0) {
                return;
            }

            Comment c1 = commentService.write(post.getId(), "comment1");
            Comment c2 = commentService.write(post.getId(), "comment2");
            Comment c3 = commentService.write(post.getId(), "comment3");

            // 1번 댓글의 부모 게시글의 정보
            System.out.println(c1.getId() + "번 댓글의 부모 게시글 번호는 " + c1.getPostId() + "입니다.");

            // 1번 댓글의 부모 게시글 제목 정보

            Post parent = postService.findById(c1.getPostId()).get();
            System.out.println(c1.getId() + "번 댓글의 부모 게시글 제목은 " + parent.getTitle() + "입니다.");


        };
    }
}