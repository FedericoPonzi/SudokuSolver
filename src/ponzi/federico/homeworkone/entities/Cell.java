package ponzi.federico.homeworkone.entities;

/**
 * Created by isaacisback on 11/12/16.
 */
public class Cell implements Cloneable
{
    private int x;

    private int y;


    private Integer[] candidates;

    private Integer val;

    public Cell(int y, int x, int val)
    {
        this.y = y;
        this.x = x;
        assert (val < 9 && val >= -1);
        this.val = val;
        this.candidates = val == -1 ? new Integer[9] : new Integer[1];
    }

    public Integer getVal()
    {
        return val;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setVal(Integer val)
    {
        this.val = val;
    }

    public Integer[] getCandidates()
    {
        return candidates;
    }

    public void setCandidates(Integer[] candidates)
    {
        this.candidates = candidates;
    }

    @Override public String toString()
    {
        String t = getVal() == -1 ? "." : getVal() + "";
        if (getX() % 3 == 2)
        {
            t += " ";
        }
        if (getX() == 8)
        {
            t += "\n";
            if (getY() % 3 == 2)
            {
                t += "\n";
            }
        }
        return t;
    }

    @Override public Object clone()
    {
        Cell c;
        try {
            c = (Cell) super.clone();
            System.arraycopy(candidates, 0, c.candidates, 0, candidates.length);
            c.val = new Integer(val);
            return c;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override public boolean equals(Object obj)
    {
        return obj instanceof Cell && ((Cell)obj).getX() == getX() && ((Cell) obj).getY() == getY();
    }
}
