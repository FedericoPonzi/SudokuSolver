package ponzi.federico.homeworkone.entities;

/**
 * Created by isaacisback on 22/12/16.
 */
public class Coordinates implements Cloneable
{
    Integer x;
    Integer y;

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

    @Override public int hashCode()
    {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override protected Object clone()
    {
        try
        {
            Coordinates c = (Coordinates) super.clone();
            c.x = x;
            c.y = y;
            return c;
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override public String toString()
    {
        return "["+ y + "][" + x + "]";
    }
}
