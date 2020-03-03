package repository;

import domain.CategVarsta;
import domain.Proba;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.*;

public class RepoProbe implements Repo<Proba> {
    private ConnectionHelper connectionHelper;
    private Map<Integer,Proba> probe=new HashMap<>();

    public RepoProbe(Properties properties) {
        connectionHelper=new ConnectionHelper(properties);

        try (Connection connection = this.connectionHelper.getConnection();)  {
            String query = "select * from contest.categvarsta inner join\n" +
                    "contest.probe on contest.categvarsta.id=contest.probe.idCateg";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                int vs = resultSet.getInt("varstaStart");
                int ve = resultSet.getInt("varstaEnd");
                CategVarsta categVarsta = new CategVarsta(nume, vs, ve);

                int idProba = resultSet.getInt("idProba");
                String numeProba = resultSet.getString("numeProba");
                Proba proba = new Proba(idProba, numeProba, categVarsta);

                probe.put(idProba,proba);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void adauga(Proba elem) {

    }

    @Override
    public void sterge(Integer id) {

    }

    @Override
    public Proba cauta(int id) {
        return probe.get(id);
    }

    @Override
    public Collection<Proba> getAll() {
        return probe.values();
    }

    @Override
    public void modifica(Proba elem) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
