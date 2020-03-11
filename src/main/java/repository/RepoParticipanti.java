package repository;

import domain.Participant;
import myException.RepoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.compiler.asm.sparc.SPARCAddress;
import utils.ConnectionHelper;
import validator.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class RepoParticipanti implements Repo<Participant> {
    ConnectionHelper connectionHelper;
    private static final Logger logger= LogManager.getLogger(RepoParticipanti.class.getName());
    Validator<Participant> valiParticipant;

    public RepoParticipanti(Properties prop, RepoProbe repoProbe,Validator<Participant> val) {
        logger.info("initializing RepoParticipanti with properties {}",prop);
        connectionHelper=new ConnectionHelper(prop);
        this.valiParticipant=val;
    }

    @Override
    public void adauga(Participant elem) throws RepoException {
        logger.traceEntry("adauga participat {}",elem);
        valiParticipant.valideaza(elem);
        try (Connection connection=connectionHelper.getConnection();){
            try (PreparedStatement preparedStatement=
                         connection.prepareStatement("insert into participanti (nume, varsta) VALUES (?,?);");) {
                preparedStatement.setString(1, elem.getNume());
                preparedStatement.setInt(2, elem.getVarsta());
                int rowAdded=preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("sterge participant cu id {}",id);
        Connection connection=connectionHelper.getConnection();
        try (PreparedStatement deleteProbePart=connection.prepareStatement("delete from partprobe where IdPart=?")){
            deleteProbePart.setInt(1,id);
            int rowCount=deleteProbePart.executeUpdate();
//            if (rowCount>0)
//                System.out.println("s-au sters probele");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement deletePart=connection.prepareStatement("delete from participanti where id=?;");){
            deletePart.setInt(1,id);
            int rowCount1=deletePart.executeUpdate();
//            if (rowCount1==1)
//                System.out.println("s-a sters participantul");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Participant cautaNume(String nume) {
        logger.traceEntry("cauta participant cu nume {}",nume);
        Participant participant=null;
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement findStm = connection.prepareStatement("select p.id,p.nume,p.varsta from participanti p where p.nume=?;");) {
                findStm.setString(1, nume);
                try (ResultSet rs = findStm.executeQuery()) {
                    if (!rs.next())
                        return participant;
                    else
                        participant = new Participant(rs.getInt("id"), rs.getString("nume"), rs.getInt("varsta"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public Participant cauta(int id) {
        logger.traceEntry("cauta participant cu id {}",id);
        Participant participant=null;
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement findStm = connection.prepareStatement("select p.id,p.nume,p.varsta from participanti p where p.Id=?;");) {
                findStm.setInt(1, id);
                try (ResultSet rs = findStm.executeQuery()) {
                    rs.next();
                    participant = new Participant(rs.getInt("id"), rs.getString("nume"), rs.getInt("varsta"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public Collection<Participant> getAll() {
        logger.traceEntry("getAll");
        ArrayList<Participant> participanti=new ArrayList<>();

        try(Connection connection=connectionHelper.getConnection();) {
            String getPart = "select * from participanti;";
            try (PreparedStatement prepStatement = connection.prepareStatement(getPart);) {
                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    int varsta = resultSet.getInt("varsta");
                    Participant participant = new Participant(id, nume, varsta);
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
        logger.traceEntry("modifica participant cu id {}", newPart.getId());
        Participant participant = cauta(newPart.getId());
        if (participant != null) {
            try (Connection connection = connectionHelper.getConnection()) {
                //update participant
                try (PreparedStatement updStm = connection.prepareStatement("update participanti " +
                        "set nume=?, varsta=? where id=?;");) {
                    updStm.setString(1, newPart.getNume());
                    updStm.setInt(2, newPart.getVarsta());
                    updStm.setInt(3, newPart.getId());
                    int rows = updStm.executeUpdate();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getSize() {
        logger.traceEntry("getSize");
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
