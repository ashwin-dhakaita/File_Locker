import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.Store;
import utils.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class changePasswordController implements Initializable {

    static ArrayList<Boolean> valids = new ArrayList<Boolean>();

    @FXML
    Pane pane;

    static String userName;
    User user = User.getUser(userName);
    String oldPassword = (String) user.getProperty("password");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valids.add(true);
        valids.add(true);
        valids.add(true);

        field3.textProperty().addListener(e -> {

            if(!field2.getText().equals(field3.getText())) {
                label2.textProperty().setValue("Passwords Do not Match");
                label2.setTextFill(Color.BLACK);
                valids.set(2, false);
            }
            else {
                label2.setText("");
                if(!valids.get(2))
                    valids.set(2, true);
            }
        });

        field2.textProperty().addListener(e->{

            if (field2.getText().length()<8) {
                label1.textProperty().setValue("Must be 8 characters long");
                valids.set(1, false);
            }
            else {
                if (!valids.get(1))
                    valids.set(1, true);
                label1.textProperty().setValue("");
            }
            label1.setTextFill(Color.BLACK);
        });

        field1.textProperty().addListener(e->{

            if (!field1.getText().equals(oldPassword)){
                label0.textProperty().setValue("Password is invalid");
                valids.set(0, false);
            }
            else{
                label0.textProperty().setValue("");
                if(!valids.get(0))
                    valids.set(0, true);
            }
        });

    }

    @FXML
    TextField field1;
    @FXML
    TextField field2;
    @FXML
    TextField field3;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label0;

    @FXML
    public void clickOK() throws IOException {

        if(Collections.frequency(valids, true)!=3){
            AlertBox.generateDialog("Password is invalid");
        }
        else {
            user.addProperty("password",field2.getText());
            try {
                User.setUser(userName, user);
                AlertBox.generateDialog("Password Changed Successfully!!!!");
                Pane paneView = FXMLLoader.load(getClass().getResource("fxml/lockerView.fxml"));
                pane.getChildren().set(0, paneView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clickCancel() throws IOException {
        Pane paneView = FXMLLoader.load(getClass().getResource("fxml/lockerView.fxml"));
        pane.getChildren().set(0, paneView);
    }

    public static void setUserName(String userName){
        changePasswordController.userName = userName;
    }

}
