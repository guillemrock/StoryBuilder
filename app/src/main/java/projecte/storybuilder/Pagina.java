package projecte.storybuilder;

public class Pagina {
    private int tipo; // 0 - Portada, 1 - Botons, 2 - Texto, 3 - Pregunta, 4 - Final
    private String texto;
    private Boton boton_izq, boton_der;
    private String id, idTarget, resp1, resp2, resp3, respOK;
    private int botonesActivos;
    private int preguntaOK;

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

    public void ActivarBotones(int estado) {this.botonesActivos = estado;}

    public void setTipo(int tipo) { this.tipo = tipo; }

    public void setRespuestas(String r1, String r2, String r3, String rC){
        this.resp1 = r1;
        this.resp2 = r2;
        this.resp3 = r3;
        this.respOK = rC;
    }

    public void setPreguntaOK(int ok) {this.preguntaOK = ok;}

    public int getPreguntaOK() { return preguntaOK;}

    public String getResp1() { return resp1;}

    public String getResp2() { return resp2;}

    public String getResp3() { return resp3;}

    public String getRespOK() { return respOK;}

    public int getTipo() { return this.tipo; }

    public int getBotonesActivos() { return this.botonesActivos;}

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
