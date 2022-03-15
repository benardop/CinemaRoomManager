package cinemaroom;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static int ticketCount = 0;
    static int backRowSeatCount = 0;
    static int frontRowSeatCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int cinemaLength = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int cinemaWidth = scanner.nextInt();
        if (cinemaWidth > 9 || cinemaLength > 9) {
            System.out.println("Wrong input!");
            System.out.println("Enter the number of rows:");
            cinemaLength = scanner.nextInt();
            System.out.println("Enter the number of seats in each row:");
            cinemaWidth = scanner.nextInt();
        }
        String openSeat = "S ";
        String paidSeat = "B ";

        String[][] cinema = cinemaSeating(cinemaLength, cinemaWidth, openSeat);


        theatreMenu(cinema, cinemaLength, cinemaWidth);

    }

    public static void theatreMenu(String[][] cinema, int cinemaLength, int cinemaWidth) {

        Scanner menuInput = new Scanner(System.in);
        boolean quitProgram = true;
        String menuMessage = ("1. Show the seats \n" +
                "2. Buy a ticket \n" +
                "3. Statistics \n" +
                "0. Exit");

        do {
            System.out.println(menuMessage);
            int menuOption = menuInput.nextInt();
            System.out.print("\n");

            switch (menuOption) {
                case 1:
                    printTheatre(cinema, cinemaLength, cinemaWidth);
                    break;
                case 2:
                    fillSeat(cinema, cinemaLength, cinemaWidth);
                    System.out.print("\n");
                    break;
                case 3:
                    getStatistics();
                    getSeatPercentage(cinemaWidth, cinemaLength);
                    System.out.println();
                    int currentIncome = getCurrentIncome(cinemaLength, cinemaWidth);
                    System.out.println("Current income: " + "$" + currentIncome);
                    int totalSales = getTotalSales(cinemaLength, cinemaWidth);
                    System.out.println("Total income: " + "$" + totalSales);
                    System.out.println();
                    break;
                case 0:
                    quitProgram = false;
                    menuInput.close();
                    break;
            }
        }
        while (quitProgram);
    }

    private static void printTheatre(String[][] cinema, int cinemaLength, int cinemaWidth) {

        System.out.print("Cinema:\n  ");

        for (int i = 0; i < cinemaWidth; i++) {
            System.out.print(i + 1 + " ");
        }

        System.out.println();

        for (int i = 0; i < cinemaLength; i++) {
            for (int j = 0; j < cinemaWidth; j++) {
                if (j == 0) {
                    System.out.print((i + 1) + " " + cinema[i][j]);
                } else {
                    System.out.print(cinema[i][j]);
                }
            }

            System.out.println();
        }
    }

    private static void fillSeat(String[][] cinema, int cinemaLength, int cinemaWidth) {

        int seatPrice = 0;
        int frontRows = (cinemaLength / 2) - 1;
        int backRows = (cinemaLength / 2) + 1;
        int seats = cinemaLength * cinemaWidth;
        boolean status = true;
        int rowPick;
        int seatPick;

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Enter a row number:");
            rowPick = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            seatPick = scanner.nextInt() - 1;
            if (rowPick > cinema.length - 1 || seatPick > cinema.length -1) {
                System.out.println("Wrong input!");
                System.out.println("Enter a row number:");
                rowPick = scanner.nextInt() - 1;
                System.out.println("Enter a seat number in that row:");
                seatPick = scanner.nextInt() - 1;
            } else if (cinema[rowPick][seatPick].equals("S ") && rowPick <= frontRows) {
                cinema[rowPick][seatPick] = "B ";
                frontRowSeatCount += 1;
                ticketCount = backRowSeatCount + frontRowSeatCount;
                status = false;
            } else if (cinema[rowPick][seatPick].equals("S ") && rowPick >= frontRows) {
                cinema[rowPick][seatPick] = "B ";
                backRowSeatCount += 1;
                ticketCount = backRowSeatCount + frontRowSeatCount;
                status = false;

            } else if (cinema[rowPick][seatPick].equals("B ")){
                System.out.println("That ticket has already been purchased!");
                System.out.println();
                status = true;
            }
        } while (status);


        if (seats <= 60) {
            System.out.println("Ticket Price:" + "\n" + "$10" + "\n");
        } else if (seats > 60 && rowPick <= frontRows) {
            System.out.println("Ticket Price:" + "\n" + "$10" + "\n");
        } else if (rowPick >= frontRows)
            System.out.println("Ticket Price:" + "\n" + "$8" + "\n");
    }

    private static String[][] cinemaSeating(int cinemaLength, int cinemaWidth, String openSeat) {

        String[][] cinema = new String[cinemaLength][cinemaWidth];
        for (String[] row : cinema) {
            Arrays.fill(row, openSeat);
        }
        return cinema;
    }

    public static void getStatistics() {
        System.out.println("Number of purchased tickets: " + ticketCount);
    }

    public static void getSeatPercentage(int cinemaLength, int cinemaWidth) {
        double totalSeats = (double) cinemaLength * cinemaWidth;
        double seatPercentage = (ticketCount / totalSeats) * 100;
        System.out.format("Percentage %.2f", seatPercentage);
        System.out.print("%");
    }

    public static int getTotalSales(int cinemaLength, int cinemaWidth) {
        int totalSeat = cinemaLength * cinemaWidth;
        int seatPrice = 0;
        int frontRowSales = 0;
        int backRowSales = 0;
        int backRows = 0;
        int frontRows = 0;
        int totalSales = 0;
        if (totalSeat <= 60) {
            seatPrice = 10;
            totalSales = seatPrice * totalSeat;
        } else if (totalSeat > 60 && totalSeat % 2 == 0){
            frontRows = cinemaWidth / 2;
            frontRowSales = frontRows * 10 * cinemaWidth;

            backRows = cinemaWidth / 2;
            backRowSales = backRows * 8 * cinemaWidth;
            totalSales = frontRowSales + backRowSales;
        } else if ( totalSeat > 60 && totalSeat % 2 != 0) {
            frontRows = (cinemaWidth - 1) / 2;
            frontRowSales = frontRows * 10 * cinemaWidth;
            backRows = (cinemaWidth + 1) / 2;
            backRowSales = backRows * 8 * cinemaWidth;
            totalSales = frontRowSales + backRowSales;
        }
        return totalSales;
    }

    public static int getCurrentIncome(int cinemaLength, int cinemaWidth) {
        int totalSeat = cinemaLength * cinemaWidth;
        int frontRows;
        int frontRowIncome;
        int backRows;
        int backRowIncome;
        int currentIncome = 0;
        int seatPrice = 0;
        if (totalSeat <= 60) {
            seatPrice = 10;
            currentIncome = seatPrice * frontRowSeatCount;
        } else if (totalSeat > 60 && totalSeat % 2 != 0){
            frontRows = (cinemaWidth - 1) / 2;
            frontRowIncome = 10 * frontRowSeatCount;
            backRows = (cinemaWidth + 1) / 2;
            backRowIncome = 8 * backRowSeatCount;
            currentIncome = frontRowIncome + backRowIncome;
        } else if (totalSeat > 60 && totalSeat % 2 == 0) {
            frontRows = (cinemaWidth - 1) / 2;
            frontRowIncome = 10 * frontRowSeatCount;
            backRows = (cinemaWidth + 1) / 2;
            backRowIncome = 8 * backRowSeatCount;
            currentIncome = frontRowIncome + backRowIncome;
        }
        return  currentIncome;
    }

}
