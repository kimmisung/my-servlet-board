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

@WebServlet("/ex/login")
public class LoginServlet extends HttpServlet {
    //test용이라서 DB말고, 기본정보 해쉬맵으로 생성해줌
    static HashMap<String, String> members = new HashMap<>();
    static {
        members.put("abc123","12345");
        members.put("captain91","12345");
        members.put("ilovecoding","12345");
    }

    @Override //GET으로 요청하면 GET이 응답 -> 로그인 폼을 던져줄 예정
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ex/login/login.jsp");
        dispatcher.forward(request, response);

    }


    @Override //POST로 요청하며 POST가 응답 -> 로그인을 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        //로그인 검증
        boolean isLoginFailed = false;
        if (id.isEmpty() || pw.isEmpty()) {
            isLoginFailed = true;
        }
        if (members.get(id) == null){ //null체크
            isLoginFailed = true;
        } else {
            if (!members.get(id).equals(pw)){
                isLoginFailed = true;
            }
        }

        if (isLoginFailed){ //로그인 실패
            request.setAttribute("loginFailed", isLoginFailed);
        } else { //**로그인 성공 -> session에 담아주면 된다(서버가 가지고 있는 정보)
            HttpSession session = request.getSession(); //사용자에 대한 세션
            session.setAttribute("id", id);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ex/login/login.jsp");
        dispatcher.forward(request,response);
    }
}
