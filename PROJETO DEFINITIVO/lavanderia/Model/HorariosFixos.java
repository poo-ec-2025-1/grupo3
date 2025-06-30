package lavanderia.Model;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// classe final para evitar que ela seja modificada
public final class HorariosFixos {
    private static final List<IntervaloHorario> intervalosPadrao;

    // o código vai rodar so uma vez quando a classe for instanciada
    // os horarios sao adicionados dessa forma para manter a formatacao correta
    static {
        List<IntervaloHorario> intervalos = new ArrayList<>();
        
        intervalos.add(new IntervaloHorario(LocalTime.of(0, 0), LocalTime.of(2, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(2, 0), LocalTime.of(4, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(4, 0), LocalTime.of(6, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(6, 0), LocalTime.of(8, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(10, 0), LocalTime.of(12, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(12, 0), LocalTime.of(14, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(14, 0), LocalTime.of(16, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(16, 0), LocalTime.of(18, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(18, 0), LocalTime.of(20, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(20, 0), LocalTime.of(22, 0)));
        intervalos.add(new IntervaloHorario(LocalTime.of(22, 0), LocalTime.of(0, 0)));
        
        // comando para evitar que a lista seja modificada
        intervalosPadrao = Collections.unmodifiableList(intervalos);
    }

    public static List<IntervaloHorario> getIntervalos() {
        return intervalosPadrao;
    }
}
