package PresetStore.model;

import java.util.List;


public class Prodotto {
    private int id;
    private String nome;
    private String descrizione;
    private long prezzoCent;
    private List<Categoria> categorie;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public long getPrezzoCent() {
        return prezzoCent;
    }

    public void setPrezzoCent(long prezzoCent) {
        this.prezzoCent = prezzoCent;
    }

   public String getPrezzoEuro() {
        return String.format("%.2f", prezzoCent / 100.);
    }

    public List<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Prodotto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", prezzoCent=" + prezzoCent
                + ", categorie=" + categorie + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
        result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
        result = prime * result + id;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + (int) (prezzoCent ^ (prezzoCent >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Prodotto other = (Prodotto) obj;
        if (categorie == null) {
            if (other.categorie != null)
                return false;
        } else if (!categorie.equals(other.categorie))
            return false;
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        if (id != other.id)
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (prezzoCent != other.prezzoCent)
            return false;
        return true;
    }

}

