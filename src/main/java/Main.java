import domain.Participant;
import domain.Proba;
import repository.RepoParticipanti;
import repository.RepoProbe;
import utils.ConnectionHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties=new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\mppConc\\src\\main\\config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RepoProbe repoProbe = new RepoProbe(properties);
        repoProbe.getAll().
                stream()
                .forEach(x -> System.out.println(x));

        RepoParticipanti repoParticipanti=new RepoParticipanti(properties,repoProbe);
        repoParticipanti.getAll().stream()
                .forEach(x->
                        System.out.println(x));
/*
        Participant participant=new Participant(9,"David",6);
        participant.addProba(repoProbe.cauta(3));
        repoParticipanti.adauga(participant);
        repoParticipanti.getAll().stream()
                .forEach(x->
                        System.out.println(x));
        repoParticipanti.sterge(participant);
        repoParticipanti.getAll().stream()
                .forEach(x->
                        System.out.println(x));
*/
        System.out.println(repoParticipanti.getSize());
    }
}
