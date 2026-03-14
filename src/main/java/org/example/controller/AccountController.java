package org.example.controller;

import org.example.enums.AccountType;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.service.AccountService;
import org.example.service.TransactionService;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.UUID;

public class AccountController {

    private final AccountService service;
    private final TransactionService transactionService;
    private final Scanner sc;

    public AccountController(AccountService service, TransactionService transactionService, Scanner sc) {
        this.service = service;
        this.transactionService = transactionService;
        this.sc = sc;
    }

    public void showMenu(UUID id){

        UUID currentAccount = null;

        UUID loginId = this.login(id);

        if(loginId != null){
            currentAccount = loginId;
        }

        while(true) {

            if(currentAccount == null){

                System.out.println("\n--- CONTAS ---");
                System.out.println("1. Abrir uma nova Conta");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Entrada inválida. Digite um número (1).");
                    sc.next();
                    System.out.print("Escolha: ");
                }

                int op = sc.nextInt();
                sc.nextLine();

                switch (op){

                    case 1:
                        System.out.println("Qual o saldo inicial da conta?");
                        while (!sc.hasNextDouble()){
                            System.out.println("Entrada inválida. Digite um valor real");
                            sc.next();
                            System.out.println("Valor: ");
                        }
                        double initialBalance = sc.nextDouble();
                        sc.nextLine();

                        System.out.println("A conta vai ser corrente ou poupança?");
                        String tipo = sc.nextLine();
                        while(!tipo.equalsIgnoreCase("corrente") && !tipo.equalsIgnoreCase("poupança")){
                            System.out.println("Escolha um tipo válido! (corrente/poupança)");
                            tipo = sc.nextLine();
                        }

                        AccountType type;

                        if(tipo.equals("corrente")){
                            type = AccountType.CheckingAccount;
                        } else if(tipo.equals("poupança")){
                            type = AccountType.SavingsAccount;
                        } else {
                            System.out.println("Inválido");
                            break;
                        }

                        Account newAccount = this.create(new Account(initialBalance, type, id));
                        currentAccount = newAccount.getId();
                        System.out.println("Conta criada com sucesso");
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Inválido");
                        break;
                }

            } else {

                System.out.println("\n--- CONTAS ---");
                System.out.println("1. Criar Conta");
                System.out.println("2. Minhas contas");
                System.out.println("3. Depósitar");
                System.out.println("4. Saque");
                System.out.println("5. Transferir");
                System.out.println("6. Histórico de Transferências");
                System.out.println("0. Logout");
                System.out.print("Escolha: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Entrada inválida. Digite um número (0, 1, 2, 3, 4, 5, 6).");
                    sc.next();
                    System.out.print("Escolha: ");
                }

                int op = sc.nextInt();
                    sc.nextLine();

                switch (op){

                    case 1:
                        System.out.println("Qual o saldo inicial da conta?");
                        while (!sc.hasNextDouble()){
                            System.out.println("Entrada inválida. Digite um valor real");
                            sc.next();
                            System.out.print("Valor: ");
                        }
                        double initialBalance = sc.nextDouble();
                        sc.nextLine();

                        System.out.println("A conta vai ser corrente ou poupança?");
                        String tipo = sc.nextLine();
                        while(!tipo.equalsIgnoreCase("corrente") && !tipo.equalsIgnoreCase("poupança")){
                            System.out.println("Escolha um tipo válido! (corrente/poupança)");
                            tipo = sc.nextLine();
                        }

                        AccountType type;

                        if(tipo.equals("corrente")){
                            type = AccountType.CheckingAccount;
                        } else if(tipo.equals("poupança")){
                            type = AccountType.SavingsAccount;
                        } else {
                            System.out.println("Inválido");
                            break;
                        }

                        Account newAccount = this.create(new Account(initialBalance, type, id));

                        String res = "";

                        while(!res.equals("S") && !res.equals("N")){
                            System.out.println("Deseja utilizar essa conta? (S/N)");
                            res = sc.nextLine();
                        }
                        if(res.equals("S")){
                            currentAccount = newAccount.getId();
                        } else {
                            System.out.println("Caso queira entrar nessa nova conta, selecione a chave correspondente a ela");
                            System.out.println(newAccount.getId());
                        }
                        break;

                    case 2:

                        this.findAllAccounts(id);
                        break;

                    case 3:

                        double valueDeposit;

                        System.out.println("Quanto você quer depositar?");
                        while (!sc.hasNextDouble()){
                            System.out.println("Entrada inválida. Digite um valor real");
                            sc.next();
                            System.out.print("Valor: ");
                        }
                        valueDeposit = sc.nextDouble();
                        sc.nextLine();

                        this.deposit(currentAccount, valueDeposit);
                        break;

                    case 4:

                        double valueWithdraw;

                        System.out.println("Quanto você quer resgatar?");
                        while (!sc.hasNextDouble()){
                            System.out.println("Entrada inválida. Digite um valor real");
                            sc.next();
                            System.out.print("Valor: ");
                        }
                        valueWithdraw = sc.nextDouble();
                        sc.nextLine();

                        this.withdraw(currentAccount, valueWithdraw);
                        break;

                    case 5:

                        double valueTransfer;

                        System.out.println("Quanto você quer transferir?");
                        while (!sc.hasNextDouble()){
                            System.out.println("Entrada inválida. Digite um valor real");
                            sc.next();
                            System.out.print("Valor: ");
                        }
                        valueTransfer = sc.nextDouble();
                        sc.nextLine();
                        try {
                            System.out.println("Qual a chave?");
                            String value = sc.nextLine();
                            UUID key = UUID.fromString(value);
                            while (this.find(key) == null){
                                System.out.println("Entrada inválida. Digite uma chave válida");
                                System.out.print("Chave: ");
                                value = sc.nextLine();
                                key = UUID.fromString(value);
                            }
                            transfer(currentAccount, valueTransfer, key);
                            break;
                        } catch (Exception e){
                            System.out.println("invalid key");
                        }
                        break;

                    case 6:
                        viewHistory(currentAccount);
                        break;

                    case 0:
                        System.out.println("Logout realizado com sucesso");
                        currentAccount = null;
                        return;

                    default:
                        System.out.println("Inválido");
                        break;
                }

            }



        }

    }

    public Account create(Account account) {

        try {

            return service.create(account);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;

    }

    public Account find(UUID id) {

        try {

            return service.find(id);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;
    }

    public void findAllAccounts(UUID id) {

        try {

            List<Account> accounts = service.findByCustomerId(id);

            for(int i = 0; i < accounts.size(); i++){
                System.out.println("====== CONTA " + (i + 1) + " ======");
                System.out.println();
                System.out.println("Chave:");
                System.out.println(accounts.get(i).getId());
                System.out.println();
                System.out.println("Tipo da Conta:");
                if(accounts.get(i).getAccountType().equals(AccountType.CheckingAccount)){
                    System.out.println("Conta corrente");
                } else {
                    System.out.println("Poupança");
                }
                System.out.println();
                System.out.println("Saldo disponível:");
                System.out.println(accounts.get(i).getBalance());
                System.out.println("==================================");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public UUID login(UUID id) {

        try {

            List<UUID> accounts = service.login(id);

            System.out.println("Qual conta você deseja usar?");
            for(int i = 0; i < accounts.size(); i++){

                System.out.println(i + " - " + this.find(accounts.get(i)).getId());

            }
            System.out.println("Digite o número da conta");
            int idx = sc.nextInt();
            sc.nextLine();

            if(idx < 0 || idx >= accounts.size()){

                System.out.println("Índice inválido");
                return null;

            }

            UUID existingId = accounts.get(idx);
            return existingId;

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;

    }

    public void deposit(UUID id, double value) {

        try {

            transactionService.deposit(id, value);
            System.out.println("Valor depositado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
    public void withdraw(UUID id, double value) {

        try {

            transactionService.withdraw(id, value);
            System.out.println("Valor resgatado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void transfer(UUID id, double value, UUID to_id) {

        try {

            transactionService.transfer(id, to_id, value);
            System.out.println("Valor transferido com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void viewHistory(UUID id){

        try {

            Stack<Transaction> transactions = service.listTransactions(id);

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
