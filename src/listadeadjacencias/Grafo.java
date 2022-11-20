package listadeadjacencias;

import java.util.ArrayList;
import java.util.Arrays;

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

    private boolean checaVerticeExiste(T valor) {
        // Busco o vertice pelo valor de entrada
        Vertice<T> vertice = obterVertice(valor);

        if (vertice == null) {
            return false;
        }

        return true;
    }

    // Metodo para adicionar uma aresta com peso a um dos vertices do grafo
    public void adicionarArestaComPeso(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem = new Vertice<>(), verticeDestino = new Vertice<>();

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(!checaVerticeExiste(origem)) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(!checaVerticeExiste(destino)) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta com peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino, peso));
    }

    // Metodo para adicionar uma aresta sem peso a um dos vertices do grafo
    public void adicionarArestaSemPeso(T origem, T destino) {
        Vertice<T> verticeOrigem = new Vertice<>(), verticeDestino = new Vertice<>();

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(!checaVerticeExiste(origem)) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(!checaVerticeExiste(destino)) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta sem peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino));
    }

    public ArrayList<Vertice<T>> calculaCaminhoMinimo(Vertice<T> origem, Vertice<T> destino) {
        int quantidadeVertices = this.vertices.size();
        float distancias[] = new float[quantidadeVertices];

        ArrayList<Vertice<T>> predecessores = new ArrayList<>();
        Vertice<T> ultimoNoRotulado = origem;

        ArrayList<Vertice<T>> fila = new ArrayList<>();
        ArrayList<Vertice<T>> marcados = new ArrayList<>();

        float distanciaAtual = Float.POSITIVE_INFINITY;

        // Distancias infinitas para os que nao tiverem caminho a partir do vertice de origem
        for (int posicao = 0; posicao < quantidadeVertices; posicao++) {
            Vertice<T> verticeP = new Vertice<>();

            if (origem.getDestinos().get(posicao).getPeso() > 0) {
                float distanciaPred = distanciaAtual;
                distanciaAtual = origem.getDestinos().get(posicao).getPeso();

                distancias[posicao] = origem.getDestinos().get(posicao).getPeso();
                predecessores.add(posicao, origem);

                ultimoNoRotulado = origem.getDestinos().get(posicao).getDestino();
                if (distanciaAtual < distanciaPred) {
                    fila.add(0, ultimoNoRotulado);
                } else {
                    fila.add(1, ultimoNoRotulado);
                    distanciaAtual = distanciaPred;
                }
            } else {
                distancias[posicao] = Float.POSITIVE_INFINITY;
                verticeP.setValor((T) "null");
                predecessores.add(posicao, verticeP);
            }
        }
        distancias[this.vertices.indexOf(origem)] = 0; // menor distancia do no de origem eh 0


        for (int i = 0; i < fila.size(); i++) {
            System.out.println(Arrays.deepToString((Object[]) fila.get(i).getValor()));
        }

        ultimoNoRotulado = fila.get(0);
        marcados.add(origem);
        marcados.add(ultimoNoRotulado);

        // Proximas iteracoes
        while (ultimoNoRotulado != destino) {
            System.out.println("-- " + Arrays.deepToString((Object[]) ultimoNoRotulado.getValor()));
            distanciaAtual = Float.POSITIVE_INFINITY;
            for (int posicao = 0; posicao < quantidadeVertices; posicao++) {
                if (ultimoNoRotulado.getDestinos().get(posicao).getPeso() > 0 && !marcados.contains(ultimoNoRotulado.getDestinos().get(posicao).getDestino())) {
                    float distanciaPred = distanciaAtual;
                    distanciaAtual = distancias[this.vertices.indexOf(ultimoNoRotulado)] + ultimoNoRotulado.getDestinos().get(posicao).getPeso();
                    float distAntiga = distancias[posicao];

                    // System.out.println(distanciaAtual + ", " + distanciaPred);

                    if (distanciaAtual < distAntiga) {
                        distancias[posicao] = distanciaAtual;
                        predecessores.set(posicao, ultimoNoRotulado);

                        // System.out.println(distanciaAtual + ", " + distAntiga);
                        // System.out.println("- " + Arrays.deepToString((Object[]) predecessores.get(posicao).getValor()));
                    }

                    if (distanciaAtual < distanciaPred) {
                        fila.add(0, ultimoNoRotulado.getDestinos().get(posicao).getDestino());
                        // System.out.println("- " + Arrays.deepToString((Object[]) ultimoNoRotulado.getDestinos().get(posicao).getDestino().getValor()));
                    } else {
                        fila.add(1, ultimoNoRotulado.getDestinos().get(posicao).getDestino());
                        distanciaAtual = distanciaPred;
                    }

                    // System.out.println(Arrays.deepToString((Object[]) ultimoNoRotulado.getDestinos().get(posicao).getDestino().getValor()));
                }
            }
            fila.remove(ultimoNoRotulado);
            ultimoNoRotulado = fila.get(0);
            if (!marcados.contains(ultimoNoRotulado)) {
                marcados.add(ultimoNoRotulado);
            }
        }
        predecessores.set(0, origem);


        return marcados;
    }
}