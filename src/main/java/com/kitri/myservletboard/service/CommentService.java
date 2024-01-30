package com.kitri.myservletboard.service;

import com.kitri.myservletboard.dao.comment.CommentDao;
import com.kitri.myservletboard.dao.comment.CommentJdbcDao;
import com.kitri.myservletboard.data.Comment;

import java.util.ArrayList;

public class CommentService {

    CommentDao commentDao = CommentJdbcDao.getInstance();

    private static final CommentService instance = new CommentService();
    public static CommentService getInstance(){
        return instance;
    }
    private CommentService(){}

    public ArrayList<Comment> getComment(Long boardId){
        return commentDao.getAll(boardId);
    }
    public void addComment(Comment comment){
      commentDao.save(comment);
    }
    public void deleteComment(Comment comment){
        commentDao.delete(comment);
    }


}
