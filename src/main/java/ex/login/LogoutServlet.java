package ex.login;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/ex/logout")
public class LogoutServlet extends HttpServlet {

    @Override //로그아웃 시 get으로 받아줌 -> 세션정보를 주었던 것을 만료시키면 로그아웃
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     HttpSession session = request.getSession();
     session.invalidate();

     response.sendRedirect("/ex/login");

    }
}
