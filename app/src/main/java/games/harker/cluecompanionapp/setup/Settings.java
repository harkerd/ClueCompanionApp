package games.harker.cluecompanionapp.setup;

public class Settings
{
    private static boolean showCardCount;
    private static boolean autoPopulate;
    private static boolean accessAccusationList;

    protected static void setShowCardCount(boolean show)
    {
        showCardCount = show;
    }

    public static boolean showCardCount()
    {
        return showCardCount;
    }

    protected static void setAutoPopulate(boolean auto)
    {
        autoPopulate = auto;
    }

    public static boolean autoPopulate()
    {
        return autoPopulate;
    }

    protected static void setAccusationListAccess(boolean access)
    {
        accessAccusationList = access;
    }

    public static boolean accessAccusationList()
    {
        return accessAccusationList;
    }
}
