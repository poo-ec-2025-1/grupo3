package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MinhasReservasController {
    @FXML
    private ListView<String> reservasListView; // Especificado como String para compatibilidade
    @FXML
    private Label dataLabel;
    @FXML
    private Label horaInicialLabel;
    @FXML
    private Label horaFinalLabel;
    @FXML
    private Label maquinaReservadaLabel;
    @FXML
    private Button cancelarReservaButton;
    @FXML
    private Button novaReservaButton;
    @FXML
    private Button voltarButton;

    @FXML
    private void initialize() {
        // Preenche a ListView com as reservas salvas
        reservasListView.getItems().addAll(CalendarioController.reservas);
    }

    @FXML
    private void handleCancelarReserva(ActionEvent event) {
        System.out.println("Cancelar reserva clicado em " + java.time.LocalDateTime.now());
        String reservaSelecionada = reservasListView.getSelectionModel().getSelectedItem();
        if (reservaSelecionada != null) {
            CalendarioController.reservas.remove(reservaSelecionada);
            reservasListView.getItems().remove(reservaSelecionada);
            System.out.println("Reserva cancelada: " + reservaSelecionada);
        } else {
            System.out.println("Selecione uma reserva para cancelar!");
        }
    }

    @FXML
    private void handleNovaReserva(ActionEvent event) {
        System.out.println("Nova reserva clicado em " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaCalendario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Nova Reserva");
            stage.setScene(scene);
            stage.show();
            Stage reservasStage = (Stage) novaReservaButton.getScene().getWindow();
            reservasStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar tela de calendário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        System.out.println("Voltar clicado em " + java.time.LocalDateTime.now());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaPainelUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Painel do Usuário");
            stage.setScene(scene);
            stage.show();
            Stage reservasStage = (Stage) voltarButton.getScene().getWindow();
            reservasStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}