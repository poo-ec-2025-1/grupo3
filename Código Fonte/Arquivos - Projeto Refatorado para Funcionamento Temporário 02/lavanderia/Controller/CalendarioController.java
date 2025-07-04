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
import lavanderia.Model.Agenda;
import lavanderia.Model.HorariosFixos;
import lavanderia.Model.IntervaloHorario;
import lavanderia.Model.Database;
import lavanderia.Model.DatabaseManager;
import lavanderia.Model.Usuario;
import lavanderia.Model.UsuarioRepository;
import lavanderia.Model.Aparelho;
import lavanderia.Model.AparelhoRepository;
import lavanderia.Model.Reserva;
import lavanderia.Model.ReservaRepository;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class CalendarioController {
    private int matriculaUsuarioLogado;
    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Aparelho, Integer> aparelhoDao;
    private Dao<Reserva, Integer> reservaDao;
    private UsuarioRepository usuarioRepo;
    private Usuario usuario;
    private AparelhoRepository aparelhoRepo;
    private Aparelho aparelho;
    private HorariosFixos horarios;
    private List<IntervaloHorario> listaDeHorarios;
    private ObservableList<IntervaloHorario> listaDeHorariosObservavel;
    private List<Aparelho> listaDeAparelhos;
    private ObservableList<Aparelho> listaDeAparelhosObservavel = FXCollections.observableArrayList();
    
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
        popularComboBox();
        mostrarUsuario();
    }
   
    private List<Aparelho> carregarDadosAparelho(LocalTime inicio, LocalTime fim, LocalDate dia) {
        try{
            Database database = new Database("lavanderia.db");
            Agenda agenda = new Agenda(database);
            return agenda.agregarMaquinas(inicio, fim, dia);
        } catch (SQLException e) {
            System.err.println("Erro ao carregar Aparelhos.");
            return null;
        }
    }
    
    private void mostrarUsuario() {
        try {
            DatabaseManager.init();
            usuarioDao = new UsuarioRepository().getDao();
            Usuario usuario = usuarioDao.queryBuilder()
                                        .where()
                                        .eq("matricula", matriculaUsuarioLogado)
                                        .queryForFirst();
            String nomeUsuario = usuario.getNomeCompleto();
            mostrarUsuarioLabel.setText(nomeUsuario);
        } catch(SQLException e) {
            System.err.println("Erro ao mostrar Usuario.");
        }
    }
    
    private void popularComboBox() {
        horarios = new HorariosFixos();
        listaDeHorarios = horarios.getIntervalos();
        listaDeHorariosObservavel = FXCollections.observableArrayList(listaDeHorarios);
        horarioComboBox.setItems(listaDeHorariosObservavel);
    }
    
    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    @FXML
    private void initialize() {
        
        //Listener para o horário
        horarioComboBox.valueProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo != null) {
                System.out.println("Horário selecionado: " + valorNovo);
                
                String[] partes = valorNovo.toString().split(" - ");
                LocalTime inicio = LocalTime.parse(partes[0]);
                LocalTime fim = LocalTime.parse(partes[1]);
                
                LocalDate dataAtual = dataDatePicker.getValue();
                
                listaDeAparelhos = carregarDadosAparelho(inicio, fim, dataAtual);
                listaDeAparelhosObservavel = FXCollections.observableArrayList(listaDeAparelhos);
                maquinaComboBox.setItems(listaDeAparelhosObservavel);
            }
        });
        
        //Listener para o dia
        dataDatePicker.valueProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo != null) {
                System.out.println("Dia selecionado: " + valorNovo);
                
                IntervaloHorario intervaloHorarioSelecionado = horarioComboBox.getValue();
                if (intervaloHorarioSelecionado != null) 
                {
                    System.out.println("Dia selecionado: " + valorNovo + " // Horário: " + intervaloHorarioSelecionado);
                    String[] partes = intervaloHorarioSelecionado.toString().split(" - ");
                    LocalTime inicioAtual = LocalTime.parse(partes[0]);
                    LocalTime fimAtual    = LocalTime.parse(partes[1]);
    
                    // Carrega a lista de aparelhos com data e intervalo válidos
                    listaDeAparelhos = carregarDadosAparelho(inicioAtual, fimAtual, valorNovo);
                    listaDeAparelhosObservavel = FXCollections.observableArrayList(listaDeAparelhos);
                    maquinaComboBox.setItems(listaDeAparelhosObservavel);
                }
            }
        });
    }

    @FXML
    private void handleLimpar(ActionEvent event) {
        System.out.println("Limpar clicado em " + java.time.LocalDateTime.now());
        dataDatePicker.setValue(null);
        horarioComboBox.getSelectionModel().clearSelection();
        maquinaComboBox.getSelectionModel().clearSelection();
        listaDeAparelhos.clear();
                listaDeAparelhosObservavel = FXCollections.observableArrayList(listaDeAparelhos);
                maquinaComboBox.setItems(listaDeAparelhosObservavel);
        valorTotalLabel.setText("");
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            System.out.println("Tentando carregar telaMinhasResevas.fxml");
            System.out.println("Recurso: " + getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Minhas Reservas");
            stage.setScene(scene);
            System.out.println("Exibindo tela de Minhas Reservas");
            
            int matriculaUsuario = matriculaUsuarioLogado;
            MinhasReservasController minhasReservasController = fxmlLoader.getController();
            minhasReservasController.iniciarDados(matriculaUsuario);
            
            stage.show();
            Stage calendarioStage = (Stage) confirmarReservaButton.getScene().getWindow();
            calendarioStage.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar Minhas Reservas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmarReserva(ActionEvent event) {
        System.out.println("Confirmar reserva clicado em " + java.time.LocalDateTime.now());
        
        LocalDate dataSelecionada = dataDatePicker.getValue();
        IntervaloHorario intervaloHorarioSelecionado = horarioComboBox.getValue();
        Aparelho maquinaSelecionada = maquinaComboBox.getValue();
        
        if (dataSelecionada == null || intervaloHorarioSelecionado == null || maquinaSelecionada == null) {
            exibirAlerta("Campos Incompletos", "Por favor, preencha todos os campos para fazer a reserva.");
            return;
        }
        
        if (dataSelecionada.isBefore(LocalDate.now())) {
            exibirAlerta("Data Inválida", "A data do agendamento não pode ser anterior ao dia de hoje.");
            return;
        }
        
        String[] partes = intervaloHorarioSelecionado.toString().split(" - ");
        LocalTime inicio = LocalTime.parse(partes[0]);
        LocalTime fim = LocalTime.parse(partes[1]);
        
        LocalDateTime dataEHoraReserva = dataSelecionada.atTime(inicio);
        LocalDateTime horarioAtual = LocalDateTime.now();
        
        if (dataEHoraReserva.isBefore(horarioAtual)){
            exibirAlerta("Horário Inválido", "O horário do agendamento não pode ser anterior ao horário atual.");
            return;
        }
        
        try{
            Database database = new Database("lavanderia.db");
            Agenda agenda = new Agenda(database);
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
            Usuario usuarioLogado = usuarioDao.queryBuilder()
                                            .where()
                                            .eq("matricula", matriculaUsuarioLogado)
                                            .queryForFirst();;
                                            
            agenda.fazerReserva(usuarioLogado, maquinaSelecionada, dataSelecionada, inicio, fim);
                            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lavanderia/view/telaMinhasReservas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Minhas Reservas");
            stage.setScene(scene);
                        
            int matriculaUsuario = matriculaUsuarioLogado;
            MinhasReservasController minhasReservasController = fxmlLoader.getController();
            minhasReservasController.iniciarDados(matriculaUsuario);
                        
            stage.show();
            Stage calendarioStage = (Stage) confirmarReservaButton.getScene().getWindow();
            calendarioStage.close();
        } catch(SQLException e) {
            exibirAlerta("Reserva Duplicada.", "A máquina já está reservada para essa data e horário.");
        } catch(Exception e) {
            System.err.println("Erro ao carregar painel de reservas: " + e.getMessage());
        }
        
        }
    }
