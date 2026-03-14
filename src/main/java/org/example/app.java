package org.example;

import org.example.controller.AccountController;
import org.example.controller.CostumerController;
import org.example.controller.TransactionController;
import org.example.enums.UserType;
import org.example.model.Customer;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.CustomerRepositoryImpl;
import org.example.repository.TransactionRepositoryImpl;
import org.example.service.AccountService;
import org.example.service.CustomerService;
import org.example.service.TransactionService;

import java.util.Scanner;
import java.util.UUID;

public class app {

    private final AccountController accountController;
    private final CostumerController costumerController;
    private final TransactionController transactionController;
    private final Scanner sc;

    public app(AccountController accountController, CostumerController costumerController, TransactionController transactionController, Scanner sc) {
        this.accountController = accountController;
        this.costumerController = costumerController;
        this.transactionController = transactionController;
        this.sc = sc;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AccountRepositoryImpl accountsRepo = new AccountRepositoryImpl();
        TransactionRepositoryImpl transRepo = new TransactionRepositoryImpl(accountsRepo);
        CustomerRepositoryImpl customerRepo = new CustomerRepositoryImpl();

        AccountService accountService = new AccountService(accountsRepo);
        TransactionService transService = new TransactionService(transRepo);
        CustomerService customerService = new CustomerService(customerRepo);

        AccountController accountCtrl = new AccountController(accountService, transService, sc);
        TransactionController transCtrl = new TransactionController(transService, accountService, sc);
        CostumerController customerCtrl = new CostumerController(customerService, sc);

        customerCtrl.create(new Customer("admin@gmail.com", "admin123456", UserType.ADMIN));
        app application = new app(accountCtrl, customerCtrl, transCtrl, sc);
        application.run();
    }

    public void run() {
        UUID currentCustomerId = null;
        System.out.println("=== SISTEMA BANCÁRIO ===\n");

        try {
            while (true) {
                if (currentCustomerId == null) {
                    System.out.println("1. CRIAR CLIENTE");
                    System.out.println("2. LOGIN");
                    System.out.println("0. SAIR");
                    System.out.print("Escolha: ");

                    while (!sc.hasNextInt()) {
                        System.out.println("Entrada inválida. Digite um número (0, 1 ou 2).");
                        sc.next();
                        System.out.print("Escolha: ");
                    }

                    int opt = sc.nextInt();
                    sc.nextLine();

                    switch (opt) {
                        case 1:
                            UUID newId = costumerController.showMenuRegister();
                            if (newId != null) {
                                currentCustomerId = newId;
                                System.out.println("Perfil criado com sucesso");
                            }
                            break;
                        case 2:
                            UUID loginId = costumerController.showMenuLogin();
                            if (loginId != null) {
                                currentCustomerId = loginId;
                                System.out.println("Login realizado com sucesso");
                            }
                            break;
                        case 0:
                            System.out.println("Volte sempre!");
                            return;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }

                } else if(currentCustomerId != null && costumerController.find(currentCustomerId).getRole().equals(UserType.ADMIN)){

                    transactionController.showMenu(currentCustomerId);
                    currentCustomerId = null;

                } else {

                    accountController.showMenu(currentCustomerId);
                    currentCustomerId = null;

                }

            }
        }
        finally {
            sc.close();
        }
    }

}
