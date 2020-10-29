package PresetStore.servlets;

import PresetStore.model.Ordine;
import PresetStore.model.OrdineDAO;
import PresetStore.model.Prodotto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminOrdini")
public class AdminOrdiniServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrdineDAO ordineDAO= new OrdineDAO();
        String address;
        List <Ordine> orders = ordineDAO.doRetrieveAll();
        request.setAttribute("ordini",orders);

        if(request.getParameter("rimuovi") != null)
        {
            String idstr = request.getParameter("id");
            ordineDAO.doDelete(Integer.parseInt(idstr));
            orders = ordineDAO.doRetrieveAll();
            request.setAttribute("notifica", "Ordine rimosso con successo.");
            request.setAttribute("ordini", orders);
            address="WEB-INF/jsp/adminordini.jsp";
        }

        if (request.getParameter("dettagli") != null)
        {
            String idord= request.getParameter("id");
            List <Prodotto> prodotti = ordineDAO.doRetrieveByIdOrd(Integer.parseInt(idord));
            address="WEB-INF/jsp/prodottiordine.jsp";
            request.setAttribute("prodotti",prodotti);
            request.setAttribute("id", idord);
        }


        else
        {
            request.setAttribute("ordini",orders);
            address="WEB-INF/jsp/adminordini.jsp";
        }


        RequestDispatcher requestDispatcher = request.getRequestDispatcher( address);
        requestDispatcher.forward(request, response);
    }
}
