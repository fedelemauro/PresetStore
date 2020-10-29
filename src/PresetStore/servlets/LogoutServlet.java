package PresetStore.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.IOException;
import java.util.Arrays;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import PresetStore.model.LoginDAO;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginDAO loginDAO = new LoginDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("utente");
        request.getSession().removeAttribute("carrello");


        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            // cookie con nome 'login' o null se non esiste
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("login")).findAny().orElse(null);
            if (cookie != null) {
                cookie.setMaxAge(0); // rimuove cookie
                response.addCookie(cookie);
                String id = cookie.getValue().split("_")[0];
                loginDAO.doDelete(id);
            }
        }

       String dest = request.getHeader("referer");
        if (dest == null || dest.contains("/Logout") || dest.trim().isEmpty()) {
            dest = ".";
        }

        response.sendRedirect(".");

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
