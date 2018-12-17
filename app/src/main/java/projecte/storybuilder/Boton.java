package projecte.storybuilder;

class Boton {
    private String texto, idTarget;

    public Boton(String texto, String idTarget) {
        this.texto = texto;
        this.idTarget = idTarget;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }
}
