package lavanderia.Controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class LoginController {
    private int matriculaUsuario;
    private Dao<Usuario, Integer> usuarioDao;
    
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

    public void iniciar() {
        try {
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
        } catch (SQLException e) {
            System.err.println("Erro ao iniciar database.");
        }
        
        matriculaTextField.requestFocus();

        logarButton.disableProperty().bind(
            Bindings.isEmpty(matriculaTextField.textProperty())
            .or(Bindings.isEmpty(senhaPasswordField.textProperty())
            .and(Bindings.isEmpty(senhaTextField.textProperty())))
        );
    }
    

    public Usuario validarLoginEObterUsuario(int matricula, String senha) {
        try {
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
            Usuario usuario = usuarioDao.queryBuilder()
                                        .where()
                                        .eq("matricula", matricula)
                                        .queryForFirst();
            if (usuario != null) {
                if (usuario.getSenha().equals(senha)) {
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login com ORMLite: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @FXML
    private void initialize() {
        
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Botão Logar clicado! - " + java.time.LocalDateTime.now());
        String matriculaStr = matriculaTextField.getText();
        String senha = senhaPasswordField.isVisible() ? senhaPasswordField.getText() : senhaTextField.getText();
        
        if (matriculaStr.isEmpty() || senha.isEmpty()) {
            System.out.println("Erro: Matrícula ou senha não podem estar vazios!");
            return;
        }
        int matricula;
        try {
            matricula = Integer.parseInt(matriculaStr);
        } catch (NumberFormatException e) {
            System.out.println("Erro: A matrícula deve ser um número.");
            return;
        }
        if(validarLoginEObterUsuario(matricula, senha) != null) {
           try {
            System.out.println("Tentando carregar telaPainelUsuario.fxml");
            System.out.println("Recurso: " + getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            System.out.println("Exibindo tela do painel do usuário");
            
            matriculaUsuario = matricula;
            PainelUsuarioController painelUsuarioController = fxmlLoader.getController();
            painelUsuarioController.iniciarDados(matriculaUsuario);
            
            stage.show();

            Stage loginStage = (Stage) logarButton.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar telaPainelUsuario.fxml: " + e.getMessage());
            e.printStackTrace();
        } 
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
    
    public Button getLogarButton() {
        return this.logarButton;
    }
    
    public TextField getMatriculaTextField() {
        return this.matriculaTextField;
    }
    
    public TextField getSenhaTextField() {
        return this.senhaTextField;
    }
    
    public PasswordField getSenhaPasswordField() {
        return this.senhaPasswordField;
    }
}