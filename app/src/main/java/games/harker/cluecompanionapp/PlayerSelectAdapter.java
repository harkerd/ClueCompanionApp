package games.harker.cluecompanionapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerSelectAdapter extends BaseAdapter
{
    private static ArrayList<Pair<String, Integer>> colors;
    private Context context;

    public static int getColor(int position)
    {
        return getColors().get(position).second;
    }

    public static int getIndex(int color)
    {
        for(int i = 0; i < getColors().size(); i++)
        {
            if(getColors().get(i).second == color)
            {
                return i;
            }
        }
        return 0;
    }

    private static ArrayList<Pair<String, Integer>> getColors()
    {
        if(colors == null)
        {
            colors = new ArrayList<>();
            colors.add(new Pair<>("Mr. Green", Color.GREEN));
            colors.add(new Pair<>("Col. Mustard", Color.rgb(255, 219, 88)));
            colors.add(new Pair<>("Prof. Plum", Color.rgb(221,160,221)));
            colors.add(new Pair<>("Mrs. Peacock", Color.rgb(0,191,255)));
            colors.add(new Pair<>("Miss Scarlet", Color.rgb(255, 36, 0)));
            colors.add(new Pair<>("Mrs. White", Color.WHITE));
        }
        return colors;
    }

    public PlayerSelectAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount()
    {
        return getColors().size();
    }

    @Override
    public Object getItem(int index)
    {
        return getColors().get(index);
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv = view.findViewById(android.R.id.text1);
        txv.setBackgroundColor(getColors().get(pos).second);
        txv.setTextSize(20f);
        txv.setText(getColors().get(pos).first);
        return view;
    }

}