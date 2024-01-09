package com.kitri.myservletboard.controller.dao;
import com.kitri.myservletboard.controller.data.Board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class BoardJdbcDao implements BoardDao {
    private static final BoardJdbcDao instance = new BoardJdbcDao();
    public static BoardJdbcDao getInstance(){
        return instance;
    }
    private BoardJdbcDao(){}; //생성자까지 추가해주면 완벽한 싱글톤 생성

    public Connection connectDB() {
        Connection conn = null; //try catch 사용
        //커넥션 접속시 url, id, pwd 필요 -> mysql 정보랑 동일해야함
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/my_servlet_board?serverTimezone=UTC";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);

            if (conn != null){
                System.out.println("성공");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    @Override
    public ArrayList<Board> getAll() {
        Connection conn = null; //커넥션 맺는 코드
        PreparedStatement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //쿼리문에 대한 결과를 출력받는 코드

        ArrayList<Board> boards = new ArrayList<>();

        try {
            conn = connectDB();
            String sql = "SELECT * FROM board";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while (rs.next()){
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAT = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                boards.add(new Board(id, title, content, writer, createdAT,viewCount, commentCount));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return boards;
    }

    @Override
    public Board getById(Long id) {
        return null;
    }

    @Override
    public void save(Board board) {

    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(Board board) {

    }
}
