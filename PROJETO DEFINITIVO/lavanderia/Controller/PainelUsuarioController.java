package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PainelUsuarioController {
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

    @FXML
    private void initialize() {
        nomeLabel.setText("Nome:");
        emailLabel.setText("Email:");
        matriculaLabel.setText("Matrícula:");
        saldoLabel.setText("Saldo:");
        nomeDisplay.setText("Pessoa Exemplo");
        emailDisplay.setText("emailpessoaexemplo@gmail.com");
        matriculaDisplay.setText("00000000000");
        saldoDisplay.setText("R$: 0.00");
    }

    @FXML
    private void handleAlterarCadastro(ActionEvent event) {
        System.out.println("Alterar cadastro clicado em " + java.time.LocalDateTime.now());
    }

    @FXML
    private void handleEncerrarSessao(ActionEvent event) {
        System.out.println("Encerrando sessão em " + java.time.LocalDateTime.now());
        mudarTela("telaLogin.fxml", event);
    }

    @FXML
    private void handleAdicionarSaldo(ActionEvent event) {
        System.out.println("Adicionar saldo clicado em " + java.time.LocalDateTime.now());
        mudarTela("telaAdicionarSaldo.fxml", event);
    }

    @FXML
    private void handleMinhasReservas(ActionEvent event) {
        System.out.println("Navegando para minhas reservas em " + java.time.LocalDateTime.now());
        mudarTela("telaMinhasReservas.fxml", event);
    }

    private void mudarTela(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/" + fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(fxmlFile.replace(".fxml", "").replace("tela", ""));
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos públicos para atualizar o saldo
    public void setSaldo(double novoSaldo) {
        saldoLabel.setText("Saldo:");
        saldoDisplay.setText(String.format("R$: %.2f", novoSaldo));
    }

    public double getSaldo() {
        String textoSaldo = saldoDisplay.getText().replace("R$: ", "").trim();
        return Double.parseDouble(textoSaldo);
    }
}