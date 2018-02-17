package pl.zaxola.moto.practice.day1;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Board.ParseException, InterruptedException {
        Board board = Board.parse("7 6 " +
                "1 0 0 0 1 0 1 " +
                "0 1 0 1 0 1 0 " +
                "0 0 1 0 1 0 1 " +
                "0 1 1 0 1 0 1 " +
                "0 1 0 1 0 1 0 " +
                "1 0 1 0 1 0 1");
        while (true) {
            board.generateNextStep();
            board.drawInConsole();
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
