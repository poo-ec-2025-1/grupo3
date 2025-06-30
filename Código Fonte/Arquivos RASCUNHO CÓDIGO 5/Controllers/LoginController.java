package lavanderia.Controller;

import lavanderia.Model.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import lavanderia.Controller.PainelUsuarioController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controla os dados do modelo e a interação com a interface (visão) para o Login
 * 
 * @author (seu nome)
 * @version 29/06/2025
 */
public class LoginController
{
    @FXML
    private TextField matriculaTextField; // Campo para matrícula
    @FXML
    private PasswordField senhaPasswordField; // Campo para senha
    @FXML
    private Button logarButton; // Botão de login
    @FXML
    private Button cadastroButton; // Botão de registro
    @FXML
    private Label mensagemLabel; // Label para mensagens
    @FXML
    private CheckBox exibirSenhaCheckbox; // Checkbox para exibir senha

    private Stage stage; // Janela atual

    /**
     * Construtor para objetos da classe LoginController
     */
    public LoginController()
    {
        // O LoginView cuida do carregamento do FXML
    }
    
    /**
     * Inicia a janela da interface (chamado pela LoginView)
     */
    public void iniciar() {
        // Inicializa a exibição da senha com base no CheckBox
        exibirSenhaCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            senhaPasswordField.setVisible(!newVal); // Oculta o PasswordField
            // Simulação simples: exibe a senha como texto (em produção, use um TextField)
            if (newVal) {
                System.out.println("Exibir senha implementado (a ajustar).");
            }
        });
        mensagemLabel.setText(""); // Limpa a mensagem inicial
 }
    
    
    @FXML
private void logar() {
    String matricula = matriculaTextField.getText();
    String senha = senhaPasswordField.getText();

    if (matricula.isEmpty() || senha.isEmpty()) {
        mensagemLabel.setText("Preencha todos os campos.");
        return;
    }

    try {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:lavanderia.db");
        String sql = "SELECT * FROM usuarios WHERE matricula = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, matricula);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            mensagemLabel.setText("Login bem-sucedido!");
            // Aqui você pode trocar de tela ou abrir o painel do usuário
        } else {
            mensagemLabel.setText("Matrícula ou senha inválida.");
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        mensagemLabel.setText("Erro ao conectar ao banco.");
        e.printStackTrace();
    }
}    
}
