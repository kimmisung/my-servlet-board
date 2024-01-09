package com.kitri.myservletboard.controller.dao;
import com.kitri.myservletboard.controller.data.Board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardJdbcDao implements BoardDao {

    private static final BoardJdbcDao instance = new BoardJdbcDao(); //싱글톤 생성

    public static BoardJdbcDao getInstance() {
        return instance;
    }

    private BoardJdbcDao(){}; //생성자까지 추가해주어야 완벽한 싱글톤 작성


    public Connection connectDB() {
        Connection conn = null;

        //커넥션 접속 시 user id, url, pwd이 필요하며 -> MySQL 정보와 일치 시켜주어야한다
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //class.forName은 드라이버를 로드시킨다고 생각!
            String url = "jdbc:mysql://localhost:3306/my_servlet_board"; // board 스키마 url
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd); //url, user, pwd

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //BoardService 입장에서는 구현객체가 바껴도 상관없으나, 동일하게 맞춰서 메서드를 오버라이드해주면된다
    @Override
    public ArrayList<Board> getAll() {
        Connection connection = null; //커넥션 맺는 코드
        PreparedStatement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //결과 출력 받는 코드

        ArrayList<Board> boards = new ArrayList<>();

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board";
            ps = connection.prepareStatement(sql); //ps를 반환해줌
            rs = ps.executeQuery(sql); //결과 값으로 ResultSet이 나옴 -> 때문에 선언한 rs로 받아줌

            //데이터를 가지고 오기 위해서는 메서드로 접근해야한다
            while (rs.next()){
                //id, 1번 row, 2번 로우....true이면 데이터를 읽을 수 있다
                //데이터를 컬럼 단위로 읽는다
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_Count");
                int commentCount = rs.getInt("comment_count");


                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }
        }catch (Exception e){


        }
        finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
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
