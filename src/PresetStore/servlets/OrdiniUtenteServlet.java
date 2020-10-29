package PresetStore.servlets;

import PresetStore.model.Ordine;
import PresetStore.model.OrdineDAO;
import PresetStore.model.Prodotto;
import PresetStore.model.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/OrdiniUtente")
public class OrdiniUtenteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        String address;
        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> ordini = ordineDAO.doRetrieveByUtente(utente.getId());
        request.setAttribute("ordini", ordini);
        address="WEB-INF/jsp/ordiniUtente.jsp";

        if (request.getParameter("dettagli") != null)
        {
            String idord= request.getParameter("id");

            List <Prodotto> prodotti = ordineDAO.doRetrieveByIdOrd(Integer.parseInt(idord));
            address="WEB-INF/jsp/prodottiordine.jsp";
            request.setAttribute("prodotti",prodotti);
            request.setAttribute("id", idord);
        }




        RequestDispatcher requestDispatcher = request.getRequestDispatcher(address);
        requestDispatcher.forward(request, response);
    }
}
