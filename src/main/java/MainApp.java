import controller.Controller;
import domain.CategVarsta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import service.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    public static void main(String[] args){
        launch(args);
    }

        @Override
        public void start(Stage primaryStage) throws Exception {
            Properties properties=new Properties();
            try {
                properties.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\main\\config.properties"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(properties);
            RepoProbe repoProbe = new RepoProbe(properties,repoCategVarsta);
            RepoParticipanti repoParticipanti=new RepoParticipanti(properties,repoProbe);
            RepoInscrieri repoInscrieri=new RepoInscrieri(properties,repoParticipanti,repoProbe);
            Service service=new Service(repoParticipanti,repoInscrieri,repoProbe);

            primaryStage.setTitle("Concurs");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("view.fxml"));
            AnchorPane myPane = loader.load();
            Controller ctrl=loader.getController();

            ctrl.setService(getService());
            Scene myScene = new Scene(myPane);
            primaryStage.setScene(myScene);


//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                //  System.out.println("Stage is closing");
//                ctrl.close();
//            }
//        });
            primaryStage.show();
        }
        private Service getService() {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("C:\\Users\\Flore\\Desktop\\info18\\MPP\\gitApps\\ContestApp\\src\\main\\config.properties"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(properties);
            RepoProbe repoProbe = new RepoProbe(properties,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(properties, repoProbe);
            RepoInscrieri repoInscrieri = new RepoInscrieri(properties,repoParticipanti,repoProbe);
            return new Service(repoParticipanti, repoInscrieri, repoProbe);
        }

}
