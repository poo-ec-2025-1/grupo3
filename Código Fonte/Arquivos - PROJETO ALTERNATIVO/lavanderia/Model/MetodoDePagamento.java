package lavanderia.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MetodoDePagamento {
    private static List<String> listaMetodos;
    
    static {
        List<String> metodos = new ArrayList<>();
        metodos.add("Cartão de Débito");
        metodos.add("Cartão de Crédito");
        metodos.add("Pix");
        
        listaMetodos = Collections.unmodifiableList(metodos);
    }
    
    public List<String> getMetodoDePagamento() {
        return this.listaMetodos;
    }
}
