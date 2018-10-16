import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class userNameController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    TextField textField;

    @FXML
    static
    Label label;

    @FXML
    public void clickOk(){
        String userName = textField.getText();
        setAppUserName(userName);
        System.out.println(userName);
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickCancel(){
        System.exit(1);
    }

    private void setAppUserName(String userName){
//        userPasswordController.setUserName(userName);
        dashBoardController.setUserName(userName);
        UserDialogController.setUserName(userName);
        changePasswordController.setUserName(userName);
        lockerController.setUserName(userName);
        voiceRecordController.setUserName(userName);
        Main.setUserName(userName);
    }

    public static void setLabel(String labelMessage){
        label.setText(labelMessage);
    }
}
