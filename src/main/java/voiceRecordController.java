import com.bitsinharmony.recognito.VoicePrint;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.*;
import voice.JavaSoundRecorder;
import voice.VoiceRecognizer;
import java.net.URL;
import java.util.ResourceBundle;

public class voiceRecordController implements Initializable {

    private static String userName;
    private static Boolean isTest=false;
    private static int score=-1;

    @FXML
    Pane voicePane;
    @FXML
    Label recorderDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle circle = new Circle();
        circle.setCenterX(219);
        circle.setCenterY(100);
        circle.setRadius(40);
        circle.setFill(Color.color(0.75, 0.42, 0.33));

        Circle innerCircle = new Circle();
        innerCircle.setCenterX(219);
        innerCircle.setCenterY(100);
        innerCircle.setRadius(20);
        innerCircle.setFill(Color.color(0.19, 0.18, 0.15));

        voicePane.getChildren().addAll(circle, innerCircle);

        ScaleTransition transition = new ScaleTransition(Duration.seconds(1), circle);
        transition.setNode(innerCircle);
        transition.setToX(1.5);
        transition.setToY(1.5);
        transition.setAutoReverse(true);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.play();
        transition.setOnFinished(event -> {
            System.out.println("Finished");
        });
    }

    @FXML
    public void startInit() {
        JavaSoundRecorder recorder = new JavaSoundRecorder("userName");

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                recorder.start();
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                recorderDisplay.setText("Recording Finished");
                VoiceRecognizer voiceRecognizer = new VoiceRecognizer("VoicePrints");
                try {
                    if(!isTest) {
                        voiceRecognizer.mergeVoicePrint(userName, recorder.getFileName());
                    }
                    else{
                        score = voiceRecognizer.recognize(userName, recorder.getFileName());
                        if(score<0){ AlertBox.generateDialog("Authentication Error !!!!"); }
                        else {
                            Stage window = (Stage) recorderDisplay.getScene().getWindow();
                            window.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Task<Void> stopper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Thread.sleep(recorder.RECORD_TIME);
                recorder.finish();
                return null;
            }
        };
        recorderDisplay.setText("Started Recording");
        new Thread(task).start();
        new Thread(stopper).start();
    }

    public static String getUserName(){  return voiceRecordController.userName; }
    public static void setUserName(String userName){  voiceRecordController.userName = userName; }
    public static void setIsTest(Boolean value){ isTest = value; }
    public static int getScore() throws Exception {
        if (!isTest){ throw new Exception("Not in Testing Mode"); }
        else{ return score; }
    }
}