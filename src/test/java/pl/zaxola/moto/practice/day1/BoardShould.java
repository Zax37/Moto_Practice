package pl.zaxola.moto.practice.day1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BoardShould {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] {
                {"4 3 0 1 0 0 1 0 1 1 0 1 0 0", new boolean[][]{
                        {false, true, false, false},
                        {true, false, true, true},
                        {false, true, false, false}
                }, new boolean[][]{
                        {false, true, true, false},
                        {true, false, true, false},
                        {false, true, true, false}
                }, new int[][]{
                        {2, 2, 3, 2},
                        {2, 4, 3, 1},
                        {2, 2, 3, 2}
                }},
                {"3 3 0 1 0 0 0 1 1 1 1", new boolean[][]{
                        {false, true, false},
                        {false, false, true},
                        {true, true, true}
                }, new boolean[][]{
                        {false, false, false},
                        {true, false, true},
                        {false, true, true}
                }, new int[][]{
                        {1, 1, 2},
                        {3, 5, 3},
                        {1, 3, 2}
                }}
        });
    }

    public BoardShould(String input,
                       boolean[][] inputShouldBe,
                       boolean[][] nextStepShouldBe,
                       int[][] numberOfNeighboursShouldBe){
        this.input = input;
        this.inputShouldBe = inputShouldBe;
        this.nextStepShouldBe = nextStepShouldBe;
        this.numberOfNeighboursShouldBe = numberOfNeighboursShouldBe;
    }

    private final String input;
    private final boolean[][] inputShouldBe;
    private final boolean[][] nextStepShouldBe;
    private final int[][] numberOfNeighboursShouldBe;

    @Test
    public void parseInputCorrectly() {
        Board board;
        try {
            board = Board.parse(input);
            Assert.assertArrayEquals(inputShouldBe, board.getData());
        } catch (Board.ParseException exception) {
            exception.printStackTrace();
            assert false;
        }
    }

    @Test
    public void getNumberOfNeighboursCorrectly() {
        Board board = Board.fromBooleanArray(inputShouldBe);
        final int width = board.getWidth(),
                  height = board.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Assert.assertEquals(numberOfNeighboursShouldBe[y][x],
                        board.getNumberOfNeighbours(x, y));
            }
        }
    }

    @Test
    public void generateNextStepCorrectly() {
        Board board = Board.fromBooleanArray(inputShouldBe);
        board.generateNextStep();
        System.out.println(Arrays.deepToString(board.getData()));
        Assert.assertArrayEquals(nextStepShouldBe, board.getData());
    }
}
