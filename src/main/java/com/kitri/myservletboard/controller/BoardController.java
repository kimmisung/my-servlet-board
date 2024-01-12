package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.BoardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    BoardService boardService = BoardService.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request 클라이언트의 요청이 담겨 옴, response로 응답해 줌

        //브라우저에서 작성한 내용을 불러올 때
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
        if (command.equals("/board/list")) {

            //페이지 정보를 넘겨주어야함 -> url : /board/list?page=3
            String page = request.getParameter("page"); //페이지정보가 String이니까 형변환 필요
            if (page == null){
                page = "1";
            }
            Pagination pagination = new Pagination(Integer.parseInt(page));

            String type = request.getParameter("type");
            String keyword = request.getParameter("keyword");
            String period = request.getParameter("period");


            ArrayList<Board> boards
                    = boardService.getBoards(type, keyword, period, pagination);

            request.setAttribute("pagination", pagination); //페이지네이션 정보를 담아줄 예정
            //가져온 리스트를 jsp한테 넘겨줘야함 -> jsp가 동적으로 만들어줌

            request.setAttribute("type", type);
            request.setAttribute("keyword", keyword);
            request.setAttribute("period", period);
            request.setAttribute("boards", boards); //저장소 역할(key,value)형식
            //request.getRequestDispatcher("/view/board/list.jsp");
            view += "list.jsp";


        }   else if (command.equals("/board/createForm")) {
            view += "createForm.jsp";

        } else if (command.contains("/board/updateForm")) {
            //상세 페이지의 내욜을 채워서 달라
            String id = request.getParameter("id");
            Board board = boardService.getBoard(Long.parseLong(id));
            request.setAttribute("board", board);
            view += "updateForm.jsp";

        }else if (command.contains("/board/update")){
            //수정폼에서 보낸 데이터를 읽는다
            //수정하려는 데이터를 수정한다
            //식별자 id를 함께 보내주어야 함

            Long id = Long.parseLong(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            //board로 넘겨주는 것은 올바른 방법은 아님 -> dto, VO형태의 데이터 타입을 사용할 예정
            Board board = new Board(id, title, content, null, LocalDateTime.now(), 0,0);
            boardService.updateBoard(board);

//          리다이렉트로 리스트로 보내주기
            response.sendRedirect("/board/list");
            return;





        } else if (command.equals("/board/create")) {
            //데이터를 읽고
            //등록시키면 된다

            String title = request.getParameter("title"); //제목 (.jsp파일에 지정된 name을 보면된다
            String content = request.getParameter("content"); //내용
            String writer = request.getParameter("writer"); //작성자

            //보드 객체로 넘겨주기로 약속했기 때문에, Board 객체를 만들어 주어야 한다
            //id -> null을 넣어줄 수 있는 것은 object 타입이여서 가능 (고유의 식별자이기 때문에 따로 지정하지 않을 예정)
            //조회수와 댓글수는 처음 생성 시 0으로 주면 됨
            //게시판 객체 생성
            Board board = new Board(null, title, content, writer, LocalDateTime.now(), 0, 0);
            boardService.addBoard(board);

            //생성된지 확인하려면 list로 보내주기
            response.sendRedirect("/board/list");
            return;

        } else if (command.contains("/board/delete")){
            Long id = Long.parseLong(request.getParameter("id"));
            Board board = new Board(id, null, null, null, null, 0,0);
            boardService.deleteBoard(board);

            response.sendRedirect("/board/list");
            return;

        } else if (command.contains("/board/detail")) {
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
