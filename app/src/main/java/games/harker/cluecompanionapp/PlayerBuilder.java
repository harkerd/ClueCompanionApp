package games.harker.cluecompanionapp;

import java.util.LinkedList;
import java.util.List;

public class PlayerBuilder {
    private static List<Player> players;
    private static int playerId = 0;

    private static List<Player> getPlayers()
    {
        if(players == null)
        {
            players = new LinkedList<>();
        }
        return players;
    }

    public static int getPlayersSize()
    {
        return getPlayers().size();
    }

    public static Player getPlayer(int id)
    {
        for(int i = 0; i < getPlayers().size(); i++)
        {
            if(getPlayers().get(i).getId() == id)
                return getPlayers().get(i);
        }
        return null;
    }

    public static Player getPlayerByIndex(int index)
    {
        return getPlayers().get(index);
    }

    public static int createPlayer(String playerName, int colorIndex)
    {
        Player player = new Player(playerId, playerName, colorIndex);
        playerId++;
        getPlayers().add(player);
        return player.getId();
    }

    public static int removePlayer(int id)
    {
        Player player = getPlayer(id);
        int index = getPlayers().indexOf(player);
        getPlayers().remove(player);
        return index;
    }
}
