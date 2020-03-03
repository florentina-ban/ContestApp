package domain;

public class CategVarsta {
    private String nume;
    private int varstaStart;
    private int varstaEnd;

    public CategVarsta(String nume, int varstaStart, int varstaEnd) {
        this.nume = nume;
        this.varstaStart = varstaStart;
        this.varstaEnd = varstaEnd;
    }

    @Override
    public String toString() {
        return "CategVarsta: "+nume;
    }

    public boolean apartine(int varsta){
        return varsta>=this.varstaStart && varsta<=varstaEnd;
    }

    public String getNume() {
        return nume;
    }

    public int getVarstaStart() {
        return varstaStart;
    }

    public int getVarstaEnd() {
        return varstaEnd;
    }
}