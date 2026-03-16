package org.example.controller;

import org.example.dto.*;
import org.example.enums.AccountType;
import org.example.enums.PixKeyType;
import org.example.model.Customer;
import org.example.model.PixKey;
import org.example.service.AccountService;
import org.example.service.CustomerService;
import org.example.service.PixKeyService;
import org.example.service.TransactionService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AccountController {

    private final AccountService service;
    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final PixKeyService pixKeyService;

    private final Scanner sc;

    public AccountController(AccountService service, TransactionService transactionService, CustomerService customerService, PixKeyService pixKeyService, Scanner sc) {
        this.service = service;
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.pixKeyService = pixKeyService;
        this.sc = sc;
    }

    public void showMenu(Long id){

        CustomerResponse customer = findCustomerById(id);

        if(customer == null){
            System.out.println("Cliente não encontrado, não é possível abrir contas");
            return;
        }

        Long currentAccount = null;

        Long loginId = this.login(id);

        if(loginId != null){
            currentAccount = loginId;
        }

        while(true) {

            if(currentAccount == null){

                System.out.println("\n--- BEM VINDO " + customer.name().toUpperCase() + " ---");
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
                        } else {
                            type = AccountType.SavingsAccount;
                        }

                        AccountResponse newAccount = this.create(new AccountRequest(initialBalance, type, customer.id()));

                        System.out.println(newAccount.id());

                        if (newAccount == null) {
                            System.out.println("Falha ao criar conta.");
                            break;
                        }

                        System.out.println(newAccount.id());

                        System.out.println("Crie uma chave pix");
                        System.out.println("Qual será o tipo da sua chave pix? (CPF/EMAIL/TELEFONE/ALEATÓRIA)");
                        System.out.print("tipo: ");
                        String value = sc.nextLine();
                        while (!value.equalsIgnoreCase("cpf") &&  !value.equalsIgnoreCase("email") && !value.equalsIgnoreCase("telefone") && !value.equalsIgnoreCase("aleatoria")){

                            System.out.println("Tipo inválido, insira novamente");
                            System.out.print("tipo: ");
                            value = sc.nextLine();
                        }

                        PixKeyType keyType;
                        String keyValue = "";

                        if(value.equalsIgnoreCase("cpf")){
                            keyType = PixKeyType.CPF;
                            keyValue = customer.document();
                        } else if(value.equalsIgnoreCase("email")){
                            keyType = PixKeyType.EMAIL;
                            keyValue = customer.email();
                        } else if(value.equalsIgnoreCase("telefone")){
                            keyType = PixKeyType.PHONE;
                            keyValue = customer.phone();
                        } else {
                            keyType = PixKeyType.RANDOM;
                            keyValue = String.valueOf(UUID.randomUUID());
                        }

                        PixKeyResponse newPixKey = this.createPixKey(new PixKeyRequest(keyValue, keyType, newAccount.id()));

                        if (newPixKey == null){
                            System.out.println("Falha ao criar a chave");
                            break;
                        }

                        currentAccount = newAccount.id();
                        System.out.println("Conta criada com sucesso");
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Inválido");
                        break;
                }

            } else {

                System.out.println("\n--- BEM VINDO " + customer.name().toUpperCase() + " ---");
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
                        } else {
                            type = AccountType.SavingsAccount;
                        }

                        AccountResponse newAccount = this.create(new AccountRequest(initialBalance, type, customer.id()));

                        System.out.println(newAccount.id());

                        if (newAccount == null) {
                            System.out.println("Falha ao criar conta.");
                            break;
                        }

                        System.out.println("Crie uma chave pix");
                        System.out.println("Qual será o tipo da sua chave pix? (CPF/EMAIL/TELEFONE/ALEATÓRIA)");
                        System.out.print("tipo: ");
                        String value = sc.nextLine();
                        while (!value.equalsIgnoreCase("cpf") &&  !value.equalsIgnoreCase("email") && !value.equalsIgnoreCase("telefone") && !value.equalsIgnoreCase("aleatoria")){

                            System.out.println("Tipo inválido, insira novamente");
                            System.out.print("tipo: ");
                            value = sc.nextLine();
                        }

                        PixKeyType keyType;
                        String keyValue = "";

                        if(value.equalsIgnoreCase("cpf")){
                            keyType = PixKeyType.CPF;
                            keyValue = customer.document();
                        } else if(value.equalsIgnoreCase("email")){
                            keyType = PixKeyType.EMAIL;
                            keyValue = customer.email();
                        } else if(value.equalsIgnoreCase("telefone")){
                            keyType = PixKeyType.PHONE;
                            keyValue = customer.phone();
                        } else {
                            keyType = PixKeyType.RANDOM;
                            keyValue = String.valueOf(UUID.randomUUID());
                        }

                        PixKeyResponse newPixKey = this.createPixKey(new PixKeyRequest(keyValue, keyType, newAccount.id()));

                        if (newPixKey == null){
                            System.out.println("Falha ao criar a chave");
                            break;
                        }

                        String res = "";

                        while(!res.equals("S") && !res.equals("N")){
                            System.out.println("Deseja utilizar essa conta? (S/N)");
                            res = sc.nextLine();
                        }
                        if(res.equals("S")){
                            currentAccount = newAccount.id();
                        } else {
                            System.out.println("Caso queira entrar nessa nova conta, selecione o código correspondente a ela");
                            System.out.println(newAccount.id());
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
                            System.out.println("Qual a chave pix?");
                            String valuePixKey = sc.nextLine();
                            if (this.findByPixKey(valuePixKey) == null){
                                System.out.println("Entrada inválida. Digite uma chave válida");
                            }
                            transfer(currentAccount, valueTransfer, findByPixKey(valuePixKey).id());
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

    public AccountResponse create(AccountRequest request) {

        try {

            return service.create(request);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;

        }

    }

    public AccountResponse find(Long id) {

        try {

            return service.find(id);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;
    }

    public void findAllAccounts(Long id) {

        try {

            List<AccountResponse> accounts = service.findByCustomerId(id);

            for(int i = 0; i < accounts.size(); i++){

                AccountResponse acc = accounts.get(i);

                System.out.println("====== CONTA " + acc.id() + " ======");
                System.out.print("Chave: ");
                System.out.println(acc.pixKey().keyValue());
                System.out.print("Tipo da Conta: ");
                if(acc.type().equals(AccountType.CheckingAccount)){
                    System.out.println("Conta corrente ");
                } else {
                    System.out.println("Poupança");
                }
                System.out.print("Saldo disponível: ");
                System.out.println(acc.balance());
                System.out.println("==================================");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public Long login(Long id) {

        try {

            List<Long> accounts = service.login(id);
            int idx;

            System.out.println("Qual conta você deseja usar?");

            accounts.forEach(accId -> {
                System.out.println("Conta ID: " + accId);
            });

            System.out.print("Conta (número): ");
            long chosenId = sc.nextLong();
            sc.nextLine();

            while(!accounts.contains(chosenId)){

                System.out.println("ID inválido");
                System.out.print("Conta (número) : ");
                idx = sc.nextInt();

            }

            return chosenId;

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;

    }

    public void deposit(Long id, double value) {

        try {

            transactionService.deposit(id, value);
            System.out.println("Valor depositado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
    public void withdraw(Long id, double value) {

        try {

            transactionService.withdraw(id, value);
            System.out.println("Valor resgatado com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void transfer(Long id, double value, Long to_id) {

        try {

            transactionService.transfer(id, to_id, value);
            System.out.println("Valor transferido com sucesso!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void viewHistory(Long id){

        try {

            List<TransactionResponse> transactions = transactionService.listTransactionsByAccountId(id);

            if (transactions.isEmpty()) {
                System.out.println("Nenhuma transação encontrada.");
                return;
            }

            System.out.println();
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

    public CustomerResponse findCustomerById(Long id) {

        try {

            return customerService.findById(id);

        } catch (Exception e){

            System.out.println(e.getMessage());
            return null;

        }

    }

    public PixKeyResponse createPixKey(PixKeyRequest request){

        try {

            return pixKeyService.create(request);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public AccountIdResponse findByPixKey(String key){

        try {

            return service.findByPixKey(key);

        } catch (Exception e){

            System.out.println(e.getMessage());
            return null;

        }

    }

}
