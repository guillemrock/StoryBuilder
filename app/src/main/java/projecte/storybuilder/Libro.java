package projecte.storybuilder;

import java.util.HashMap;
import java.util.Map;

public class Libro {
    private String idInicial;
    private Map<String, Pagina> paginas;

    public Libro() {
        paginas = new HashMap<>();
    }

    public void addPagina(Pagina pagina) {
        paginas.put(pagina.getId(), pagina);
    }

    public Pagina buscaPagina(String id) {
        return paginas.get(id);
    }
}
