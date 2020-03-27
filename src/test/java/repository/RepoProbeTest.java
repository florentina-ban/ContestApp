package repository;

import domain.Proba;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class RepoProbeTest {

    @Test
    public void cauta() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoProbe repoProbe = factory.getBean(RepoProbe.class);
        Proba proba = repoProbe.findOne(2);
        assertEquals(proba.getNume(), "1000m");
        assertEquals(proba.getCategVarsta().getNume(), "12-15");
    }

    @Test
    public void getAll() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoProbe repoProbe = factory.getBean(RepoProbe.class);
        assertEquals(repoProbe.findAll().size(), 9);
    }
}