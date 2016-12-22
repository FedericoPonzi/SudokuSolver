package ponzi.federico.homeworkone;


import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by isaacisback on 22/12/16.
 */
public class EmptyCellGraph implements Cloneable
{
    HashMap<Coordinates, HashSet<Coordinates>> ec;
    HashMap<Coordinates, ArrayList<Integer>> cand;

    public EmptyCellGraph()
    {
        ec = new HashMap<>();
        cand = new HashMap<>();
    }

    /**
     * Clone costructor.
     * @param e
     */
    public EmptyCellGraph(EmptyCellGraph e)
    {
        this();
        for(Coordinates c : e.cand.keySet())
        {
            Coordinates k = new Coordinates(c.getX(),c.getY());
            ArrayList<Integer> v = new ArrayList<>();
            for(Integer i : e.cand.get(c))
            {
                v.add(new Integer(i));
            }

            cand.put(k, v);
        }
        for(Coordinates c : e.ec.keySet())
        {
            Coordinates k = new Coordinates(c.getX(),c.getY());
            HashSet<Coordinates> v = new HashSet<Coordinates>();
            for(Coordinates z : e.ec.get(c))
            {
                Coordinates r = new Coordinates(z.getX(), z.getY());
                v.add(r);
            }
            ec.put(k, v);
        }
    }

    /**
     * May return null. Returns the node with less candidates.
     *
     * @return
     */
    public Coordinates getSmaller()
    {
        int i = 9;
        Coordinates toRet = null;
        for (Coordinates c : ec.keySet())
        {
            if (cand.get(c).size() < i)
            {
                toRet = c;
                i = cand.get(c).size();
            }
        }
        return toRet;
    }

    public void addCoordinates(int x, int y, HashSet<Coordinates> toAdd, ArrayList<Integer> candidates)
    {
        Coordinates c = new Coordinates(x, y);
        ec.put(c, toAdd);
        cand.put(c, candidates);
    }

    /**
     * Check if setting coordinates = val will yeld to a valid configuration of the sudoku.
     *
     * @param coordinates
     * @param val
     * @return boolean true: if it's a valid configuration, false otherwise.
     */
    public boolean tryRemoveCoordinates(Coordinates coordinates, Integer val)
    {
        HashSet<Coordinates> neighbours = ec.get(coordinates);
        for (Coordinates c : neighbours)
        {
            ArrayList<Integer> candsOfC = cand.get(c);
            for (Integer i : candsOfC)
            {
                if (i.equals(val) && candsOfC.size() == 1)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Removes a node from the graph.
     * In sudoku: set cell toRem with value Val
     * @param toRem the node to remove.
     */
    public void removeCoordinates(Coordinates toRem, Integer val)
    {
        HashSet<Coordinates> neighbours = ec.get(toRem);
        for (Coordinates c : neighbours)
        {
            //Rimuovo val come possibile scelta di candidati alle altre coordinate
            ArrayList<Integer> candidati = cand.get(c);
            candidati.remove(val);

            //Faccio sparire toRem dalla lista di adiacenza delle altre coordinate:
            ec.get(c).remove(toRem);
        }

        ec.remove(toRem);
        cand.remove(toRem);
    }

    public ArrayList<Integer> getCandidates(Coordinates c)
    {
        return cand.get(c);
    }
}
