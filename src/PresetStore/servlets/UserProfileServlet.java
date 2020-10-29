package PresetStore.servlets;

import PresetStore.model.Carrello;
import PresetStore.model.Utente;
import PresetStore.model.UtenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UserProfile")
public class UserProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UtenteDAO utenteDAO = new UtenteDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        boolean admin;

        if(request.getParameter("moduser") != null)
        {
            if (utente.isAdmin())
            {
                admin = true;
            }
            else {
                admin = false;
            }

            utente = new Utente(Integer.parseInt(request.getParameter("id")), request.getParameter("username"), request.getParameter("nome"), request.getParameter("email"));
            utente.setAdmin(admin);
            UtenteDAO user = new UtenteDAO();
            user.doUpdate(utente);
            session.setAttribute("id",utente);
            session.setAttribute("utente", utente);
            request.setAttribute("notifica", "Utente modificato con successo.");
            request.setAttribute("utente", utente);
        }




        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/userprofile.jsp");
        requestDispatcher.forward(request, response);

    }
}
