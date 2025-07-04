package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import java.text.DecimalFormat;

public class PainelUsuarioController {
    private int matriculaUsuarioLogado;
    private Dao<Usuario, Integer> usuarioDao;
    
    DecimalFormat df = new DecimalFormat("#.##");
    String saldoFormatado;
    
    @FXML
    private Label nomeLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label matriculaLabel;
    @FXML
    private Label saldoLabel;
    @FXML
    private Label nomeDisplay;
    @FXML
    private Label emailDisplay;
    @FXML
    private Label matriculaDisplay;
    @FXML
    private Label saldoDisplay;
    @FXML
    private Button alterarCadastroButton;
    @FXML
    private Button encerrarSessaoButton;
    @FXML
    private Button adicionarSaldoButton;
    @FXML
    private Button minhasReservasButton;

    public void iniciarDados(int matricula) {
        this.matriculaUsuarioLogado = matricula;
        popularDadosNaTela();
    }
    
    private void popularDadosNaTela() {
        if (matriculaUsuarioLogado != 0) {
            nomeLabel.setText("Nome:");
            emailLabel.setText("Email:");
            matriculaLabel.setText("Matrícula:");
            saldoLabel.setText("Saldo:");
        
            try {
                DatabaseManager.init();
                UsuarioRepository usuarioRepo = new UsuarioRepository();
                usuarioDao = usuarioRepo.getDao();
                Usuario usuario = usuarioDao.queryBuilder()
                                            .where()
                                            .eq("matricula", matriculaUsuarioLogado)
                                            .queryForFirst();
                nomeDisplay.setText(usuario.getNomeCompleto());
                emailDisplay.setText("emailpessoaexemplo@gmail.com");
                matriculaDisplay.setText(usuario.getStringMatricula());
                df.setMinimumFractionDigits(2);
                this.saldoFormatado = df.format(usuario.getSaldo());
                saldoDisplay.setText("R$: "+this.saldoFormatado);
            
            } catch (SQLException e) {
                System.err.println("Erro ao validar login com ORMLite: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void initialize() {
        
    }

    @FXML
    private void handleAlterarCadastro(ActionEvent event) {
        System.out.println("Alterar cadastro clicado em " + java.time.LocalDateTime.now());
    }

    @FXML
    private void handleEncerrarSessao(ActionEvent event) {
        System.out.println("Encerrar sessão clicado - " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Tela de Login");
            stage.setScene(scene);
            
            LoginController loginController = fxmlLoader.getController();
            
            loginController.getMatriculaTextField().requestFocus();

            loginController.getLogarButton().disableProperty().bind(
            Bindings.isEmpty(loginController.getMatriculaTextField().textProperty())
            .or(Bindings.isEmpty(loginController.getSenhaPasswordField().textProperty())
            .and(Bindings.isEmpty(loginController.getSenhaTextField().textProperty())))
            );
            
            stage.show();
            Stage painelUsuarioStage = (Stage) encerrarSessaoButton.getScene().getWindow();
            painelUsuarioStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar tela de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdicionarSaldo(ActionEvent event) {
        System.out.println("Adicionar saldo clicado em " + java.time.LocalDateTime.now());
        try {
            System.out.println("Tentando carregar telaAdicionarSaldo.fxml");
            System.out.println("Recurso: " + getClass().getResource("/lavanderia/view/telaAdicionarSaldo.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaAdicionarSaldo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Adicionar Saldo");
            stage.setScene(scene);
            System.out.println("Exibindo tela de Adicionar Saldo");
            
            int matriculaUsuario = matriculaUsuarioLogado;
            AdicionarSaldoController adicionarSaldoController = fxmlLoader.getController();
            adicionarSaldoController.iniciarDados(matriculaUsuario);
            
            stage.show();

            Stage painelUsuarioStage = (Stage) adicionarSaldoButton.getScene().getWindow();
            painelUsuarioStage.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMinhasReservas(ActionEvent event) {
        System.out.println("Navegando para minhas reservas em " + java.time.LocalDateTime.now());
        try {
            System.out.println("Tentando carregar telaMinhasReservas.fxml");
            System.out.println("Recurso: " + getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MinhasReservas");
            stage.setScene(scene);
            System.out.println("Exibindo tela de MinhasReservas");
            
            int matriculaUsuario = matriculaUsuarioLogado;
            MinhasReservasController minhasReservasController = fxmlLoader.getController();
            minhasReservasController.iniciarDados(matriculaUsuario);
            
            stage.show();

            Stage painelUsuarioStage = (Stage) minhasReservasButton.getScene().getWindow();
            painelUsuarioStage.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}