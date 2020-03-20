package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import myException.LogException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.LogInService;
import service.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogController {
    LogInService service;
    Map<String, Pane> allScenes=new HashMap<>();
    private Scene main;

    @FXML TextField userField;
    @FXML TextField passField;

    public void setService(LogInService s){
        service=s;
    }
    public void initialize() throws IOException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springConfig.xml");
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/view.fxml"));

        AnchorPane root = loader1.load();
        Controller controller=loader1.getController();
        controller.setService(applicationContext.getBean(Service.class));
        controller.setLogController(this);

        this.addScene("service",root);
    }

    public void setMain(Scene main) {
        this.main = main;
    }

    public void handleAutentifica(MouseEvent mouseEvent) {
        try {
            service.logare(userField.getText(), passField.getText());
            this.startApp();

        }catch (LogException ex){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        finally {
            userField.clear();
            passField.clear();
        }

    }
    public void startApp() {
        main.setRoot(allScenes.get("service"));

    }
    public void setLogScene(){
        main.setRoot(allScenes.get("main"));
    }

    public void addScene(String s, Pane scene){
        allScenes.put(s,scene);
    }
}
