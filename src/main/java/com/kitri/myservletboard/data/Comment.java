package com.kitri.myservletboard.data;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String contents;
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(Long memberId, String contents, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public Comment(Long id, Long boardId, Long memberId, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return contents;
    }

    public void setContent(String content) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
