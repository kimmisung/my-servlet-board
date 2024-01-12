package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import java.util.ArrayList;

//인터페이스로 작성하면 추후 교체가 용이
//인터페이스는 구현객체가 필요 -> 구현객체 생성해주어야한다 (MemoryDao)
public interface BoardDao {


    //서비스에서 하는 일과 동일(게시판 리스트 가져오는 로직으로 이름만 수정해줌)

    public ArrayList<Board> getAll();
    public ArrayList<Board> getAll(Pagination pagination); //추가
    public ArrayList<Board> getAll(String type, String keyword, Pagination pagination);

    public Board getById(Long id);
    public void save(Board board);
    public void update(Board board);
    public void delete(Board board);

}
