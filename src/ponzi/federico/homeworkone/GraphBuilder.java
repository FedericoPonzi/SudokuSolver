package ponzi.federico.homeworkone;


import ponzi.federico.homeworkone.entities.Cell;
import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by isaacisback on 11/12/16.
 */
public class GraphBuilder
{
    private Cell[][] table;
    public EmptyCellGraph emptyCells;

    public GraphBuilder(final Cell[][] table)
    {
        emptyCells = new EmptyCellGraph();
        this.table = table;
        setAllCandidates();
    }

    public EmptyCellGraph getEmptyCellGraph(){
        return emptyCells;
    }

    public EmptyCellGraph setAllCandidates()
    {

        for(int r = 0; r < Main.SIZE; r++)
        {
            for(int c = 0; c < Main.SIZE; c++)
            {
                if(table[r][c].getVal() != -1)
                    continue;

                findCandidates(table[r][c]);
            }
        }
        return emptyCells;
    }

    public ArrayList<Integer> findCandidates(Cell cell) throws RuntimeException
    {
        ArrayList<Coordinates> ec = new ArrayList<>();

        ArrayList<Integer> candidates = new ArrayList<>();
        candidates.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        // Controllo righe e colonne:.
        for (int i = 0; i < Main.SIZE; i++)
        {
            if(table[i][cell.getX()].getVal() == -1 && cell.getY() != i)
            {
                ec.add(new Coordinates(cell.getX(), i));
            }
            if(table[cell.getY()][i].getVal() == -1 && cell.getX() != i)
            {
                ec.add(new Coordinates(i, cell.getY()));
                //System.out.println("Gotcha." + cell.getY() +" -"+ i);

            }
            candidates.remove(table[cell.getY()][i].getVal());
            candidates.remove(table[i][cell.getX()].getVal());
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
                    ec.add(new Coordinates(c, r));
                }
                candidates.remove(table[r][c].getVal());
            }
        }

        //Se arrivo qui, candidates contiene piu di un candidato.
        if(candidates.size() == 0)
        {
            throw new RuntimeException("Impossible sudoku!");
        }
        emptyCells.addCoordinates(cell.getX(), cell.getY(), ec, candidates);

        return candidates;

    }
}
