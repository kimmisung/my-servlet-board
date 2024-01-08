package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.controller.data.Board;
import com.kitri.myservletboard.service.BoardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    BoardService boardService = BoardService.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request 클라이언트의 요청이 담겨 옴, response로 응답해 줌
        request.setCharacterEncoding("UTF-8");
        // HTML이 UTF-8 형식이라는 것을 브라우저에게 전달
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<h1>요청을 잘 응답받았습니다!</h1>");

        //URL을 파싱해서 어떤 요청인지 파악 -> request
        String requestURI = request.getRequestURI();  //URL주소 확인할 수 있음 ex)/board/list
        String contextPath = request.getContextPath(); // / or 공백
        String command = requestURI.substring(contextPath.length());   // /board/list 길이만큼 부분 문자열 추출
        //out.println("command = " + command);

        String view = "/view/board/";

        if (command.equals("/board/list")){
            ArrayList<Board> boards = boardService.getBoards(); //서비스를 통해 게시판 리스트를 가져옴
            //가져온 리스트를 jsp한테 넘겨줘야함 -> jsp가 동적으로 만들어줌
            request.setAttribute("boards",boards); //저장소 역할(key,value)형식

            //request.getRequestDispatcher("/view/board/list.jsp");
            view += "list.jsp";
        }
        else if (command.equals("/board/createForm")){
            view += "createForm.jsp";
        }
        else if (command.contains("/board/updateForm")){
            view += "updateForm.jsp";
        }
        else if (command.contains("/board/detail")){
            // /board/detail?id=3
            // id에 해당하는 게시판 하나를 가져오면 된다
            // 클라이언트의 요청이 서버로 들어올 때 HttpServletRequest -> request를 통해 들어온다
//            request.getQueryString();
//            out.println(request.getQueryString()); -> "id=3" 형식으로 가져오게 됨
//            String queryString = request.getQueryString();
            String id = request.getParameter("id"); //게시판 번호를 얻기엔 getParameter 편리
            Board board = boardService.getBoard(Long.parseLong(id));
            //board 데이터를 detail.jsp 에 전달하기 위해 어딘가에 담아줘야한다.
            request.setAttribute("board", board);
            view += "detail.jsp";
        }
        else if (command.equals("/board/join")){

        }
        else if (command.equals("/board/login")){

        }
        else if (command.equals("/board/registration")){

        }
        else if (command.contains("/board/updateForm")){
            view += "updateForm.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);


        //*뷰(페이지)를 응답하는 방법
        // 1. 리다이렉트
        // 2. 포워드 : request.getRequestDispatcher("/view/board/list");
    }
}
