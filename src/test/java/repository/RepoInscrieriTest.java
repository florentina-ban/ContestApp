package repository;

import domain.Inscriere;
import myException.InscrieriException;
import myException.RepoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import utils.ConnectionHelper;
import validator.ValInscriere;
import validator.ValParticipanti;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoInscrieriTest {

    @Test
    public void adauga() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        try {
            Inscriere inscriere = new Inscriere(2, 3, 1);
            repoInscrieri.adauga(inscriere);
            assertEquals(repoInscrieri.getSize(), 2);
        } catch (RepoException e) {
            e.printStackTrace();
        }
        ConnectionHelper ch = new ConnectionHelper((Properties) factory.getBean("jdbcProps"));
        try (Connection con = ch.getConnection();) {
            try (PreparedStatement findSt = con.prepareStatement("select nrInscriere from contesttest.partprobe where idProba=? and idPart=?");) {
                findSt.setInt(1, 3);
                findSt.setInt(2, 1);
                try {
                    ResultSet rs = findSt.executeQuery();
                    rs.next();
                    repoInscrieri.sterge(rs.getInt("nrInscriere"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Test
    public void getAll() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        assertEquals(repoInscrieri.getAll().size(), 1);
    }

    @Test
    public void getSize() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        assertEquals(repoInscrieri.getSize(), 1);
    }

    @Test
    public void sterge() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        Inscriere inscriere = new Inscriere(2, 3, 1);
        try {
            repoInscrieri.adauga(inscriere);
        } catch (RepoException e) {
            e.printStackTrace();
        }
        assertEquals(repoInscrieri.getSize(), 2);
        ConnectionHelper ch = new ConnectionHelper((Properties) factory.getBean("jdbcProps"));
        try (Connection con = ch.getConnection();) {
            try (PreparedStatement findSt = con.prepareStatement("select nrInscriere from contesttest.partprobe where idProba=? and idPart=?");) {
                findSt.setInt(1, 3);
                findSt.setInt(2, 1);
                try {
                    ResultSet rs = findSt.executeQuery();
                    rs.next();
                    repoInscrieri.sterge(rs.getInt("nrInscriere"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cauta() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        Inscriere inscriere = repoInscrieri.cauta(1);
        assert (inscriere.getIdPart() == 1);
        assert (inscriere.getIdProba() == 2);
    }

    @Test
    public void getParticipantiLaProba() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        assertEquals(repoInscrieri.getParticipantiLaProba(2).size(), 1);
    }

    @Test
    public void getProbeLaParticipant() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoInscrieri repoInscrieri = factory.getBean(RepoInscrieri.class);
        assertEquals(repoInscrieri.getProbeLaParticipant(1).size(), 1);
    }
}