package PresetStore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class OrdineDAO {

    public List<Ordine> doRetrieveAll(int offset, int limit) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con
                    .prepareStatement("SELECT id, prezzoTotCent FROM ordine LIMIT ?, ?");
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ArrayList<Ordine> ordini = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ordine p = new Ordine();
                p.setId(rs.getInt(1));
                p.setPrezzoTotCent(rs.getLong(2));
                p.setUtente(getUtente(con, p.getId()));
                ordini.add(p);
            }
            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Ordine> doRetrieveAll() {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con
                    .prepareStatement("SELECT id, prezzoTotCent FROM ordine ORDER BY id DESC ");
            ArrayList<Ordine> ordini = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ordine p = new Ordine();
                p.setId(rs.getInt(1));
                p.setPrezzoTotCent(rs.getLong(2));
                p.setUtente(getUtente(con, p.getId()));
                ordini.add(p);
            }
            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private static Utente getUtente(Connection con, int idOrdine) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                    "SELECT id, username, nome, email FROM utente LEFT JOIN ordine_utente ON id=idutente WHERE idordine=?");
            ps.setInt(1, idOrdine);
            Utente utente = new Utente();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                utente.setId(rs.getInt(1));
                utente.setUsername(rs.getString(2));
                utente.setNome(rs.getString(3));
                utente.setEmail(rs.getString(4));
            }
            return utente;
        }


    public Ordine doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con
                    .prepareStatement("SELECT id, prezzoTotCent FROM ordine WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ordine p = new Ordine();
                p.setId(rs.getInt(1));
                p.setPrezzoTotCent(rs.getLong(2));
                p.setUtente(getUtente(con, p.getId()));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Ordine> doRetrieveByUtente(int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, prezzoTotCent FROM ordine LEFT JOIN ordine_utente ON id=idordine WHERE idutente=? ");
            ps.setInt(1, idUtente);
            ArrayList<Ordine> ordini = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ordine p = new Ordine();
                p.setId(rs.getInt(1));
                p.setPrezzoTotCent(rs.getLong(2));
                p.setUtente(p.getUtente());
                ordini.add(p);
            }
            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


     public void doSave(Ordine ordine) {
         try (Connection con = ConPool.getConnection()) {
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO ordine (id,prezzoTotCent) VALUES(?,?)",
                     Statement.RETURN_GENERATED_KEYS);
             ps.setInt(1, ordine.getId());
             ps.setLong(2, ordine.getPrezzoTotCent());
             if (ps.executeUpdate() != 1) {
                 throw new RuntimeException("INSERT error.");
             }
             ResultSet rs = ps.getGeneratedKeys();
             rs.next();
             int id = rs.getInt(1);
             ordine.setId(id);

             PreparedStatement psCa = con
                     .prepareStatement("INSERT INTO ordine_utente (idordine, idutente) VALUES (?, ?)");

                 psCa.setInt(1, ordine.getId());
                 psCa.setInt(2, ordine.getUtente().getId());
                 psCa.addBatch();
             psCa.executeBatch();


             PreparedStatement psOr = con
					.prepareStatement("INSERT INTO ordine_prodotto (idordine, idprodotto) VALUES (?, ?)");
			for (Prodotto c : ordine.getProdotti()) {
				psOr.setInt(1, id);
				psOr.setInt(2, c.getId());
				psOr.addBatch();
			}
			psOr.executeBatch();


         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }



    public void doDelete(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM ordine WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<Prodotto> doRetrieveByIdOrd(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
                    "SELECT id, nome, prezzoCent FROM prodotto LEFT JOIN ordine_prodotto ON id=idprodotto WHERE idordine=?");
			ps.setInt(1, id);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzoCent(rs.getLong(3));
                prodotti.add(p);
            }
			return prodotti;


		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}





}
