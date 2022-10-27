package listadeadjacencias;

import java.util.ArrayList;
import java.util.Arrays;

public class Vertice<T> {
    private T valor;
    // Estrategia de Armazenamento - Lista de Adjacencias
    private ArrayList<Aresta<T>> destinos;

    public Vertice(){ }

    public Vertice(T valor){
        this.valor = valor;
    }

    public Vertice(T valor, ArrayList<Aresta<T>> destinos){
        this.valor = valor;
        this.destinos = destinos;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public ArrayList<Aresta<T>> getDestinos() {
        return destinos;
    }

    public void setDestinos(ArrayList<Aresta<T>> destinos) {
        this.destinos = destinos;
    }

    public void adicionarDestino(Aresta<T> aresta) {
        this.destinos.add(aresta);
    }
}
