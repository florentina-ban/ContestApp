package controller;
import domain.CategVarsta;
import domain.Participant;
import domain.Proba;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import service.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    public LogController logController;
    private Service service;
    ObservableList<Proba> modelProbe= FXCollections.observableArrayList();
    ObservableList<CategVarsta> modelCateg= FXCollections.observableArrayList();
    ObservableList<Participant> modelPart=FXCollections.observableArrayList();

    @FXML TableView<CategVarsta> tabelCateg;
    @FXML TableColumn<CategVarsta, String> colCateg;
    @FXML TableView<Proba> tabelProbe;
    @FXML TableColumn<Proba, String> colProbe;
    @FXML TableView<Participant> tabelPart;
    @FXML TableColumn<Participant, Integer> colIdPart;
    @FXML TableColumn<Participant, String> colNumePart;
    @FXML TableColumn<Participant, Integer> colVarstaPart;
    @FXML TableColumn<Participant, Integer> colNrProbe;
    @FXML AnchorPane probeAnchor;
    @FXML AnchorPane mainAnchor;
    @FXML AnchorPane categAnchor;
    @FXML TextField searchTextField;
    @FXML CheckBox allCheck;
    @FXML TextField proba1;
    @FXML TextField proba2;

    public void setService(Service s){
        this.service=s;
        modelCateg.setAll(service.getCategVarsta());
    }

    public Service getService() {
        return service;
    }

    public void initialize() throws IOException{
        probeAnchor.setVisible(false);
        categAnchor.setVisible(false);
        tabelProbe.setItems(modelProbe);
        tabelCateg.setItems(modelCateg);
        tabelPart.setItems(modelPart);
        colCateg.setCellValueFactory(new PropertyValueFactory<CategVarsta, String>("nume"));
        colProbe.setCellValueFactory(new PropertyValueFactory<Proba, String>("nume"));
        colIdPart.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("id"));
        colNumePart.setCellValueFactory(new PropertyValueFactory<Participant, String>("nume"));
        colVarstaPart.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("varsta"));
        colNrProbe.setCellValueFactory(new PropertyValueFactory<Participant,Integer>("nrProbe"));

        initializeEverything();

    }

    public void setLogController(LogController logController) {
        this.logController = logController;
    }

    public void showProbe(CategVarsta categ){
        modelProbe.setAll(service.getProbe(categ));
        probeAnchor.setVisible(true);
        final float movingDistance = 110;
        Thread thread = new Thread() {
            @Override
            public void run() {
                double i = 0;
                try {
                    while (i < movingDistance) {
                        Thread.sleep(1);
                        mainAnchor.setLeftAnchor(probeAnchor, i);
                        i += 1;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void showParticipanti(Proba proba){
        allCheck.setSelected(false);
        modelPart.setAll(service.getParticipantiSearch(searchTextField.getText(),proba));
        tabelPart.getSelectionModel().clearSelection();
        proba1.clear();
        proba2.clear();
    }

    public void showAllParticipants(){
        if (allCheck.isSelected()){
            tabelProbe.getSelectionModel().clearSelection();
            tabelCateg.getSelectionModel().clearSelection();
            backProbeFunction(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,0, 0,0, MouseButton.PRIMARY, 1,
            true,true, true, true, true, false,
                    false, false, false, false, null));
            backCategFunction(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,0, 0,0, MouseButton.PRIMARY, 1,
                    true,true, true, true, true, false,
                    false, false, false, false, null));
            modelPart.setAll(service.getParticipantiSearch(searchTextField.getText()));
        }
        else {
            Proba p=tabelProbe.getSelectionModel().getSelectedItem();
            if (p!=null)
                modelPart.setAll(service.getParticipantiSearch(searchTextField.getText(),p));
            else
                modelPart.setAll(service.getParticipantiSearch(searchTextField.getText(),p));
        }

    }
    public void participantSelection(Participant participant){
        ArrayList<Proba> all=service.getProbe(participant);
        if (all.size()>=1){
            proba1.setText(all.get(0).getNume());
        }
        else
            proba1.clear();
        if (all.size()==2){
            proba2.setText(all.get(1).getNume());
        }
        else proba2.clear();
    }

    public void initializeEverything(){
        tabelCateg.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CategVarsta>() {
            @Override
            public void changed(ObservableValue<? extends CategVarsta> observable, CategVarsta oldValue, CategVarsta newValue) {
                showProbe(newValue);
            }
        });
        tabelProbe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proba>() {
            @Override
            public void changed(ObservableValue<? extends Proba> observable, Proba oldValue, Proba newValue) {
                showParticipanti(newValue);
            }
        });
        tabelPart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Participant>() {
            @Override
            public void changed(ObservableValue<? extends Participant> observable, Participant oldValue, Participant newValue) {
                participantSelection(newValue);
            }
        });
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               showAllParticipants();
            }
        });
        allCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                allCheck.setSelected(newValue);
                showAllParticipants();
            }
        });
        allCheck.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (allCheck.isFocused())
                    allCheck.setSelected(true);
            }
        });
    }

    public void showAnchorCategFunction(MouseEvent mouseEvent) {
        mainAnchor.setLeftAnchor(categAnchor,-110d);
        categAnchor.setVisible(true);
        Thread thread = new Thread() {
            @Override
            public void run() {
                double i = -110;
                try {
                    while (i <= 0) {
                        Thread.sleep(1);
                        mainAnchor.setLeftAnchor(categAnchor, i);
                        i += 1;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void backProbeFunction(MouseEvent mouseEvent) {
        final float movingDistance = 110;
        Thread thread = new Thread() {
                @Override
                public void run() {
                    double i = 110d;
                    try {
                        while (i >=0) {
                            Thread.sleep(1);
                            mainAnchor.setLeftAnchor(probeAnchor, i);
                            i-= 1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try {
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            probeAnchor.setVisible(false);
    }

    public void backCategFunction(MouseEvent mouseEvent) {
            final float movingDistance = 110;
            if (probeAnchor.isVisible())
                return;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    double i = 0d;
                    try {
                        while (i >= -110) {
                            Thread.sleep(1);
                            mainAnchor.setLeftAnchor(categAnchor, i);
                            i -= 1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            categAnchor.setVisible(false);

        }

    public void logOut(MouseEvent mouseEvent) {
        this.logController.setLogScene();
    }
}
