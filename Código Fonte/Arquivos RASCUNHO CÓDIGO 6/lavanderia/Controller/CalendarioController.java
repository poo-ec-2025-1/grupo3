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
import lavanderia.Model.HorariosFixos;
import lavanderia.Model.IntervaloHorario;
import lavanderia.Model.IntervaloDeUso;
import lavanderia.Model.DatabaseManager;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.Aparelho;
import lavanderia.Model.AparelhoRepository;
import lavanderia.Model.Reserva;
import lavanderia.Model.ReservaRepository;
import com.j256.ormlite.dao.Dao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.sql.SQLException;

public class CalendarioController {
    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Aparelho, Integer> aparelhoDao;
    private int matriculaUsuarioLogado;
    private UsuarioRepository usuarioRepo;
    private Usuario usuario;
    private int idUsuarioLogado;
    private AparelhoRepository aparelhoRepo;
    private Aparelho aparelho;
    private HorariosFixos horarios;
    private List<IntervaloHorario> listaDeHorarios;
    private ObservableList<IntervaloHorario> listaDeHorariosObservavel;
    private List<Aparelho> listaDeAparelhos;
    private ObservableList<Aparelho> listaDeAparelhosObservavel;
    
    @FXML
    private DatePicker dataDatePicker;
    @FXML
    private ComboBox<IntervaloHorario> horarioComboBox;
    @FXML
    private ComboBox<Aparelho> maquinaComboBox;
    @FXML
    private Label mostrarUsuarioLabel;
    @FXML
    private Label valorTotalLabel;
    @FXML
    private Button limparReservaButton;
    @FXML
    private Button confirmarReservaButton;

    public void iniciarDados(int matricula) {
        this.matriculaUsuarioLogado = matricula;
    }
    
    public int carregarDadosUsuario() {
        try{
            DatabaseManager.init();
            usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
            usuario = usuarioDao.queryBuilder()
                                            .where()
                                            .eq("matricula", matriculaUsuarioLogado)
                                            .queryForFirst();
            idUsuarioLogado = usuario.getId();
            return idUsuarioLogado;
        } catch (SQLException e) {
            System.err.println("Erro ao carregar dados do Usuario.");
            return 0;
        }
    }
    
    public List<Aparelho> carregarDadosAparelho() {
        try{
            DatabaseManager.init();
            aparelhoRepo = new AparelhoRepository();
            List<Aparelho> todosAparelhos = aparelhoRepo.loadAll();
            return todosAparelhos;
        } catch (SQLException e) {
            System.err.println("Erro ao carregar Aparelhos.");
            return null;
        }
    }
    
    public void popularComboBox() {
        horarios = new HorariosFixos();
        listaDeHorarios = horarios.getIntervalos();
        listaDeHorariosObservavel = FXCollections.observableArrayList(listaDeHorarios);
        horarioComboBox.setItems(listaDeHorariosObservavel);
        
        listaDeAparelhos = carregarDadosAparelho();
        listaDeAparelhosObservavel = FXCollections.observableArrayList(listaDeAparelhos);
        maquinaComboBox.setItems(listaDeAparelhosObservavel);
    }
    
    @FXML
    private void initialize() {
        popularComboBox();
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
        IntervaloHorario intervaloHorarioSelecionado = horarioComboBox.getValue();
        Aparelho maquinaSelecionada = maquinaComboBox.getValue();

        if (data != null && intervaloHorarioSelecionado != null && maquinaSelecionada != null) {
            String[] partes = intervaloHorarioSelecionado.toString().split(" - ");
            LocalDateTime inicio = LocalDateTime.parse(partes[0]);
            LocalDateTime fim = LocalDateTime.parse(partes[1]);
            IntervaloDeUso intervaloDeUso = new IntervaloDeUso(maquinaSelecionada, inicio, fim);
            
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