
package PresetStore.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdottoDAO {
	public enum OrderBy {
		DEFAULT(""), PREZZO_ASC("ORDER BY prezzoCent ASC"), PREZZO_DESC("ORDER BY prezzoCent DESC");
		private String sql;

		private OrderBy(String sql) {
			this.sql = sql;
		}
	};

	public List<Prodotto> doRetrieveAll(int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT id, nome, descrizione, prezzoCent FROM prodotto LIMIT ?, ?");
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Prodotto> doRetrieveAll() {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT id, nome, descrizione, prezzoCent FROM prodotto ");

			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Prodotto doRetrieveById(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT id, nome, descrizione, prezzoCent FROM prodotto WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				return p;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countByCategoria(int categoria) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT COUNT(*) FROM prodotto LEFT JOIN prodotto_categoria ON id=idprodotto WHERE idcategoria=?");
			ps.setInt(1, categoria);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Prodotto> doRetrieveByCategoria(int categoria, OrderBy orderBy, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"SELECT id, nome, descrizione, prezzoCent FROM prodotto LEFT JOIN prodotto_categoria ON id=idprodotto WHERE idcategoria=? "
							+ orderBy.sql + " LIMIT ?, ?");
			ps.setInt(1, categoria);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Prodotto> doRetrieveByNomeOrDescrizione(String against, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"SELECT id, nome, descrizione, prezzoCent FROM prodotto WHERE MATCH(nome, descrizione) AGAINST(?) LIMIT ?, ?");
			ps.setString(1, against);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Prodotto> doRetrieveByNome(String against, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"SELECT id, nome, descrizione, prezzoCent FROM prodotto WHERE MATCH(nome) AGAINST(? IN BOOLEAN MODE) LIMIT ?, ?");
			ps.setString(1, against);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzoCent(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(Prodotto prodotto) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO prodotto (nome, descrizione, prezzoCent) VALUES(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, prodotto.getNome());
			ps.setString(2, prodotto.getDescrizione());
			ps.setLong(3, prodotto.getPrezzoCent());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			prodotto.setId(id);

			PreparedStatement psCa = con
					.prepareStatement("INSERT INTO prodotto_categoria (idprodotto, idcategoria) VALUES (?, ?)");
			for (Categoria c : prodotto.getCategorie()) {
				psCa.setInt(1, id);
				psCa.setInt(2, c.getId());
				psCa.addBatch();
			}
			psCa.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doUpdate(Prodotto prodotto) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("UPDATE prodotto SET nome=?, descrizione=?, prezzoCent=? WHERE id=?");
			ps.setString(1, prodotto.getNome());
			ps.setString(2, prodotto.getDescrizione());
			ps.setLong(3, prodotto.getPrezzoCent());
			ps.setInt(4, prodotto.getId());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("UPDATE error.");
			}

			if (prodotto.getCategorie().isEmpty()) {
				PreparedStatement psCaDel = con.prepareStatement("DELETE FROM prodotto_categoria WHERE idprodotto=?");
				psCaDel.setInt(1, prodotto.getId());
				psCaDel.executeUpdate();
			} else {
				PreparedStatement psCaDel = con
						.prepareStatement("DELETE FROM prodotto_categoria WHERE idprodotto=? AND idcategoria NOT IN ("
								+ prodotto.getCategorie().stream().map(c -> String.valueOf(c.getId()))
								.collect(Collectors.joining(","))
								+ ")");
				psCaDel.setInt(1, prodotto.getId());
				psCaDel.executeUpdate();

				PreparedStatement psCa = con.prepareStatement(
						"INSERT IGNORE INTO prodotto_categoria (idprodotto, idcategoria) VALUES (?, ?)");
				for (Categoria c : prodotto.getCategorie()) {
					psCa.setInt(1, prodotto.getId());
					psCa.setInt(2, c.getId());
					psCa.addBatch();
				}
				psCa.executeBatch();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doDelete(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement("DELETE FROM prodotto WHERE id=?");
			ps.setInt(1, id);
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("DELETE error.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Categoria> getCategorie(Connection con, int idProdotto) throws SQLException {
		PreparedStatement ps = con.prepareStatement(
				"SELECT id, nome, descrizione FROM categoria LEFT JOIN prodotto_categoria ON id=idcategoria WHERE idprodotto=?");
		ps.setInt(1, idProdotto);
		ArrayList<Categoria> categorie = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Categoria c = new Categoria();
			c.setId(rs.getInt(1));
			c.setNome(rs.getString(2));
			c.setDescrizione(rs.getString(3));
			categorie.add(c);
		}
		return categorie;
	}
}
