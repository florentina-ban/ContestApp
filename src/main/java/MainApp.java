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

public class MainApp extends Application {

    public static void main(String[] args) {
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

        ctrl.addScene("main", myPane);
        ctrl.setMain(myScene);
        primaryStage.show();
    }
}
