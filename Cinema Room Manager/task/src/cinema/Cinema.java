package cinema;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Cinema {

    public static void main(String[] args) {
        Room room = new Room(InputUtils.inputRows(), InputUtils.inputSeats());
        while (true) {
            switch (InputUtils.inputMenuOption()) {
                case 1:
                    room.printRoom();
                    break;
                case 2:
                    while (!room.buyTicket(InputUtils.inputRow(), InputUtils.inputSeat())) ;
                    break;
                case 3:
                    room.caclAndPrintStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void askForSeat(int rows, int seatsByRow, String[][] layout) {
        boolean isCorrect = true;
        while (isCorrect) {
            printInput("Enter a row number:");
            int rowSel = askInput();
            printInput("Enter a seat number in that row:");
            int seatSel = askInput();
            if (rowSel > rows || seatSel > seatsByRow) {
                printInput("Wrong input!");
                continue;
            }
            boolean isNotEmpty = layout[rowSel - 1][seatSel - 1] == "S" ? false : true;
            if (isNotEmpty) {
                printInput("That ticket has already been purchased!");
                continue;
            }
            layout[rowSel - 1][seatSel - 1] = "B";
            printLayout(layout);
            int priceCalculated = calculatePrice(rows, seatsByRow, rowSel);
            printInput("Ticket Price: $" + priceCalculated);
            isCorrect = false;
        }
    }


    private static void printStadistics(String[][] layout) {
        int row = layout.length;
        int seatsByRow = layout[0].length;
        int numberOfSeats = row * seatsByRow;
        int ticketsPurchased = calculateTicketsPurchased(layout);
        printInput("Number of purchased tickets: " + ticketsPurchased);
        printInput("Percentage: " + calculatePercentage(ticketsPurchased, numberOfSeats) + "%");
        printInput("Current income: $" + calculateCurrentIncome(layout, numberOfSeats, row ,seatsByRow));
        printInput("Total income: $" + calculateTotalIncome(row, seatsByRow));
    }

    private static BigDecimal calculatePercentage(int ticketsPurchased, int numberOfSeats) {
        BigDecimal percentage = new BigDecimal("0.00");
        if (numberOfSeats != 0) {
            percentage = new BigDecimal(ticketsPurchased)
                    .divide(new BigDecimal(String.valueOf(numberOfSeats)), 4, RoundingMode.HALF_UP);
        }
        return percentage.multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
    }


    private static int calculateCurrentIncome(String[][] layout , int seats, int rows, int seatByRow) {
        int totalIncome = 0;
        for(int i = 0 ; i < layout.length ; i++) {
            for (int j = 0 ; j < seatByRow ; j++) {
                if(layout[i][j] == "B") {
                    int price = calculatePrice(rows, seats, i + 1);
                    totalIncome = totalIncome + price;
                }
            }
        }
        return totalIncome;
    }

    private static int calculateTotalIncome(int rows, int seatsByRow) {
        int seats = rows * seatsByRow;
        return seats <= 60 ? 10 * seats
                : (rows / 2) * (seats / rows) * 10 + ((rows + 1) / 2) * (seats / rows) * 8;
    }

    private static int calculatePrice(int rows, int seatsByRow, int rowSel) {
        int seats = rows * seatsByRow;
        return rows * seats <= 60 ? 10 : rowSel <= rows / 2 ? 10 : 8;
    }

    private static int calculateTicketsPurchased(String[][] layout) {
        int numberOfTickets = 0;
        for (String[] s : layout) {
            for (String d : s) {
                if (d.equals("B")) {
                    numberOfTickets++;
                }
            }
        }
        return numberOfTickets;
    }

    private static int numberOfSeats(String[][] layout) {
        return (layout.length * layout[0].length);
    }


    private static void printMenu() {
        printInput("1. Show the seats");
        printInput("2. Buy a ticket");
        printInput("3. Statistics");
        printInput("0. Exit");
    }

    private static void printInput(String message) {
        System.out.println(message);
    }

    private static int askInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static String[][] loadLayout(int rows, int seatsByRow) {
        String[][] layout = new String[rows][seatsByRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsByRow; j++) {
                layout[i][j] = "S";
            }
        }
        return layout;
    }

    private static void printLayout(String[][] layout) {
        printInput("Cinema:");
        int seatsByRow = layout.length;
        int rows = 1;
        IntStream it = IntStream.range(1, seatsByRow + 2);
        System.out.print(" ");
        it.forEach(x -> {
            if (x <= 9) {
                System.out.print(" " + x);
            }
        });
        newLine();
        for (String[] array : layout) {
            System.out.print(rows + " ");
            for (String n : array) {
                System.out.print(n + " ");
            }
            newLine();
            rows++;
        }
    }

    private static void newLine() {
        System.out.println();
    }
}