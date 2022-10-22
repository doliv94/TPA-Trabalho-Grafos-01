package matrizdeadjacencias;

import java.util.ArrayList;

public class GrafoMA<T> {

    private ArrayList<VerticeMA<T>> vertices;
    private float arestas[][];
    int quantVertices;

    public GrafoMA(int quantVertices) {
        this.vertices = new ArrayList<VerticeMA<T>>();
        this.arestas = new float[quantVertices][quantVertices];
        this.quantVertices = quantVertices;
    }

    public VerticeMA<T> adicionarVertice(T valor) {
        VerticeMA<T> novo = new VerticeMA<T>(valor);
        this.vertices.add(novo);

        return novo;
    }

    private int obterIndiceVertice(T valor) {
        VerticeMA v;

        for(int i = 0; i < this.vertices.size(); i++) {
            v = this.vertices.get(i);

            if(v.getValor().equals(valor)) {
                return i;
            }
        }

        // Se chegou aqui eh pq nao existe um vertice com esse valor
        return -1;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        VerticeMA verticeOrigem, verticeDestino;
        ArestaMA novaAresta;

        // Busco o indice do vertice com o valor de origem
        int indiceOrigem = obterIndiceVertice(origem);

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(indiceOrigem == -1) {
            verticeOrigem = adicionarVertice(origem);
            indiceOrigem = this.vertices.indexOf(verticeOrigem);
        }

        // Busco o indice do vertice com o valor de destino
        int indiceDestino = obterIndiceVertice(destino);

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(indiceDestino == -1) {
            verticeDestino = adicionarVertice(destino);
            indiceDestino = this.vertices.indexOf(verticeDestino);
        }

        this.arestas[indiceOrigem][indiceDestino] = peso;
    }
}
