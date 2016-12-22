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
    public static void main(String[] args) throws FileNotFoundException
    {
        float density = 0f;
        String filename = "/home/isaacisback/workspace/Homework1/src/ponzi/federico/homeworkone/game3.txt";
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
        BigInteger ss = w.getSolutionSpace();
        System.out.println("File name: " + filename);

        printTable(table);
        for(Coordinates coord : w.emptyCells.keySet()){
            System.out.print("Chiave:" + coord + " -> ");
            w.emptyCells.getAdjacents(coord).forEach(System.out::print);
            System.out.println();
        }
        System.out.println("Size:" + w.emptyCells.keySet().size());
        System.out.println("Empty cells:" + (81-(int)density));
        System.out.println("fill factor: " + ((int)((density/81)*100)) + "%");
        System.out.println("Search space before elimination: " + ss.toString() + " (len:" + ss.toString().length() +")");
        System.out.println("Searching in parallel:");
        long t0 = System.currentTimeMillis();
        int i = computeSolutions(table, w.emptyCells);
        long t1 = System.currentTimeMillis();
        System.out.println("Numero di soluzioni: " + i);
        System.out.println("Tempo passato (in parallelo): " + (t1 - t0) + "ms");

    }
    private static int computeSolutions(Cell[][] table, EmptyCellGraph ecg)
    {
        ComputeSolutions t = new ComputeSolutions(table, ecg);
        return fjPool.invoke(t);
    }
    public static void printTable(Cell[][] t)
    {
        for(Cell[] cells : t)
        {
            for(Cell c : cells)
            {
                //System.out.print("["+ c.getY() +"]["+c.getX() +"]");
                System.out.print(c);
            }
        }
    }
}
