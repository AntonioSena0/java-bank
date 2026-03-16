package org.example;

import org.example.mapper.AccountMapper;
import org.example.mapper.CustomerMapper;
import org.example.mapper.PixKeyMapper;
import org.example.mapper.TransactionMapper;
import org.example.controller.AccountController;
import org.example.controller.CustomerController;
import org.example.controller.TransactionController;
import org.example.enums.UserType;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.CustomerRepositoryImpl;
import org.example.repository.PixKeyRepositoryImpl;
import org.example.repository.TransactionRepositoryImpl;
import org.example.service.AccountService;
import org.example.service.CustomerService;
import org.example.service.PixKeyService;
import org.example.service.TransactionService;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class app {

    private final AccountController accountController;
    private final CustomerController costumerController;
    private final TransactionController transactionController;
    private final Scanner sc;
    private final SessionFactory sf;

    public app(AccountController accountController, CustomerController costumerController, TransactionController transactionController, Scanner sc, SessionFactory sf) {
        this.accountController = accountController;
        this.costumerController = costumerController;
        this.transactionController = transactionController;
        this.sc = sc;
        this.sf = sf;
    }

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Scanner sc = new Scanner(System.in);

        CustomerMapper customerMapper = new CustomerMapper();
        AccountMapper accountMapper = new AccountMapper(customerMapper);
        TransactionMapper transactionMapper = new TransactionMapper();
        PixKeyMapper pixKeyMapper = new PixKeyMapper();

        AccountRepositoryImpl accountsRepo = new AccountRepositoryImpl();
        TransactionRepositoryImpl transRepo = new TransactionRepositoryImpl();
        CustomerRepositoryImpl customerRepo = new CustomerRepositoryImpl();
        PixKeyRepositoryImpl pixKeyRepo = new PixKeyRepositoryImpl();

        AccountService accountService = new AccountService(accountsRepo, accountMapper, customerRepo);
        TransactionService transService = new TransactionService(transRepo, transactionMapper);
        CustomerService customerService = new CustomerService(customerRepo, customerMapper);
        PixKeyService pixKeyService = new PixKeyService(pixKeyRepo, accountsRepo, pixKeyMapper);

        AccountController accountCtrl = new AccountController(accountService, transService, customerService, pixKeyService, sc);
        TransactionController transCtrl = new TransactionController(transService, accountService, sc);
        CustomerController customerCtrl = new CustomerController(customerService, sc);

        customerCtrl.createAdminIfNotExists();

        app application = new app(accountCtrl, customerCtrl, transCtrl, sc, sf);
        application.run();
    }

    public void run() {
        Long currentCustomerId = null;
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
                            Long newId = costumerController.showMenuRegister();
                            if (newId != null) {
                                currentCustomerId = newId;
                                System.out.println("Perfil criado com sucesso");
                            }
                            break;
                        case 2:
                            Long loginId = costumerController.showMenuLogin();
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

                } else if(costumerController.find(currentCustomerId).role().equals(UserType.ADMIN)){

                    transactionController.showMenu(currentCustomerId);
                    currentCustomerId = null;

                } else {

                    accountController.showMenu(currentCustomerId);
                    currentCustomerId = null;

                }

            }
        }
        finally {
            sf.close();
            sc.close();
        }
    }

}
