package PresetStore.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import PresetStore.model.Prodotto;
import PresetStore.model.ProdottoDAO;


@WebServlet("/RicercaAjax")
public class RicercaAjaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONArray prodJson = new JSONArray();
        String query = request.getParameter("q");
        if (query != null) {
            List<Prodotto> prodotti = prodottoDAO.doRetrieveByNome(query + "*", 0, 10);
            for (Prodotto p : prodotti) {
                prodJson.put(p.getNome());
            }
        }
        response.setContentType("application/json");
        response.getWriter().append(prodJson.toString());
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
