import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Store;
import utils.User;
import voice.JavaSoundRecorder;
import voice.VoiceRecognizer;

import utils.Locker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;


public class Main extends Application {

    private static String userName;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage stage = new Stage();
        Stage stage2 = new Stage();
        Stage stage3 = new Stage();
        Parent front = FXMLLoader.load(getClass().getResource("/fxml/frontScene.fxml"));
        Scene scene = new Scene(front);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createWindow(stage,"/fxml/dashboard.fxml");
                        stage.show();
                    }
                });

                return null;
            }
        };

        Task<Void> task3 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        User user = User.getUser(userName);
                        if(user == null){
                            try {
                                Stage stage = AlertBox.showDialog("Not a valid User\nTerminating Session..");
                                Thread.sleep(2000);
                                stage.close();
                                System.exit(1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if ((Boolean) user.getProperty("masterpassword")) {
                            createWindow(stage3, "/fxml/userPassword.fxml");
                        } else if ((Boolean) user.getProperty("voice")) {
                                voiceRecordController.setIsTest(true);
                                createWindow(stage3, "/fxml/addvoice.fxml");
                        }
                        stage3.initStyle(StageStyle.UNDECORATED);
                    }
                });
                return null;
            }
        };
        task3.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try{
                    stage3.showAndWait();
                    voiceRecordController.setIsTest(false);
                    new Thread(task).start();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Task<Void> task2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createWindow(stage2,"/fxml/userName.fxml");
                    stage2.initStyle(StageStyle.UNDECORATED);
                    primaryStage.close();
                }
            });

            return null;
            }
        };
        task2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                stage2.showAndWait();
                try {
                    new Thread(task3).start();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(task2).start();
        /*
        Store<User> store = new Store<>("UserData");
        User user = new User("Ashwin");
        user.addProperty("voice",false);
        user.addProperty("masterpassword", true);
        user.addProperty("password", "Excaliber17");
        User.addUser("Ashwin", user);
        */
    }

    private Stage createWindow(Stage stage, String fxml){

        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.setTitle("File Locker");
        stage.setScene(new Scene(root));
        return stage;
    }

    public static void setUserName(String userName){
        Main.userName = userName;
    }
    public static String getUserName(){ return userName; }
}

/*
public class Main{
    public static void main(String[] args) throws Exception {
        Store<User> store = new Store<>("UserData");
        User user = new User("Ashwin");
        user.addProperty("voice",false);
        user.addProperty("masterpassword", true);
        user.addProperty("password", "Excaliber17");
        User.addUser("Ashwin", user);
    }
}
*/