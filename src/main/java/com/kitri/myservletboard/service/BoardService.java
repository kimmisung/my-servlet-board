package com.kitri.myservletboard.service;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.dao.BoardDao;
import com.kitri.myservletboard.dao.BoardMemoryDao;

import java.util.ArrayList;

public class BoardService {
    BoardDao boardDao = BoardMemoryDao.getInstance();

    private BoardService(){

    };
    private static final BoardService instance = new BoardService();

    public static BoardService getInstance(){
        return instance;
    }
    public ArrayList<Board> getBoards(){
        return boardDao.getAll();
    }
    public void addBoard(Board board){
        boardDao.save(board);
    }
    public void updateBoard(Board board){
        boardDao.update(board);
    }
    public void deleteBoard(Board board){
        boardDao.delete(board);
    }
    public Board getBoard(Long id){
        return boardDao.getById(id);
    }
}
