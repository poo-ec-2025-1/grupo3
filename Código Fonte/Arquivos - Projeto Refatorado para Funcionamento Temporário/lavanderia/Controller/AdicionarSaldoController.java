package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.DatabaseManager;
import lavanderia.Model.Caixa;
import lavanderia.Model.MetodoDePagamento;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class AdicionarSaldoController {
    private Dao<Usuario, Integer> usuarioDao;
    private int matriculaUsuarioLogado;
    
    @FXML
    private ComboBox<?> metodoPagamentoComboBox;
    @FXML
    private TextField valorTextField;
    @FXML
    private Label mostrarUsuarioLabel;
    @FXML
    private Button voltarButton;
    @FXML
    private Button confirmarDepositoButton;

    public void iniciarDados(int matricula) {
        this.matriculaUsuarioLogado = matricula;
    }
    
    @FXML
    private void handleVoltar(ActionEvent event) {
        System.out.println("Voltar clicado em " + java.time.LocalDateTime.now());
        try {
            System.out.println("Tentando carregar telaPainelUsuario.fxml");
            System.out.println("Recurso: " + getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            System.out.println("Exibindo tela do painel do usuário");
            
            int matriculaUsuario = matriculaUsuarioLogado;
            PainelUsuarioController painelUsuarioController = fxmlLoader.getController();
            painelUsuarioController.iniciarDados(matriculaUsuario);
            
            stage.show();

            Stage adicionarSaldoStage = (Stage) voltarButton.getScene().getWindow();
            adicionarSaldoStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmarDeposito(ActionEvent event) {
        System.out.println("Confirmar depósito clicado em " + java.time.LocalDateTime.now());
        String valor = valorTextField.getText();
        if (valor.isEmpty()) {
            System.out.println("Erro: Valor não pode estar vazio!");
            return;
        }
        try {
            double valorAdicionado = Double.parseDouble(valor);
            
            int matriculaUsuario = matriculaUsuarioLogado;
            
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
            Usuario usuario = usuarioDao.queryBuilder()
                                        .where()
                                        .eq("matricula", matriculaUsuario)
                                        .queryForFirst();
                                                         
            Caixa caixa = new Caixa();
            caixa.adicionarSaldo(usuario.getId(), valorAdicionado);
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            
            PainelUsuarioController painelUsuarioController = fxmlLoader.getController();
            painelUsuarioController.iniciarDados(matriculaUsuario);
            
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            stage.show();
            Stage saldoStage = (Stage) confirmarDepositoButton.getScene().getWindow();
            saldoStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário ou parsear valor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}