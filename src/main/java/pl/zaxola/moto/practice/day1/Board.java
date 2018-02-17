package pl.zaxola.moto.practice.day1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.Scanner;

@AllArgsConstructor
public class Board {
    final static String SYMBOL_EMPTY = "░"; //low density dotted rectangle
    final static String SYMBOL_FILLED = "▓"; //filled rectangle

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private boolean [][] data;

    public Board(int w, int h) {
        data = new boolean[h][w];
        width = w;
        height = h;
    }

    public boolean isAlive(int x, int y) {
        return data[y][x];
    }

    public int getNumberOfNeighbours(int x, int y) {
        final int fromX = Math.max(0, x - 1),
                fromY = Math.max(0, y - 1),
                toX = Math.min(x + 1, width - 1),
                toY = Math.min(y + 1, height - 1);

        int fieldsAliveFound = 0;

        // count all alive fields, counting the one passed as argument
        for (int loopY = fromY; loopY <= toY; loopY++) {
            for (int loopX = fromX; loopX <= toX; loopX++){
                if (isAlive(loopX, loopY)) {
                    fieldsAliveFound++;
                }
            }
        }

        // count out the one passed as argument, if alive
        try {
            if (isAlive(x, y)) {
                fieldsAliveFound--;
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Bad input - point not on board!");
        }

        return fieldsAliveFound;
    }

    private boolean getNewStateFor(int x, int y) {
        int numberOfNeighbours = getNumberOfNeighbours(x, y);
        if (isAlive(x, y)) {
            return (numberOfNeighbours == 2 || numberOfNeighbours == 3);
        } else {
            return numberOfNeighbours == 3;
        }
    }

    public void generateNextStep() {
        boolean [][] newData = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newData[y][x] = getNewStateFor(x, y);
            }
        }

        data = newData;
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void drawInConsole() {
        clearConsole();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(isAlive(x, y)? SYMBOL_FILLED : SYMBOL_EMPTY);
            }
            System.out.println();
        }
    }

    public static Board parse(String input) throws ParseException {
        Scanner scanner = new Scanner(input);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        Board board = new Board(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int num = scanner.nextInt();
                board.data[y][x] = parseIntToBoolean(num);
            }
        }

        return board;
    }

    public static Board fromBooleanArray(boolean[][] data) {
        int height = data.length,
            width = data[0].length;
        Board board = new Board(width, height);
        board.data = data;
        return board;
    }

    private static boolean parseIntToBoolean(int num) throws ParseException {
        switch (num) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new ParseException();
        }
    }

    public static class ParseException extends Exception {}
}
