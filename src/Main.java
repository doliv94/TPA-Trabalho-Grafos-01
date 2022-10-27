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
    private Grafo grafo;
    private ArrayList<Vertice> vertices;

    // Metodo do lista de adjacencias
    public void buscaEmLargura() {
        ArrayList<Vertice> marcados = new ArrayList<Vertice>();
        ArrayList<Vertice> fila = new ArrayList<Vertice>();

        // Pego o primeiro vertice como ponto de partida e o coloco na fila
        // Poderia escolher qualquer outro...
        // Mas note que dependendo do grafo pode ser que vc nao caminhe por todos os vertices

        Vertice atual = this.vertices.get(0);
        fila.add(atual);

        // Enquanto houver vertice na fila...
        while (fila.size() > 0) {
            // Pego o proximo da fila, marco como visitado e o imprimo
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);

            System.out.println(atual.getValor());

            // Depois pego a lista de adjacencia do no e se o no adjacente ainda
            // nao tiver sido visitado, o coloco na fila
            ArrayList<Aresta> destinos = atual.getDestinos();
            Vertice proximo;

            for(int i = 0; i < destinos.size(); i++) {
                proximo = destinos.get(i).getDestino();

                if(!marcados.contains(proximo)) {
                    fila.add(proximo);
                }
            }
        }
    }

    //
    public static void salvaArquivo(String arquivo, ArrayList<String[]> arquivoRegistros) throws IOException {
        String caminhoPasta = "_saida_registros/";
        String saidaRegistro = "_" + arquivo;

        File pastaSaida = new File(caminhoPasta);
        if (!pastaSaida.exists()){
            pastaSaida.mkdir();
        }

        FileWriter novoRegistro = new FileWriter(new File(pastaSaida, saidaRegistro));

        for(String[] linha: arquivoRegistros) {
            String conteudo = "";

            for(int dado = 0; dado < linha.length; dado++) {
                StringBuilder sb = new StringBuilder();
                if(dado == linha.length - 1) {
                    conteudo = sb.append(conteudo).append(linha[dado]).toString();
                }
                else {
                    conteudo = sb.append(conteudo).append(linha[dado]).append(";").toString();
                }
            }
            novoRegistro.write(conteudo + "\n");
        }

        novoRegistro.close();

        System.out.println("\n\nRegistro salvo.");
    }

    public static ArrayList<String[]> abreArquivo(String nomeArquivoEntrada) throws IOException {
        String caminhoEntradaRegistros = "_entrada_registros/";
        String entradaRegistro = caminhoEntradaRegistros + nomeArquivoEntrada;

        ArrayList<String[]> arquivoRegistros = new ArrayList<String[]>();

        FileReader arquivo = new FileReader(entradaRegistro);
        BufferedReader leitor = new BufferedReader(arquivo);

        // primeira linha
        String linha = leitor.readLine();
        int qtdRegistros = Integer.parseInt(linha);

        ArrayList<Vertice<String[]>> cidades = new ArrayList<>();
        Vertice<String[]> cidade = new Vertice<>();
        ArrayList<String[]>distancias = new ArrayList<>();

        Aresta distanciaCidades;

        for(int contador = 0; contador < qtdRegistros * 2; contador++) {
            linha = leitor.readLine();
            String[] conteudo = linha.split(";");
            if(contador < qtdRegistros) {
                cidade.setValor(conteudo);
                cidades.add(cidade);
            } else {
                distancias.add(conteudo);
            }
        }

        System.out.println(Arrays.deepToString(cidades.toArray()));

        arquivo.close();

        System.out.println(Arrays.deepToString(arquivoRegistros.toArray()));

        return arquivoRegistros;
    }

    public static void limpaTela() throws IOException, InterruptedException {
        if(System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }



    //
    private static void mostraMenu() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        int opcao = -1;
        int entrada;

        while (opcao != 3) {
            System.out.println("\n***********************************\n");
            System.out.println("     ESCOLHA A OPÇÃO");
            System.out.println("\n --- 1: OBTER CIDADES VIZINHAS");
            System.out.println(" --- 2: OBTER TODOS OS CAMINHOS DA CIDADE");
            System.out.println("\n --- 3: SAIR");
            System.out.println("\n***********************************\n");
            System.out.print("-> ");
            opcao = input.nextInt();

            limpaTela();

            if (opcao == 1) {
                System.out.println("OBTER CIDADES VIZINHAS");
                System.out.println("Digite o código da cidade de origem:");
                System.out.print("-> ");
                entrada = input.nextInt();

            } else if (opcao == 2) {
                System.out.println("OBTER TODOS OS CAMINHOS DA CIDADE");
                System.out.println("Digite o código da cidade de origem:");
                System.out.print("-> ");
                entrada = input.nextInt();

            } else if (opcao == 3) {
                System.out.println("Tchauzim");

            }
        }
    }

    // Main
    public static void main(String[] args) throws IOException, InterruptedException {
        GeradorArquivosGrafo g = new GeradorArquivosGrafo();
        ArrayList<String[]> arquivoRegistros;
        String nomeArquivoEntrada = "";

        nomeArquivoEntrada = g.geraArquivo(10);
        arquivoRegistros = abreArquivo(nomeArquivoEntrada); // consertar e fazer direito


        //mostraMenu();
    }
}