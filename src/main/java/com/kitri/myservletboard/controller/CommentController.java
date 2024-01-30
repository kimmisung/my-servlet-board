package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;
import com.kitri.myservletboard.service.CommentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/comment/*")
public class CommentController extends HttpServlet {

    CommentService commentService = CommentService.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>요청을 잘 응답받았습니다!</h1>");

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = requestURI.substring(contextPath.length());


        String view = "";
        if (command.contains("/comment/delete")) {

            String id = request.getParameter("deleteId");
            Long boardId = Long.parseLong(request.getParameter("boardId"));
            Comment comment = new Comment(Long.parseLong(id), boardId, null, null, null);
            commentService.deleteComment(comment);

            response.sendRedirect("/board/detail?id=" + boardId);
            return;

        } else if (command.contains("/board/detail")) {
            view = "/board/detail.jsp";

        } else if (command.equals("/comment/create")) {
            Long boardId = Long.parseLong(request.getParameter("boardId"));
            Long memberId = Long.parseLong(request.getParameter("memberId"));
            String contents = request.getParameter("contents");

            Comment comment = new Comment(null, boardId, memberId, contents, null);
            commentService.addComment(comment);

            response.sendRedirect("/board/detail?id=" + boardId);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}
