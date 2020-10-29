package PresetStore.model;

import java.util.List;
import java.util.Random;

public class Ordine {

    private int id;
    private long prezzoTotCent;
    private Utente utente;
    private List<Prodotto> prodotti;

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }



    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getPrezzoEur() {
        return String.format("%.2f", prezzoTotCent / 100.);
    }

    public Ordine() {
    }

    public long getPrezzoTotCent() {
        return prezzoTotCent;
    }

    public void setPrezzoTotCent(long prezzoTotCent) {
        this.prezzoTotCent = prezzoTotCent;
    }


   /* public int generaId()
    {
        Random random = new Random();
        int casuale = random.nextInt(1000);
        return casuale;
    }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
