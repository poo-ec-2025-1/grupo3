package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import lavanderia.Model.Reserva;
import lavanderia.Model.Database;
import lavanderia.Model.Aparelho;
import lavanderia.Model.ReservaRepository;
import java.time.*;
import java.util.*;

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
private String formatarReservaParaString(Reserva reserva) {
    return reserva.getDataReserva() + " - " + reserva.getHoraInicio() + " até " + reserva.getHoraFim() + " | Máquina: " + (reserva.getAparelho() != null ? reserva.getAparelho().getId() : "N/A");
}

    @FXML
private void initialize() {
    try {
        Database db = new Database("lavanderia.db");
        ReservaRepository repo = new ReservaRepository(db);
        List<Reserva> reservasDoBanco = repo.loadAll();

        for (Reserva r : reservasDoBanco) {
            reservasListView.getItems().add(formatarReservaParaString(r));
        }

        db.close();
    } catch (Exception e) {
        System.out.println("Erro ao carregar reservas do banco: " + e.getMessage());
        e.printStackTrace();
    }
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
    
    /**
     * Cadastra um novo usuário no banco de dados
     */
    private boolean cadastrarReserva(LocalDate data, LocalTime horaInicio, LocalTime horaFim, Aparelho aparelho) {
    try {
        Database db = new Database("lavanderia.db");
        ReservaRepository repo = new ReservaRepository(db);

        // Verificar se já existe reserva igual (implemente um método para isso no repo se quiser)

        Reserva novaReserva = new Reserva(data, horaInicio, horaFim, aparelho);
        boolean sucesso = repo.create(novaReserva);
        db.close();
        return sucesso;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}