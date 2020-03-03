package repository;

import domain.Participant;
import domain.Proba;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class RepoParticipanti implements Repo<Participant> {
    ConnectionHelper connectionHelper;
    RepoProbe repoProbe;

    public RepoParticipanti(Properties prop, RepoProbe repoProbe) {
        this.repoProbe=repoProbe;
        connectionHelper=new ConnectionHelper(prop);
    }

    @Override
    public void adauga(Participant elem) {
        try (Connection connection=connectionHelper.getConnection();){
            try (PreparedStatement preparedStatement=
                         connection.prepareStatement("insert into participanti (nume, varsta) VALUES (?,?);");) {
                preparedStatement.setString(1, elem.getNume());
                preparedStatement.setInt(2, elem.getVarsta());
                int rowsAffected=preparedStatement.executeUpdate();
                if(rowsAffected==1 && elem.getProbe().size()>0){
                    try (PreparedStatement statement=connection.prepareStatement("select Id from participanti where nume=?");){
                        statement.setString(1,elem.getNume());
                        try (ResultSet rs=statement.executeQuery()){
                            rs.next();
                            int id=rs.getInt("id");
                            try (PreparedStatement addProba=connection.prepareStatement("insert into partprobe (IdPart, IdProba) VALUES (?,?);");){
                            elem.getProbe().forEach(x->{
                                try {
                                    addProba.setInt(1, id);
                                    addProba.setInt(2, x.getIdProba());
                                    addProba.executeUpdate();
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            });
                            }catch (SQLException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void sterge(Integer id) {
        Connection connection=connectionHelper.getConnection();
        try (PreparedStatement deleteProbePart=connection.prepareStatement("delete from partprobe where IdPart=?")){
            deleteProbePart.setInt(1,id);
            int rowCount=deleteProbePart.executeUpdate();
            if (rowCount>0)
                System.out.println("s-au sters probele");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement deletePart=connection.prepareStatement("delete from participanti where id=?;");){
            deletePart.setInt(1,id);
            int rowCount1=deletePart.executeUpdate();
            if (rowCount1==1)
                System.out.println("s-a sters participantul");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Participant cauta(int id) {
        Participant participant=null;
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement findStm = connection.prepareStatement("select p.id,p.nume,p.varsta,probe.idProba,probe.numeProba as proba,cat.nume as categVarsta from participanti p \n" +
                    "inner join partprobe as pr on p.id=IdPart\n" +
                    "inner join probe on probe.IdProba=pr.IdProba\n" +
                    "inner join categvarsta as cat on cat.id=probe.idCateg where p.Id=?;");) {
                findStm.setInt(1, id);
                try (ResultSet rs = findStm.executeQuery()) {
                    rs.next();
                    participant = new Participant(rs.getInt("id"), rs.getString("nume"), rs.getInt("varsta"));
                    participant.addProba(repoProbe.cauta(rs.getInt("idProba")));
                    while (rs.next()) {
                        participant.addProba(repoProbe.cauta(rs.getInt("idProba")));
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public Collection<Participant> getAll() {
        ArrayList<Participant> participanti=new ArrayList<>();

        try(Connection connection=connectionHelper.getConnection();) {
            String getPart = "select * from participanti;";
            try (PreparedStatement prepStatement = connection.prepareStatement(getPart);
                 PreparedStatement prepStatement2 = connection.prepareStatement("select IdProba from partprobe where IdPart=?;")            ) {
                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    int varsta = resultSet.getInt("varsta");
                    Participant participant = new Participant(id, nume, varsta);
                    prepStatement2.setInt(1,id);
                    ResultSet resultSet2=prepStatement2.executeQuery();
                    while (resultSet2.next()){
                        Proba proba=repoProbe.cauta(resultSet2.getInt("idProba"));
                        participant.addProba(proba);
                    }
                    participanti.add(participant);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return participanti;
    }

    @Override
    public void modifica(Participant newPart) {
        Participant participant=cauta(newPart.getId());
       if (participant!=null){
           try (Connection connection = connectionHelper.getConnection()) {
               //update participant
               try (PreparedStatement updStm = connection.prepareStatement("update participanti " +
                       "set nume=?, varsta=? where id=?;");) {
                   updStm.setString(1, newPart.getNume());
                   updStm.setInt(2,newPart.getVarsta());
                   updStm.setInt(3,newPart.getId());
                       int rows = updStm.executeUpdate();
                       //remove old probe
                   try (PreparedStatement deleteProbe=connection.prepareStatement("delete from partprobe where IdPart=?")) {
                       deleteProbe.setInt(1, newPart.getId());
                       int rowCount = deleteProbe.executeUpdate();
                   }catch (SQLException e) {
                       e.printStackTrace();
                   }
                   //add new probe
                   try (PreparedStatement addProbe=connection.prepareStatement("insert into partprobe (IdPart, IdProba) VALUES (?,?);");){
                       newPart.getProbe().forEach(x->{
                           try {
                               addProbe.setInt(1, newPart.getId());
                               addProbe.setInt(2, x.getIdProba());
                               addProbe.executeUpdate();
                           }catch (SQLException e){
                               e.printStackTrace();
                           }
                       });
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public int getSize() {
        try(Connection connection=connectionHelper.getConnection();){
            try (Statement sizeStm=connection.createStatement()){
                try(ResultSet rs=sizeStm.executeQuery("select count(*) as size from participanti;")){
                    rs.next();
                    return rs.getInt("size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
