package repository;

import domain.Participant;
import myException.RepoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class RepoParticipantiTest {
    private static Logger logger= LogManager.getLogger(RepoParticipanti.class.getName());

    @Test
    public void adauga() {
        ApplicationContext factory= new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        try {
            RepoParticipanti repoParticipanti = factory.getBean(RepoParticipanti.class);
            Participant participant = new Participant(2, "Ionel", 8);
            repoParticipanti.adauga(participant);
            assertEquals(repoParticipanti.getSize(), 2);
            Participant p=repoParticipanti.cautaNume("Ionel");
            repoParticipanti.sterge(p.getId());
        }catch (RepoException e){
            e.printStackTrace();
        }
    }

    @Test
    public void cauta() {
        logger.traceEntry("cauta participant test");
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoParticipanti repoParticipanti=factory.getBean(RepoParticipanti.class);
        Participant participant = repoParticipanti.findOne(1);
        assertEquals(participant.getNume(), "Gigela");
    }

    @Test
    public void size(){
        logger.traceEntry("size RepoParticipanti test");
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoParticipanti repoParticipanti=factory.getBean(RepoParticipanti.class);
        assertEquals(repoParticipanti.findAll().size(),1);
    }

    @Test
    public void getAll() {
        logger.traceEntry("getALl participanti test");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoParticipanti repoParticipanti = factory.getBean(RepoParticipanti.class);
        assertEquals(repoParticipanti.findAll().size(), 1);
    }

    @Test
    public void modifica() {
        logger.traceEntry("modifica participant test");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoParticipanti repoParticipanti = factory.getBean(RepoParticipanti.class);
        Participant participant = repoParticipanti.findOne(1);
        participant.setNume("Gigel");
        participant.setVarsta(7);
        repoParticipanti.modifica(participant);
        Participant participant1 = repoParticipanti.findOne(1);
        assertEquals(participant1.getNume(), "Gigel");
        assertEquals(participant1.getVarsta(), 7);

        participant = repoParticipanti.findOne(1);
        participant.setNume("Gigela");
        participant.setVarsta(8);
        repoParticipanti.modifica(participant);
    }

    @Test
    public void sterge() {
        logger.traceEntry("sterge participant test");
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:springConfigTest.xml");
        RepoParticipanti repoParticipanti=factory.getBean(RepoParticipanti.class);
        Participant participant = new Participant(2, "Ionel", 8);

        try{
            repoParticipanti.adauga(participant);
            Participant participant1 = repoParticipanti.cautaNume("Ionel");
            repoParticipanti.sterge(participant1.getId());
            assertEquals(repoParticipanti.getSize(), 1);
        }catch (RepoException ex){
            ex.printStackTrace();
        }
    }
}