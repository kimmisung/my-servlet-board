package com.kitri.myservletboard.dao.comment;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentJdbcDao implements CommentDao {

    private static final CommentJdbcDao instance = new CommentJdbcDao();

    public static CommentJdbcDao getInstance() {
        return instance;
    }

    private CommentJdbcDao() {
    }


    public Connection connectDB() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/my_servlet_board";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    @Override
    public Comment getById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Comment comment = null;

        try {
            conn = connectDB();
            String sql = "SELECT * FROM comment WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Long boardId = rs.getLong("boardId");
                Long memberId = rs.getLong("memberId");
                String contents = rs.getString("contents");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();

                comment = new Comment(id, boardId, memberId, contents, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comment;
    }


    @Override
    public void save(Comment comment) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            String sql = "INSERT INTO comment (board_id, member_id, content) VALUES (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, comment.getBoardId());
            ps.setLong(2, comment.getMemberId());
            ps.setString(3, comment.getContent());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Comment comment) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            String sql = "DELETE FROM comment WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, comment.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public ArrayList<Comment> getAll(Long boardId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Comment> comments = new ArrayList<>();

        try {
            conn = connectDB();
            String sql = "SELECT member_id, content, created_at FROM comment WHERE board_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, boardId);
            rs = ps.executeQuery();


            while (rs.next()) {
                Long memberID = rs.getLong("member_id");
                String contents = rs.getString("content");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                comments.add(new Comment(memberID, contents, createdAt));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return comments;
    }
}
