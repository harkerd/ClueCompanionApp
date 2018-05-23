package games.harker.cluecompanionapp.suggestion;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.game.ClueGameSheet;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class SuggestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LinearLayout row = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_row, parent, false);
        return new SuggestionRowHolder(row);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        SuggestionRowHolder row = (SuggestionRowHolder) holder;
        SuggestionModel model = SuggestionModel.get(position);
        row.bind(model);
    }

    @Override
    public int getItemCount()
    {
        return SuggestionModel.getListSize();
    }

    private class SuggestionRowHolder extends RecyclerView.ViewHolder
    {
        public SuggestionRowHolder(View itemView)
        {
            super(itemView);
        }

        public void bind(SuggestionModel model)
        {
            TextView accuser = itemView.findViewById(R.id.accuser);
            accuser.setBackgroundColor(PlayerBuilder.getPlayer(model.getAccuserPlayer()).getColor());
            accuser.setText(PlayerBuilder.getPlayer(model.getAccuserPlayer()).getName());


            TextView suspect = itemView.findViewById(R.id.suspect);
            int owner = ClueGameSheet.getModel().getOwnerIndex(ClueGameSheet.getRow(model.getSuspect()));
            if(owner != -1)
                suspect.setBackgroundColor(PlayerBuilder.getPlayerByIndex(owner).getColor());
            suspect.setText(ClueGameSheet.getText(model.getSuspect()));
            if(model.getSuspect() == model.getCardShown())
                suspect.setTypeface(null, Typeface.BOLD);

            TextView weapon = itemView.findViewById(R.id.weapon);
            owner = ClueGameSheet.getModel().getOwnerIndex(ClueGameSheet.getRow(model.getWeapon()));
            if(owner != -1)
                weapon.setBackgroundColor(PlayerBuilder.getPlayerByIndex(owner).getColor());
            weapon.setText(ClueGameSheet.getText(model.getWeapon()));
            if(model.getWeapon() == model.getCardShown())
                weapon.setTypeface(null, Typeface.BOLD);

            TextView room = itemView.findViewById(R.id.room);
            owner = ClueGameSheet.getModel().getOwnerIndex(ClueGameSheet.getRow(model.getRoom()));
            if(owner != -1)
                room.setBackgroundColor(PlayerBuilder.getPlayerByIndex(owner).getColor());
            room.setText(ClueGameSheet.getText(model.getRoom()));
            if(model.getRoom() == model.getCardShown())
                room.setTypeface(null, Typeface.BOLD);


            TextView responder = itemView.findViewById(R.id.responder);
            if(model.getResponderPlayer() == -1)
            {
                responder.setText("None");
            }
            else
            {
                responder.setBackgroundColor(PlayerBuilder.getPlayer(model.getResponderPlayer()).getColor());
                responder.setText(PlayerBuilder.getPlayer(model.getResponderPlayer()).getName());
            }
        }
    }
}