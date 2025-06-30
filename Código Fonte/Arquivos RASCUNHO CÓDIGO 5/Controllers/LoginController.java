package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lavanderia.Model.Database;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.Usuario;

public class LoginController {
    @FXML
    private TextField matriculaTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private Button logarButton;
    @FXML
    private Button cadastroButton;
    @FXML
    private CheckBox exibirSenhaCheckbox;

    @FXML
private void handleLogin(ActionEvent event) {
    System.out.println("Botão Logar clicado! - " + java.time.LocalDateTime.now());
    String matriculaStr = matriculaTextField.getText();
    String senha = senhaPasswordField.isVisible() ? senhaPasswordField.getText() : senhaTextField.getText();

    if (matriculaStr.isEmpty() || senha.isEmpty()) {
        System.out.println("Erro: Matrícula ou senha não podem estar vazios!");
        return;
    }

    try {
        int matricula = Integer.parseInt(matriculaStr);
        Database db = new Database("lavanderia.db");
        UsuarioRepository usuarioRepo = new UsuarioRepository(db);

        var usuario = usuarioRepo.buscarPorMatriculaESenha(matricula, senha);
        db.close();

        if (usuario != null) {
            System.out.println("Login bem-sucedido. Usuário: " + usuario.getNomeCompleto());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            stage.show();

            Stage loginStage = (Stage) logarButton.getScene().getWindow();
            loginStage.close();
        } else {
            System.out.println("Erro: Matrícula ou senha inválidos!");
        }

    } catch (NumberFormatException e) {
        System.out.println("Erro: Matrícula deve ser numérica.");
    } catch (Exception e) {
        System.out.println("Erro ao processar login: " + e.getMessage());
        e.printStackTrace();
    }
}

    @FXML
    private void handleCadastro(ActionEvent event) {
        System.out.println("Cadastro clicado - " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaCadastro.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Tela de Cadastro");
            stage.setScene(scene);
            stage.show();
            Stage loginStage = (Stage) cadastroButton.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar telaCadastro.fxml: " + e.getMessage());
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