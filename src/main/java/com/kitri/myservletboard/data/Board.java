package com.kitri.myservletboard.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
    //게시판
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private int viewCount;
    private int commentCount;
    private Long member_id;



    public Board() {
    }

    public Board(Long id, String title, String content, String writer, LocalDateTime createdAt, int viewCount, int commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }

    public Board(Long id, String title, String content, String writer, LocalDateTime createdAt, int viewCount, int commentCount, Long member_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.member_id = member_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCreatedAt() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh:ss");
        return createdAt.format(df);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }
}

