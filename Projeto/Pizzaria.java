package Projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Projeto.Pizza.TamanhoPizza;

public class Pizzaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println();
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido");
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas");
            System.out.println("5 - Gerar lista de clientes");
            System.out.println("9 - Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner));
                    System.out.println("Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio();
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 9:
                    System.out.println("Até amanha...");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    break;
            }
        }

    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x+" - "+cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int cliente = scanner.nextInt();
        scanner.nextLine();

        boolean continuar = true;
        while (continuar) {
            x = 1;
            System.out.println("Qual o tamanho da pizza? ");
            System.out.println("Selecione um tamanho: ");
            for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                System.out.println(x+" - "+tamanhos);
                x++;
            }
            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("Digite a quantidade de sabores: 1 - 4 ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            Cardapio cardapio = new Cardapio();
            List<String> saboresList = new ArrayList<>();
            List<String> saboresSelect = new ArrayList<>();

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione um sabor: ");

                x = 1;
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x+" - "+sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcao-1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho-1));
            pizzas.add(pizza);

            System.out.println("Pizza cadastrada com sucesso!");
            System.out.println();
            System.out.println("Deseja cadastrar mais uma pizza no pedido?");
            System.out.print("1 - Sim, 2 - Não: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if(opcao != 1){
                continuar = false;
            }
        }
        Pedido pedido = new Pedido(listaPedidos.size()+1,listaClientes.get(cliente-1), pizzas, somarPizzas(pizzas));
        listaPedidos.add(pedido);
    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        if (listaPedidos.isEmpty()) {
            System.out.println("Não há pedidos para alterar.");
            return;
        }

        // Listar pedidos existentes
        System.out.println("Selecione o pedido que deseja alterar: ");
        for (int i = 0; i < listaPedidos.size(); i++) {
            Pedido pedido = listaPedidos.get(i);
            System.out.println((i + 1) + " - Pedido ID: " + pedido.getId() + ", Cliente: " + pedido.getCliente().getNome() + ", Valor Total: R$" + pedido.getValorTotal());
        }
        System.out.print("Opção: ");
        int pedidoIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (pedidoIndex < 0 || pedidoIndex >= listaPedidos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Pedido pedidoSelecionado = listaPedidos.get(pedidoIndex);

        // Menu de opções para alterar o pedido
        boolean continuarAlterando = true;
        while (continuarAlterando) {
            System.out.println("Escolha o que deseja alterar: ");
            System.out.println("1 - Alterar cliente");
            System.out.println("2 - Alterar pizzas do pedido");
            System.out.println("3 - Cancelar alteração");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    // Alterar o cliente do pedido
                    System.out.println("Selecione o novo cliente: ");
                    for (int i = 0; i < listaClientes.size(); i++) {
                        Cliente cliente = listaClientes.get(i);
                        System.out.println((i + 1) + " - " + cliente.getNome());
                    }
                    System.out.print("Opção: ");
                    int clienteIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (clienteIndex < 0 || clienteIndex >= listaClientes.size()) {
                        System.out.println("Opção inválida.");
                    } else {
                        pedidoSelecionado.setCliente(listaClientes.get(clienteIndex));
                        System.out.println("Cliente alterado com sucesso!");
                    }
                    break;
                case 2:
                    // Alterar as pizzas do pedido
                    List<Pizza> novasPizzas = new ArrayList<>();
                    boolean continuarAdicionando = true;
                    while (continuarAdicionando) {
                        // Reutilizando a lógica de fazerPedido para adicionar novas pizzas
                        System.out.println("Qual o tamanho da pizza? ");
                        int x = 1;
                        for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                            System.out.println(x + " - " + tamanhos);
                            x++;
                        }
                        System.out.print("Opção: ");
                        int tamanho = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Digite a quantidade de sabores (1 a 4): ");
                        int quantiSabores = scanner.nextInt();
                        scanner.nextLine();

                        Cardapio cardapio = new Cardapio();
                        List<String> saboresList = new ArrayList<>();
                        List<String> saboresSelect = new ArrayList<>();
                        for (int i = 0; i < quantiSabores; i++) {
                            System.out.println("Selecione um sabor: ");
                            x = 1;
                            for (String sabor : cardapio.getCardapio().keySet()) {
                                saboresList.add(sabor);
                                System.out.println(x + " - " + sabor);
                                x++;
                            }
                            System.out.print("Opção: ");
                            int saborIndex = scanner.nextInt();
                            scanner.nextLine();
                            saboresSelect.add(saboresList.get(saborIndex - 1));
                        }

                        Pizza novaPizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho - 1));
                        novasPizzas.add(novaPizza);

                        System.out.println("Deseja adicionar outra pizza? 1 - Sim, 2 - Não");
                        int continuar = scanner.nextInt();
                        scanner.nextLine();
                        if (continuar != 1) {
                            continuarAdicionando = false;
                        }
                    }
                    pedidoSelecionado.setPizzas(novasPizzas);
                    pedidoSelecionado.setValorTotal(somarPizzas(novasPizzas));
                    System.out.println("Pizzas do pedido alteradas com sucesso!");
                    break;
                case 3:
                    continuarAlterando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }


    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        System.out.println();

        Cliente cliente = new Cliente(nome, endereco, telefone, email);
        return cliente;
    }

    private static void gerarRelatorio() {
        System.out.println("Gerar relatorio");
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        int x = 1;
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes esta vazia");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println("Cliente "+x);
                System.out.println(cliente.getNome());
                System.out.println(cliente.getEndereco());
                System.out.println(cliente.getTelefone());
                System.out.println(cliente.getEmail());
                System.out.println();
                x++;
            }
        }
    }
}