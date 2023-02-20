package cinema;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Room {

    private int rows;
    private int seats;
    private char[][] arr;

    public Room(int rows, int seats) {
        this.rows = rows;
        this.seats = seats;
        this.arr = new char[rows][seats];
        Arrays.stream(arr).forEach(rowArr -> Arrays.fill(rowArr, 'S'));
    }

    void printRoom() {
        int rowsWithNum = rows + 1;
        int seatsWithNum = seats + 1;
        System.out.print("\nCinema:\n ");
        IntStream.range(1, rowsWithNum * seatsWithNum)
                .forEach(i -> System.out.printf(" %s",
                        i < seatsWithNum ? i : (i % seatsWithNum == 0 ? "\n" + i / seatsWithNum : arr[i / seatsWithNum - 1][i % seatsWithNum - 1])));
        System.out.println("\n");
    }

    boolean buyTicket(int row, int seat) {
        if (row > rows || seat > seats || row < 1 || seat < 1) {
            System.out.println("Wrong input!");
            return false;
        } else if (arr[row - 1][seat - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            return false;
        } else {
            arr[row - 1][seat - 1] = 'B';
            printPrice(row);
            return true;
        }
    }

    int getPrice(int row) {
        return rows * seats <= 60 ? 10 : row <= rows / 2 ? 10 : 8;
    }

    void printPrice(int row) {
        System.out.println("Ticket price: $" + getPrice(row));
    }

    void caclAndPrintStatistics() {
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        for (int r = 1; r <= rows; r++) {
            for (int s = 1; s <= seats; s++) {
                totalIncome += getPrice(r);
                if (arr[r - 1][s - 1] == 'B') {
                    purchasedTickets++;
                    currentIncome += getPrice(r);
                }
            }
        }
        float percentage = (float) purchasedTickets / (rows * seats) * 100;
        System.out.printf("Number of purchased tickets:%d\n" +
                        "Percentage: %.2f%%\n" +
                        "Current income: $%d\n" +
                        "Total income: $%d\n\n",
                purchasedTickets, percentage, currentIncome, totalIncome);
    }
}
