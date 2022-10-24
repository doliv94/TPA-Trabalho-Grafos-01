package listadearestas;

public class VerticeAR<T> {

    private T valor;

    public VerticeAR() {}

    public VerticeAR(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }
}
