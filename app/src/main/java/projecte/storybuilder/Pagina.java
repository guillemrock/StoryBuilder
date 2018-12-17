package projecte.storybuilder;

public class Pagina {
    private int tipus; // 0 - Portada, 1 - Botons, 2 - Texto, 3 - Pregunta, 4 - Final
    private String texto;
    private Boton boton_izq, boton_der;
    private String id, idTarget;


    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setBoton_izq(Boton boton_izq) {
        this.boton_izq = boton_izq;
    }

    public void setBoton_der(Boton boton_der) {
        this.boton_der = boton_der;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    public String getTexto() {
        return texto;
    }

    public Boton getBoton_izq() {
        return boton_izq;
    }

    public Boton getBoton_der() {
        return boton_der;
    }
}
