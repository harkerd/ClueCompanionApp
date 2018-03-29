package games.harker.cluecompanionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class InitialSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_settings);

        NumberPicker picker = findViewById(R.id.num_players);
        picker.setWrapSelectorWheel(true);
        picker.setMinValue(3);
        picker.setMaxValue(6);

        Button start = findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitialSettingsActivity.this, GameActivity.class);

                NumberPicker picker = findViewById(R.id.num_players);
                int value = picker.getValue();
                ClueGameSheet.quitGame();
                ClueGameSheet.setNumberOfPlayers(value);
                startActivity(intent);
            }
        });
    }


}
