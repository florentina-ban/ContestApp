package repository;

import domain.Participant;
import org.junit.Test;
import utils.ConnectionHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoParticipantiTest {

    @Test
    public void adauga() {
            Properties testProp=new Properties();
            try {
                testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\test\\resources\\configTest.properties"));
                RepoProbe repoProbe=new RepoProbe(testProp);
                RepoParticipanti  repoParticipanti=new RepoParticipanti(testProp,repoProbe);
                Participant participant=new Participant(2,"Ionel",8);
                participant.addProba(repoProbe.cauta(7));
                repoParticipanti.adauga(participant);
                assertEquals(repoParticipanti.getSize(),2);

                System.out.println("test Adauga");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Test
    public void cauta() {
        Properties testProp=new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\test\\resources\\configTest.properties"));
            RepoProbe repoProbe = new RepoProbe(testProp);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            Participant participant = repoParticipanti.cauta(1);
            assertEquals(participant.getNume(), "Gigel");
            assertEquals(participant.getProbe().size(), 1);
            System.out.println("test Cauta");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @Test
    public void getAll() {
        Properties testProp=new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\test\\resources\\configTest.properties"));
            RepoProbe repoProbe = new RepoProbe(testProp);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            assertEquals(repoParticipanti.getAll().size(),2);
            System.out.println("test getAll");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifica() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\test\\resources\\configTest.properties"));
            RepoProbe repoProbe = new RepoProbe(testProp);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);

            Connection connection = new ConnectionHelper(testProp).getConnection();
            try (PreparedStatement findStm = connection.prepareStatement(
                    "select p.id,p.nume,p.varsta,probe.idProba,probe.numeProba as proba,cat.nume as categVarsta from participanti p \n" +
                            "inner join partprobe as pr on p.id=IdPart\n" +
                            "inner join probe on probe.IdProba=pr.IdProba\n" +
                            "inner join categvarsta as cat on cat.id=probe.idCateg where p.nume=?;");) {
                findStm.setString(1, "Ionel");
                try (ResultSet rs = findStm.executeQuery()) {
                    rs.next();
                    int idPart = rs.getInt("id");
                    Participant participant = repoParticipanti.cauta(idPart);
                    participant.setNume("Ionela");
                    participant.setVarsta(7);
                    participant.addProba(repoProbe.cauta(3));
                    repoParticipanti.modifica(participant);
                    Participant participant1 = repoParticipanti.cauta(idPart);
                    assertEquals(participant1.getNume(), "Ionela");
                    assertEquals(participant1.getVarsta(), 7);
                    assertEquals(participant1.getProbe().size(), 2);
                    System.out.println("test Modifica");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @Test
    public void sterge() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\test\\resources\\configTest.properties"));
            RepoProbe repoProbe = new RepoProbe(testProp);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);

            Connection connection = new ConnectionHelper(testProp).getConnection();
            try (PreparedStatement findStm = connection.prepareStatement(
                    "select p.id,p.nume,p.varsta,probe.idProba,probe.numeProba as proba,cat.nume as categVarsta from participanti p \n" +
                            "inner join partprobe as pr on p.id=IdPart\n" +
                            "inner join probe on probe.IdProba=pr.IdProba\n" +
                            "inner join categvarsta as cat on cat.id=probe.idCateg where p.nume=?;");) {
                findStm.setString(1, "Ionela");
                try (ResultSet rs = findStm.executeQuery()) {
                    rs.next();
                    int idPart = rs.getInt("id");
                    repoParticipanti.sterge(idPart);
                    assertEquals(repoParticipanti.getSize(), 1);
                    System.out.println("test Sterge");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
                e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}