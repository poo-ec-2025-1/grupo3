package lavanderia.Controller;

import lavanderia.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;

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

    /**
     * Lida com o clique no botão "Finalizar cadastro"
     */
    @FXML
    private void handleFinalizarCadastro(ActionEvent event) {
        System.out.println("Finalizar cadastro clicado - " + java.time.LocalDateTime.now());
        String nome = nomeTextField.getText().trim();
        int matricula;
        try {
        matricula= Integer.parseInt(matriculaTextField.getText().trim());           
            } 
        catch (NumberFormatException e) {
        System.out.println("Erro: Matrícula deve ser um número.");
        return;
        }
        String senha = senhaPasswordField.getText().trim();
        System.out.println("Nome: " + nome + ", Matrícula: " + matricula + ", Senha: " + senha);

        if (nome.isEmpty() || senha.isEmpty()) {
            System.out.println("Erro: Preencha todos os campos.");
            return;
        }

        if (cadastrarUsuario(nome, matricula, senha)) {
            System.out.println("Cadastro realizado com sucesso!");
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
        } else {
            System.out.println("Matrícula já existe ou erro no cadastro.");
        }
        
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

    /**
     * Lida com o clique no botão "Fazer login"
     */
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

    /**
     * Alterna a visibilidade da senha
     */
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

    /**
     * Cadastra um novo usuário no banco de dados
     */
    private boolean cadastrarUsuario(String nome, int matricula, String senha) {
    Database db = new Database("lavanderia.db");
    try {
        ;var connectionSource = db.getConnection();
        TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
        Dao<Usuario, Integer> usuarioDao = DaoManager.createDao(connectionSource, Usuario.class);

        // Verifica se matrícula já existe
        if (usuarioDao.queryBuilder().where().eq("matricula", matricula).queryForFirst() != null) {
            System.out.println("Matrícula já cadastrada.");
            return false;
        }

        Usuario novoUsuario = new Usuario(nome, matricula, senha);
        usuarioDao.create(novoUsuario);
        db.close();
        return true;

    } catch (Exception e) {
        System.out.println("Erro ao cadastrar usuário (ORMLite): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
}