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
import lavanderia.View.CalendarioView;

public class CalendarioController
{
    CalendarioView calendarioView;
    Stage stage;
    Agenda agenda; 
    public ComboBox<IntervaloHorario> horarioCombo; 
    public TableView<Reserva> reservaTable;
    public TableColumn<Reserva, String> dataColumn;
    public TableColumn<Reserva, String> horarioColumn;
    public TableColumn<Reserva, String> aparelhoColumn;
    public Button novaReservaButton;
    public Aparelho aparelhoSelecionado;
    
    public CalendarioController()
    {
        this.stage = new Stage();
        this.calendarioView = new CalendarioView();
        this.calendarioView.setController(this);
    }
        
    public void iniciar() throws Exception {
        this.calendarioView.start(this.stage);
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
        
    public void botaoConfirmarDepositoClicado(javafx.event.Event e) {
        
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
