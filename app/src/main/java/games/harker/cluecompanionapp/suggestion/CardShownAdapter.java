package games.harker.cluecompanionapp.suggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import games.harker.cluecompanionapp.game.ClueGameSheet;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class CardShownAdapter extends BaseAdapter {
    private Context context;
    private Spinner suspect;
    private Spinner weapon;
    private Spinner room;


    public CardShownAdapter(Context context, Spinner suspect, Spinner weapon, Spinner room)
    {
        this.context = context;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public Object getItem(int index)
    {
        int result = -1;

        switch(index)
        {
            case 0: result = -1; break;
            case 1: result = (int) suspect.getSelectedItem(); break;
            case 2: result = (int) weapon.getSelectedItem(); break;
            case 3: result = (int) room.getSelectedItem(); break;
        }

        return result;
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent)
    {
        int owner = -1;
        String text = "";
        if(index == 0)
        {
            text = "Unknown";
        }
        else
        {
            int position = -1;
            switch(index)
            {
                case 1: position = (int) suspect.getSelectedItem(); break;
                case 2: position = (int) weapon.getSelectedItem(); break;
                case 3: position = (int) room.getSelectedItem(); break;
            }
            owner = ClueGameSheet.getModel().getOwnerIndex(ClueGameSheet.getRow(position));
            text = ClueGameSheet.getText(position);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv = view.findViewById(android.R.id.text1);


        if(owner != -1)
            txv.setBackgroundColor(PlayerBuilder.getPlayerByIndex(owner).getColor());

        txv.setTextSize(20f);
        txv.setText(text);

        return view;
    }
}
