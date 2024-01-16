package com.kitri.myservletboard.service;


import com.kitri.myservletboard.dao.member.MemberDao;
import com.kitri.myservletboard.dao.member.MemberJdbcDao;
import com.kitri.myservletboard.data.Member;

public class MemberService {

    MemberDao memberDao = MemberJdbcDao.getInstance();

    private static final MemberService instance = new MemberService();
    public static MemberService getInstance(){
        return instance;
    }
    private MemberService(){}

    public Member getMember(String login_id){
        return memberDao.getById(login_id);
    }
    public void addMember(Member member){
        memberDao.saveMember(member);
    }
}
