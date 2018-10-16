import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Locker;
import utils.User;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class dashBoardController implements Initializable {

    static String userName;
    User user = User.getUser(userName);
    HashMap<String, Locker> lockers = user.getLockerList();

    @FXML
    VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Map.Entry<String, Locker> entry : lockers.entrySet()) {
            try {
                createLockerBar(entry.getKey());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createLockerBar(String lockerName) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lockerbar.fxml"));
        Parent root = (Parent) loader.load();
        LockerBarController controller = loader.getController();
        controller.label.textProperty().setValue(lockerName);
        vbox.getChildren().addAll(root);
    }

    public static void setUserName(String userName){
        dashBoardController.userName = userName;
    }
}
