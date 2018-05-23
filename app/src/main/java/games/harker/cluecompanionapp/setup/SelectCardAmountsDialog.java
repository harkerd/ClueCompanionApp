package games.harker.cluecompanionapp.setup;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import games.harker.cluecompanionapp.PlayerSelectAdapter;
import games.harker.cluecompanionapp.R;

public class SelectCardAmountsDialog extends DialogFragment {
    private Button positiveButton;
    private Spinner select1;
    private Spinner select2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.dialog_select_card_amounts, null);

        select1 = view.findViewById(R.id.less_1);
        select2 = view.findViewById(R.id.less_2);

        select1.setAdapter(new PlayerSelectAdapter(this.getActivity(), false));
        select1.setOnItemSelectedListener(new SpinnerListener());

        select2.setAdapter(new PlayerSelectAdapter(this.getActivity(), false));
        select2.setOnItemSelectedListener(new SpinnerListener());

        builder.setView(view);
        builder.setTitle("Select the two players with less cards");

        builder.setPositiveButton("Start Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                PlayerBuilder.setPlayersWithLessCards((int) select1.getSelectedItem(), (int) select2.getSelectedItem());

                InitialSettingsActivity activity = (InitialSettingsActivity) getActivity();
                activity.startGame();
            }
        });

        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setEnabled(false);
        }

    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            positiveButton.setEnabled(select1.getSelectedItemPosition() != select2.getSelectedItemPosition());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }
}
