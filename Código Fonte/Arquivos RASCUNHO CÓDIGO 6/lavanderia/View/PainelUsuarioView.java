package lavanderia.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import lavanderia.Controller.PainelUsuarioController;

public class PainelUsuarioView extends Application
{
    private FXMLLoader loader;
    private URL url;
    
    public PainelUsuarioView() {
        this.loader = new FXMLLoader();
        try {
            this.url = new File("telaPainelUsuario.fxml").toURI().toURL();
        } catch (Exception e) {
            System.out.println("Erro na carga do FXML:" + e);
        }
        this.loader.setLocation(this.url);        
    }
    
    public void setController(PainelUsuarioController controller) {
        this.loader.setController(controller);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = loader.<Pane>load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

   
