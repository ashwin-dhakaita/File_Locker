import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Locker;
import utils.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LockerBarController implements Initializable {

    String userName=Main.getUserName();
    User user = User.getUser(userName);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public Label label;

    @FXML
    public File clickOpen() throws Exception {

        String lockerName = label.getText();
        Locker locker = user.getLocker(lockerName);
        String lockerPassword = locker.getPassword();
        userPasswordController.setPassword(lockerPassword);
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        userPasswordController controller = loader.getController();
        locker.openLocker();
        File file = openFileWindow(userName+"//"+lockerName);
        locker.closeLocker();
        return file;
    }

    @FXML
    public void clickAddFiles() throws Exception {
        File file = clickOpen();
        if(file==null)return;
        String lockerName = label.getText();
        Locker locker = user.getLocker(lockerName);
        locker.addToLocker(file.getPath());
    }

    @FXML
    public void clickRemoveFiles() throws Exception {
        File file = clickOpen();
        if(file==null)return;
        String lockerName = label.getText();
        Locker locker = user.getLocker(lockerName);
        locker.removeFromLocker(userName, file.getPath());
    }

    private File openFileWindow(String fileName){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(fileName));
        return fileChooser.showOpenDialog(null);
    }

}
