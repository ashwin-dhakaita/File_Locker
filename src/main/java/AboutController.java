import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View 2 loaded");
    }

    private void loadLink(String url) throws URISyntaxException, IOException {
        System.out.println(url);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    @FXML
    public void loadLink1() throws IOException, URISyntaxException {
        loadLink("https://github.com/martinwithaar/Encryptor4j");
    }

    @FXML
    public void loadLink2() throws IOException, URISyntaxException {
        loadLink("https://github.com/amaurycrickx/recognito");
    }

}
