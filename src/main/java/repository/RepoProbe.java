package repository;

import domain.CategVarsta;
import domain.Proba;
import myException.RepoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.*;

public class RepoProbe implements Repo<Proba> {
    private ConnectionHelper connectionHelper;
    private static final Logger  logger= LogManager.getLogger(RepoProbe.class.getName());
    RepoCategVarsta repoCategVarsta;

    public RepoProbe(Properties properties, RepoCategVarsta rep)  {
        logger.info("initializing RepoParticipanti with properties {}",properties);
        connectionHelper = new ConnectionHelper(properties);
        repoCategVarsta=rep;
    }

    @Override
    public Collection<Proba> getAll() {
        logger.traceEntry("getAll");
        ArrayList<Proba> allProbe=new ArrayList<>();
        try (Connection connection = this.connectionHelper.getConnection();) {
            String query = "select * from contest.probe";
            try (Statement statement = connection.createStatement();) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int idProba = resultSet.getInt("idProba");
                    String numeProba = resultSet.getString("numeProba");
                    CategVarsta categVarsta=repoCategVarsta.cauta(resultSet.getInt("idCateg"));
                    Proba proba = new Proba(idProba, numeProba, categVarsta);
                    allProbe.add(proba);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allProbe;
    }
    @Override
    public Proba cauta(int id) {
        logger.traceEntry("cauta proba cu id {}",id);
        Proba proba=null;
        try (Connection connection=connectionHelper.getConnection()){
            try(PreparedStatement selectStm=connection.prepareStatement("select * from probe where probe.idProba=?");){
                selectStm.setInt(1,id);
                ResultSet resultSet=selectStm.executeQuery();
                resultSet.next();
                CategVarsta categVarsta = repoCategVarsta.cauta(resultSet.getInt("idCateg"));
                int idProba = resultSet.getInt("idProba");
                String numeProba = resultSet.getString("numeProba");
                proba = new Proba(idProba, numeProba, categVarsta);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return proba;
    }

    @Override
    public void adauga(Proba elem)  throws RepoException {
        if (false)
            throw new RepoException(" ");
    }

    @Override
    public void sterge(Integer id) {

    }

    @Override
    public void modifica(Proba elem) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
