import controller.Controller;
import domain.Participant;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import repository.RepoCategVarsta;
import repository.RepoInscrieri;
import repository.RepoParticipanti;
import repository.RepoProbe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import service.Service;

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

      RepoCategVarsta repoCategVarsta = new RepoCategVarsta(properties);
      RepoProbe repoProbe = new RepoProbe(properties,repoCategVarsta);
      RepoParticipanti repoParticipanti = new RepoParticipanti(properties, repoProbe);
      RepoInscrieri repoInscrieri = new RepoInscrieri(properties,repoParticipanti,repoProbe);
      Service service = new Service(repoParticipanti, repoInscrieri, repoProbe);


      // MainApp.main(args);


  }
}
