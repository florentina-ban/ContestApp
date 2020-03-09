package repository;

import domain.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import utils.ConnectionHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoParticipantiTest {
    private static Logger logger= LogManager.getLogger(RepoParticipanti.class.getName());

    @Test
    public void adauga() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            Participant participant = new Participant(2, "Ionel", 8);
            repoParticipanti.adauga(participant);
            assertEquals(repoParticipanti.getSize(), 2);

            ConnectionHelper ch = new ConnectionHelper(testProp);
            try (Connection con = ch.getConnection();) {
                try (PreparedStatement findSt = con.prepareStatement("select id from contesttest.participanti where nume=?");) {
                    findSt.setString(1, "Ionel");
                    try {
                        ResultSet rs = findSt.executeQuery();
                        rs.next();

                        repoParticipanti.sterge(rs.getInt("id"));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cauta() {
        logger.traceEntry("cauta participant test");
        Properties testProp=new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            Participant participant = repoParticipanti.cauta(1);
            assertEquals(participant.getNume(), "Gigela");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void size(){
        logger.traceEntry("size RepoParticipanti test");
        Properties testProp=new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            assertEquals(repoParticipanti.getAll().size(),1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAll() {
        logger.traceEntry("getALl participanti test");
        Properties testProp=new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            assertEquals(repoParticipanti.getAll().size(),1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifica() {
        logger.traceEntry("modifica participant test");
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);
            Connection connection = new ConnectionHelper(testProp).getConnection();
            Participant participant = repoParticipanti.cauta(1);
            participant.setNume("Gigel");
            participant.setVarsta(7);
            repoParticipanti.modifica(participant);
            Participant participant1 = repoParticipanti.cauta(1);
            assertEquals(participant1.getNume(), "Gigel");
            assertEquals(participant1.getVarsta(), 7);

            participant=repoParticipanti.cauta(1);
            participant.setNume("Gigela");
            participant.setVarsta(8);
            repoParticipanti.modifica(participant);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sterge() {
        logger.traceEntry("sterge participant test");
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(testProp);
            RepoProbe repoProbe = new RepoProbe(testProp,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(testProp, repoProbe);

            Participant participant=new Participant(2,"Ionel",8);
            repoParticipanti.adauga(participant);

            Connection connection = new ConnectionHelper(testProp).getConnection();
            try (PreparedStatement findStm = connection.prepareStatement(
                    "select id from participanti where nume=?;");) {
                findStm.setString(1, "Ionel");
                try (ResultSet rs = findStm.executeQuery()) {
                    rs.next();
                    int idPart = rs.getInt("id");
                    repoParticipanti.sterge(idPart);
                    assertEquals(repoParticipanti.getSize(), 1);
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