package com.kitri.myservletboard.service;

import com.kitri.myservletboard.dao.BoardDao;
import com.kitri.myservletboard.dao.BoardJdbcDao;
import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import java.util.ArrayList;

public class BoardService { //컨트롤러에게 서비스를 할당받을 예정
    //BoardDao boardDao = BoardMemoryDao.getInstance(); //싱글톤이기때문에 new가 아닌 getInstance()로
    BoardDao boardDao = BoardJdbcDao.getInstance();

    //싱글톤으로 생성
    private BoardService() {
    }


    private static final BoardService instance = new BoardService();

    public static BoardService getInstance() {
        return instance;
    }

    //게시판 리스트 가져오는 로직
    public ArrayList<Board> getBoards() {
        return boardDao.getAll(); //역할 위임의 개념
    }

    public ArrayList<Board> getBoards(Pagination pagination) {

        //total record ->
        pagination.setTotalRecords(((BoardJdbcDao) boardDao).count());
        pagination.calcPagination();
        return boardDao.getAll(pagination);
    }

    public Board getBoard(Long id) {
        return boardDao.getById(id);
    }

    public void addBoard(Board board) {
        boardDao.save(board);
    }

    public void updateBoard(Board board) {
        boardDao.update(board);
    }

    public void deleteBoard(Board board) {
        boardDao.delete(board);
    }


}
