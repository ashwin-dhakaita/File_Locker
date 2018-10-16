import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertBox implements Initializable {

    static String message=null;
    static Stage stage = new Stage();

    @FXML
    Label labelAlertBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelAlertBox.textProperty().setValue(message);
    }

    private static void setMessage(String message){
        AlertBox.message = message;
    }

    @FXML
    public void terminate(){
        Stage stage = (Stage) labelAlertBox.getScene().getWindow();
        stage.close();
    }

    private static void createStage(String message) throws IOException {
        AlertBox.setMessage(message);
        Parent root = (Parent)FXMLLoader.load(AlertBox.class.getResource("fxml/alertBox.fxml"));
        stage.setTitle("Alert");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public static Stage generateDialog(String message) throws IOException {
        createStage(message);
        stage.showAndWait();
        return stage;
    }

    public static Stage showDialog(String message) throws IOException {
        createStage(message);
        stage.show();
        return stage;
    }

}