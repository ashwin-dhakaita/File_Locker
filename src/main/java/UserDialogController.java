import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.Store;
import utils.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDialogController implements Initializable {

    private static String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup group = new ToggleGroup();
        toggleButton1.setToggleGroup(group);
        toggleButton2.setToggleGroup(group);
        toggleButton1.setSelected(false);
        toggleButton2.setSelected(false);
        toggleButton1.setText("OFF");
        toggleButton2.setText("OFF");
        toggleButton1.selectedProperty().addListener(e->{
            if (toggleButton1.getText().equals("OFF"))
                toggleButton1.setText("ON");
            else
                toggleButton1.setText("OFF");
        });

        toggleButton2.selectedProperty().addListener(e->{
            if (toggleButton2.getText().equals("OFF"))
                toggleButton2.setText("ON");
            else
                toggleButton2.setText("OFF");
        });

    }

    @FXML
    AutoCompleteTextField field;
    @FXML
    ToggleButton toggleButton1;
    @FXML
    ToggleButton toggleButton2;
    @FXML
    AnchorPane pane;

    @FXML
    public void createUser() throws Exception {
        User user = new User(field.getText());
        user.addProperty("voice", toggleButton1.isSelected());
        user.addProperty("masterpassword",toggleButton2.isSelected());
        User.addUser(field.getText(), user);
        AlertBox.generateDialog("User Created Successfully !!!!");
        System.out.println("User Created");
    }

    @FXML
    public void cancel() throws IOException {
        Pane paneView = FXMLLoader.load(getClass().getResource("fxml/lockerView.fxml"));
        pane.getChildren().set(0, paneView);
    }

    public static void setUserName(String userName){
        UserDialogController.userName = userName;
    }

}
