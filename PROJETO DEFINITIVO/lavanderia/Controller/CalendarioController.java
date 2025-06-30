package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioController {
    @FXML
    private DatePicker dataDatePicker;
    @FXML
    private ComboBox<String> horarioComboBox; // Especificado como String
    @FXML
    private ComboBox<String> maquinaComboBox; // Especificado como String
    @FXML
    private Label mostrarUsuarioLabel;
    @FXML
    private Label valorTotalLabel;
    @FXML
    private Button limparReservaButton;
    @FXML
    private Button confirmarReservaButton;

    public static List<String> reservas = new ArrayList<>();

    @FXML
    private void initialize() {
        // Preenche os ComboBoxes com opções fictícias
        horarioComboBox.getItems().addAll("08:00", "10:00", "14:00", "16:00");
        maquinaComboBox.getItems().addAll("Máquina 1", "Máquina 2", "Máquina 3");
    }

    @FXML
    private void handleLimpar(ActionEvent event) {
        System.out.println("Limpar clicado em " + java.time.LocalDateTime.now());
        dataDatePicker.setValue(null);
        horarioComboBox.getSelectionModel().clearSelection();
        maquinaComboBox.getSelectionModel().clearSelection();
        valorTotalLabel.setText("");
    }

    @FXML
    private void handleConfirmarReserva(ActionEvent event) {
        System.out.println("Confirmar reserva clicado em " + java.time.LocalDateTime.now());
        LocalDate data = dataDatePicker.getValue();
        String horario = horarioComboBox.getValue();
        String maquina = maquinaComboBox.getValue();

        if (data != null && horario != null && maquina != null) {
            String reserva = String.format("Data: %s, Horário: %s, Máquina: %s", data, horario, maquina);
            reservas.add(reserva);
            System.out.println("Reserva adicionada: " + reserva);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Minhas Reservas");
                stage.setScene(scene);
                stage.show();
                Stage calendarioStage = (Stage) confirmarReservaButton.getScene().getWindow();
                calendarioStage.close();
            } catch (Exception e) {
                System.out.println("Erro ao carregar painel de reservas: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Preencha todos os campos da reserva!");
        }
    }
}