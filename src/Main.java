// TPA - 1a Etapa do Trabalho Prático de Grafos - prof. Victorio Carvalho
// Ifes Serra 2022/2
// Dupla: Brunna Dalzini e Rafaela Amorim Pessim

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import geradorarquivos.*;
import listadeadjacencias.*;


public class Main {

    // Metodo para imprimir a ordem em que as cidades foram visitadas quando foi realizada a busca em largura
    private static void imprimeBusca(ArrayList<Vertice<String[]>> marcados) {
        System.out.println("\n---");
        System.out.println("\nOrdem do Caminhamento:");

        for (Vertice<String[]> cidadeVisitada: marcados) {
            System.out.print(Arrays.deepToString(cidadeVisitada.getValor()) + " ");
        }
        System.out.print("\n");
    }

    // Metodo de busca em largura
    public static void buscaEmLargura(Vertice<String[]> cidadeOrigem) {
        ArrayList<Vertice<String[]>> marcados = new ArrayList<>(); // Cria uma lista de vertices que ja foram visitados
        ArrayList<Vertice<String[]>> fila = new ArrayList<>(); // Cria uma fila dos vertices que poderao ser visitados se nao tiverem sido marcados ainda

        // Coloca o vertice da cidade de origem como ponto de partida e adiciona na fila
        Vertice<String[]> atual = cidadeOrigem;
        fila.add(atual);

        // Enquanto houver vertice na fila...
        while (fila.size() > 0) {

            // Pego o proximo da fila, marco como visitado e o imprimo
            atual = fila.get(0);
            fila.remove(0); // E ja tira esse vertice visitado da fila
            marcados.add(atual); // Adiciona o vertice visitado na lista de marcados

            // Cria uma lista com os destinos da cidade de origem
            // Lembrando que a lista foi montada conforme o arquivo de entrada, entao todas as cidades estao inclusas,
            // sendo as nao adjacentes com peso 0
            ArrayList<Aresta<String[]>> destinos = atual.getDestinos();

            // Para cada aresta de destino da lista de destinos...
            for (Aresta<String[]> destino: destinos) {
                Vertice<String[]> proximo = destino.getDestino(); // Pego o vertice da cidade destino

                // Se a cidade nao estiver na lista de vertices que ja foram marcados
                // e nem tiver um peso maior que 0 (o que significa que tem caminho da
                // cidade atual para aquela cidade destino)
                if (!marcados.contains(proximo) && destino.getPeso() > 0) {

                    // Se a cidade tambem nao estiver na fila
                    if(!fila.contains(proximo)) {
                        fila.add(proximo); // Adiciona na fila

                        System.out.println("\nCidade de Origem: " + Arrays.deepToString(marcados.get(marcados.size() - 1).getValor()));
                        System.out.println("Cidade de Destino: " + Arrays.deepToString(proximo.getValor()));
                    }
                }
            }
        }

        imprimeBusca(marcados); // Imprime a ordem em que as cidades foram visitadas
    }

    // Metodo para obter caminhos, vai passar o vertice da cidade de origem
    // para o metodo de busca em largura com base no grafo e na entrada do usuario
    public static void obterCaminhos(Grafo<String[]> grafo, int entrada) {
        Vertice<String[]> cidadeOrigem = grafo.getVertices().get(entrada - 1); // Pega no grafo o vertice da cidade de origem de acordo com a entrada do usuario
        System.out.println("\nCidade Entrada: " + Arrays.deepToString(cidadeOrigem.getValor()));

        // Chama o metodo que vai fazer a busca em largura para encontrar os caminhos a partir do vertice da cidade de origem
        buscaEmLargura(cidadeOrigem);
    }

    // Metodo para obter cidades vizinhas, vai chamar a impressao apenas das cidades adjacentes a
    // cidade que o usuario escolheu como origem
    public static void obterCidadesVizinhas(Grafo<String[]> grafo, int entrada) {
        Vertice<String[]> cidadeOrigem = grafo.getVertices().get(entrada - 1); // Pega no grafo o vertice da cidade de origem de acordo com a entrada do usuario
        System.out.println("\nCidade Entrada: " + Arrays.deepToString(cidadeOrigem.getValor()) + "\n");

        // Chama o metodo de impressao dos destinos, passando o vertice de origem como parametro
        imprimeDestinos(cidadeOrigem, false); // O false eh para dizer que nao vai imprimir todas as cidades como destino da cidade de origem, apenas as que sao adjacentes
    }

    // Metodo para impressao das cidades que sao destino
    // Se o booleano todas for true, vai retornar a relacao completa de cidades igual o arquivo de entrada,
    // com as cidades que tem caminho e as que nao tem (peso 0) a partir da origem
    // Se o booleano todas for false, vai retornas apenas as cidades adjacentes a cidade origem
    private static void imprimeDestinos(Vertice<String[]> cidade, boolean todas) {
        System.out.print("____Destinos: \n");

        for(int contador = 0; contador < cidade.getDestinos().size(); contador++) {
            if(todas || cidade.getDestinos().get(contador).getPeso() > 0) {
                System.out.print("    Cidade de Destino: ");
                System.out.println(Arrays.deepToString(cidade.getDestinos().get(contador).getDestino().getValor()));
                System.out.print("      Distância: ");
                System.out.println(cidade.getDestinos().get(contador).getPeso());
            }
        }
    }

    // Imprime as cidades (codigo e nome)
    private static void imprimeCidades(Grafo<String[]> grafo) {
        System.out.println("\nGrafo - Relação das Distâncias entre as Cidades");

        // Pega cada cidade da lista de cidades do grafo e imprime o valor (codigo e nome)
        for(Vertice<String[]> cidade: grafo.getVertices()) {
            System.out.print("\n__Cidade Origem: ");
            System.out.println(Arrays.deepToString(cidade.getValor()));

            imprimeDestinos(cidade, true);
        }
    }

    // Metodo para adicionar os valores do grafo
    private static Grafo<String[]> montaGrafo(ArrayList<Vertice<String[]>> cidades, ArrayList<String[]> pesos) {
        Grafo<String[]> grafo = new Grafo<>();

        // Para cada cidade... (usando int para pegar dado pela posicao das cidades)
        for(int cidade = 0; cidade < cidades.size(); cidade++) {
            ArrayList<Aresta<String[]>> cidadesDestino = new ArrayList<>(); // Cria uma lista de arestas que vai conter as cidades de destino de cada cidade

            // Para cada destino dessa cidade...
            for(int contador = 0; contador < pesos.size(); contador++) {
                Aresta<String[]> cidadeDestino = new Aresta<>(); // Cria uma nova aresta para armazenar as informacoes de cada cidade destino

                cidadeDestino.setDestino(cidades.get(contador)); // Define como destino da cidade destino a cidade de acordo com o contador do loop

                // Define o peso da cidade destino segundo a posicao na lista de pesos da cidade
                cidadeDestino.setPeso(Float.parseFloat(pesos.get(cidade)[contador].replace(',', '.'))); // Usa o replace para substituir a , do registro por . para depois ser entendido como numero

                cidadesDestino.add(cidadeDestino); // Adiciona cada cidade destino na lista das cidades destino
            }
            cidades.get(cidade).setDestinos(cidadesDestino); // Define os destinos de cada cidade como a lista de cidades destino que foi montada
        }

        // Adiciona a lista de cidades agora completa com os destinos no grafo
        grafo.setVertices(cidades);

        return grafo;
    }

    // Metodo para abrir/ler o arquivo que foi gerado com as cidades e distancias
    private static Grafo<String[]> abreArquivo(String nomeArquivoEntrada) throws IOException {
        String caminhoEntrada = "_entrada_registros/"; // Define o nome da pasta onde ficarao os arquivos de entrada
        String entrada = caminhoEntrada + nomeArquivoEntrada; // Define o caminho para o arquivo de entrada que sera lido

        FileReader arquivo = new FileReader(entrada);
        BufferedReader leitor = new BufferedReader(arquivo);

        // Primeira linha do arquivo
        String linha = leitor.readLine();
        int qtdRegistros = Integer.parseInt(linha); // Pega a primeira linha do arquivo que eh a quantidade de registros que ele tem

        ArrayList<Vertice<String[]>> cidades = new ArrayList<>(); // Cria uma lista de vertices para armazenar as cidades do arquivo
        ArrayList<String[]> pesos = new ArrayList<>(); // Cria uma lista de pesos para armazenar as distancias do arquivo

        // Passa por todas as linhas do arquivo, que primeiro tem a qtdRegistros do total de cidades
        // e depois tem a mesma qtdRegistros das distancias de cada uma (por isso o qtdRegistros * 2)
        for(int contador = 0; contador < qtdRegistros * 2; contador++) {
            Vertice<String[]> cidade = new Vertice<>(); // Cria um novo vertice para armazenar as informacoes de cada cidade que for sendo lida

            linha = leitor.readLine();
            String[] conteudo = linha.split(";"); // Separa os registro de cada linha do arquivo onde houver ;

            // Adiciona as primeiras linhas do arquivo que sao das cidades na lista
            if(contador < qtdRegistros) {

                cidade.setValor(conteudo); // Coloca o codigo e o nome da cidade no vertice
                cidades.add(cidade); // Adiciona o vertice da cidade na lista dos vertices de cidades
            }
            // Depois adiciona o resto das linhas que sao as distancias em outra lista
            else {
                pesos.add(conteudo);
            }
        }

        arquivo.close();

        // Chama o metodo que vai montar o grafo com os valores que foram pegos do arquivo
        return montaGrafo(cidades, pesos);
    }

    // Metodo para garantir que o codigo entrado pelo usuario eh um valor valido para leitura do grafo
    private static int checaEntrada (Grafo<String[]> grafo, Scanner input) {
        System.out.println("\nDigite o código da cidade de origem (1 a " + grafo.getVertices().size() + "):");
        System.out.print("-> ");

        int entradaOk = -1;

        // Se o valor de entrada for um numero
        if (input.hasNext("\\d+")) {
            entradaOk = input.nextInt(); // Nao eh maior, pode chamar o metodo escolhido

            if (entradaOk > grafo.getVertices().size()) { // Se o numero for maior que a quantidade de cidades
                System.out.println("\nA cidade com esse código não consta no arquivo.");
                entradaOk = -1;
            }
        }
        else {
            System.out.println("\nO código deve ser um número entre 1 e " + grafo.getVertices().size() + ".");
        }

        return entradaOk; // Retorna o codigo do usuario se estiver ok, e -1 se for um codigo invalido
    }

    // Menu de opcoes do usuario
    private static void mostraMenu(Grafo<String[]> grafo) {
        Scanner input = new Scanner(System.in); // Cria o leitor da entrada do usuario
        String opcao = ""; // Variavel que vai guiar as opcoes do menu

        int entrada; // Vai receber a entrada do usuario de codigo da cidade

        // O menu com as opcoes vai rodar enquanto o usuario nao escolher umas das 3 opcoes disponiveis
        while (!opcao.equals("3")) {
            System.out.println("\n*******************************************\n");
            System.out.println("     ESCOLHA A OPÇÃO");
            System.out.println("\n --- 1: OBTER TODAS AS CIDADES VIZINHAS");
            System.out.println(" --- 2: OBTER TODOS OS CAMINHOS DA CIDADE");
            System.out.println("\n --- 3: SAIR");
            System.out.println("\n*******************************************\n");
            System.out.print("-> ");
            opcao = input.next();

            switch (opcao) {
                case "1" -> {
                    System.out.println("\n\n******OBTER TODAS AS CIDADES VIZINHAS******");

                    // Verifica se o valor digitado eh uma entrada valida
                    entrada = checaEntrada(grafo, input);
                    if(entrada != -1) {
                        System.out.println(entrada);
                        obterCidadesVizinhas(grafo, entrada);
                    }

                    input = new Scanner(System.in);
                    System.out.print("\nAperte enter para voltar");
                    input.nextLine();
                }
                case "2" -> {
                    System.out.println("\n\n*****OBTER TODOS OS CAMINHOS DA CIDADE*****");

                    // Verifica se o valor digitado eh uma entrada valida
                    entrada = checaEntrada(grafo, input);
                    if(entrada != -1) {
                        obterCaminhos(grafo, entrada);
                    }

                    input = new Scanner(System.in);
                    System.out.print("\nAperte enter para voltar");
                    input.nextLine();
                }
                case "3" -> System.out.println("\nTchauzim!"); // Encerra o programa
            }
        }
    }

    // Main
    public static void main(String[] args) throws IOException {
        Scanner voltar = new Scanner(System.in); // Um leitor que ira esperar o enter do usuario para mostrar o menu

        // Instancia um grafo vazio para receber as informacoes do arquivo de entrada
        Grafo<String[]> grafo;

        GeradorArquivosGrafo g = new GeradorArquivosGrafo(); // Instancia um gerador de arquivos
        String nomeArquivoEntrada; // Cria variavel que vai ter o nome do arquivo gerado
        int quantCidades = 10; // Variavel para definir a quantidade de cidade que serao criadas no arquivo

        // Gera um arquivo com a quantidade definida de cidades
        // Passa o nome do arquivo gerado para a variavel
        nomeArquivoEntrada = g.geraArquivo(quantCidades);

        // Le o arquivo gerado
        grafo = abreArquivo(nomeArquivoEntrada); // O grafo vai receber os valores do arquivo a partir desse metodo

        // Imprime as cidades que foram colocadas no grafo conforme o arquivo de entrada
        imprimeCidades(grafo);

        System.out.print("\nAperte enter para ir para o menu de opções");
        voltar.nextLine();

        // Chama o menu de opcoes para o usuario
        mostraMenu(grafo);
    }
}