package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Cell;
import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Federico Ponzi on 21/12/16.
 */
public class ComputeSolutions extends RecursiveTask<Integer>
{
    private final EmptyCellGraph emptyCellGraph;
    Cell[][] table;
    public ComputeSolutions(Cell[][] t, EmptyCellGraph emptyCellGraph)
    {
        this.table = t;
        this.emptyCellGraph = emptyCellGraph;
    }

    @Override protected Integer compute()
    {
        Cell n = null;
        Coordinates coords = emptyCellGraph.getSmaller();
        if(coords != null)
        {
            n = table[coords.getY()][coords.getX()];
        }/*
        int lowest = 9;

        for(Cell[] row : table)
        {
            for(Cell el : row)
            {
                if (el.getVal().equals(-1) && el.getCandidates().length < lowest)
                {
                    n = el;
                    lowest = el.getCandidates().length;
                }
            }
        }*/



        /**
         * 1. mi prendo i candidati
         * 2. Se non ci sono candidati allora non è una configurazione valida, torno 0
         * 3. Se ci sono candidati, spawno tanti threads quanti sono i candidati,
         * 4. Sommo i risultati dei threads e ritorno il valore
         */
        //In caso non ci siano più celle vuote, abbiamo una soluzione completa.
        if(coords == null)
        {
            //System.out.println("Done.");
            return 1;
        }

        Integer[] candidates = findCandidates(n);
        if(candidates.length == 0)
        {
            //System.out.println("Nessuna soluzione, sorry");
            //Caso baso e motivo di uscita. Siamo in una configurazione non valida.
            return 0;
        }

        ArrayList<ComputeSolutions> configurations = new ArrayList<>();
        for (Integer candidate : candidates)
        {
            Cell[][] t = deepCopy(table);
            t[n.getY()][n.getX()].setVal(candidate);
            configurations.add(new ComputeSolutions(t, (EmptyCellGraph) emptyCellGraph.clone()));
        }

        for(ComputeSolutions c : configurations)
        {
            c.fork();
        }

        int res = 0;

        for(ComputeSolutions c : configurations)
        {
            res += c.join();
        }
        return res;
    }

    public Integer[] findCandidates(Cell cell)
    {
        ArrayList<Integer> candidates = new ArrayList<>();
        candidates.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        //System.out.println("Controllo riga e colonna:");
        //System.out.println(cell);
        // Controllo righe e colonne:.

        for (int i = 0; i < Main.SIZE; i++)
        {
            candidates.remove(table[i][cell.getX()].getVal());
            candidates.remove(table[cell.getY()][i].getVal());

        }
        //System.out.println("Dopo filter per righe e colonne:");
        //candidates.stream().forEach(System.out::println);

        int startR = 0;
        int startC = 0;
        //Allineo con l'inizio del quadrato:
        if(cell.getY() >= 3)
        {
            startR = 3;
        }
        if(cell.getY() >= 6)
        {
            startR = 6;
        }
        if(cell.getX() >= 3)
        {
            startC = 3;
        }
        if(cell.getX() >= 6)
        {
            startC = 6;
        }

        for(int r = startR; r < startR + 3; r++)
        {
            for(int c = startC; c < startC + 3; c++)
            {
                //System.out.println("Confronto valore [" + r + "] ["+ c +"]: " + table[r][c].getVal());

                candidates.remove(table[r][c].getVal());

            }
        }


        return candidates.toArray(new Integer[candidates.size()]);
    }

    private Cell[][] deepCopy(Cell[][] t)
    {
        Cell[][] toRet = new Cell[9][9];
        for(int i = 0; i < Main.SIZE; i++)
        {
            for(int x = 0; x < Main.SIZE; x++)
                 toRet[i][x] =  (Cell) t[i][x].clone();
        }
        return toRet;
    }

}
