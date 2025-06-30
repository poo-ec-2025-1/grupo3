package lavanderia.Controller;

import lavanderia.Model.Reserva;
import lavanderia.Model.Agenda;
import lavanderia.Model.HorariosFixos;
import lavanderia.Model.IntervaloHorario;
import lavanderia.Model.Aparelho;
import lavanderia.Model.ReservaRepository;
import lavanderia.Model.Database;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lavanderia.View.MinhasReservasView;

public class MinhasReservasController
{
    MinhasReservasView minhasReservasView;
    Stage stage;
    Agenda agenda; 
    public ComboBox<IntervaloHorario> horarioCombo; 
    public TableView<Reserva> reservaTable;
    public TableColumn<Reserva, String> dataColumn;
    public TableColumn<Reserva, String> horarioColumn;
    public TableColumn<Reserva, String> aparelhoColumn;
    public Button novaReservaButton;
    public Aparelho aparelhoSelecionado;
    
    public MinhasReservasController()
    {
        this.stage = new Stage();
        this.minhasReservasView = new MinhasReservasView();
        this.minhasReservasView.setController(this);
    }
        
    public void iniciar() throws Exception {
        this.minhasReservasView.start(this.stage);
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
        
    public void botaoMinhasResevasClicado(javafx.event.Event e) {
        // Fecha a tela atual
        this.stage.close();
        
        // Cria e inicia a nova tela
        CalendarioController calendarioController = new CalendarioController();
        try {
            calendarioController.iniciar();
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
