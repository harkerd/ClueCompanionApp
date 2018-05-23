package games.harker.cluecompanionapp.setup;

public class PlayerSetup
{
    private String name;
    private int color;
    private int colorIndex;
    private int id;
    private boolean hasLessCards;

    public PlayerSetup(int id, String name, int colorIndex)
    {
        this.id = id;
        this.name = name;
        this.hasLessCards = false;
        setColorIndex(colorIndex);
    }

    private PlayerSetup(int id, String name, int colorIndex, boolean hasLessCards)
    {
        this.id = id;
        this.name = name;
        this.hasLessCards = hasLessCards;
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

    public void setHasLessCards(boolean hasLessCards)
    {
        this.hasLessCards = hasLessCards;
    }

    public boolean getHasLessCards()
    {
        return hasLessCards;
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
        return new PlayerSetup(this.id, this.name, this.colorIndex, hasLessCards);
    }
}
