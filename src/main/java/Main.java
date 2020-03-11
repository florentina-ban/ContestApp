import domain.Proba;
import repository.RepoCategVarsta;
import repository.RepoInscrieri;
import repository.RepoParticipanti;
import repository.RepoProbe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import service.Service;
import validator.ValInscriere;
import validator.ValParticipanti;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main{
  public static void main(String[] args) {

      Properties properties = new Properties();
      try {
          properties.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\main\\config.properties"));
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }


      ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:springConfig.xml");
     // Properties properties= (Properties) applicationContext.getBean("jdbcProps");

/*
      RepoCategVarsta repoCategVarsta = applicationContext.getBean(RepoCategVarsta.class);
      RepoProbe repoProbe = applicationContext.getBean(RepoProbe.class);
      ValParticipanti valParticipanti=applicationContext.getBean(ValParticipanti.class);

      RepoParticipanti repoParticipanti = new RepoParticipanti(properties, repoProbe,valParticipanti);
      valParticipanti.setRepoParticipanti(repoParticipanti);

      ValInscriere valInscriere=new ValInscriere();
      RepoInscrieri repoInscrieri = new RepoInscrieri(properties,repoParticipanti,repoProbe,valInscriere);
      valInscriere.setRepoInscrieri(repoInscrieri);

 */
      Service service = applicationContext.getBean(Service.class);


      // MainApp.main(args);


  }
}
