import  java.util.Scanner;

public class Lanchonete {

    static class Pedido {
        int    numero;
        String nomeCliente;
        String descricao;
        double valorTotal;
        String status; // "AGUARDANDO", "EM PREPARO", "CONCLUÍDO"

        Pedido(int numero, String nomeCliente, String descricao, double valorTotal) {
            this.numero      = numero;
            this.nomeCliente = nomeCliente;
            this.descricao   = descricao;
            this.valorTotal  = valorTotal;
            this.status      = "AGUARDANDO";
        }

        void exibir() {
            System.out.printf("  Pedido #%d | Cliente: %s%n", numero, nomeCliente);
            System.out.printf("  Descrição: %s%n", descricao);
            System.out.printf("  Valor: R$ %.2f | Status: %s%n", valorTotal, status);
            System.out.println("  " + "-".repeat(40));
        }
    }

    static Pedido[] fila      = new Pedido[50];
    static int      inicio    = 0;  // índice do próximo a ser atendido
    static int      fim       = 0;  // índice onde o próximo entra
    static int      tamanho   = 0;  // quantidade de pedidos na fila
    static int      contadorId = 1; // gerador de número de pedido

    static void enfileirar(Pedido p) {
        if (tamanho == fila.length) {
            System.out.println("Fila cheia! Não é possível adicionar pedidos.");
            return;
        }
        fila[fim] = p;
        fim = (fim + 1) % fila.length; // array circular
        tamanho++;
    }

    static Pedido desenfileirar() {
        if (tamanho == 0) {
            System.out.println("Nenhum pedido na fila.");
            return null;
        }
        Pedido p = fila[inicio];
        fila[inicio] = null;                  // libera referência
        inicio = (inicio + 1) % fila.length;  // avança início
        tamanho--;
        return p;
    }

    static boolean filaVazia() {
        return tamanho == 0;
    }

    //  Menu principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("--------------------------");
            System.out.println("    SISTEMA DE PEDIDOS    ");
            System.out.println("--------------------------");
            System.out.println(" 1 - Registrar pedido     ");
            System.out.println(" 2 - Atender próximo      ");
            System.out.println(" 3 - Listar fila          ");
            System.out.println(" 0 - Sair                 ");
            System.out.println("--------------------------");
            System.out.print(  "Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> registrarPedido(sc);
                case 2 -> atenderProximo();
                case 3 -> listarFila();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        sc.close();
    }

    // ── Operações ──────────────────────────────────────────────────
    static void registrarPedido(Scanner sc) {
        System.out.print("Nome do cliente: ");
        String nome = sc.nextLine();

        System.out.print("Descrição do pedido: ");
        String desc = sc.nextLine();

        System.out.print("Valor total: R$ ");
        double valor = sc.nextDouble();
        sc.nextLine();

        Pedido p = new Pedido(contadorId++, nome, desc, valor);
        enfileirar(p);
        System.out.println("✔ Pedido #" + p.numero + " registrado! Posição na fila: " + tamanho);
    }

    static void atenderProximo() {
        Pedido p = desenfileirar();
        if (p == null) return;

        p.status = "EM PREPARO";
        System.out.println("\n>> Atendendo pedido:");
        p.exibir();

        // Simula conclusão imediata
        p.status = "CONCLUÍDO";
        System.out.println("✔ Pedido #" + p.numero + " concluído!");
    }

    static void listarFila() {
        if (filaVazia()) {
            System.out.println("Fila vazia.");
            return;
        }
        System.out.println("\n── Fila de Pedidos (" + tamanho + ") ──");
        for (int i = 0; i < tamanho; i++) {
            int idx = (inicio + i) % fila.length;
            System.out.println("  [" + (i + 1) + "ª posição]");
            fila[idx].exibir();
        }
    }
}
