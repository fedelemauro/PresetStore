package PresetStore.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import PresetStore.model.*;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UtenteDAO utenteDAO = new UtenteDAO();
    private final LoginDAO loginDAO = new LoginDAO();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Utente utente = null;
        if (username != null && password != null) {
            utente = utenteDAO.doRetrieveByUsernamePassword(username, password);
        }

        if (utente == null) {
            throw new MyServletException("Username e/o password non validi.");
        }
        request.getSession().setAttribute("utente", utente);

        Login login = new Login();
        login.setIdutente(utente.getId());
        login.setToken(UUID.randomUUID().toString());
        login.setTime(Timestamp.from(Instant.now()));

        loginDAO.doSave(login);

        Cookie cookie = new Cookie("login", login.getId() + "_" + login.getToken());
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 giorni
        response.addCookie(cookie);

        String dest = request.getHeader("referer");
        if (dest == null || dest.contains("/Login") || dest.trim().isEmpty()) {
            dest = ".";
        }
        response.sendRedirect(dest);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}