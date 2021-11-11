package com.techelevator.view;


import com.techelevator.tenmo.model.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ConsoleService {

    private PrintWriter out;
    private Scanner in;

    public ConsoleService(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        out.println();
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public String getUserInput(String prompt) {
        out.print(prompt + ": ");
        out.flush();
        return in.nextLine();
    }

    public Integer getUserInputInteger(String prompt) {
        Integer result = null;
        do {
            out.print(prompt + ": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while (result == null);
        return result;
    }

    public Double getUserInputDouble(String prompt) {
        Double result = null;
        do {
            out.print(prompt + ": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Double.parseDouble(userInput);
            } catch (NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while (result == null);
        return result;
    }

    public void displayBalance(double balance) {
        DecimalFormat df = new DecimalFormat("$###,##0.00");
        out.printf("Your current account balance is: %11s\n", df.format(balance));
    }


    public void displayUsers(User[] users, AuthenticatedUser currentUser) {
        out.println("---------------------------------------------");
        out.println("Users");
        out.println("ID          Name");
        out.println("---------------------------------------------");
        for (User user : users) {
            if (!user.getUsername().equalsIgnoreCase(currentUser.getUser().getUsername())) {
                out.printf("%-5s       %s\n", user.getId(), user.getUsername());
            }
        }
        out.println("---------------------------------------------");
    }


    public void displayBucksSent(Transfer transfer) {
        out.println();
        out.println("Transfer " + transfer.getTransferId() + " successful!");
    }


    //TODO: Format numbers to represent money.
    public void displayTransferDetails(TransferDetail[] transferDetails, AuthenticatedUser currentUser) {
        out.println("---------------------------------------------");
        out.println("Transfers");
        out.println("ID          From/To                 Amount");
        out.println("---------------------------------------------");
        for (TransferDetail transferDetail : transferDetails) {
            if (transferDetail.getUserFromId().equals(currentUser.getUser().getId()) && transferDetail.getTransferStatusId().equals(2)) {
                out.println(transferDetail.getTransferId() + "      To: " + transferDetail.getUserToUsername() + "                 " + transferDetail.getAmount());
            }
            if (transferDetail.getUserToId().equals(currentUser.getUser().getId()) && transferDetail.getTransferStatusId().equals(2)) {
                out.println(transferDetail.getTransferId() + "    From: " + transferDetail.getUserFromUsername() + "                 " + transferDetail.getAmount());
            }
        }
    }
        //TODO: Format numbers to represent money.
    public void displaySingleTransferDetail(TransferDetail[] transferDetails, Integer transferId){
        out.println("---------------------------------------------");
        out.println("Transfer Details");
        out.println("---------------------------------------------");
        for(TransferDetail transferDetail : transferDetails){
            if(transferDetail.getTransferId().equals(transferId)){
                out.println("Id: " + transferDetail.getTransferId());
                out.println("From: "+ transferDetail.getUserFromUsername());
                out.println("To: "+ transferDetail.getUserToUsername());
                out.println("Type: "+transferDetail.getTransferTypeDesc());
                out.println("Status: "+transferDetail.getTransferStatusDesc());
                out.println("Amount: "+transferDetail.getAmount());
            }

        }


    }
    public void displayPendingRequest(Transfer transfer) {
        out.println();
        out.println("Request for " + transfer.getAmount() +" from user: "+transfer.getAccountToId() + " successful!");
    }

    public void displayPendingTransfers(TransferDetail[] transferDetails, AuthenticatedUser currentUser) {
        out.println("---------------------------------------------");
        out.println("Pending Transfers");
        out.println("ID              To:                 Amount");
        out.println("---------------------------------------------");
        for (TransferDetail transferDetail : transferDetails) {
            if (transferDetail.getUserFromId().equals(currentUser.getUser().getId()) && transferDetail.getTransferStatusId().equals(1)) {
                out.println(transferDetail.getTransferId() +"            "+ transferDetail.getUserToUsername() + "                 " + transferDetail.getAmount());
            }

        }
    }

    //view pending requests



}

