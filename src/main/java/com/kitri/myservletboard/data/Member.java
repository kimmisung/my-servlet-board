package com.kitri.myservletboard.data;

public class Member {

    private Long id;
    private String login_id;
    private String password;
    private String password2;
    private String name;
    private String email;




    public Member() {
    }

    public Member(String name, String login_id, String password, String password2, String email) {
        this.name = name;
        this.login_id = login_id;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }

    public Member(Long id, String login_id, String password, String name, String email) {
        this.id = id;
        this.login_id = login_id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
