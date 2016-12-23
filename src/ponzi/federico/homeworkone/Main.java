package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Cell;
import ponzi.federico.homeworkone.entities.Coordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    public final static int SIZE = 9;
    public static void main(String[] args) throws FileNotFoundException, RuntimeException
    {
        float density = 0f;
        String filename = "/home/isaacisback/workspace/Homework1/src/res/game3.txt";
        if(args.length > 1)
        {
            filename = args[1];
        }

        Scanner s = new Scanner(new File(filename));

        Cell[][] table = new Cell[Main.SIZE][Main.SIZE];
        String row = "";
        int val = -1;
        for(int r = 0; r < SIZE; r++)
        {
            row = s.nextLine();
            table[r] = new Cell[Main.SIZE];
            for (int c = 0; c < SIZE; c++)
            {

                if(row.charAt(c) == '.')
                {
                    val = -1;
                }
                else
                {
                    density++;
                    val = Character.getNumericValue(row.charAt(c));
                }

                table[r][c] = new Cell(r, c, val);;
            }
        }
        // End of initialize.
        Worker w = new Worker(table);

        EmptyCellGraph emptyCellGraph = w.getEmptyCellGraph();
        System.out.println("File name: " + filename);
        System.out.println("Empty cells:" + emptyCellGraph.ec.size());
        System.out.println("Fill factor: " + (int)((((float)emptyCellGraph.ec.size())/81)*100) + "%");
        BigInteger ss = new BigInteger("1");
        for(Coordinates c : emptyCellGraph.cand.keySet())
        {
            ss = ss.multiply(new BigInteger(emptyCellGraph.cand.get(c).size() + ""));
        }
        System.out.println("Search space before elimination: " + ss.toString() + " (len:" + ss.toString().length() +")");
        System.out.println("Searching in parallel:");
        long t0 = System.currentTimeMillis();
        int i = computeSolutions(w.emptyCells);
        long t1 = System.currentTimeMillis();
        System.out.println("Numero di soluzioni: " + i);
        System.out.println("Tempo passato (in parallelo): " + (t1 - t0) + "ms");
    }
    private static int computeSolutions(EmptyCellGraph ecg)
    {
        EfficientComputeSolutions t = new EfficientComputeSolutions(ecg);
        return fjPool.invoke(t);
    }

}
