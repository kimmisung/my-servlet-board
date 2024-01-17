package com.kitri.myservletboard.dao.member;
import com.kitri.myservletboard.data.Member;

public interface MemberDao {
    public Member getById(String login_id);
    public void saveMember(Member member);
}
