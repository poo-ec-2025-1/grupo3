package lavanderia.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lavanderia.Model.Reserva;
import lavanderia.Model.ReservaRepository;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class MinhasReservasController {
    private int matriculaUsuarioLogado;
    private Dao<Usuario, Integer> usuarioDao;
    private UsuarioRepository usuarioRepo;
    private Usuario usuario;
    private Dao<Reserva, Integer> reservaDao;
    private ReservaRepository reservaRepo;
    
    @FXML
    private ListView<Reserva> reservasListView;
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
    
    /*  ainda nao implementamos uma forma de popular as labels
        referentes a data, hora inicial, hora final e a maquina
        reservada. mantemos no codigo apenas como indicativo
        do que seria a funcionalidade delas.
    */

    public void iniciarDados(int matricula) {
        this.matriculaUsuarioLogado = matricula;
        popularReservas();
    }
    
    
    private void popularReservas() {
        try{
            int matriculaUsuario = this.matriculaUsuarioLogado;
            DatabaseManager.init();
            usuarioDao = new UsuarioRepository().getDao();
            reservaDao = new ReservaRepository().getDao();
            
            Usuario usuario = usuarioDao.queryBuilder()
                                        .where()
                                        .eq("matricula", matriculaUsuarioLogado)
                                        .queryForFirst();
                                        
            List<Reserva> reservas = reservaDao.queryBuilder()
                                        .where()
                                        .eq("usuario_id", usuario)
                                        .query();
            ObservableList<Reserva> observableListReservas = FXCollections.observableArrayList(reservas);
            reservasListView.setItems(observableListReservas);
            
        } catch (SQLException e) {
            System.err.println("Erro ao mostrar reservas.");
        }
    }
    
   
    @FXML
    private void initialize() {
        
    }

    @FXML
    private void handleCancelarReserva(ActionEvent event) {
        System.out.println("Cancelar reserva clicado em " + java.time.LocalDateTime.now());
        Reserva reservaSelecionada = reservasListView.getSelectionModel().getSelectedItem();
        if (reservaSelecionada != null) {
            try {
                DatabaseManager.init();
                ReservaRepository reservaRepo = new ReservaRepository();
                reservaRepo.delete(reservaSelecionada);
                reservasListView.getItems().remove(reservaSelecionada);
                System.out.println("Reserva cancelada: " + reservaSelecionada);
            } catch(SQLException e) {
                System.err.println("Erro ao carregar/deletar alguma reserva.");
            }
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
            
            int matriculaUsuario = matriculaUsuarioLogado;
            CalendarioController calendarioController = fxmlLoader.getController();
            calendarioController.iniciarDados(matriculaUsuario);
            
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

            Stage minhasReservasStage = (Stage) voltarButton.getScene().getWindow();
            minhasReservasStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar painel do usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}