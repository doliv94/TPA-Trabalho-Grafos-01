package listadeadjacencias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Grafo<T> {
    // O grafo eh formado por uma lista de vertices (cidades, nesse caso)
    private ArrayList<Vertice<T>> vertices = new ArrayList<>(); // Inicializa lista

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

    public Vertice<T> adicionarVerticeComPeso(Vertice<T> vertice) {
        this.vertices.add(vertice);

        return vertice;
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

    // Metodo para checar se existe com o valor entrado
    // Esse metodo foi criado por ser feita tal checagem nos dois metodos de adicionarAresta
    private boolean checaVerticeExiste(T valor) {
        // Busco o vertice pelo valor de entrada
        Vertice<T> vertice = obterVertice(valor);

        if (vertice == null) { // Se nao for encontrado, retorna falso
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

    // Metodo para calcular o caminho minimo entre dois vertices
    // (ta feio, professor, desculpa)
    // De forma resumida, segue mais ou menos conforme o slide disponibilizado no ava, tem as listas de distancias e a de predecessores,
    // alem de salvar o ultimo no que foi rotulado (aquele que levar a uma distancia menor)
    // Faz a interacao com o vertice de origem definindo as distancias que existirem com o valor,
    // adicionando a origem como predecessor nesses casos, e os outros vertices ficam com distancia infinita e predecessor nulo
    // Tambem vai montar uma fila com os vertices que foram alcancados, sempre colocando na primeira posicao o que tiver menor valor de distancia
    // E guarda numa lista os vertices que fizerem parte do caminho minimo (tanto essa lista quanto a
    // da fila tiveram algo de inpiracao no codigo de busca em largura da entrega 1)
    // As demais iteracoes depois da origem irao acontecer ate que o vertice que leva a um menor caminho seja o proprio vertice de destino
    public ArrayList<Vertice<T>> calculaCaminhoMinimo(Vertice<T> origem, Vertice<T> destino) {
        int quantidadeVertices = this.vertices.size(); // Pega a quantidade de vertices do grafo
        float distancias[] = new float[quantidadeVertices]; // Cria uma variavel para guardar a distancia minima de cada vertice segundo a posicao dele no grafo

        // Cria uma lista para guardar os vertices predecessores de cada ultimoNoRotulado, seguindo a explicacao do slide disponibilizado no ava
        ArrayList<Vertice<T>> predecessores = new ArrayList<>();
        Vertice<T> ultimoNoRotulado = origem; // Define o ultimoNoRotulado como o vertice de origem

        // Cria uma lista para colocar os vertices que sao destino do vertice atual, e ainda nao foram visitados
        ArrayList<Vertice<T>> fila = new ArrayList<>();
        // Cria uma lista para colocar os vertices que fazem parte do caminho minimo
        ArrayList<Vertice<T>> marcados = new ArrayList<>();

        Vertice<T> verticeP = new Vertice<>(); // Cria um vertice vazio

        float distanciaAtual = Float.POSITIVE_INFINITY; // Define uma variavel distanciaAtual como infinita
        float distanciaPred; // Variavel que vai guardar a distancia anterior entre dois vertices

        // Realiza a primeira iteracao para o vertice de origem
        for (int posicao = 0; posicao < quantidadeVertices; posicao++) {

            // ...Se tem caminho a partir do vertice de origem
            if (origem.getDestinos().get(posicao).getPeso() > 0) {
                distanciaPred = distanciaAtual; // Guarda a ultima distancia a partir do vertice de origem
                distanciaAtual = origem.getDestinos().get(posicao).getPeso(); // Define a distancia atual do vertice de origem para o da posicao atual

                // Guarda a distancia atual ate o vertice na lista das distancias, segundo a posicao dele
                distancias[posicao] = distanciaAtual;
                predecessores.add(posicao, origem); // Adiciona o vertice de origem como atual predecessor do vertice que ele tem caminho

                // Se a distancia entre a origem e o vertice atual for menor do que a distancia da origem pro vertice anterior
                if (distanciaAtual < distanciaPred) {
                    fila.add(0, origem.getDestinos().get(posicao).getDestino()); // Eh menor, coloca ele no comeco da fila
                }
                else if (distanciaAtual == distanciaPred && origem.getDestinos().get(posicao).getDestino() == destino) { // Mas se a opcao eh o vertice destino, ele tem prioridade
                    fila.add(0, origem.getDestinos().get(posicao).getDestino());
                }
                else {
                    fila.add(1, origem.getDestinos().get(posicao).getDestino()); // Nao eh menor, coloca ele no final da fila
                    distanciaAtual = distanciaPred; // Define a distancia atual como a distancia anterior, que eh menor
                }
            }
            // Se nao tem caminho da origem pro vertice atual
            else {
                distancias[posicao] = Float.POSITIVE_INFINITY; // A distancia eh definida como infinita
                predecessores.add(posicao, verticeP); // Define o predecessor do vertice sem caminho como o vertice nulo
            }
        }
        distancias[this.vertices.indexOf(origem)] = 0; // Coloca a distancia do no de origem para ele mesmo como 0

        ultimoNoRotulado = fila.get(0); // O ultimo no rotulado eh o primeiro da fila, que teve o menor caminho
        marcados.add(origem); // Adiciona o vertice de origem na lista dos marcados
        marcados.add(ultimoNoRotulado); // E adiciona o ultimo no rotulado na lista dos marcados tambem, porque agora estamos nele

        // Proximas iteracoes, agora que a base foi feita pelo vertice de origem, seguimos para os outros
        // ...Enquanto o ultimo no rotulado nao for o no de destino
        while (ultimoNoRotulado != destino) {
            // Sempre que partirmos de um vertice novo para comparacao, a distancia atual eh definida como infinita
            distanciaAtual = Float.POSITIVE_INFINITY;

            // Vai passar por todos os destinos do vertice marcado atualmente
            for (int posicao = 0; posicao < quantidadeVertices; posicao++) {

                // Se houver caminho e o vertice nao tiver sido marcado ainda
                if (ultimoNoRotulado.getDestinos().get(posicao).getPeso() > 0 && !marcados.contains(ultimoNoRotulado.getDestinos().get(posicao).getDestino())) {

                    distanciaPred = distanciaAtual; // Como visto la em cima, distanciaPred garda a distancia anterior

                    // Distancia atual soma a distancia que ja foi percorrida pelo ultimoNoRotulado com a distancia dele para o vertice atual
                    distanciaAtual = distancias[this.vertices.indexOf(ultimoNoRotulado)] + ultimoNoRotulado.getDestinos().get(posicao).getPeso();

                    float distAntiga = distancias[posicao]; // Pega o valor da distancia que ja tinha sido guardado para o vertice da posicao atual

                    // ...Se a distancia atual para o vertice der um valor menor do que o valor que ele ja tinha guardado
                    if (distanciaAtual < distAntiga) {
                        distancias[posicao] = distanciaAtual; // Substitui a distancia dele pelo menor valor de agora
                        predecessores.set(posicao, ultimoNoRotulado); // Atualiza o predecessor dele
                    }

                    // ...Se a distancia da iteracao atual for menor que a da anterior
                    if (distanciaAtual < distanciaPred) {
                        fila.add(0, ultimoNoRotulado.getDestinos().get(posicao).getDestino()); // Poe o vertice atual no inicio da fila
                    }
                    else if (distanciaAtual == distanciaPred && ultimoNoRotulado.getDestinos().get(posicao).getDestino() == destino) { // Mas se a opcao eh o vertice destino, ele tem prioridade
                        fila.add(0, ultimoNoRotulado.getDestinos().get(posicao).getDestino());
                    }
                    else {
                        fila.add(1, ultimoNoRotulado.getDestinos().get(posicao).getDestino()); // Se nao, poe o vertice depois na fila
                        distanciaAtual = distanciaPred; // E modifica a distancia atual para o valor da anterior que era menor
                    }
                }
            }
            // Quando acaba de passar pelos destinos possiveis do vertice, remove o ultimoNoRotulado da fila
            fila.remove(ultimoNoRotulado);
            // E marca como ultimo no rotulado o primeiro da fila atualizada
            ultimoNoRotulado = fila.get(0);

            // E se ele nao estiver na lista de vertices que ja foram marcados, adiciona
            if (!marcados.contains(ultimoNoRotulado)) {
                marcados.add(ultimoNoRotulado);
            }
        }
        predecessores.set(0, origem); // No final de tudo, coloca o vertice de origem como predecessor dele mesmo

        // Retorna a lista com todos os vertices que resultaram no menor caminho da origem ate o destino
        return marcados;
    }

    private boolean checaCiclo(Grafo<T> grafo, T origem, T destino){

        ArrayList<Vertice<T>> fila = new ArrayList<>();
        Vertice<T> checa = grafo.obterVertice(origem);
        fila.add(checa);

        for (int i = 0; i < fila.size(); i++) {
            checa = grafo.obterVertice(fila.get(i).getValor());

            for (int j = 0; j < checa.getDestinos().size(); j++) {
                if (checa.getDestinos().get(j).getDestino().getValor() == destino) {
                    return true;
                }
                else {
                    if (!fila.contains(checa.getDestinos().get(j).getDestino())) {
                        fila.add(checa.getDestinos().get(j).getDestino());
                    }
                }
            }
        }

        return false;
    }

    // Implementado o Algoritmo de Kruskal
    // Metodo para construcao da arvore geradora minima
    public Grafo<T> calculaArvoreGeradoraMinima (Grafo<T> grafo) {
        Grafo<T> grafoAGM = new Grafo<>();

        ArrayList<Aresta<T>> destinosOrdenados = new ArrayList<>();

        ArrayList<Aresta<T>> novaListaDestino = new ArrayList<>();
        Aresta<T> novoDestino = new Aresta<>();

        for (int i = 0; i < grafo.getVertices().size(); i++) {
            for (int j = 0; j < grafo.getVertices().get(i).getDestinos().size(); j++) {
                Aresta<T> nova = new Aresta<>();

                if (grafo.getVertices().get(i).getDestinos().get(j).getPeso() > 0) {
                    nova.setOrigem(grafo.getVertices().get(i));
                    nova.setDestino(grafo.getVertices().get(i).getDestinos().get(j).getDestino());
                    nova.setPeso(grafo.getVertices().get(i).getDestinos().get(j).getPeso());

                    destinosOrdenados.add(nova);
                }
            }
        }

        if (destinosOrdenados.size() > 0) {
            destinosOrdenados.sort(Comparator.comparing(Aresta::getPeso));

            // Adiciona primeira aresta
            grafoAGM.adicionarArestaComPeso(destinosOrdenados.get(0).getOrigem().getValor(), destinosOrdenados.get(0).getDestino().getValor(), destinosOrdenados.get(0).getPeso());

            novoDestino.setDestino(destinosOrdenados.get(0).getOrigem());
            novoDestino.setPeso(destinosOrdenados.get(0).getPeso());
            novaListaDestino.add(novoDestino);

            grafoAGM.obterVertice(destinosOrdenados.get(0).getDestino().getValor()).setDestinos(novaListaDestino);

            // Adiciona segunda aresta
            grafoAGM.adicionarArestaComPeso(destinosOrdenados.get(1).getOrigem().getValor(), destinosOrdenados.get(1).getDestino().getValor(), destinosOrdenados.get(1).getPeso());

            novoDestino = new Aresta<>();
            novaListaDestino = new ArrayList<>();

            novoDestino.setDestino(destinosOrdenados.get(1).getOrigem());
            novoDestino.setPeso(destinosOrdenados.get(1).getPeso());
            novaListaDestino.add(novoDestino);

            grafoAGM.obterVertice(destinosOrdenados.get(1).getDestino().getValor()).setDestinos(novaListaDestino);

            for (int i = 2; i < destinosOrdenados.size(); i++) {

                // sem origem com destino
                if (!grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    novoDestino = new Aresta<>();

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem());
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);

                    grafoAGM.adicionarVertice(destinosOrdenados.get(i).getOrigem().getValor());

                    novoDestino = new Aresta<>();

                    novoDestino.setDestino(destinosOrdenados.get(i).getDestino());
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                    grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);
                }
                // com origem sem destino
                else if (grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && !grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    novoDestino = new Aresta<>();

                    novoDestino.setDestino(destinosOrdenados.get(i).getDestino());
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                    grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);

                    grafoAGM.adicionarVertice(destinosOrdenados.get(i).getDestino().getValor());

                    novoDestino = new Aresta<>();

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem());
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);
                }
                // sem origem sem destino
                else if (!grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && !grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    grafoAGM.adicionarArestaComPeso(destinosOrdenados.get(i).getOrigem().getValor(), destinosOrdenados.get(i).getDestino().getValor(), destinosOrdenados.get(i).getPeso());

                    novaListaDestino = new ArrayList<>();
                    novoDestino = new Aresta<>();

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem());
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso());
                    novaListaDestino.add(novoDestino);

                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).setDestinos(novaListaDestino);
                }
                // com origem com destino
                else {
                    if (!checaCiclo(grafoAGM, grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).getValor(), grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).getValor())) {
                        novoDestino = new Aresta<>();

                        novoDestino.setDestino(destinosOrdenados.get(i).getDestino());
                        novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                        grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);

                        novoDestino = new Aresta<>();

                        novoDestino.setDestino(destinosOrdenados.get(i).getOrigem());
                        novoDestino.setPeso(destinosOrdenados.get(i).getPeso());

                        grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);
                    }
                }
            }
        }

        return grafoAGM;
    }
}
