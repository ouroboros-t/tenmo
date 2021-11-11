package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};


    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;
    private UserService userService;
    private TransferService transferService;

    public static void main(String[] args) throws TransferServiceException {
        App app = new App(new ConsoleService(System.in, System.out)
                , new AuthenticationService(API_BASE_URL)
                , new AccountService(API_BASE_URL)
                , new UserService(API_BASE_URL)
                , new TransferService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console
            , AuthenticationService authenticationService
            , AccountService accountService
            , UserService userService
            , TransferService transferService) {
        this.console = console;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.userService = userService;
        this.transferService = transferService;

    }

    public void run() throws TransferServiceException {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() throws TransferServiceException {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewCurrentBalance() {
        try {
            console.displayBalance(accountService.balanceRequest(currentUser.getToken()));
        } catch (AccountServiceException e) {
            System.out.println("BALANCE ERROR: " + e.getMessage());
        }
    }

    private void viewTransferHistory() {
        Integer transferId = null;
        try {
            console.displayTransferDetails(transferService.transferDetailRequest(currentUser.getToken()), currentUser);
            transferId = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel):");
            console.displaySingleTransferDetail(transferService.transferDetailRequest(currentUser.getToken()), transferId);
        } catch (TransferServiceException e) {
            System.out.println("TRANSFER ERROR: " + e.getMessage());
        }


    }

    private void viewPendingRequests() {
        try{
            console.displayPendingTransfers(transferService.transferDetailRequest(currentUser.getToken()), currentUser);
        } catch (TransferServiceException e) {
            System.out.println("TRANSFER ERROR: " + e.getMessage());
        }

    }

    private void sendBucks() {
        User[] users = null;
        String sendId = null;
        try {
            users = userService.usersRequest(currentUser.getToken());
            console.displayUsers(users, currentUser);
            boolean isSendUserIdValid = false;
            while (!isSendUserIdValid) {
                sendId = collectSendRequestUser();
                if (Integer.parseInt(sendId) == 0) {
                    return;
                }
                for (User user : users) {
                    if (user.getId().equals(Integer.parseInt(sendId))) {
                        int sendIdInt = Integer.parseInt(sendId);
                        isSendUserIdValid = true;
                        Double amountDouble = console.getUserInputDouble("Please enter the TE bucks amount you would like to send");
                        Transfer transfer = createTransfer(sendIdInt, amountDouble);
                        console.displayBucksSent(transferService.transferRequest(currentUser.getToken(), transfer));
                        break;
                    }
                }
            }

        } catch (UserServiceException e) {
            System.out.println("USERNAME ERROR: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("USERNAME ERROR: username not provided");
        } catch (TransferServiceException e) {
            System.out.print("INSUFFICIENT FUNDS");
        } catch (AccountServiceException e) {
            System.out.println("BALANCE ERROR: " + e.getMessage());
        }
    }

    private void requestBucks() {
        User[] users = null;
        String sendId = null;
        try {
            users = userService.usersRequest(currentUser.getToken());
            console.displayUsers(users, currentUser);
            boolean isSendUserIdValid = false;
            while (!isSendUserIdValid) {
                sendId = collectTEBucksRequestUser();
                if (Integer.parseInt(sendId) == 0) {
                    return;
                }
                for (User user : users) {
                    if (user.getId().equals(Integer.parseInt(sendId))) {
                        int sendIdInt = Integer.parseInt(sendId);
                        isSendUserIdValid = true;
                        Double amountDouble = console.getUserInputDouble("Please enter the TE bucks amount you would like to request");
                        Transfer transfer = createPendingTransfer(sendIdInt, amountDouble);
                        console.displayPendingRequest(transferService.pendingTransferRequest(currentUser.getToken(), transfer));
                        break;
                    }
                }
            }

        } catch (UserServiceException e) {
            System.out.println("USERNAME ERROR: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("USERNAME ERROR: username not provided");
        } catch (TransferServiceException e) {
            System.out.print("INSUFFICIENT FUNDS");
        } catch (AccountServiceException e) {
            System.out.println("BALANCE ERROR: " + e.getMessage());
        }

    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: " + e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
                System.out.println("\nLOGIN SUCCESSFUL: Logged in as " + credentials.getUsername());
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }

    private String collectSendRequestUser() {
        return console.getUserInput("Enter ID of user you are sending to (0 to cancel)");
    }

    private String collectTEBucksRequestUser() {
        return console.getUserInput("Enter ID of user you are requesting from (0 to cancel)");
    }

    private Transfer createTransfer(int sendId, Double amountDouble) throws TransferServiceException, AccountServiceException {
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(2);
        transfer.setTransferStatusId(2);
        transfer.setAccountFromId(accountService.getAccountIdFromUsername(currentUser.getToken(), currentUser.getUser().getUsername()));
        transfer.setAccountToId(accountService.getAccountIdFromUserId(currentUser.getToken(), sendId));

        if (validateTransferAmount(amountDouble, currentUser)) {
            transfer.setAmount(amountDouble);
        } else {
            throw new TransferServiceException("Insufficient Funds. ");
        }
        return transfer;
    }

    private boolean validateTransferAmount(Double amount, AuthenticatedUser currentUser) throws AccountServiceException {

        double currentBalance = accountService.balanceRequest(currentUser.getToken());

        if (amount.compareTo(currentBalance) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private Transfer createPendingTransfer(int sendId, Double amountDouble) throws TransferServiceException, AccountServiceException {
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(1);
        transfer.setTransferStatusId(1);
        transfer.setAccountFromId(accountService.getAccountIdFromUsername(currentUser.getToken(), currentUser.getUser().getUsername()));
        transfer.setAccountToId(accountService.getAccountIdFromUserId(currentUser.getToken(), sendId));

        if (validateTransferAmount(amountDouble, currentUser)) {
            transfer.setAmount(amountDouble);
        } else {
            throw new TransferServiceException("Insufficient Funds. ");
        }
        return transfer;
    }



}
