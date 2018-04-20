package games.harker.cluecompanionapp.setup;

public class PlayerSetup
{
    private String name;
    private int color;
    private int colorIndex;
    private int id;

    public PlayerSetup(int id, String name, int colorIndex)
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
        this.colorIndex = SetupCharacterSelectAdapter.getIndex(color);
    }

    public void setColorIndex(int index)
    {
        this.colorIndex = index;
        this.color = SetupCharacterSelectAdapter.getColor(index);
    }

    public int getColor()
    {
        return color;
    }

    public int getColorIndex()
    {
        return colorIndex;
    }

    public PlayerSetup clone()
    {
        return new PlayerSetup(this.id, this.name, this.colorIndex);
    }
}
