package PresetStore.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import PresetStore.model.Ordine;
import PresetStore.model.OrdineDAO;
import PresetStore.model.Utente;
import PresetStore.model.UtenteDAO;


@WebServlet("/AdminUtenti")
public class AdminUtentiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UtenteDAO utenteDAO = new UtenteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MyServletException.checkAdmin(request);


        String address;
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Utente> utenti = utenteDAO.doRetrieveAll(0, 10);


        if(request.getParameter("rimuovi") != null)
        {
            String idstr = request.getParameter("id");
            utenteDAO.doDelete(Integer.parseInt(idstr));
            utenti = utenteDAO.doRetrieveAll(0, 10);
            request.setAttribute("notifica", "Utente rimosso con successo.");
            request.setAttribute("utenti", utenti);
            address="WEB-INF/jsp/adminutenti.jsp";
        }
        else if (request.getParameter("modifica") != null)
        {
            String idstr = request.getParameter("id");
            Utente utente = utenteDAO.doRetrieveById(Integer.parseInt(idstr));
            address="WEB-INF/jsp/modificautente.jsp";
            request.setAttribute("utente", utente);
        }

        else if (request.getParameter("dettagli")!= null)
        {
            String idstr = request.getParameter("id");
            OrdineDAO ordineDAO = new OrdineDAO();
            List<Ordine> ordini = ordineDAO.doRetrieveByUtente(Integer.parseInt(idstr));
            request.setAttribute("ordini", ordini);
            address="WEB-INF/jsp/ordiniUtente.jsp";
        }
        else
        {
            request.setAttribute("utenti", utenti);
            address="WEB-INF/jsp/adminutenti.jsp";
        }




        RequestDispatcher requestDispatcher = request.getRequestDispatcher(address);
        requestDispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

