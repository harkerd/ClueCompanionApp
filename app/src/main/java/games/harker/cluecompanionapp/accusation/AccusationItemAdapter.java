package games.harker.cluecompanionapp.accusation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import games.harker.cluecompanionapp.game.ClueGameSheet;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class AccusationItemAdapter extends BaseAdapter {
    private Context context;
    private int startIndex;

    public AccusationItemAdapter(Context context, int startIndex)
    {
        this.context = context;
        this.startIndex = startIndex;
    }

    @Override
    public int getCount()
    {
        if(startIndex == 1 || startIndex == 8)
            return 6;
        else if(startIndex == 15)
            return 9;
        else
            return 0;
    }

    @Override
    public Object getItem(int index)
    {
        return startIndex + index;
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv = view.findViewById(android.R.id.text1);

        int owner = ClueGameSheet.getModel().getOwnerIndex(ClueGameSheet.getRow(startIndex + index));
        if(owner != -1)
            txv.setBackgroundColor(PlayerBuilder.getPlayerByIndex(owner).getColor());

        txv.setTextSize(20f);
        txv.setText(ClueGameSheet.getText(startIndex + index));
        return view;
    }
}
