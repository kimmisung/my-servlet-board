package com.kitri.myservletboard.dao.member;

import com.kitri.myservletboard.data.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberJdbcDao implements MemberDao {

    private static final MemberJdbcDao instance = new MemberJdbcDao();
    public static MemberJdbcDao getInstance(){
        return instance;
    }
    private MemberJdbcDao(){}


    public Connection connectDB(){
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/my_servlet_board";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);

        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public Member getById(String login_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Member member = new Member();

        try{
            conn = connectDB();
            String sql = "SELECT * FROM member WHERE login_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);
            rs = ps.executeQuery();

            while (rs.next()){
                Long id = rs.getLong("id");
                String pw = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Member member1 = new Member(id, login_id, pw, name, email);
                return member1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public void saveMember(Member member) { //등록
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = connectDB();
            String sql = "INSERT INTO member (login_id, password, name, email) VALUES (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, member.getLogin_id());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getEmail());
            ps.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                ps.close();
                conn.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
