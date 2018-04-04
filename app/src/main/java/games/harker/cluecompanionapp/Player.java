package games.harker.cluecompanionapp;

public class Player
{
    private String name;
    private int color;
    private int colorIndex;
    private int id;

    public Player(int id, String name, int colorIndex)
    {
        this.id = id;
        this.name = name;
        setColorIndex(colorIndex);
    }

    public int getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setColor(int color)
    {
        this.color = color;
        this.colorIndex = PlayerSelectAdapter.getIndex(color);
    }

    public void setColorIndex(int index)
    {
        this.colorIndex = index;
        this.color = PlayerSelectAdapter.getColor(index);
    }

    public int getColor()
    {
        return color;
    }

    public int getColorIndex()
    {
        return colorIndex;
    }
}
