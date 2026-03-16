package org.example.controller;

import org.example.dto.AccountResponse;
import org.example.dto.TransactionResponse;
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

    public void showMenu(Long id){

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
                    try {
                        this.listAllTransactions();
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Qual o id da transação");
                        String id_transaction = sc.nextLine();
                        Long transaction_id = Long.parseLong(id_transaction);
                        TransactionResponse transaction = listTransaction(transaction_id);
                        if(transaction == null){
                            break;
                        }
                        System.out.println();
                        System.out.println("==== Transação Encontrada ====");
                        System.out.print("Id da transação: ");
                        System.out.println(transaction.id());
                        System.out.print("Tipo: ");
                        System.out.println(transaction.type());
                        System.out.print("Quantidade: ");
                        System.out.println(transaction.amount());
                        System.out.print("Data: ");
                        System.out.println(transaction.createdAt());
                        System.out.print("Chave da conta que enviou: ");
                        System.out.println(transaction.from_account().id());
                        if(transaction.to_account() != null) {
                            System.out.print("Chave da conta que recebeu: ");
                            System.out.println(transaction.to_account().id());
                        }
                        System.out.println("===============================");
                        break;
                    } catch (Exception e){
                        System.out.println("Invalid id");
                    }
                    break;
                case 3:
                    try {

                        System.out.println("Qual o id da conta");
                        String id_account = sc.nextLine();
                        Long account_id = Long.parseLong(id_account);
                        while (this.findAccount(account_id) == null){
                            System.out.println("Entrada inválida. Digite uma chave válida");
                            System.out.print("Chave: ");
                            id_account = sc.nextLine();
                            account_id = Long.parseLong(id_account);
                        }

                        AccountResponse account = this.findAccount(account_id);

                        System.out.println();
                        System.out.println("==== Conta Encontrada ====");
                        System.out.print("Dono da conta: ");
                        System.out.println(account.customer().id());
                        System.out.print("Id da conta: ");
                        System.out.println(account.id());
                        System.out.print("Saldo: ");
                        System.out.println(account.balance());
                        System.out.print("Tipo da conta: ");
                        System.out.println(account.type());
                        System.out.println("Transações feitas até agora: ");
                        this.viewHistory(account.id());
                        System.out.println("==========================");

                        break;

                    } catch (Exception e){
                        System.out.println("Invalid id");
                    }

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

        List<TransactionResponse> transactions = service.findAll();

        System.out.println("==== Transações Realizadas ====");
        for(int i = 0; i < transactions.size(); i++){
            System.out.print("Id da transação: ");
            System.out.println(transactions.get(i).id());
            System.out.print("Tipo: ");
            System.out.println(transactions.get(i).type());
            System.out.print("Quantidade: ");
            System.out.println(transactions.get(i).amount());
            System.out.print("Data: ");
            System.out.println(transactions.get(i).createdAt());
            System.out.println("===============================");
        }

    }

    public TransactionResponse listTransaction(Long id){

        try {
            TransactionResponse transaction = service.findTransaction(id);

            return transaction;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;

    }

    public AccountResponse findAccount(Long id){

        try {

            return accountService.find(id);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;

    }


    public void viewHistory(Long id){

        try {

            List<TransactionResponse> transactions = service.listTransactionsByAccountId(id);

            if (transactions.isEmpty()) {
                System.out.println("Nenhuma transação encontrada.");
                return;
            }

            System.out.println("==== Histórico ====");
            transactions.forEach(transaction -> {

                System.out.println(transaction.type());
                System.out.println(transaction.amount());
                System.out.println(transaction.createdAt());
                System.out.println("===================");

            });

        } catch (Exception e){

            System.out.println(e.getMessage());

        }

    }



}
