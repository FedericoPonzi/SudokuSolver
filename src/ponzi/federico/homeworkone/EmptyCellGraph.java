package ponzi.federico.homeworkone;


import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.*;

/**
 * Created by isaacisback on 22/12/16.
 */
public class EmptyCellGraph implements Cloneable
{
    HashMap<Coordinates, ArrayList<Coordinates>> ec;
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

            ArrayList<Integer> get = e.cand.get(c);
            for (Integer i : get)
            {
                v.add(new Integer(i));
            }

            cand.put(k, v);
        }
        for(Coordinates c : e.ec.keySet())
        {
            Coordinates k = new Coordinates(c.getX(),c.getY());
            ArrayList<Coordinates> v = new ArrayList<>();
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
        /*
        No, this is not faster :)
        Object[] entries = ec.keySet().toArray();
        if(entries.length > 0)
            return (Coordinates) entries[0];
        */
        for (Coordinates c : ec.keySet())
        {
            if(cand.get(c).size() == 1)
                return c;
            if (cand.get(c).size() < i)
            {
                toRet = c;
                i = cand.get(c).size();
            }
        }
        return toRet;
    }

    public void addCoordinates(int x, int y, ArrayList<Coordinates> toAdd, ArrayList<Integer> candidates)
    {
        Coordinates c = new Coordinates(x, y);
        ec.put(c, toAdd);
        cand.put(c, candidates);
    }

    /**
     * Check if setting coordinates = val will yeld to a valid configuration of the sudoku.
     * This is useful because removeCoordinates' candidati.remove it's expensive.
     * @param coordinates
     * @param val
     * @return boolean true: if it's a valid configuration, false otherwise.
     */
    public boolean tryRemoveCoordinates(Coordinates coordinates, Integer val)
    {
        ArrayList<Coordinates> neighbours = ec.get(coordinates);
        for (Coordinates c : neighbours)
        {
            ArrayList<Integer> candsOfC = cand.get(c);
            //I get worried only if the size is 1. If it's 1 i check if the it contains val (this checks is O(N)).
            if(candsOfC.size() == 1 && candsOfC.contains(val))
                return false;
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
        ArrayList<Coordinates> neighbours = ec.get(toRem);

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
