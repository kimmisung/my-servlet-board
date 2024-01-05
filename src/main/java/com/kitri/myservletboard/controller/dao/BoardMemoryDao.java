package com.kitri.myservletboard.controller.dao;
import com.kitri.myservletboard.controller.data.Board;
import com.kitri.myservletboard.service.BoardService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardMemoryDao implements BoardDao { //implements한 인터페이스 메소드 재정의가 필요

    private static final BoardMemoryDao instance = new BoardMemoryDao(); //싱글톤 생성
    public static BoardMemoryDao getInstance(){
        return instance;
    }

    //DB자료를 담을 어레이리스트 생성
    ArrayList<Board> memoryBoardDB = new ArrayList<>();

    public BoardMemoryDao() {
        memoryBoardDB.add(new Board(1L, "첫번째 글입니다!!!", "내용1", "김성실", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(2L, "두번째 글입니다!!!", "내용2", "오시현", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(3L, "세번째 글입니다!!!", "내용3", "김동현", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(4L, "네번째 글입니다!!!", "내용4", "손흥민", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(5L, "다섯번째 글입니다!!!", "내용5", "박현오", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(6L, "여섯번째 글입니다!!!", "내용6", "박준혁", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(7L, "일곱번째 글입니다!!!", "내용7", "주나영", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(8L, "여덟번째 글입니다!!!", "내용8", "한민선", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(9L, "아홉번째 글입니다!!!", "내용9", "김미성", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(10L, "열번째 글입니다!!!", "내용10", "박세한", LocalDateTime.now(), 10, 1));
    }

    @Override
    public ArrayList<Board> getAll() {
        return memoryBoardDB;
    }

    @Override
    public Board getById(Long id) {
        return memoryBoardDB.stream().filter(board -> {
            return board.getId() == id;
        }).findFirst().get();
    }

    @Override
    public void save(Board board) {
        memoryBoardDB.add(board);
    }

    @Override
    public void update(Board board) {
        Board board_ = getById(board.getId());
        memoryBoardDB.remove(board_);
        memoryBoardDB.add(board);
    }

    @Override
    public void delete(Board board) {
        memoryBoardDB.remove(board);
    }
}
