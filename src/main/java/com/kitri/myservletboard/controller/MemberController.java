package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Member;
import com.kitri.myservletboard.service.MemberService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
    MemberService memberService = MemberService.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<h1>요청을 잘 응답받았습니다!</h1>");

        String requestURI = request.getRequestURI(); //url주소
        String contextPath = request.getContextPath(); // 공백
        String command = requestURI.substring(contextPath.length());
        out.println(command);

        String view = "/view/member/";
        if (command.equals("/member/loginForm")) {
            //로그인 폼
            view += "login.jsp";
        } else if (command.equals("/member/login")) {
            //로그인 처리
            String id = request.getParameter("id");
            String pw = request.getParameter("pw");

            Member member = memberService.getMember(id);

            boolean isLoginFailed = false;
            if (id.isEmpty() || pw.isEmpty()) {
                isLoginFailed = true;
            }
            if (member == null) { //id가 null일때
                isLoginFailed = true;
            } else {
                if (!member.getPassword().equals(pw)) {
                    isLoginFailed = true;
                }
            }

            if (isLoginFailed) { //로그인 실패
                request.setAttribute("loginFailed", isLoginFailed);
                response.sendRedirect("/member/loginForm");
                return;
            } else { //로그인 성공
                HttpSession session = request.getSession(); //사용자에 대한 세션 부여
                session.setAttribute("member", member);
            }

            response.sendRedirect("/board/list");
            return;


        } else if (command.equals("/member/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();

            response.sendRedirect("/board/list");
            return;
        } else if (command.equals("/member/joinForm")) {
            //회원 가입 폼
            view += "join.jsp";

        } else if (command.equals("/member/join")) {
            //회원 가입 처리
            String name = request.getParameter("name");
            String id = request.getParameter("id");
            String pw1 = request.getParameter("pw1");
            String pw2 = request.getParameter("pw2");
            String email = request.getParameter("mail");

            Member member = new Member(name, id, pw1, pw2, email);
            Member ChkMember = memberService.getMember(id);

            //아이디 중복 체크
            if (ChkMember.getLogin_id() != null) {
                //중복
                response.sendRedirect("/view/member/joinFalse.jsp");
                return;
            } else {//중복 아니니까
                if (!(pw1.equals(pw2))) {
                    //비밀번호 확인 문구
                    response.sendRedirect("/view/member/pwFalse.jsp");
                    return;

                } else {
                    //가입완료
                    memberService.addMember(member);
                    response.sendRedirect("/view/member/joinSuccess");
                    return;
                }
            }
        } else if (command.equals("/member/registrationForm")){
            view += "/registration.jsp";
        }else if (command.equals("/member/registration")){

            String name = request.getParameter("name");
            String id = request.getParameter("id");
            String pw1 = request.getParameter("pw1");
            String pw2 = request.getParameter("pw2");
            String email = request.getParameter("mail");

            Member member = new Member(name, id, pw1, pw2, email);
            Member ChkMember = memberService.getMember(id);


        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);

    }
}
