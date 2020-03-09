package repository;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import myException.InscrieriException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class RepoInscrieri implements IRepoInscrieri {
    ConnectionHelper connectionHelper;
    RepoParticipanti repoParticipanti;
    RepoProbe repoProbe;
    private static final Logger logger= LogManager.getLogger(RepoParticipanti.class.getName());

    public RepoInscrieri(Properties prop,RepoParticipanti repopa,RepoProbe repopr) {
        this.connectionHelper = new ConnectionHelper(prop);
        this.repoParticipanti=repopa;
        this.repoProbe=repopr;
    }

    public void valideaza(Inscriere inscriere) throws InscrieriException{
        if(this.getProbeLaParticipant(inscriere.getIdPart()).size()>=2)
            throw new InscrieriException("candidatul e deja inscris la 2 probe");
    }

    @Override
    public void adauga(Inscriere elem) throws InscrieriException {
        logger.traceEntry("adauga inscriere {}",elem);
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
        logger.traceEntry("get all");
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
        logger.traceEntry("getSize");
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
        logger.traceEntry("sterge inregistrare cu id {}",id);
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
        logger.traceEntry("cauta inregistrare cu id {}",id);
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

    @Override
    public Collection<Participant> getParticipantiLaProba(int idProba) {
        logger.traceEntry("cauta participanti la proba cu id: {}",idProba);
        ArrayList<Participant> allPart=new ArrayList<>();
        try (Connection connection=connectionHelper.getConnection()){
            try (PreparedStatement selectStm=connection.prepareStatement("select id from participanti inner join " +
                    "partprobe on participanti.Id = partprobe.IdPart where partprobe.idProba=?" )){
                selectStm.setInt(1,idProba);
                try(ResultSet rs=selectStm.executeQuery()){
                    while (rs.next()){
                        Participant part=repoParticipanti.cauta(rs.getInt("id"));
                        allPart.add(part);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allPart;
    }

    @Override
    public Collection<Proba> getProbeLaParticipant(int idPart) {
        logger.traceEntry("cauta probe pt participantul cu id: {}",idPart);
        ArrayList<Proba> allProbe=new ArrayList<>();
        try(Connection connection=connectionHelper.getConnection()){
            try(PreparedStatement selectStm=connection.prepareStatement("select probe.idProba from probe inner join " +
                    " partprobe on probe.idProba = partprobe.IdProba where partprobe.idPart=?")){
                    selectStm.setInt(1,idPart);
                    try(ResultSet rs=selectStm.executeQuery()){
                        while (rs.next()){
                            Proba proba=repoProbe.cauta(rs.getInt("idProba"));
                            allProbe.add(proba);
                        }
                    }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allProbe;
    }
}

