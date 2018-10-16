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
import utils.Locker;
import utils.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class lockerController implements Initializable {

    private ArrayList<Boolean> valids = new ArrayList<Boolean>();
    private static String userName;
    User user = User.getUser(userName);

    @FXML
    Label label1,label2;

    @FXML
    TextField field1, field2, field3;

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valids.add(false);
        valids.add(false);

        field2.textProperty().addListener(e->{
            if (field2.getText().length()<8) {
                label1.textProperty().setValue("Must be 8 characters long");
                valids.set(1, false);
            }
            else {
                if(!valids.get(1))
                    valids.set(1, true);
                label1.textProperty().setValue("");
            }

            label1.setTextFill(Color.BLACK);
        });

        field3.textProperty().addListener(e ->{
            System.out.println("Inside Listener");
            if(!field2.getText().equals(field3.getText())) {
                label2.textProperty().setValue("Passwords Do not Match");
                label2.setTextFill(Color.BLACK);
                valids.set(0,false);
            }
            else {
                if (!valids.get(0))
                    valids.set(0, true);
                label2.setText("");
            }
            label1.setTextFill(Color.BLACK);
        });

    }

    @FXML
    public void clickCreate() throws Exception {

        if(Collections.frequency(valids, true)!=2){
            if (!valids.get(0))
                AlertBox.generateDialog("Passwords do not match");
            else if(!valids.get(1))
                AlertBox.generateDialog("Password must be 8 characters long");
        }
        else{
            Locker locker = new Locker(userName+"//"+field1.getText(), field2.getText());
            locker.createLocker();
            user.addLocker(field1.getText(),locker);
            User.setUser(userName, user);
            AlertBox.generateDialog("Locker Created");
            clickCancel();
        }
    }

    @FXML
    public void clickCancel() throws IOException {
        Pane paneView = FXMLLoader.load(getClass().getResource("fxml/lockerView.fxml"));
        pane.getChildren().set(0, paneView);
    }

    public static void setUserName(String userName){
        lockerController.userName = userName;
    }
}
