import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o programa...");

        // Instanciar o Database
        Database database = new Database("lavanderia.db");
        System.out.println("Database instanciado.");

        // Criar repositórios
        UsuarioRepository usuarioRepository = new UsuarioRepository(database);
        AparelhoRepository aparelhoRepository = new AparelhoRepository(database);
        ReservaRepository reservaRepository = new ReservaRepository(database);

        // Criar usuários
        Usuario cliente = new Cliente("João Silva", 12345, "senha123");
        usuarioRepository.create(cliente);

        // Criar aparelhos
        Aparelho lavadora = new MaquinaDeLavar(1, "Lavadora Eco", "Lavadora de 8kg", true, 8);
        aparelhoRepository.create(lavadora);

        // Criar reserva
        LocalDate data = LocalDate.now();
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fim = LocalTime.of(10, 0);
        Reserva reserva = new Reserva(LocalDate.now(), LocalTime.of(8,0), LocalTime.of(10, 0), lavadora);
        reservaRepository.create(reserva);
        System.out.println("Reserva criada: " + reserva.getId());

        // Carregar e exibir reservas
        List<Reserva> reservas = reservaRepository.loadAll();
        System.out.println("Reservas no banco: " + reservas.size());
        for (Reserva r : reservas) {
            System.out.println("ID: " + r.getId() + ", Data: " + r.getDataReserva() + 
                               ", " + r.getHoraInicio() + " - " + r.getHoraFim() + 
                               ", Aparelho: " + r.getAparelho().getModelo() + 
                               ", Status: " + r.getStatus());
        }

        // Fechar a conexão
        Caixa.closeConnection();
        System.out.println("Teste concluído. Verifique o banco de dados.");
    }
}