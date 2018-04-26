package games.harker.cluecompanionapp.accusation;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;

import games.harker.cluecompanionapp.PlayerSelectAdapter;
import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.setup.Settings;

public class AddAccusationDialog extends DialogFragment {
    private Spinner accuser;
    private Spinner suspectItemsList;
    private Spinner weaponItemsList;
    private Spinner roomItemsList;
    private Spinner responder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.dialog_add_accusation, null);

        accuser = view.findViewById(R.id.accuser);
        accuser.setAdapter(new PlayerSelectAdapter(getActivity(), false));

        suspectItemsList = view.findViewById(R.id.suspect);
        suspectItemsList.setAdapter(new AccusationItemAdapter(getActivity(), 1));

        weaponItemsList = view.findViewById(R.id.weapon);
        weaponItemsList.setAdapter(new AccusationItemAdapter(getActivity(), 8));

        roomItemsList = view.findViewById(R.id.room);
        roomItemsList.setAdapter(new AccusationItemAdapter(getActivity(), 15));

        responder = view.findViewById(R.id.responder);
        responder.setAdapter(new PlayerSelectAdapter(getActivity(), true));


        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AccusationModel.addToList(new AccusationModel((int) accuser.getSelectedItem(), (int) suspectItemsList.getSelectedItem(), (int) weaponItemsList.getSelectedItem(), (int) roomItemsList.getSelectedItem(), (int) responder.getSelectedItem()));
                        AccusationList.updateList();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddAccusationDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
