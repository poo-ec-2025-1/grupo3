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

public class AdicionarSaldoController {
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

    @FXML
    private void handleVoltar(ActionEvent event) {
        System.out.println("Voltar clicado em " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            PainelUsuarioController controller = fxmlLoader.getController();
            // O saldo já foi atualizado em handleConfirmarDeposito, então apenas exibe
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            stage.show();
            Stage saldoStage = (Stage) voltarButton.getScene().getWindow();
            saldoStage.close();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            PainelUsuarioController controller = fxmlLoader.getController();
            double novoSaldo = controller.getSaldo() + valorAdicionado; // Soma ao saldo atual
            controller.setSaldo(novoSaldo); // Atualiza o saldo
            System.out.println("Saldo atualizado para: R$ " + novoSaldo);
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