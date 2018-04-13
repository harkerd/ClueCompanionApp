package games.harker.cluecompanionapp;

import android.content.Context;

public class Tools
{
    public static int dpConvertToPixel(int dp, Context context)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
