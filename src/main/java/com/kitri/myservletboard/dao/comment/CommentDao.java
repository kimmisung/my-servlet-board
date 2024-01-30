package com.kitri.myservletboard.dao.comment;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.util.ArrayList;

public interface CommentDao {

    public Comment getById (Long id);

    public void save (Comment comment);

    public void delete (Comment comment);

    public ArrayList<Comment> getAll(Long boardId);

}
