package ponzi.federico.homeworkone.entities;

/**
 * Created by isaacisback on 11/12/16.
 */
public class Cell
{
    private int x;

    private int y;

    private Integer val;

    public Cell(int y, int x, int val)
    {
        this.y = y;
        this.x = x;
        assert (val < 9 && val >= -1);
        this.val = val;
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

}
