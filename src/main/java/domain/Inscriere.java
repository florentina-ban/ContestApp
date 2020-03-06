package domain;

public class Inscriere {
    private Integer idProba;
    private Integer idPart;
    private Integer nrInscriere;

    public Inscriere(int nr,Integer idProba, Integer idPart) {
        this.idProba = idProba;
        this.idPart = idPart;
        this.nrInscriere=nr;
    }

    public Integer getIdProba() {
        return idProba;
    }

    public Integer getIdPart() {
        return idPart;
    }
}
