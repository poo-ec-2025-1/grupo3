package lavanderia.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class MetodoDePagamento {
    private static final List<String> listaMetodos;
    
    static {
        List<String> metodos = new ArrayList<>();
        metodos.add("Cartão de Débito");
        metodos.add("Cartão de Crédito");
        metodos.add("Pix");
        
        listaMetodos = Collections.unmodifiableList(metodos);
    }
}
