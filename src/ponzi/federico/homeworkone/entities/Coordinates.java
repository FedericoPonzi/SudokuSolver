package ponzi.federico.homeworkone.entities;

/**
 * Created by isaacisback on 22/12/16.
 */
public class Coordinates
{
    int x;
    int y;

    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override public boolean equals(Object obj)
    {
        return obj instanceof Coordinates && ((Coordinates)obj).getX() == getX() && ((Coordinates) obj).getY() == getY();
    }

    @Override protected Object clone()
    {
           return new Coordinates(x,y);

    }

    @Override public String toString()
    {
        return "["+ y + "][" + x + "] ";
    }
}
