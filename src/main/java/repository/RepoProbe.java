package repository;

import domain.CategVarsta;
import domain.Proba;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.*;

public class RepoProbe implements Repo<Proba> {
    private ConnectionHelper connectionHelper;

    public RepoProbe(Properties properties)  {
        connectionHelper = new ConnectionHelper(properties);
    }

    @Override
    public Collection<Proba> getAll() {
        ArrayList<Proba> allProbe=new ArrayList<>();
        try (Connection connection = this.connectionHelper.getConnection();) {
            String query = "select * from contest.categvarsta inner join\n" +
                    "contest.probe on contest.categvarsta.id=contest.probe.idCateg";
            try (Statement statement = connection.createStatement();) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String nume = resultSet.getString("nume");
                    int vs = resultSet.getInt("varstaStart");
                    int ve = resultSet.getInt("varstaEnd");
                    CategVarsta categVarsta = new CategVarsta(nume, vs, ve);

                    int idProba = resultSet.getInt("idProba");
                    String numeProba = resultSet.getString("numeProba");
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
        Proba proba=null;
        try (Connection connection=connectionHelper.getConnection()){
            try(PreparedStatement selectStm=connection.prepareStatement("select * from categvarsta inner join\n" +
                    "probe on categvarsta.id=probe.idCateg where probe.idProba=?");){
                selectStm.setInt(1,id);
                ResultSet resultSet=selectStm.executeQuery();
                resultSet.next();
                String nume = resultSet.getString("nume");
                int vs = resultSet.getInt("varstaStart");
                int ve = resultSet.getInt("varstaEnd");
                CategVarsta categVarsta = new CategVarsta(nume, vs, ve);

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
    public void adauga(Proba elem) {

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
