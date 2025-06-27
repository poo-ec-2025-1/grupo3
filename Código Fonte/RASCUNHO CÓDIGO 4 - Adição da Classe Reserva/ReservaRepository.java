import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservaRepository {
    private final Database database;
    private final Dao<Reserva, Integer> dao;
    private List<Reserva> loadedReservas;
    private Reserva loadedReserva;

    public ReservaRepository(Database database) {
        this.database = database;
        try {
            this.dao = DaoManager.createDao(database.getConnection(), Reserva.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Reserva.class);
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar ReservaRepository: " + e.getMessage());
            throw new RuntimeException(e);
        }
        this.loadedReservas = new ArrayList<>();
    }

    public Reserva create(Reserva reserva) {
        try {
            // Converter strings para LocalDate e LocalTime para validação
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate reservaData = LocalDate.parse(reserva.getDataReserva(), dateFormatter);
            LocalTime reservaInicio = LocalTime.parse(reserva.getHoraInicio(), timeFormatter);
            LocalTime reservaFim = LocalTime.parse(reserva.getHoraFim(), timeFormatter);

            // Verificar sobreposição de horários para o mesmo aparelho
            List<Reserva> reservasExistentes = dao.queryForEq("aparelho_id", reserva.getAparelho().getId());
            for (Reserva r : reservasExistentes) {
                LocalDate existenteData = LocalDate.parse(r.getDataReserva(), dateFormatter);
                LocalTime existenteInicio = LocalTime.parse(r.getHoraInicio(), timeFormatter);
                LocalTime existenteFim = LocalTime.parse(r.getHoraFim(), timeFormatter);

                if (existenteData.equals(reservaData) &&
                    !existenteFim.isBefore(reservaInicio) &&
                    !existenteInicio.isAfter(reservaFim)) {
                    throw new IllegalStateException("Conflito de horário com reserva existente: " + r.getId());
                }
            }

            int nrows = dao.create(reserva);
            if (nrows == 0) {
                throw new SQLException("Erro: objeto não salvo");
            }
            this.loadedReserva = reserva;
            loadedReservas.add(reserva);
            return reserva;
        } catch (SQLException e) {
            System.err.println("Erro ao criar reserva: " + e.getMessage());
            return null;
        }
    }

    public void update(Reserva reserva) {
        try {
            dao.update(reserva);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a reserva: " + e.getMessage());
        }
    }

    public void delete(Reserva reserva) {
        try {
            dao.delete(reserva);
        } catch (SQLException e) {
            System.err.println("Erro ao deletar a reserva: " + e.getMessage());
        }
    }

    public Reserva loadFromId(int id) {
        try {
            this.loadedReserva = dao.queryForId(id);
            if (this.loadedReserva != null && !loadedReservas.contains(loadedReserva)) {
                loadedReservas.add(loadedReserva);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar reserva por ID: " + e.getMessage());
        }
        return this.loadedReserva;
    }

    public List<Reserva> loadAll() {
        try {
            this.loadedReservas = dao.queryForAll();
            if (!loadedReservas.isEmpty()) {
                this.loadedReserva = loadedReservas.get(0); // Apenas para manter consistência, se necessário
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar todas as reservas: " + e.getMessage());
        }
        return new ArrayList<>(loadedReservas); // Retornar cópia para evitar modificações externas
    }
}