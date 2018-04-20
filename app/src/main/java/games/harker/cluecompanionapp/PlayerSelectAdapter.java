package games.harker.cluecompanionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class PlayerSelectAdapter extends BaseAdapter {
    private Context context;
    private boolean includeNone;

    public PlayerSelectAdapter(Context context, boolean includeNone)
    {
        this.context = context;
        this.includeNone = includeNone;
    }

    @Override
    public int getCount()
    {
        int count = PlayerBuilder.getPlayersSize();
        if(includeNone) count++;
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        if(includeNone && position == 0)
        {
            return -1;
        }
        else
        {
            int index = position;
            if(includeNone) index--;

            return PlayerBuilder.getPlayerByIndex(index).getId();
        }
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        view.setMinimumWidth(Tools.dpConvertToPixel(30, context));
        TextView txv = view.findViewById(android.R.id.text1);
        txv.setTextSize(20f);

        if(includeNone && position == 0)
        {
            txv.setText("None");
        }
        else
        {
            int index = position;
            if(includeNone) index--;

            txv.setBackgroundColor(PlayerBuilder.getPlayerByIndex(index).getColor());
            txv.setText(PlayerBuilder.getPlayerByIndex(index).getName());
        }

        return view;
    }
}
