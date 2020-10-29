package PresetStore.servlets;

import PresetStore.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Acquisto")
public class AcquistoServlet extends HttpServlet {
    private final OrdineDAO ordineDAO = new OrdineDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utente utente= (Utente) request.getSession().getAttribute("utente");
        Carrello carrello = new Carrello();
        HttpSession session = request.getSession();
        String prezzoTot = request.getParameter("prezzo");
        String [] idprodotti= request.getParameterValues("idprodotto");

        if (utente != null)
        {
            int iduser = utente.getId();
            Ordine ordine = new Ordine();
            ArrayList <Prodotto> listaprodotti = new ArrayList<Prodotto>();

            if (prezzoTot==null && idprodotti==null)
            {
                prezzoTot= (String) session.getAttribute("totale");
                idprodotti= (String[]) session.getAttribute("prodotti");

            }
            for (int i=0; i<idprodotti.length; i++)
            {
                ProdottoDAO pdao = new ProdottoDAO();
                listaprodotti.add(pdao.doRetrieveById(Integer.parseInt(idprodotti[i])));
            }
            ordine.setProdotti(listaprodotti);
            ordine.setPrezzoTotCent(Long.parseLong(prezzoTot));
            ordine.setUtente(utente);
            ordineDAO.doSave(ordine);
            session.setAttribute("carrello", carrello);
            request.setAttribute("notifica", "Grazie per l'acquisto. Ordine confermato numero: "+ordine.getId());
            session.removeAttribute("totale");
            session.removeAttribute("prodotti");
        }
        else
        {
            request.setAttribute("notifica", "Accedi per completare l'acquisto");
            session.setAttribute("totale",prezzoTot);
            session.setAttribute("prodotti",idprodotti);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher( "WEB-INF/jsp/carrello.jsp");
        requestDispatcher.forward(request, response);




    }
}
