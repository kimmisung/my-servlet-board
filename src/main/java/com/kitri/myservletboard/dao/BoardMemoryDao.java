package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardMemoryDao implements BoardDao {
    private static final BoardMemoryDao instance = new BoardMemoryDao();

    public static BoardMemoryDao getInstance() {
        return instance;
    }

    ArrayList<Board> memoryBoardDB = new ArrayList<>();

    private BoardMemoryDao() {
        memoryBoardDB.add(new Board(1L, "첫번째 글", "반가워1", "김성실", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(2L, "두번째 글", "반가워2", "오시현", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(3L, "세번째 글", "반가워3", "김동현", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(4L, "네번째 글", "반가워4", "박현오", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(5L, "다섯번째 글", "반가워5", "박준혁", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(6L, "여섯번째 글", "반가워6", "주나영", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(7L, "일곱번째 글", "반가워7", "한민선", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(8L, "여덟번째 글", "반가워8", "송준형", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(9L, "아홉번째 글", "반가워9", "김미성", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(10L, "열번째 글", "반가워10", "박세한", LocalDateTime.now(), 10, 1));

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
