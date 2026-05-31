import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RefatoracaoLanchonete {

    // ── Classe Pedido ──────────────────────────────────────────────
    static class Pedido {
        int    numero;
        String nomeCliente;
        String descricao;
        double valorTotal;
        String status;

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

    // ── Fila com Queue ─────────────────────────────────────────────
    static Queue<Pedido> fila      = new LinkedList<>();
    static int           contadorId = 1;

    // ── Menu principal ─────────────────────────────────────────────
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
            System.out.print(  " Opção:                   ");
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
        fila.offer(p); // adiciona no fim da fila
        System.out.println("✔ Pedido #" + p.numero + " registrado! Posição na fila: " + fila.size());
    }

    static void atenderProximo() {
        Pedido p = fila.poll(); // remove e retorna o primeiro
        if (p == null) {
            System.out.println("Nenhum pedido na fila.");
            return;
        }

        p.status = "EM PREPARO";
        System.out.println("\n>> Atendendo pedido:");
        p.exibir();

        p.status = "CONCLUÍDO";
        System.out.println("✔ Pedido #" + p.numero + " concluído!");
    }

    static void listarFila() {
        if (fila.isEmpty()) {
            System.out.println("Fila vazia.");
            return;
        }

        System.out.println("\n── Fila de Pedidos (" + fila.size() + ") ──");
        int posicao = 1;
        for (Pedido p : fila) { // percorre sem remover
            System.out.println("  [" + posicao++ + "ª posição]");
            p.exibir();
        }
    }
}
