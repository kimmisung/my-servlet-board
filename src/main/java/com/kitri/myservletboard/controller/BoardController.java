package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
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
//        out.println("command = " + command);

        String view = "/view/board/";

        if (command.equals("/board/list")) {
            ArrayList<Board> boards = boardService.getBoards();
            request.setAttribute("boards", boards);
            //request.getRequestDispatcher("/view/board/list.jsp");
            view += "list.jsp";
        } else if (command.equals("/board/createForm")) {
            view += "createForm.jsp";
        } else if (command.equals("/board/create")) {

        } else if (command.contains("/board/updateForm")) {
            view += "updateForm.jsp";
        } else if (command.contains("/board/update")) {

        } else if (command.contains("/board/detail")) {
            String id = request.getParameter("id");
            Board board = boardService.getBoard(Long.parseLong(id));

            request.setAttribute("board",board);
            view += "detail.jsp";
        } else if (command.contains("/board/delete")) {

        } else if (command.equals("/board/join")) {

        } else if (command.equals("/board/login")) {

        } else if (command.equals("/board/registration")) {

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);

        //*뷰(페이지)를 응답하는 방법
        // 1. 리다이렉트
        // 2. 포워드 : request.getRequestDispatcher("/view/board/list");
    }
}
