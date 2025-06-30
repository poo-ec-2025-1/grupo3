package lavanderia.Controller;

import lavanderia.Model.Reserva;
import lavanderia.Model.Agenda;
import lavanderia.Model.HorariosFixos;
import lavanderia.Model.IntervaloHorario;
import lavanderia.Model.Aparelho;
import lavanderia.Model.ReservaRepository;
import lavanderia.Model.Database;
import lavanderia.Model.Usuario;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lavanderia.View.PainelUsuarioView;

public class PainelUsuarioController
{
    PainelUsuarioView painelUsuarioView;
    Stage stage;
    Agenda agenda; 
    public ComboBox<IntervaloHorario> horarioCombo; 
    public TableView<Reserva> reservaTable;
    public TableColumn<Reserva, String> dataColumn;
    public TableColumn<Reserva, String> horarioColumn;
    public TableColumn<Reserva, String> aparelhoColumn;
    public Button novaReservaButton;
    public Aparelho aparelhoSelecionado;
    
    public PainelUsuarioController()
    {
        this.stage = new Stage();
        this.painelUsuarioView = new PainelUsuarioView();
        this.painelUsuarioView.setController(this);
    }
        
    public void iniciar() throws Exception {
        this.painelUsuarioView.start(this.stage);
        this.stage.show();
        configurarInterface();
    }
        
    private void configurarInterface() {
        horarioCombo.setItems(FXCollections.observableArrayList(HorariosFixos.getIntervalos()));
        dataColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataReserva()));
        horarioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getHoraInicio() + " - " + cellData.getValue().getHoraFim()));
        aparelhoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getAparelho().getModelo()));
        atualizarTabela();
    }
       
    private void carregarDadosUsuario(Usuario usuario) {
        usuario.getNomeCompleto();
        usuario.getMatricula();
    }
    
    public void botaoMinhasReservasClicado(javafx.event.Event e) {
        // Fecha a tela atual
        this.stage.close();
        
        // Cria e inicia a nova tela
        MinhasReservasController minhasReservasController = new MinhasReservasController();
        try {
            minhasReservasController.iniciar();
        } catch (Exception ex) {
            System.out.println("Erro ao abrir a nova tela: " + ex.getMessage());
        }
    }
    
    public void botaoAdicionarSaldoClicado(javafx.event.Event e) {
        // Fecha a tela atual
        this.stage.close();
        
        // Cria e inicia a nova tela
        AdicionarSaldoController adicionarSaldoController = new AdicionarSaldoController();
        try {
            adicionarSaldoController.iniciar();
        } catch (Exception ex) {
            System.out.println("Erro ao abrir a nova tela: " + ex.getMessage());
        }
    }
    
    public void botaoEncerrarSessaoClicado(javafx.event.Event e) {
        // Fecha a tela atual
        this.stage.close();
        
        // Cria e inicia a nova tela
        LoginController loginController = new LoginController();
        try {
            loginController.iniciar();
        } catch (Exception ex) {
            System.out.println("Erro ao abrir a nova tela: " + ex.getMessage());
        }
    }
        
    public void botaoAlterarCadastroClicado(javafx.event.Event e) {
        // Fecha a tela atual
        this.stage.close();
        
        // Cria e inicia a nova tela
        CadastroController cadastroController = new CadastroController();
        try {
            cadastroController.iniciar();
        } catch (Exception ex) {
            System.out.println("Erro ao abrir a nova tela: " + ex.getMessage());
        }
    }
    
    public void botaoLimparClicado(javafx.event.Event e) {
        horarioCombo.getSelectionModel().clearSelection();
        atualizarTabela();
    }
        
    private void atualizarTabela() {
        ReservaRepository repo = new ReservaRepository(new Database("lavanderia.db"));
        reservaTable.setItems(FXCollections.observableArrayList(repo.loadAll()));
    }
        
    public void setAparelhoSelecionado(Aparelho aparelho) {
        this.aparelhoSelecionado = aparelho;
    }
}
