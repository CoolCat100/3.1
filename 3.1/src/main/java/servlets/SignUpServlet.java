package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class SignUpServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get public user profile
//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().println("привет");
//        //todo: module 2 home work
//    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        if (login == null || password == null || password.isEmpty() || login.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Необходимо указать данные");
            return;
        }
        try {
            if (accountService.getUserByLogin(request.getParameter("login")) == null) {
                try {
                    accountService.addNewUser(new UserProfile(request.getParameter("login").toLowerCase(), request.getParameter("password").toLowerCase(),
                            request.getParameter("email")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("регистрация выполнена");
            } else {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Пользователь с таким логином уже зарегистрирован");
            }
            //todo: module 2 home work
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //change profile
//    public void doPut(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        //todo: module 2 home work
//    }
//
//    //unregister
//    public void doDelete(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        //todo: module 2 home work
//    }
}
