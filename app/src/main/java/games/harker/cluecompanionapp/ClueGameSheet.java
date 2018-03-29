package games.harker.cluecompanionapp;

public class ClueGameSheet
{
    public static final int SEEN = 4; //Eye symbol
    public static final int KNOW_HAS_NOT = 3; //An X
    public static final int MUST_HAVE = 2; //A !
    public static final int MAYBE_HAS = 1; //A ?
    public static final int UNKNOWN = 0; //No symbol

    private static ClueGameSheet model;
    public static int numberOfPlayers_onPendingGame;

    public final int length = 21;
    public final int numberOfPlayers;
    public int numberOfHeaders = 3;

    public static ClueGameSheet getModel()
    {
        if(model == null) model = new ClueGameSheet(numberOfPlayers_onPendingGame);
        return model;
    }

    public static void quitGame()
    {
        model = null;
    }

    public static void setNumberOfPlayers(int players)
    {
        numberOfPlayers_onPendingGame = players;
    }

    public static boolean isHeader(int position)
    {
        boolean isHeader = false;

        switch(position)
        {
            case 0:
            case 7:
            case 14:
                isHeader = true;
        }

        return isHeader;
    }

    public static int getRow(int position)
    {
        if(isHeader(position)) return -1;

        int row = position;
        if(row > 14) row -= 3;
        else if(row > 7) row -= 2;
        else if(row > 0) row -= 1;

        return row;
    }

    public static String getText(int staticRowId)
    {
        String text = "";

        switch(staticRowId)
        {
            case 0: text = "Suspects:"; break;

            case 1: text = "Mr. Green"; break;
            case 2: text = "Col. Mustard"; break;
            case 3: text = "Prof. Plum"; break;
            case 4: text = "Mrs. Peacock"; break;
            case 5: text = "Miss Scarlet"; break;
            case 6: text = "Mrs. White"; break;

            case 7: text = "Weapons:"; break;

            case 8: text = "Candlestick"; break;
            case 9: text = "Knife"; break;
            case 10: text = "Lead Pipe"; break;
            case 11: text = "Revolver"; break;
            case 12: text = "Rope"; break;
            case 13: text = "Wrench"; break;

            case 14: text = "Rooms:"; break;

            case 15: text = "Ballroom"; break;
            case 16: text = "Billiard Room"; break;
            case 17: text = "Conservatory"; break;
            case 18: text = "Dining Room"; break;
            case 19: text = "Hall"; break;
            case 20: text = "Kitchen"; break;
            case 21: text = "Library"; break;
            case 22: text = "Lounge"; break;
            case 23: text = "Study"; break;

        }
        return text;
    }

    private int[][] grid;
    private int selectedType = UNKNOWN;

    private ClueGameSheet(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        grid = new int[length][numberOfPlayers];
    }

    public void setValue(int type, int row, int col)
    {
        if(grid[row][col] == type)
            grid[row][col] = UNKNOWN;
        else
            grid[row][col] = type;

    }

    public int getValue(int row, int col)
    {
        return grid[row][col];
    }

    public void setSelectedType(int type)
    {
        selectedType = type;
    }

    public int getSelectedType()
    {
        return selectedType;
    }
}
