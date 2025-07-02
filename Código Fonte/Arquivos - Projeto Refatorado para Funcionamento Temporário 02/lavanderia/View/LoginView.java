package lavanderia.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lavanderia.Controller.LoginController;

public class LoginView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lavanderia/View/telaLogin.fxml"));
        Pane pane = loader.load();

        // Obtém o controller automaticamente e chama o método iniciar()
        LoginController controller = loader.getController();
        controller.iniciar();

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Lavanderia - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Permite rodar isoladamente também
    }
}