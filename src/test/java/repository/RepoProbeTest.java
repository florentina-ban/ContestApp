package repository;

import domain.Proba;
import org.junit.Test;
import utils.ConnectionHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoProbeTest {

    @Test
    public void cauta() {
        Properties properties=new Properties();
        try {
           //String filePath=this.getClass().getResource("\\resoures\\configTest.properties").getPath();
            properties.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\test\\resources\\configTest.properties"));

            RepoProbe repoProbe=new RepoProbe(properties);
            Proba proba=repoProbe.cauta(2);
            assertEquals(proba.getNume(),"1000m");
            assertEquals(proba.getCategVarsta().getNume(),"12-15");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}