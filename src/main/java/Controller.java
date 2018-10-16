import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Pane paneDashboard;
    @FXML
    HBox voiceBiometricHandle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
        try {
            loadDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDashboardView(String fxml) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource(fxml));
        paneDashboard.getChildren().setAll(pane);
    }

    @FXML
    public void loadChangePassword() throws IOException {
        loadDashboardView("fxml/changepassword.fxml");
    }

    @FXML
    public void loadNewUser() throws IOException {
        loadDashboardView("fxml/newuser.fxml");
    }

    @FXML
    public void loadNewLocker() throws IOException {
        loadDashboardView("fxml/newlocker.fxml");
    }

    @FXML
    public void loadAbout() throws IOException {
        loadDashboardView("fxml/about.fxml");
    }

    @FXML
    public void loadVoice() throws IOException {
        loadDashboardView("fxml/addvoice.fxml");
    }

    @FXML
    public void loadDashboard() throws Exception {
        loadDashboardView("fxml/lockerView.fxml");
    }

}