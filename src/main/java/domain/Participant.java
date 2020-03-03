package domain;

import java.util.ArrayList;

public class Participant {
    private Integer id;
    private String nume;
    private int varsta;
    private ArrayList<Proba> probe=new ArrayList<>();

    public Participant(Integer id, String nume, int varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
    }

    public void addProba(Proba p){
        probe.add(p);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", probe=" + probe +
                '}';
    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public void setProbe(ArrayList<Proba> probe) {
        this.probe = probe;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public ArrayList<Proba> getProbe() {
        return probe;
    }
}
