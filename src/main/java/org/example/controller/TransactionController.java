package org.example.controller;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.service.AccountService;
import org.example.service.TransactionService;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.UUID;

public class TransactionController {

    private final TransactionService service;
    private final AccountService accountService;
    private final Scanner sc;

    public TransactionController(TransactionService service, AccountService accountService, Scanner sc) {
        this.service = service;
        this.accountService = accountService;
        this.sc = sc;
    }

    public void showMenu(UUID id){

        while(true) {

            System.out.println("\n--- ADMIN VIEW ---");
            System.out.println("1. Visualizar todas as transações existentes");
            System.out.println("2. Buscar transação");
            System.out.println("3. Encontrar conta");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Digite 1, 2 ou 0.");
                sc.next();
                System.out.print("Escolha: ");
            }

            int op = sc.nextInt();
            sc.nextLine();

            switch (op){

                case 1:
                    this.listAllTransactions();
                    break;
                case 2:
                    System.out.println("Qual o id da transação");
                    String id_transaction = sc.nextLine();
                    UUID transaction_id = UUID.fromString(id_transaction);
                    this.listTransaction(transaction_id);
                    break;
                case 3:
                    System.out.println("Qual o id da conta");
                    String id_account = sc.nextLine();
                    UUID account_id = UUID.fromString(id_account);
                    this.findAccount(account_id);
                    break;
                case 0:
                    System.out.println("Logout realizado com sucesso");
                    return;
                default:
                    System.out.println("Inválido");
                    break;
            }


        }

    }

    public void listAllTransactions(){

        List<Transaction> transactions = service.findAll();

        System.out.println("==== Transações Realizadas ====");
        for(int i = 0; i < transactions.size(); i++){
            System.out.print("Id da transação: ");
            System.out.println(transactions.get(i).getId());
            System.out.print("Tipo: ");
            System.out.println(transactions.get(i).getType());
            System.out.print("Quantidade: ");
            System.out.println(transactions.get(i).getAmount());
            System.out.print("Data: ");
            System.out.println(transactions.get(i).getDate());
            System.out.println("===============================");
        }

    }

    public void listTransaction(UUID id){

        try {
            Transaction transaction = service.findTransaction(id);

            System.out.println("==== Transação Encontrada ====");
            System.out.print("Id da transação: ");
            System.out.println(transaction.getId());
            System.out.print("Tipo: ");
            System.out.println(transaction.getType());
            System.out.print("Quantidade: ");
            System.out.println(transaction.getAmount());
            System.out.print("Data: ");
            System.out.println(transaction.getDate());
            System.out.print("Chave da conta que enviou: ");
            System.out.println(transaction.getId_account());
            if(transaction.getTo_account() != null) {
                System.out.print("Chave da conta que recebeu: ");
                System.out.println(transaction.getTo_account());
            }
            System.out.println("===============================");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void findAccount(UUID id){

        try {

            Account account = accountService.find(id);


            System.out.println("==== Conta Encontrada ====");
            System.out.print("Dono da conta: ");
            System.out.println(account.getId_costumer());
            System.out.print("Id da conta: ");
            System.out.println(account.getId());
            System.out.print("Saldo: ");
            System.out.println(account.getBalance());
            System.out.print("Tipo da conta: ");
            System.out.println(account.getAccountType());
            System.out.print("Transações feitas até agora: ");
            this.viewHistory(account.getId());
            System.out.println("==========================");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public void viewHistory(UUID id){

        try {

            Stack<Transaction> transactions = accountService.listTransactions(id);

            if (transactions.isEmpty()) {
                System.out.println("Nenhuma transação encontrada.");
                return;
            }

            Stack<Transaction> copy = (Stack<Transaction>) transactions.clone();

            System.out.println();
            System.out.println("==== Histórico ====");
            while(!copy.isEmpty()){
                Transaction t = copy.pop();
                System.out.println(t.getType());
                System.out.println(t.getAmount());
                System.out.println(t.getDate());
                System.out.println("===================");
            }

        } catch (Exception e){

            System.out.println(e.getMessage());

        }

    }



}
