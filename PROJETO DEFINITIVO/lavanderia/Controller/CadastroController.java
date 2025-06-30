package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class CadastroController {
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField matriculaTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private Button finalizarCadastroButton;
    @FXML
    private Button fazerLoginButton;
    @FXML
    private CheckBox exibirSenhaCheckbox;

    @FXML
    private void handleFinalizarCadastro(ActionEvent event) {
        System.out.println("Finalizar cadastro clicado - " + java.time.LocalDateTime.now());
        String nome = nomeTextField.getText();
        String matricula = matriculaTextField.getText();
        String senha = senhaPasswordField.getText();
        System.out.println("Nome: " + nome + ", Matrícula: " + matricula + ", Senha: " + senha);

        // Navegar de volta para telaLogin.fxml após finalizar cadastro
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Tela de Login");
            stage.setScene(scene);
            stage.show();
            Stage cadastroStage = (Stage) finalizarCadastroButton.getScene().getWindow();
            cadastroStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar tela de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFazerLogin(ActionEvent event) {
        System.out.println("Fazer login clicado - " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Tela de Login");
            stage.setScene(scene);
            stage.show();
            Stage cadastroStage = (Stage) fazerLoginButton.getScene().getWindow();
            cadastroStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar tela de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleSenhaVisibilidade(ActionEvent event) {
        System.out.println("Toggle senha visibilidade: " + exibirSenhaCheckbox.isSelected() + " - " + java.time.LocalDateTime.now());
        if (exibirSenhaCheckbox.isSelected()) {
            if (senhaPasswordField.isVisible()) {
                senhaTextField.setText(senhaPasswordField.getText());
                senhaTextField.setVisible(true);
                senhaPasswordField.setVisible(false);
                senhaTextField.requestFocus(); // Foco no campo visível
            }
        } else {
            if (senhaTextField.isVisible()) {
                senhaPasswordField.setText(senhaTextField.getText());
                senhaPasswordField.setVisible(true);
                senhaTextField.setVisible(false);
                senhaPasswordField.requestFocus(); // Foco no campo visível
            }
        }
    }
}