package games.harker.cluecompanionapp.suggestion;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import games.harker.cluecompanionapp.PlayerSelectAdapter;
import games.harker.cluecompanionapp.R;

public class AddSuggestionDialog extends DialogFragment {
    private Spinner accuser;
    private Spinner suspectItemsList;
    private Spinner weaponItemsList;
    private Spinner roomItemsList;
    private Spinner responder;
    private Spinner cardShown;
    private BaseAdapter cardShownAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.dialog_add_suggestion, null);

        accuser = view.findViewById(R.id.accuser);
        accuser.setAdapter(new PlayerSelectAdapter(getActivity(), false));

        suspectItemsList = view.findViewById(R.id.suspect);
        suspectItemsList.setAdapter(new SuggestionItemAdapter(getActivity(), 1));
        suspectItemsList.setOnItemSelectedListener(new OnSuggestionItemSelected());

        weaponItemsList = view.findViewById(R.id.weapon);
        weaponItemsList.setAdapter(new SuggestionItemAdapter(getActivity(), 8));
        weaponItemsList.setOnItemSelectedListener(new OnSuggestionItemSelected());

        roomItemsList = view.findViewById(R.id.room);
        roomItemsList.setAdapter(new SuggestionItemAdapter(getActivity(), 15));
        roomItemsList.setOnItemSelectedListener(new OnSuggestionItemSelected());

        responder = view.findViewById(R.id.responder);
        responder.setAdapter(new PlayerSelectAdapter(getActivity(), true));

        cardShown = view.findViewById(R.id.card_shown);
        cardShownAdapter = new CardShownAdapter(getActivity(), suspectItemsList, weaponItemsList, roomItemsList);
        cardShown.setAdapter(cardShownAdapter);


        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SuggestionModel.addToList(new SuggestionModel((int) accuser.getSelectedItem(), (int) suspectItemsList.getSelectedItem(), (int) weaponItemsList.getSelectedItem(), (int) roomItemsList.getSelectedItem(), (int) responder.getSelectedItem(), (int) cardShown.getSelectedItem()));
                        SuggestionList.updateList();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddSuggestionDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public class OnSuggestionItemSelected implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            cardShownAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}
