package domain;

public class Proba {
    private int idProba;
    private String nume;
    private CategVarsta categVarsta;


    public Proba(int id,String nume, CategVarsta categVarsta) {
        this.idProba=id;
        this.nume = nume;
        this.categVarsta=categVarsta;
    }

    public int getIdProba() {
        return idProba;
    }

    public String getNume() {
        return nume;
    }

    public CategVarsta getCategVarsta() {
        return categVarsta;
    }

    @Override
    public String toString() {
        return "proba: "+nume+" "+categVarsta;
    }
}
