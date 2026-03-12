package org.example.controller;

import org.example.enums.AccountType;
import org.example.model.Account;
import org.example.service.Account.AccountService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AccountController {

    private final AccountService service;
    private final Scanner sc;

    public AccountController(AccountService service, Scanner sc) {
        this.service = service;
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
                        double initialBalance = sc.nextDouble();
                        sc.nextLine();

                        System.out.println("A conta vai ser corrente ou poupança?");
                        String tipo = sc.nextLine();
                        AccountType type;

                        if(tipo.contains("corrente")){
                            type = AccountType.CheckingAccount;
                        } else if(tipo.contains("poupança")){
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
                System.out.println("0. Logout");
                System.out.print("Escolha: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Entrada inválida. Digite um número (0, 1, 2, 3, 4 ou 5).");
                    sc.next();
                    System.out.print("Escolha: ");
                }

                int op = sc.nextInt();
                    sc.nextLine();

                switch (op){

                    case 1:
                        System.out.println("Qual o saldo inicial da conta?");
                        double initialBalance = sc.nextDouble();
                        sc.nextLine();

                        System.out.println("A conta vai ser corrente ou poupança?");
                        String tipo = sc.nextLine();
                        AccountType type;

                        if(tipo.contains("corrente")){
                            type = AccountType.CheckingAccount;
                        } else if(tipo.contains("poupança")){
                            type = AccountType.SavingsAccount;
                        } else {
                            System.out.println("Inválido");
                            break;
                        }

                        Account newAccount = this.create(new Account(initialBalance, type, id));

                        System.out.println("Conta criada com sucesso, caso queira entrar nessa conta, faça o logout e insira o número correspondente a ela");
                        System.out.println(newAccount.getId());

                        break;

                    case 2:

                        this.findAllAccounts(id);
                        break;

                    case 3:

                        System.out.println("Quanto você quer depositar?");
                        double valueDeposit = sc.nextDouble();
                        sc.nextLine();

                        this.deposit(currentAccount, valueDeposit);
                        break;

                    case 4:
                        System.out.println("Quanto você quer resgatar?");
                        double valueWithdraw = sc.nextDouble();
                        sc.nextLine();

                        this.withdraw(currentAccount, valueWithdraw);
                        break;

                    case 5:
                        System.out.println("Quanto você quer transferir?");
                        double valueTransfer = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Qual a chave?");
                        String value = sc.nextLine();
                        UUID key = UUID.fromString(value);

                        this.transfer(currentAccount, valueTransfer, key);
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
            return null;

        }

    }

    public Account find(UUID id) {

        try {

            return service.find(id);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;

        }

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
            return null;

        }

    }

    public void deposit(UUID id, double value) {

        try {

            service.deposit(id, value);
            System.out.println("Valor depositado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
    public void withdraw(UUID id, double value) {

        try {

            service.withdraw(id, value);
            System.out.println("Valor resgatado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void transfer(UUID id, double value, UUID to_id) {

        try {

            service.transfer(id, to_id, value);
            System.out.println("Valor transferido com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
