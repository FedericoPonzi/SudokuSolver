package ponzi.federico.homeworkone;


import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by isaacisback on 22/12/16.
 */
public class EmptyCellGraph
{
    HashMap<Coordinates, HashSet<Coordinates>> ec;
    HashMap<Coordinates, Integer[]> cand;

    public EmptyCellGraph(){
        ec = new HashMap<>();
        cand = new HashMap<>();
    }
    public Set<Coordinates> keySet(){
        return ec.keySet();
    }
    /**
     * May return null. Returns the node with less adjacents, and removes it from the graph.
     * @return
     */
    public Integer[] getSmaller()
    {
        int i = 9+9+9;
        Integer[] toRet = null;
        for (Coordinates c : ec.keySet())
        {
            if(ec.get(c).size() < i)
            {
                toRet = cand.get(c);
            }
        }
        return toRet;
    }
    public void addCoordinates(int x, int y, HashSet<Coordinates> toAdd, Integer[] candidates)
    {
        Coordinates c = new Coordinates(x,y);
        ec.put(c, toAdd);
        cand.put(c, candidates);
    }

    /**
     * Removes a node from the graph.
     * @param toRem the node to remove.
     */
    public void removeCoordinates(Coordinates toRem)
    {
        HashSet<Coordinates> adj =  getAdjacents(toRem);
        for(Coordinates c : adj)
        {
            HashSet<Coordinates> prova = ec.get(c);
            if(prova != null)
                prova.remove(toRem);
        }
        ec.remove(toRem);
    }

    public Integer[] getCandidates(Coordinates c )
    {
        return cand.get(c);
    }
    public Integer[] getCandidates(int x, int y)
    {
        return cand.get(new Coordinates(x,y));
    }

    public HashSet<Coordinates> getAdjacents(Coordinates c)
    {
        return ec.get(c);
    }

    public HashSet<Coordinates> getAdjacents(int x, int y)
    {
        return getAdjacents(new Coordinates(x, y));
    }

    @Override protected Object clone()
    {
        EmptyCellGraph toRet;
        toRet = new EmptyCellGraph(); // (EmptyCellGraph) super.clone();
        toRet.ec = (HashMap<Coordinates, HashSet<Coordinates>>) ec.clone();
        return toRet;

    }
}
