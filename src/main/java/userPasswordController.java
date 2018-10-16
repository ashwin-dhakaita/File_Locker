import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Store;
import utils.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userPasswordController implements Initializable {

    static String userName = Main.getUserName();
    static User user = User.getUser(userName);
    static String password = (String) user.getProperty("password");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    TextField textField;

    @FXML
    public void clickOk() throws Exception {
        String passwordEntered = textField.getText();
        if(password==null){
            user.addProperty("password", passwordEntered);
            User.setUser(userName, user);
            Stage window = (Stage)textField.getScene().getWindow();
            window.close();
            return;
        }
        if(passwordEntered.equals(password)){
            Stage window = (Stage)textField.getScene().getWindow();
            window.close();
        }
        else {
            AlertBox.generateDialog("Authentication Error!!!!!!");
        }
    }

    @FXML
    public void clickCancel(){
        System.exit(1);
    }

    public static void setUserName(String userName){
        userPasswordController.userName = userName;
    }
    public static void setPassword(String password){
        userPasswordController.password = password;
    }
}
