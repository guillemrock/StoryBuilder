package projecte.storybuilder;

import java.util.List;

public class Libro {
    private String Title;
    private List<String> Texto_con_botones, Texto_sin_botones, btn_elegir_izq, btn_elegir_der, Preguntas, btn_respuesta_1, btn_respuesta_2;

    public String getTitle() {
        return Title;
    }

    public List<String> getTexto_con_botones() {
        return Texto_con_botones;
    }

    public List<String> getTexto_sin_botones() {
        return Texto_sin_botones;
    }

    public List<String> getBtn_elegir_izq() {
        return btn_elegir_izq;
    }

    public List<String> getBtn_elegir_der() {
        return btn_elegir_der;
    }

    public List<String> getPreguntas() {
        return Preguntas;
    }

    public List<String> getBtn_respuesta_1() {
        return btn_respuesta_1;
    }

    public List<String> getBtn_respuesta_2() {
        return btn_respuesta_2;
    }
}
