package PresetStore.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import PresetStore.model.Categoria;
import PresetStore.model.Prodotto;
import PresetStore.model.ProdottoDAO;

@WebServlet("/Categoria")
public class CategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        List<Categoria> categorie = (List<Categoria>) getServletContext().getAttribute("categorie");
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("categoria", categorie.stream().filter(c -> c.getId() == id).findAny().get());

        String pagstr = request.getParameter("pag");
        int pag = pagstr == null ? 1 : Integer.parseInt(pagstr);
        request.setAttribute("pag", pag);

        String perpagstr = request.getParameter("perpag");
        int perpag = perpagstr == null ? 10 : Integer.parseInt(perpagstr);
        request.setAttribute("perpag", perpag);

        int totaleprodotti = prodottoDAO.countByCategoria(id);
        int npag = (totaleprodotti + perpag - 1) / perpag;
        request.setAttribute("npag", npag);

        String ordstr = request.getParameter("ord");
        ProdottoDAO.OrderBy ord = ordstr == null ? ProdottoDAO.OrderBy.DEFAULT : ProdottoDAO.OrderBy.valueOf(ordstr);
        request.setAttribute("ord", ord);

        List<Prodotto> prodotti = prodottoDAO.doRetrieveByCategoria(id, ord, (pag - 1) * perpag, perpag);
        request.setAttribute("prodotti", prodotti);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/categoria.jsp");
        requestDispatcher.forward(request, response);
    }



}
