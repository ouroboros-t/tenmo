package com.techelevator.view;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

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

    //TODO: Figure out how to display: userId, userName, transferAmount. Right now it's showing all transfers.
    public void displayTransferHistory(User[] users, Transfer[] transfers, AuthenticatedUser currentUser) {
        out.println("---------------------------------------------");
        out.println("Transfers");
        out.println("ID          From/To                 Amount");
        out.println("---------------------------------------------");

        for (User user : users) {
            if (currentUser.getUser().getUsername().equals(user.getUsername())) {
                for (Transfer transfer : transfers) {
                    if (!transfer.getAccountFromId().equals(currentUser.getUser().getId() + 1000)) {
                        out.println((transfer.getAccountFromId() - 2000) + "   " + user.getUsername()+"   "  + transfer.getAmount());
                        //ToDO: get it to display the right username
                    }
                    if (transfer.getAccountFromId().equals(user.getId() + 1000)) {
                        out.println((transfer.getAccountToId() - 2000) + "   " + user.getUsername()+"   " + transfer.getAmount());
                    }

                }
            }
        }

//        //ToDO: The "To" account needs to reflect which user money went to. Currently shows only current Username..
//        if (transfer.getAccountToId().equals(currentUser.getUser().getId()+ 1000)) { //couldn't think of another way!
//            if(user.getUsername().equals(user.getUsername())) {
//                out.println("         To: " + user.getUsername() + "                   " + transfer.getAmount());
//            }
//        }
//        if (transfer.getAccountFromId().equals(user.getId() + 1000)) {
//            if(!user.getUsername().equals(currentUser.getUser().getUsername())) {
//                out.println("        From: " + user.getUsername() + "                 " + transfer.getAmount());
//            }
//        }

//        for (Transfer transfer : transfers) {
//            //I know there's a better way...just don't know the way right now...
//            if(transfer.getAccountToId().equals(currentUser.getUser().getId() + 1000)) {
//                out.println("          "+"To You:" +"                   "+transfer.getAmount());
//            }
//            if(transfer.getAccountFromId().equals(currentUser.getUser().getId() + 1000)){
//                out.println("          "+"From You:"+"                 "+transfer.getAmount());
//            }
//        }
        out.println("---------------------------------------------");
    }


}

