package games.harker.cluecompanionapp.game;

import android.util.Pair;

import games.harker.cluecompanionapp.suggestion.SuggestionModel;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class ClueGameSheet
{
    public static final int SEEN = 4; //Eye symbol
    public static final int KNOW_HAS_NOT = 3; //An X
    public static final int MUST_HAVE = 2; //A !
    public static final int MAYBE_HAS = 1; //A ?
    public static final int UNKNOWN = 0; //No symbol

    private static ClueGameSheet model;

    public final int length = 21;
    public final int numberOfPlayers;
    public int numberOfHeaders = 3;

    public static ClueGameSheet getModel()
    {
        if(model == null) model = new ClueGameSheet(PlayerBuilder.getPlayersSize());
        return model;
    }

    public static void quitGame()
    {
        model = null;
        SuggestionModel.reset();
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

    /*
        3 players - 6 cards
        4 players - 2 with 5 cards, 2 with 4 cards
        5 players - 3 with 4 cards, 2 with 3 cards
        6 players - 3 cards
    */

    private int[][] grid;
    private int selectedType = UNKNOWN;

    private ClueGameSheet(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        grid = new int[length][numberOfPlayers];
    }

    public void toggleValue(int type, int row, int col)
    {
        if(grid[row][col] == type)
            grid[row][col] = UNKNOWN;
        else
            grid[row][col] = type;
    }

    public void setValue(int type, int row, int col)
    {
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

    public PlayerCardCount getPlayerCardCount(int playerIndex)
    {
        PlayerCardCount count = new PlayerCardCount();
        count.playerKnowHasNot = 0;
        count.playerIndicated = 0;
        count.playerTotal = -1;

        for (int i = 0; i < length; i++)
        {
            int value = grid[i][playerIndex];
            if(value == KNOW_HAS_NOT)
                count.playerKnowHasNot++;
            else if(value == SEEN || value == MUST_HAVE)
                count.playerIndicated++;
        }


        switch(numberOfPlayers)
        {
            case 6:
                count.playerTotal = 3;
                break;

            case 5:
                if(PlayerBuilder.getPlayerByIndex(playerIndex).getHasLessCards())
                    count.playerTotal = 3;
                else
                    count.playerTotal = 4;
                break;

            case 4:
                if(PlayerBuilder.getPlayerByIndex(playerIndex).getHasLessCards())
                    count.playerTotal = 4;
                else
                    count.playerTotal = 5;
                break;

            case 3:
                count.playerTotal = 6;
                break;
        }

        return count;
    }

    public int getOwnerIndex(int row)
    {
        for(int i = 0; i < numberOfPlayers; i++)
        {
            if(grid[row][i] == SEEN || grid[row][i] == MUST_HAVE)
            {
                return i;
            }
        }

        return -1;
    }

    public void setXOnRow(int row)
    {
        for(int i = 0; i < numberOfPlayers; i++)
        {
            if(grid[row][i] != SEEN)
            {
                grid[row][i] = KNOW_HAS_NOT;
            }
        }
    }

    public void setXOnCol(int playerIndex)
    {
        for(int i = 0; i < length; i++)
        {
            if(grid[i][playerIndex] != SEEN && grid[i][playerIndex] != MUST_HAVE)
            {
                grid[i][playerIndex] = KNOW_HAS_NOT;
            }
        }
    }

    public void setMustHaveOnCol(int playerIndex)
    {
        for(int i = 0; i < length; i++)
        {
            if(grid[i][playerIndex] != SEEN && grid[i][playerIndex] != KNOW_HAS_NOT)
            {
                grid[i][playerIndex] = MUST_HAVE;
            }
        }
    }

    public boolean noOneHas(int row)
    {
        for(int i = 0; i < numberOfPlayers; i++)
        {
            if(grid[row][i] != KNOW_HAS_NOT)
            {
                return false;
            }
        }

        return true;
    }
}
