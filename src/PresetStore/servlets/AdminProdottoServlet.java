package PresetStore.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import PresetStore.model.Categoria;
import PresetStore.model.Prodotto;
import PresetStore.model.ProdottoDAO;


@WebServlet("/AdminProdotto")
public class AdminProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MyServletException.checkAdmin(request);

        String idstr = request.getParameter("id");
        if (idstr != null) {
            if (request.getParameter("rimuovi") != null) {
                prodottoDAO.doDelete(Integer.parseInt(idstr));
                request.setAttribute("notifica", "Prodotto rimosso con successo.");
            } else {
                Prodotto prodotto;
                String nome = request.getParameter("nome");
                String descrizione = request.getParameter("descrizione");
                String prezzoCent = request.getParameter("prezzoCent");
                if (nome != null && descrizione != null && prezzoCent != null) {
                    // modifica/aggiunta prodotto
                    prodotto = new Prodotto();
                    prodotto.setNome(nome);
                    prodotto.setDescrizione(descrizione);
                    prodotto.setPrezzoCent(Long.parseLong(prezzoCent));

                    String[] categorie = request.getParameterValues("categorie");
                    prodotto.setCategorie(categorie != null ? Arrays.stream(categorie).map(id -> {
                        Categoria c = new Categoria();
                        c.setId(Integer.parseInt(id));
                        return c;
                    }).collect(Collectors.toList()) : Collections.emptyList());

                    if (idstr.isEmpty()) { // aggiunta nuovo prodotto
                        prodottoDAO.doSave(prodotto);
                        request.setAttribute("notifica", "Prodotto aggiunto con successo.");
                    } else { // modifica prodotto esistente
                        prodotto.setId(Integer.parseInt(idstr));
                        prodottoDAO.doUpdate(prodotto);
                        request.setAttribute("notifica", "Prodotto modificato con successo.");
                    }
                } else {
                    int id = Integer.parseInt(idstr);
                    prodotto = prodottoDAO.doRetrieveById(id);
                }
                request.setAttribute("prodotto", prodotto);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminprodotto.jsp");
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