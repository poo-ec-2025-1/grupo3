import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Agenda {
    private Map<Integer, LocalDateTime> reservas;

    public Agenda() {
        this.reservas = new HashMap<>();
    }

    public boolean reservar(Aparelho aparelho, LocalDateTime data) {
        if (aparelho.getAgendavel() && aparelho.getDisponivel() && !estaReservado(aparelho.getId())) {
            reservas.put(aparelho.getId(), data);
            aparelho.setDisponivel(false);
            System.out.println("Reserva realizada para " + aparelho.getModelo() + " (ID: " + aparelho.getId() + ") em " + data);
            return true;
        } else {
            System.out.println("Falha na reserva para " + aparelho.getModelo() + " (ID: " + aparelho.getId() + "). Já reservado ou não agendável.");
            return false;
        }
    }

    public void liberar(Aparelho aparelho) {
        if (estaReservado(aparelho.getId())) {
            reservas.remove(aparelho.getId());
            aparelho.setDisponivel(true);
            System.out.println("Reserva liberada para " + aparelho.getModelo() + " (ID: " + aparelho.getId() + ").");
        } else {
            System.out.println("Nenhuma reserva para liberar em " + aparelho.getModelo() + " (ID: " + aparelho.getId() + ").");
        }
    }

    public boolean estaReservado(int id) {
        return reservas.containsKey(id);
    }

    public LocalDateTime getDataReserva(int id) {
        return reservas.get(id);
    }
}