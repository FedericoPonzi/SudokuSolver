package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Cell;
import ponzi.federico.homeworkone.entities.Coordinates;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Created by isaacisback on 11/12/16.
 */
public class Worker
{
    private Cell[][] table;

    public Worker(final Cell[][] table)
    {

        this.table = table;
    }

    public EmptyCellGraph emptyCells = new EmptyCellGraph();

    BigInteger ss = new BigInteger("1");

    public BigInteger getSolutionSpace(){
        setAllCandidates();
        return ss;
    }

    public Cell[][] setAllCandidates()
    {
        Integer[] cands;
        for(int r = 0; r < Main.SIZE; r++)
        {
            for(int c = 0; c < Main.SIZE; c++)
            {
                if(table[r][c].getVal() != -1)
                    continue;

                cands = findCandidates(table[r][c]);

                table[r][c].setCandidates(cands);
            }
        }
        return table;
    }

    public Integer[] findCandidates(Cell cell)
    {
        HashSet<Coordinates> ec = new HashSet<>();

        ArrayList<Integer> candidates = new ArrayList<>();
        candidates.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        // Controllo righe e colonne:.
        for (int i = 0; i < Main.SIZE; i++)
        {
            if(table[i][cell.getX()].getVal() == -1 && i != cell.getY())
            {
                ec.add(new Coordinates(i, cell.getX()));
            }
            if(table[cell.getY()][i].getVal() == -1 && cell.getX() != i)
            {
                ec.add(new Coordinates(cell.getY(), i));
                //System.out.println("Gotcha." + cell.getY() +" -"+ i);

            }
            candidates.remove(table[cell.getY()][i].getVal());
            candidates.remove(table[i][cell.getX()].getVal());

            if (candidates.size() == 1)
            {
                    emptyCells.addCoordinates(new Coordinates(cell.getX(), cell.getY()), ec);
                    return new Integer[] { candidates.get(0) };
            }
        }

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
                if(table[r][c].getVal() == -1 && r != cell.getY() && c != cell.getX())
                {
                    ec.add(new Coordinates(r,c));

                }
                candidates.remove(table[r][c].getVal());

                if (candidates.size() == 1)
                {
                    emptyCells.addCoordinates(new Coordinates(cell.getX(), cell.getY()), ec, new Integer[] { candidates.get(0) });
                    return new Integer[] { candidates.get(0) };
                }
            }
        }

        //Se arrivo qui, candidates contiene piu di un candidato.
        ss = ss.multiply(new BigInteger(candidates.size() + ""));

        emptyCells.addCoordinates(new Coordinates(cell.getX(), cell.getY()), ec);

        return candidates.toArray(new Integer[candidates.size()]);

    }
}
