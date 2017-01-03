package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Cell;
import ponzi.federico.homeworkone.entities.Coordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
     public static int workers = 1;
    synchronized public static void addWorker()
    {
        workers++;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public final static int SIZE = 9;
    public static void main(String[] args) throws FileNotFoundException, RuntimeException
    {
        String filename;

        if(args.length >= 1)
        {
            throw new IllegalArgumentException("A path to the sudoku file is needed.");
        }
        filename = args[0];

        EmptyCellGraph emptyCellGraph = getTable(filename);

        System.out.println("File name: " + filename);
        System.out.println("Empty cells:" + emptyCellGraph.ec.size());
        System.out.println("Fill factor: " + (int)((((float)emptyCellGraph.ec.size())/81)*100) + "%");
        BigInteger ss = new BigInteger("1");
        for(Coordinates c : emptyCellGraph.cand.keySet())
        {
            ss = ss.multiply(new BigInteger(emptyCellGraph.cand.get(c).size() + ""));
        }
        System.out.println("Search space before elimination: " + ss.toString() + " (len:" + ss.toString().length() +")");

        if(args.length == 2)
        {
            switch (args[1])
            {
                case "--par":
                    runPar(emptyCellGraph);
                    break;
                case "--seq":
                    runSeq(emptyCellGraph);
                    break;
                default:
                    throw new IllegalArgumentException("Argument not recoginized:" + args[1]);
            }
        }else{
            runPar(emptyCellGraph);
            runSeq(emptyCellGraph);
        }
    }
    private static void runPar(EmptyCellGraph emptyCellGraph)
    {
        emptyCellGraph = new EmptyCellGraph(emptyCellGraph);
        System.out.println("Solving in parallel:");
        long paral0 = System.currentTimeMillis();
        int solParal = computeSolutions(emptyCellGraph, true, true);
        long paral1 = System.currentTimeMillis();
        System.out.println("Number of solutions:: " + solParal);
        System.out.println("Done in: " + (paral1 - paral0) + "ms");
        System.out.println("Spawned workers: " + workers);
        System.out.println("Pool:" + fjPool.toString());
        System.out.println();
    }

    private static void runSeq(EmptyCellGraph emptyCellGraph)
    {
        emptyCellGraph = new EmptyCellGraph(emptyCellGraph);
        System.out.println("Solving sequentially:");
        long seq0 = System.currentTimeMillis();
        int solSeq = computeSolutions(emptyCellGraph, false, false);
        long seq1 = System.currentTimeMillis();

        System.out.println("Number of solutions:: " + solSeq);
        System.out.println("Done in: " + (seq1 - seq0) + "ms");
        System.out.println();
    }
    private static int computeSolutions(EmptyCellGraph ecg, boolean isParallel, boolean halveThreads)
    {
        EfficientComputeSolutions t = new EfficientComputeSolutions(new EmptyCellGraph(ecg), isParallel, halveThreads);
        if(isParallel)
            return fjPool.invoke(t);
        return t.compute();
    }

    /**
     * Parses the file and returns a graph of the sudoku's empty cells.
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    private static EmptyCellGraph getTable(String filename) throws FileNotFoundException
    {
        Scanner s = new Scanner(new File(filename));

        Cell[][] table = new Cell[Main.SIZE][Main.SIZE];
        String row;
        int val;
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
                    val = Character.getNumericValue(row.charAt(c));
                }

                table[r][c] = new Cell(r, c, val);
            }
        }

        GraphBuilder w = new GraphBuilder(table);
        s.close();
        return w.getEmptyCellGraph();
    }

}
