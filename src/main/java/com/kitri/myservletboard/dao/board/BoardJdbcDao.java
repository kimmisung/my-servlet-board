package com.kitri.myservletboard.dao.board;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class BoardJdbcDao implements BoardDao {


    private static final BoardJdbcDao instance = new BoardJdbcDao(); //싱글톤 생성

    public static BoardJdbcDao getInstance() {
        return instance;
    }

    private BoardJdbcDao() {
    }

    ; //생성자까지 추가해주어야 완벽한 싱글톤 작성


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
    public ArrayList<Board> getAll(String type, String keyword, String period, String orderBy, Pagination pagination) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();
        if (type == null) {
            type = "title";
        }
        if (keyword == null) {
            keyword = "";
        }
        if (period == null) {
            period = "100 year"; //전체기간이 조회되게 설정
        }


        if (orderBy.equals("latest")) {
            orderBy = "created_at";
        } else if (orderBy.equals("views")) {
            orderBy = "view_count";
        }

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board WHERE " + type + " LIKE " + "'%" + keyword
                    + "%' AND" + " created_at BETWEEN DATE_ADD(NOW(), INTERVAL -" + period + ") AND NOW() ORDER BY " + orderBy + " DESC LIMIT ?, ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pagination.getPage() - 1) * pagination.getMaxRecordsPerPage()); //1page : 0, 2page : 10, 3page : 20
            ps.setInt(2, pagination.getMaxRecordsPerPage());
            rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boards;
    }

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
            while (rs.next()) {
                //id, 1번 row, 2번 로우....true이면 데이터를 읽을 수 있다
                //데이터를 컬럼 단위로 읽는다
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }
        } catch (Exception e) {


        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boards;
    }

    @Override
    public ArrayList<Board> getAll(Pagination pagination) {
        Connection connection = null; //커넥션 맺는 코드
        PreparedStatement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //결과 출력 받는 코드

        ArrayList<Board> boards = new ArrayList<>();

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board LIMIT ?, ?"; //LIMIT 10(startIndex), 10(MaxRecordsPerPage); -> 11번째부터 10개가 추출
            ps = connection.prepareStatement(sql); //ps를 반환해줌
            ps.setInt(1, (pagination.getPage() - 1) * pagination.getMaxPagesOnScreen()); //1page : 0, 2page : 10, 3page : 20
            ps.setInt(2, pagination.getMaxRecordsPerPage());
            rs = ps.executeQuery(); //결과 값으로 ResultSet이 나옴 -> 때문에 선언한 rs로 받아줌


            //데이터를 가지고 오기 위해서는 메서드로 접근해야한다
            while (rs.next()) {
                //id, 1번 row, 2번 로우....true이면 데이터를 읽을 수 있다
                //데이터를 컬럼 단위로 읽는다
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }
        } catch (Exception e) {


        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boards;
    }

    @Override
    public ArrayList<Board> getAll(String type, String keyword, Pagination pagination) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();
        if (type == null) {
            type = "title";
        }
        if (keyword == null) {
            keyword = "";
        }

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board WHERE " + type + " LIKE " + "'%" + keyword + "%'" + " LIMIT ?, ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pagination.getPage() - 1) * pagination.getMaxPagesOnScreen()); //1page : 0, 2page : 10, 3page : 20
            ps.setInt(2, pagination.getMaxRecordsPerPage());
            rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boards;
    }

    @Override
    public ArrayList<Board> getAll(String type, String keyword, String period, Pagination pagination) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();
        if (type == null) {
            type = "title";
        }
        if (keyword == null) {
            keyword = "";
        }
        if (period == null) {
            period = "100 year"; //전체기간이 조회되게 설정
        }

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board WHERE " + type + " LIKE " + "'%" + keyword
                    + "%' AND" + " created_at BETWEEN DATE_ADD(NOW(), INTERVAL -" + period + ") AND NOW() LIMIT ?, ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pagination.getPage() - 1) * pagination.getMaxRecordsPerPage()); //1page : 0, 2page : 10, 3page : 20
            ps.setInt(2, pagination.getMaxRecordsPerPage());
            rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                //DB에서 가져온 정보들로 Board board 객체 생성해주기
                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boards;
    }


    @Override
    public Board getById(Long id) {

        //connection
        // ps -> executeQuery(); 이용해서 쿼리 날리기
        // res -> 데이터 출력

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Board board = null;

        try {
            connection = connectDB();
            String sql = "SELECT * FROM board where id=?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();


            while (rs.next()) {
                Long id_ = rs.getLong("id");
                String title_ = rs.getString("title");
                String content_ = rs.getString("content");
                String writer_ = rs.getString("writer");
                LocalDateTime createdAt_ = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount_ = rs.getInt("view_Count");
                int commentCount_ = rs.getInt("comment_count");

                board = new Board(id_, title_, content_, writer_, createdAt_, viewCount_, commentCount_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }

    @Override
    public void save(Board board) { //등록
        //AI는 자동으로 증감시키는 것

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String sql = "INSERT INTO board (title, content, writer) VALUES (?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setString(3, board.getWriter());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Board board) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String sql = "UPDATE board SET title = ?, content = ? where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setLong(3, board.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Board board) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String sql = "DELETE FROM board WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, board.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int count() {
        Connection connection = null; //커넥션 맺는 코드
        PreparedStatement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //결과 출력 받는 코드

        int count = 0;

        try {
            connection = connectDB();
            String sql = "SELECT count(*) FROM board";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();

            count = rs.getInt("count(*)");


        } catch (Exception e) {


        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public int count(String type, String keyword) {

        if (keyword == null) {
            return count();
        }
        Connection connection = null; //커넥션 맺는 코드
        Statement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //결과 출력 받는 코드

        int count = 0;

        try {
            connection = connectDB();
            String sql = "SELECT count(*) FROM board WHERE " + type + " LIKE " + "'%" + keyword + "%'";
            ps = connection.createStatement();
            rs = ps.executeQuery(sql);
            rs.next();

            count = rs.getInt("count(*)");

        } catch (Exception e) {


        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public int count(String type, String keyword, String period) {

        if (keyword == null || period == null) {
            return count();
        }

        Connection connection = null; //커넥션 맺는 코드
        Statement ps = null; //쿼리 날리는 코드
        ResultSet rs = null; //결과 출력 받는 코드

        int count = 0;

        try {
            connection = connectDB();
            String sql = "SELECT count(*) FROM board WHERE " + type + " LIKE " + "'%" + keyword
                    + "%' AND" + " created_at BETWEEN DATE_ADD(NOW(), INTERVAL -" + period + ") AND NOW()";
            ps = connection.createStatement();
            rs = ps.executeQuery(sql);
            rs.next();

            count = rs.getInt("count(*)");

        } catch (Exception e) {


        } finally {
            //커넥션을 사용했으면 사용후 종료를 해주어야 한다.
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
