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
import PresetStore.model.CategoriaDAO;


@WebServlet("/AdminCategoria")
public class AdminCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MyServletException.checkAdmin(request);

        String idstr = request.getParameter("id");
        if (idstr != null) {
            @SuppressWarnings("unchecked")
            List<Categoria> categorie = ((List<Categoria>) getServletContext().getAttribute("categorie"));

            Categoria categoria = null;
            if (!idstr.isEmpty()) {
                int id = Integer.parseInt(idstr);
                categoria = categorie.stream().filter(c -> c.getId() == id).findAny().get();
            }

            if (request.getParameter("rimuovi") != null) {
                categoriaDAO.doDelete(categoria.getId());
                categorie.remove(categoria);
                request.setAttribute("notifica", "Categoria rimossa con successo.");
            } else {
                String nome = request.getParameter("nome");
                String descrizione = request.getParameter("descrizione");
                if (nome != null && descrizione != null) { // modifica/aggiunta categoria
                    if (categoria == null) { // aggiunta nuova categoria
                        categoria = new Categoria();
                        categoria.setNome(nome);
                        categoria.setDescrizione(descrizione);
                        categoriaDAO.doSave(categoria);
                        categorie.add(categoria);
                        request.setAttribute("notifica", "Categoria aggiunta con successo.");
                    } else { // modifica categoria esistente
                        categoria.setNome(nome);
                        categoria.setDescrizione(descrizione);
                        categoriaDAO.doUpdate(categoria);
                        request.setAttribute("notifica", "Categoria modificata con successo.");
                    }
                }
                request.setAttribute("categoria", categoria);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/admincategoria.jsp");
        requestDispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}