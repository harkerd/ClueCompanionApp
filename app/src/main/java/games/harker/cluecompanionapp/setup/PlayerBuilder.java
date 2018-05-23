package games.harker.cluecompanionapp.setup;

import java.util.LinkedList;
import java.util.List;

public class PlayerBuilder {
    private static List<PlayerSetup> players;
    private static int playerId = 0;
    private static boolean isFinal;

    private static List<PlayerSetup> getPlayers()
    {
        if(players == null)
        {
            players = new LinkedList<>();
            isFinal = false;
        }
        return players;
    }



    protected static void reset()
    {
        isFinal = false;
    }

    protected static int createPlayer(String playerName, int colorIndex)
    {
        PlayerSetup player = new PlayerSetup(playerId, playerName, colorIndex);
        playerId++;
        getPlayers().add(player);
        return player.getId();
    }

    protected static int removePlayer(int id)
    {
        PlayerSetup player = getPlayer(id);
        int index = getPlayers().indexOf(player);
        getPlayers().remove(player);
        return index;
    }

    protected static void setPlayersWithLessCards(int id1, int id2)
    {
        getPlayerByIndex(id1).setHasLessCards(true);
        getPlayerByIndex(id2).setHasLessCards(true);
    }

    protected static boolean isValid()
    {
        if(getPlayersSize() < 3)
            return false;

        boolean[] colorIndexUsed = new boolean[6];
        for(int i = 0; i < getPlayersSize(); i++)
        {
            int colorIndex = getPlayerByIndex(i).getColorIndex();
            if(colorIndexUsed[colorIndex])
            {
                return false;
            }
            else
            {
                colorIndexUsed[colorIndex] = true;
            }
        }

        return true;
    }

    protected static void setFinal()
    {
        isFinal = true;
    }

    protected static boolean isFinal()
    {
        return isFinal;
    }




    public static int getPlayersSize()
    {
        return getPlayers().size();
    }

    public static PlayerSetup getPlayer(int id)
    {
        for(int i = 0; i < getPlayers().size(); i++)
        {
            if(getPlayers().get(i).getId() == id)
            {
                PlayerSetup p = getPlayers().get(i);
                if(isFinal)
                    p = p.clone();
                return p;
            }

        }
        return null;
    }

    public static PlayerSetup getPlayerByIndex(int index)
    {
        PlayerSetup p = getPlayers().get(index);
        if(isFinal)
            p = p.clone();
        return p;
    }

    public static int getPlayerIndex(int id)
    {
        for(int i = 0; i < getPlayers().size(); i++)
        {
            if(getPlayers().get(i).getId() == id)
            {
                return i;
            }

        }
        return -1;
    }
}
