import controller.Controller;
import controller.LogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.LogInService;
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
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springConfig.xml");
            LogInService logService = applicationContext.getBean(LogInService.class);

            primaryStage.setTitle("Concurs");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
            AnchorPane myPane = loader.load();
            LogController ctrl = loader.getController();
            ctrl.setService(logService);

            Scene myScene = new Scene(myPane);
            primaryStage.setScene(myScene);

            ctrl.addScene("main",myPane);
            ctrl.setMain(myScene);
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
/*
            RepoCategVarsta repoCategVarsta=new RepoCategVarsta(properties);
            RepoProbe repoProbe = new RepoProbe(properties,repoCategVarsta);
            RepoParticipanti repoParticipanti = new RepoParticipanti(properties, repoProbe);
            RepoInscrieri repoInscrieri = new RepoInscrieri(properties,repoParticipanti,repoProbe);
            return new Service(repoParticipanti, repoInscrieri, repoProbe);

 */
            return null;
        }

}
