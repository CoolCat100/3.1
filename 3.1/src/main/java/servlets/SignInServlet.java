package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        String sessionId = request.getSession().getId();
//        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        if (profile == null) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().println("регайся");
//        } else {
//            Gson gson = new Gson();
//            String json = gson.toJson(profile);
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().println(json);
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().println("зареган");
//        }
//    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Unauthorized");
            return;
        }

        UserProfile profile = null;
        try {
            profile = accountService.getUserByLogin(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (profile == null || !profile.getPass().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Authorized: " + login);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
//    public void doDelete(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        String sessionId = request.getSession().getId();
//        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        if (profile == null) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            accountService.deleteSession(sessionId);
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().println("Goodbye!");
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//
//    }
}
