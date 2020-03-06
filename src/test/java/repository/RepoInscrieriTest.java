package repository;

import domain.Inscriere;
import myException.InscrieriException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class RepoInscrieriTest {
    private static Logger logger= LogManager.getLogger();

    @Test
    public void adauga() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoInscrieri repoInscrieri=new RepoInscrieri(testProp);
            Inscriere inscriere = new Inscriere(2, 3, 1);
            repoInscrieri.adauga(inscriere);
            assertEquals(repoInscrieri.getSize(), 2);

            ConnectionHelper ch = new ConnectionHelper(testProp);
            try (Connection con = ch.getConnection();) {
                try (PreparedStatement findSt = con.prepareStatement("select nrInscriere from contesttest.partprobe where idProba=? and idPart=?");) {
                    findSt.setInt(1, 3);
                    findSt.setInt(2,1);
                    try {
                        ResultSet rs = findSt.executeQuery();
                        rs.next();
                        repoInscrieri.sterge(rs.getInt("nrInscriere"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (InscrieriException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAll() {
        Properties prop=new Properties();
        try {
            prop.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoInscrieri repoInscrieri = new RepoInscrieri(prop);
            assertEquals(repoInscrieri.getAll().size(),1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getSize() { Properties prop=new Properties();
        try {
            prop.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoInscrieri repoInscrieri = new RepoInscrieri(prop);
            assertEquals(repoInscrieri.getSize(),1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void sterge() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoInscrieri repoInscrieri=new RepoInscrieri(testProp);
            Inscriere inscriere = new Inscriere(2, 3, 1);
            repoInscrieri.adauga(inscriere);
            assertEquals(repoInscrieri.getSize(), 2);

            ConnectionHelper ch = new ConnectionHelper(testProp);
            try (Connection con = ch.getConnection();) {
                try (PreparedStatement findSt = con.prepareStatement("select nrInscriere from contesttest.partprobe where idProba=? and idPart=?");) {
                    findSt.setInt(1, 3);
                    findSt.setInt(2,1);
                    try {
                        ResultSet rs = findSt.executeQuery();
                        rs.next();
                        repoInscrieri.sterge(rs.getInt("nrInscriere"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (InscrieriException e){
            e.printStackTrace();
        }
    }

    @Test
    public void cauta() {
        Properties testProp = new Properties();
        try {
            testProp.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));
            RepoInscrieri repoInscrieri = new RepoInscrieri(testProp);
            Inscriere inscriere=repoInscrieri.cauta(1);
            assert(inscriere.getIdPart()==1);
            assert(inscriere.getIdProba()==2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}