package repository;

import domain.Inscriere;
import myException.InscrieriException;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class RepoInscrieri implements Repo<Inscriere> {
    ConnectionHelper connectionHelper;

    public RepoInscrieri(Properties prop) {
        this.connectionHelper = new ConnectionHelper(prop);
    }

    public void valideaza(Inscriere inscriere) throws InscrieriException{
        long nr=this.getAll().stream()
                .filter(x->{
                    return x.getIdPart()==inscriere.getIdPart();
                })
                .count();
        if (nr>=2)
            throw new InscrieriException("candidatul e deja inscris la 2 probe");
    }

    @Override
    public void adauga(Inscriere elem) throws InscrieriException {
        valideaza(elem);
        try (Connection connection = connectionHelper.getConnection();) {
            try (PreparedStatement insStat = connection.prepareStatement("insert into partprobe (IdPart, IdProba) " +
                    "values (?,?);");) {
                insStat.setInt(2, elem.getIdProba());
                insStat.setInt(1, elem.getIdPart());
                int rowAdded = insStat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Collection<Inscriere> getAll() {
        List<Inscriere> all = new ArrayList<>();
        try (Connection connection = connectionHelper.getConnection();) {
            try (PreparedStatement selectStm = connection.prepareStatement("select * from partprobe");) {
                try (ResultSet rez = selectStm.executeQuery();) {
                    while (rez.next()) {
                        int nrInscr = rez.getInt("NrInscriere");
                        int nrProba = rez.getInt("idProba");
                        int nrPart = rez.getInt("idPart");
                        Inscriere inscriere = new Inscriere(nrInscr, nrProba, nrPart);
                        all.add(inscriere);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public void modifica(Inscriere elem) {

    }

    @Override
    public int getSize() {
        try(Connection connection=connectionHelper.getConnection();){
            try (Statement sizeStm=connection.createStatement()){
                try(ResultSet rs=sizeStm.executeQuery("select count(*) as size from partprobe;")){
                    rs.next();
                    return rs.getInt("size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void sterge(Integer id) {
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement delStat = connection.prepareStatement("delete from partprobe where NrInscriere=?");) {
                delStat.setInt(1, id);
                delStat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inscriere cauta(int id) {
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement delStat = connection.prepareStatement("select * from partprobe where NrInscriere=?");) {
                delStat.setInt(1, id);
                try(ResultSet resultSet= delStat.executeQuery();){
                    resultSet.next();
                    int nr=resultSet.getInt("nrInscriere");
                    int idPart=resultSet.getInt("idPart");
                    int idProba=resultSet.getInt("idProba");
                    return new Inscriere(nr,idProba,idPart);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

