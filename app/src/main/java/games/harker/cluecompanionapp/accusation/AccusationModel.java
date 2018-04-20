package games.harker.cluecompanionapp.accusation;

import java.util.ArrayList;
import java.util.List;

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


    private int accuserPlayerId;
    private int suspect;
    private int weapon;
    private int room;
    private int responderPlayerId;

    protected AccusationModel(int accuserPlayerId, int suspect, int room, int weapon, int responderPlayerId)
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

    /*protected int updateResponderPlayerResult()
    {
        boolean doesNotHaveSuspect = ClueGameSheet.getModel().getValue(suspect, responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;
        boolean doesNotHaveWeapon = ClueGameSheet.getModel().getValue(weapon, responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;
        boolean doesNotHaveRoom = ClueGameSheet.getModel().getValue(room, responderPlayerId) == ClueGameSheet.KNOW_HAS_NOT;

        if(doesNotHaveSuspect && doesNotHaveWeapon)
        {
            ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, room, responderPlayerId);
            return room;
        }
        if(doesNotHaveSuspect && doesNotHaveRoom)
        {
            ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, weapon, responderPlayerId);
            return weapon;
        }
        if(doesNotHaveWeapon && doesNotHaveRoom)
        {
            ClueGameSheet.getModel().setValue(ClueGameSheet.MUST_HAVE, suspect, responderPlayerId);
            return suspect;
        }
        else
        {
            if(ClueGameSheet.getModel().getValue(suspect, responderPlayerId) == ClueGameSheet.UNKNOWN)
                ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, suspect, responderPlayerId);

            if(ClueGameSheet.getModel().getValue(weapon, responderPlayerId) == ClueGameSheet.UNKNOWN)
                ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, suspect, responderPlayerId);

            if(ClueGameSheet.getModel().getValue(room, responderPlayerId) == ClueGameSheet.UNKNOWN)
                ClueGameSheet.getModel().setValue(ClueGameSheet.MAYBE_HAS, suspect, responderPlayerId);

            return -1;
        }
    }*/
}
