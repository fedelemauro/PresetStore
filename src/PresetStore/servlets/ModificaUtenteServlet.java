package PresetStore.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import PresetStore.model.ProdottoDAO;
import PresetStore.model.Utente;
import PresetStore.model.UtenteDAO;


@WebServlet("/ModificaUtente")
public class ModificaUtenteServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyServletException.checkAdmin(request);



        RequestDispatcher dispatcher;
        Utente utente = new Utente(Integer.parseInt(request.getParameter("id")), request.getParameter("username"), request.getParameter("nome"), request.getParameter("email"));
        UtenteDAO user = new UtenteDAO();
        user.doUpdate(utente);
        List<Utente> utenti = user.doRetrieveAll(0, 10);
        request.setAttribute("notifica", "Utente modificato con successo.");
        request.setAttribute("utenti", utenti);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminutenti.jsp");
        requestDispatcher.forward(request, response);




    }
}



