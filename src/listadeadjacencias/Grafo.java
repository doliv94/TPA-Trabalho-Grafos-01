package listadeadjacencias;

import java.util.ArrayList;

public class Grafo<T> {
    // O grafo eh formado por uma lista de vertices (cidades, nesse caso)
    private ArrayList<Vertice<T>> vertices;

    // Pode criar um grafo vazio e depois acrescentar os vertices dele
    public Grafo() { }

    // Pode criar um grafo com a lista de vertices ja definida
    public Grafo(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    // get e set para acesso a lista de vertices (cidades)
    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }
    public void setVertices(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    // Metodo para adicionar um novo vertice na lista de vertices do grafo
    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> novo = new Vertice<>(valor);
        this.vertices.add(novo);

        return novo;
    }

    // Metodo para procurar/retornar um vertice especifico do grafo
    private Vertice<T> obterVertice(T valor) {
        Vertice<T> v;

        for (Vertice<T> vertex : this.vertices) {
            v = vertex;

            if (v.getValor().equals(valor)) {
                return v;
            }
        }

        // Se chegou aqui eh pq nao existe um vertice com esse valor
        return null;
    }

    // Metodo para adicionar uma aresta com peso a um dos vertices do grafo
    public void adicionarArestaComPeso(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem, verticeDestino;

        // Busco o vertice com o valor de origem
        verticeOrigem = obterVertice(origem);

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(verticeOrigem == null) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Busco o vertice com o valor de destino
        verticeDestino = obterVertice(destino);

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(verticeDestino == null) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta com peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino, peso));
    }

    // Metodo para adicionar uma aresta sem peso a um dos vertices do grafo
    public void adicionarArestaSemPeso(T origem, T destino) {
        Vertice<T> verticeOrigem, verticeDestino;

        // Busco o vertice com o valor de origem
        verticeOrigem = obterVertice(origem);

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(verticeOrigem == null) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Busco o vertice com o valor de destino
        verticeDestino = obterVertice(destino);

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(verticeDestino == null) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta sem peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino));
    }
}
