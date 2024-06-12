//importing necessary packages
import java.util.*;
import java.io.*;

public class Theatre {
    //creating a ragged array containing 3 rows
    static int[][] rows_array = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    //initializing the scanner
    static Scanner input = new Scanner(System.in);

    //    ********************************************************************************************************************
    //main method
    public static void main(String [] args) throws Exception {
        //initializing variable valid to true
        boolean repeat = true;
        int task_num;
        //creating an arraylist for the tickets
        ArrayList<Ticket> ticket_arraylist=new ArrayList<Ticket>();

        //displaying the welcome statement
        System.out.println("\nWelcome to the New Theatre");
        while (repeat) {
            //displaying the menu
            System.out.print("-".repeat(100)+"\n");
            System.out.println("\n1)Buy a ticket\n2)Print seating area\n3)Cancel ticket\n4)List available seats\n5)Save the file\n6)Load from file\n7)Print ticket information and total price\n8)Sort tickets by price\n0)Quit\n");
            System.out.print("-".repeat(100)+"\n");

            System.out.print("Select an option (0-8) :");
            if (input.hasNextInt()){
                task_num= input.nextInt();

                switch (task_num) {
                    case 1 -> buy_ticket(ticket_arraylist);     //calling the method to buy ticket
                    case 2 -> print_seating_area(); //calling the method to print the seating area
                    case 3 -> cancel_ticket(ticket_arraylist);   //calling the method to cancel the reservations
                    case 4 -> show_available();  //calling the method to show available seats
                    case 5 -> save(); //calling the method to save the arrays into a text file
                    case 6 -> load(); //calling the method to load and restore the arrays from the text file
                    case 7 ->
                            show_tickets_info(ticket_arraylist);   //calling the method to print the ticket information
                    case 8 -> {
                        merge_sort(ticket_arraylist, ticket_arraylist.size());   //calling the method to sort the tickets according to their price
                        for (Ticket ticket :ticket_arraylist) {
                            ticket.print();
                        }
                    }
                    case 0 -> {
                        repeat = false;
                        System.out.print("Thank you!\n");
                    }
                    default -> System.out.print("The input is out of range\n");
                }
            }
            else{
                System.out.print("The input is not an integer.\n");
                input.next();
            }
        }
    }
    //    ********************************************************************************************************************
    //method for buying tickets
    private static void buy_ticket(ArrayList <Ticket> ticket_array) {
        boolean repeat = true;
        while (repeat) {
            System.out.print("\nEnter the name of the buyer: ");
            String name=input.next();   //inputting the name of the buyer

            System.out.print("Enter the surname of the buyer: ");
            String surname=input.next();   //inputting the surname of the buyer

            //calling the method to get the valid email of the buyer
            String email=valid_email();

            //calling the method to get the valid price of the seat
            double price=valid_price();

            while (true) {
                try {
                    System.out.print("\nEnter the row number(1-3) : ");
                    int row_num = input.nextInt();  //inputting the row number
                    if (row_num > 0 && row_num <= 3) {   //inputting the seat number if the row number is valid
                        boolean invalid_seat = true;
                        while (invalid_seat) {
                            try {
                                System.out.print("\nEnter the seat number :");
                                int seat_num = input.nextInt();      //inputting the seat number
                                String success_statement = "Your selected seat was successfully reserved.";
                                String unavailable_statement = "The seat you want is already taken.";
                                if (row_num == 1) {
                                    //calling the method to reserve seats in the 1st row
                                    invalid_seat = ticket_status_change(1, seat_num, 0, 1, success_statement, unavailable_statement);
                                }
                                else if (row_num == 2) {
                                    //calling the method to reserve seats in the 2nd row
                                    invalid_seat = ticket_status_change(2, seat_num, 0, 1, success_statement, unavailable_statement);
                                }
                                else {
                                    //calling the method to reserve seats in the 3rd row
                                    invalid_seat = ticket_status_change(3, seat_num, 0, 1, success_statement, unavailable_statement);
                                }
                                //breaking the loop if the seat number is in range of row
                                if (!invalid_seat) {
                                    //creating a new ticket
                                    Ticket Ticket1=new Ticket(row_num,seat_num,price,name,surname,email);
                                    ticket_array.add(Ticket1);

                                    repeat = valid_repeat("for reserving seats", "reserve another seat");
                                }
                            }
                            catch (Exception exception) {
                                System.out.println("The input is not an integer");
                                input.next();
                            }
                        }
                        break;
                    }
                    else {
                        System.out.println("The input row number is out of range.");
                    }
                }
                catch (Exception exception) {
                    System.out.println("The input is not an integer");
                    input.next();
                }
            }
        }
    }

    //    ********************************************************************************************************************
    //method for changing the status of the seats in each specific row
    private static boolean ticket_status_change(int row_num, int seat_num, int needed_status, int change_status, String success, String unavailable) {
        boolean invalid_seat = true;

        if (seat_num > 0 && seat_num <= rows_array[row_num-1].length) {    //checking whether the seat number is situated in the needed row
            if (rows_array[row_num - 1][seat_num - 1] == needed_status) {
                rows_array[row_num - 1][seat_num - 1] = change_status;    // changing the status of the seat as needed
                System.out.println(success+"\n");
                invalid_seat = false;
            }
            else {
                System.out.println(unavailable);
            }
        }
        else {
            System.out.println("The input seat number is out of range.Please enter a valid seat number from 1 to " + rows_array[row_num-1].length+"\n");
        }
        return invalid_seat;
    }

    //    ********************************************************************************************************************
    //method to find if the user need to repeat reserving seats
    private static boolean valid_repeat(String thankyou, String que) {
        boolean repeat = true;
        while (true) {
            System.out.print("\nDo you want to " + que + "? (y for yes and n or no) :");
            String want_to_repeat = input.next().toLowerCase();
            if (want_to_repeat.equals("n") || want_to_repeat.equals("y")) {
                if (want_to_repeat.equals("n")) {
                    System.out.println("Thank you " + thankyou + ".");
                    repeat = false;
                }
                break;
            }
            else {
                System.out.println("Input is not valid.\n");
            }
        }
        return repeat;  //returning the value for repeat
    }

    //************************************************************************************************************************
    //method to display seating area
    private static void print_seating_area() {
        //displaying the first row of stars
        System.out.println();
        System.out.print("      ");  //printing spaces to gain the required indentation
        for (int i = 1; i < 12; i++) {
            System.out.print("*");
        }
        System.out.println();

        //displaying the line containing the word stage
        System.out.println("      *  STAGE  *");

        //displaying the second row of stars
        System.out.print("      ");  //printing spaces to gain the required indentation
        for (int i = 1; i < 12; i++) {
            System.out.print("*");
        }
        System.out.println("\n");     //line breaker
        //calling the method to display the first row
        row_display("     ", 1, 5, 11);
        //calling the method to display the second row
        row_display("   ", 2, 7, 15);
        //calling the method to display the third row
        row_display(" ", 3, 9, 19);
        System.out.println();     //displaying an empty line
    }
    // ********************************************************s**************************************************************
    //method to display seats in each row
    private static void row_display(String space, int row, int end_first_group, int end_second_group) {
        System.out.print(space);
        //displaying the first group of seats in the row
        for (int i = 0; i <= end_first_group; i++) {
            if (rows_array[row - 1][i] == 1) {
                System.out.print("X");
            }
            else {
                System.out.print("O");
            }
        }
        System.out.print(" ");   //displaying a space between the two groups of seats
        //displaying the second group of seats in the row
        for (int i = end_first_group + 1; i <= end_second_group; i++) {
            if (rows_array[row - 1][i] == 1) {
                System.out.print("X");
            }
            else {
                System.out.print("O");
            }
        }
        System.out.println();           //displaying an empty line
    }

    //    ********************************************************************************************************************
    //method to cancel tickets
    private static void cancel_ticket(ArrayList <Ticket> ticket_array) {
        boolean repeat = true;
        String success_statement = "Your ticket was successfully cancelled.";
        String unavailable_statement = "The reservation you selected had not been made.";
        while (repeat) {
            while (true) {
                try {
                    System.out.println();
                    System.out.print("Enter the row number(1-3) : ");
                    int row_num=input.nextInt();
                    if (row_num > 0 && row_num <= 3) {
                        boolean invalid_seat = true;
                        while (invalid_seat) {
                            try {
                                System.out.print("Enter the seat number :");
                                int seat_num=input.nextInt();
                                if (row_num == 1) {
                                    //calling the method to cancel seats in the 1st row
                                    invalid_seat = ticket_status_change(1, seat_num, 1,  0,success_statement, unavailable_statement);
                                    //repeating the process if the seat number is invalid
                                    if (invalid_seat){
                                        repeat = valid_repeat("for canceling seats", "cancel another seat");
                                        break;
                                    }
                                }
                                else if (row_num == 2) {
                                    //calling the method to cancel seats in the 2nd row
                                    invalid_seat = ticket_status_change(2, seat_num, 1, 0, success_statement, unavailable_statement);
                                    //repeating the process if the seat number is invalid
                                    if (invalid_seat){
                                        repeat = valid_repeat("for canceling seats", "cancel another seat");
                                        break;
                                    }
                                }
                                else {
                                    //calling the method to cancel seats in the 3rd row
                                    invalid_seat = ticket_status_change(3, seat_num,1, 0, success_statement, unavailable_statement);
                                    //repeating the process if the seat number is invalid
                                    if (invalid_seat){
                                        repeat = valid_repeat("for canceling seats", "cancel another seat");
                                        break;
                                    }
                                }
                                //breaking the loop if the seat number is in range of row
                                repeat = valid_repeat("for canceling seats", "cancel another seat");
                                for(int i=0; i<ticket_array.size();i++) {
                                    //checking if the seat and row of each ticket match the seat_num and row_num.
                                    if(ticket_array.get(i).getRow()==row_num && ticket_array.get(i).getSeat()==seat_num) {
                                        ticket_array.remove(i); //removing the ticket if the ticket matches
                                    }
                                }
                            }
                            catch (Exception e) {
                                System.out.println("The input seat is not an integer\n");
                                input.next();
                            }
                        }
                        break;  //breaking the loop valid_row since the row number is valid
                    }
                    else {
                        System.out.println("The input row number is out of range.\n");
                    }
                }
                catch (Exception e) {
                    System.out.println("The row input is not an integer");
                    input.next();
                }
            }
        }
    }
    //    ********************************************************************************************************************
    //method to display the available seat slots
    private static void show_available() {
        System.out.println();
        for (int r = 1; r <= rows_array.length; r++) {
            System.out.print("Seats available in row " + r + ": ");
            for (int s = 1; s < rows_array[r-1].length+1; s++) {
                if (rows_array[r-1][s - 1] == 0) {
                    if (s == rows_array[r-1].length) {
                        System.out.print(s + ".");
                    }
                    else {
                        System.out.print(s + ",");
                    }

                }
            }
            System.out.println();
        }
        System.out.println();
    }
    //    ********************************************************************************************************************
    //method to save the arrays into a text file
    private static void save() {
        try {
            FileWriter myWriter = new FileWriter("array_text.txt");
            for (int[] ints : rows_array) {
                for (int anInt : ints) {
                    myWriter.write(anInt + " ");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("\nSuccessfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //    ********************************************************************************************************************
    //method to load and restore the arrays from the text file
    private static void load() throws Exception {
        Scanner read=new Scanner(new FileReader("array_text.txt"));
        while (read.hasNextLine()) {
            for (int i=1;i-1<rows_array.length;i++) {
                String[] line=read.nextLine().trim().split(" ");
                for(int s=0; s<line.length; s++){
                    rows_array[i-1][s]=Integer.parseInt(line[s]);
                }
                System.out.println("row"+i+" : "+Arrays.toString(rows_array[i-1]));
            }
            System.out.println();           //displaying an empty line
            System.out.println("The previous reservations were successfully restored");
        }
    }

    //    ********************************************************************************************************************
    //method to input the price of seats and find out if it is valid
    private static double valid_price() {
        double price;
        while (true) {
            try {
                System.out.print("Enter the price of the seat: ");
                price = input.nextDouble();
                if (price>0) {
                    break;
                }
                else {
                    System.out.println("Input number is a negative number.\n");
                }
            }
            catch (Exception e) {
                System.out.println("Input number is not of a double value.\n");
                input.next();
            }

        }
        return price;
    }
    //    ********************************************************************************************************************
    //method to find if the email is valid
    private static String valid_email() {
        String email;
        while(true) {
            System.out.print("Enter the email address: ");
            email = input.next();
            if(email.contains("@") && email.contains(".")) {
                break;
            }
            else {
                System.out.println("Invalid email.\n");
            }
        }
        return email;
    }
    //    ********************************************************************************************************************
    private static void show_tickets_info(ArrayList <Ticket> ticket_arraylist) {
        double tot_price=0;
        if(ticket_arraylist.size()==0){
            System.out.println("No seats have been purchased.");
        }
        else {
            for (Ticket ticket : ticket_arraylist) {
                ticket.print();
                System.out.println();
                tot_price = tot_price + ticket.getPrice();
            }
            System.out.println();           //displaying an empty line
            System.out.print("The total price of all the tickets is " + tot_price + "\n");
        }
    }
    //    ********************************************************************************************************************
    //method to sort the tickets
    private static void merge_sort(ArrayList <Ticket> ticket_arraylist, int length ) {
        if(ticket_arraylist.size()==0) {
            System.out.println("No tickets have been purchased to sort");
        }
        else {
            if (length < 2) {
                return;
            }
            int middle = length / 2;

            //creating 2 temporary arrays to store the divided array
            ArrayList<Ticket> left_ticket = new ArrayList<Ticket>(middle);
            ArrayList<Ticket> right_ticket = new ArrayList<Ticket>(length-middle);

            //adding values to the temporary arrays
            for (int i = 0; i < middle; i++) {
                left_ticket.add(ticket_arraylist.get(i));
            }
            for (int i = middle; i < length; i++) {
                right_ticket.add(ticket_arraylist.get(i));
            }
            //recursion of the method until arrays could no longer be divided
            merge_sort(left_ticket, middle);
            merge_sort(right_ticket, length - middle);

            //calling the merge method to sort and combine the divided arrays
            merge(ticket_arraylist, left_ticket, right_ticket, middle, length - middle);
        }
    }
    //method to merge the divided sub arrays
    private static void merge(ArrayList <Ticket> ticket_arraylist, ArrayList <Ticket> left_ticket, ArrayList <Ticket> right_ticket, int end_left,int end_right) {
        int l=0,m=0,n=0;  //initializing 3 variables to 0
        while (l <end_left && m<end_right) {
            if(left_ticket.get(l).getPrice() <= right_ticket.get(m).getPrice()) {
                ticket_arraylist.set(n,left_ticket.get(l));
                n++;
                l++;
            }
            else {
                ticket_arraylist.set(n,right_ticket.get(m));
                n++;
                m++;
            }
        }
        while(l<end_left) {
            ticket_arraylist.set(n,left_ticket.get(l));
            n++;
            l++;
        }
        while (m<end_right) {
            ticket_arraylist.set(n, right_ticket.get(m));
            n++;
            m++;
        }
    }
}








































































































































































































































