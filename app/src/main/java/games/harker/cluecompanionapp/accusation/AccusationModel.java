package games.harker.cluecompanionapp.accusation;

import java.util.ArrayList;
import java.util.List;

import games.harker.cluecompanionapp.game.ClueGameSheet;
import games.harker.cluecompanionapp.game.GameActivity;
import games.harker.cluecompanionapp.setup.PlayerBuilder;
import games.harker.cluecompanionapp.setup.PlayerSetup;
import games.harker.cluecompanionapp.setup.Settings;

public class AccusationModel {
    private static List<AccusationModel> list;

    public static void addToList(AccusationModel model)
    {
        if(list == null)
        {
            list = new ArrayList<>();
        }
        list.add(model);
    }

    public static int getListSize()
    {
        if(list == null)
        {
            return 0;
        }
        else
        {
            return list.size();
        }
    }

    public static AccusationModel get(int index)
    {
        if(list == null || list.size() <= index)
        {
            return null;
        }
        else
        {
            return list.get(index);
        }
    }

    public static void reset()
    {
        list = null;
    }


    private int accuserPlayerId;
    private int suspect;
    private int weapon;
    private int room;
    private int responderPlayerId;

    protected AccusationModel(int accuserPlayerId, int suspect, int weapon, int room, int responderPlayerId)
    {
        this.accuserPlayerId = accuserPlayerId;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
        this.responderPlayerId = responderPlayerId;
    }

    protected int getAccuserPlayer()
    {
        return accuserPlayerId;
    }

    protected int getSuspect()
    {
        return suspect;
    }

    protected int getWeapon()
    {
        return weapon;
    }

    protected int getRoom()
    {
        return room;
    }

    protected int getResponderPlayer()
    {
        return responderPlayerId;
    }

    protected void updateResults()
    {
        if(Settings.autoPopulate() && Settings.accessAccusationList())
        {
            updateResponderResult();
            updateNonResponders();
            GameActivity.notifyChanged();
        }
    }

    private void updateNonResponders()
    {
        if(responderPlayerId == -1)
        {
            for(int i = 0; i < PlayerBuilder.getPlayersSize(); i++)
            {
                PlayerSetup p = PlayerBuilder.getPlayerByIndex(i);
                if(p.getId() != accuserPlayerId)
                {

                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(suspect), p.getId());
                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(weapon), p.getId());
                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(room), p.getId());
                }
            }
        }
        else
        {
            int startPlayerIndex = PlayerBuilder.getPlayerIndex(accuserPlayerId);
            int endPlayerIndex = PlayerBuilder.getPlayerIndex(responderPlayerId);

            int i = startPlayerIndex;
            while(i != endPlayerIndex)
            {
                i++;
                if(i == PlayerBuilder.getPlayersSize())
                {
                    i = 0;
                }

                if(i != startPlayerIndex && i != endPlayerIndex)
                {
                    PlayerSetup p = PlayerBuilder.getPlayerByIndex(i);
                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(suspect), p.getId());
                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(weapon), p.getId());
                    ClueGameSheet.getModel().setValue(ClueGameSheet.KNOW_HAS_NOT, ClueGameSheet.getRow(room), p.getId());
                }
            }
        }
    }

    private void updateResponderResult()
    {
        if(responderPlayerId != -1)
        {
            boolean doesHaveSuspect = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(suspect), responderPlayerId) == ClueGameSheet.SEEN;
            boolean doesHaveWeapon = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(weapon), responderPlayerId) == ClueGameSheet.SEEN;
            boolean doesHaveRoom = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(room), responderPlayerId) == ClueGameSheet.SEEN;

            if(doesHaveSuspect || doesHaveWeapon || doesHaveRoom)
            {
                int unknownRow1 = -1;
                int unknownRow2 = -1;
                if(doesHaveSuspect)
                {
                    unknownRow1 = ClueGameSheet.getRow(weapon);
                    unknownRow2 = ClueGameSheet.getRow(room);
                }
                else if(doesHaveWeapon)
                {
                    unknownRow1 = ClueGameSheet.getRow(suspect);
                    unknownRow2 = ClueGameSheet.getRow(room);
                }
                else if(doesHaveRoom)
                {
                    unknownRow1 = ClueGameSheet.getRow(suspect);
                    unknownRow2 = ClueGameSheet.getRow(weapon);
                }

                if(ClueGameSheet.getModel().getValue(unknownRow1, responderPlayerId) == ClueGameSheet.MAYBE_HAS)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.UNKNOWN, unknownRow1, responderPlayerId);

                if(ClueGameSheet.getModel().getValue(unknownRow2, responderPlayerId) == ClueGameSheet.MAYBE_HAS)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.UNKNOWN, unknownRow2, responderPlayerId);
            }
            else
            {
                boolean doesNotHaveSuspect = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(suspect), responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;
                boolean doesNotHaveWeapon = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(weapon), responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;
                boolean doesNotHaveRoom = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(room), responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;

                if (doesNotHaveSuspect && doesNotHaveWeapon)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, ClueGameSheet.getRow(room), responderPlayerId);

                else if (doesNotHaveSuspect && doesNotHaveRoom)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, ClueGameSheet.getRow(weapon), responderPlayerId);

                else if (doesNotHaveWeapon && doesNotHaveRoom)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, ClueGameSheet.getRow(suspect), responderPlayerId);

                else
                {
                    if (ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(suspect), responderPlayerId) == ClueGameSheet.UNKNOWN)
                        ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(suspect), responderPlayerId);

                    if (ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(weapon), responderPlayerId) == ClueGameSheet.UNKNOWN)
                        ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(weapon), responderPlayerId);

                    if (ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(room), responderPlayerId) == ClueGameSheet.UNKNOWN)
                        ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(room), responderPlayerId);
                }
            }
        }
        else
        {
            int suspectValue = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(suspect), accuserPlayerId);
            int weaponValue = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(weapon), accuserPlayerId);
            int roomValue = ClueGameSheet.getModel().getValue(ClueGameSheet.getRow(room), accuserPlayerId);

            if(suspectValue != ClueGameSheet.MUST_HAVE && suspectValue != ClueGameSheet.SEEN &&
                    weaponValue != ClueGameSheet.MUST_HAVE && weaponValue != ClueGameSheet.SEEN &&
                    roomValue != ClueGameSheet.MUST_HAVE && roomValue != ClueGameSheet.SEEN)
            {
                if (suspectValue == ClueGameSheet.UNKNOWN)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(suspect), accuserPlayerId);

                if (weaponValue == ClueGameSheet.UNKNOWN)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(weapon), accuserPlayerId);

                if (roomValue == ClueGameSheet.UNKNOWN)
                    ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, ClueGameSheet.getRow(room), accuserPlayerId);
            }
        }
    }
}
