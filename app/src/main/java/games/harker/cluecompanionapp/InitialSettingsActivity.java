package games.harker.cluecompanionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class InitialSettingsActivity extends AppCompatActivity {
    private LinearLayout playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_settings);
        playerList = findViewById(R.id.player_list);

        if(PlayerBuilder.getPlayersSize() != 0)
        {
            rebuildPlayers();
        }
        else
        {
            int numberOfPlayers = 3;
            for (int i = 0; i < numberOfPlayers; i++)
            {
                addPlayer();
            }
        }

        Button start = findViewById(R.id.start_button);
        start.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitialSettingsActivity.this, GameActivity.class);

                ClueGameSheet.quitGame();
                startActivity(intent);
            }
        });

        Button addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer();
            }
        });
    }

    private void rebuildPlayers()
    {
        for(int i = 0; i < PlayerBuilder.getPlayersSize(); i ++)
        {
            Player player = PlayerBuilder.getPlayerByIndex(i);
            addPlayerField(player.getName(), player.getColorIndex(), player.getId());
        }
    }

    private void addPlayer()
    {
        String playerName = "Player " + (PlayerBuilder.getPlayersSize() + 1);
        int arrayIndex =  PlayerBuilder.getPlayersSize();
        int id = PlayerBuilder.createPlayer(playerName, arrayIndex);

        addPlayerField(playerName, arrayIndex, id);
    }

    private void addPlayerField(String playerName, int colorSelectIndex, final int id) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        EditText playerNameField = new EditText(this);
        playerNameField.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10.0f));
        playerNameField.setText(playerName);
        playerNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PlayerBuilder.getPlayer(id).setName((String) charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Spinner colorSelect = new Spinner(this);
        colorSelect.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        colorSelect.setMinimumWidth(10);
        colorSelect.setAdapter(new PlayerSelectAdapter(this));
        colorSelect.setSelection(colorSelectIndex);

        colorSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PlayerBuilder.getPlayer(id).setColorIndex(i);
                //TODO: Validate if can start
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button removePlayer = new Button(this);
        int dps = 50;
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        removePlayer.setMinimumWidth(pixels);
        removePlayer.setWidth(pixels);
        removePlayer.setText("-");
        removePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = PlayerBuilder.removePlayer(id);
                playerList.removeViewAt(index);
                if (PlayerBuilder.getPlayersSize() == 5)
                {
                    findViewById(R.id.add_player).setEnabled(true);
                }
                //TODO: Validate if can start
            }
        });


        row.addView(playerNameField);
        row.addView(colorSelect);
        row.addView(removePlayer);
        playerList.addView(row);
        if (PlayerBuilder.getPlayersSize() == 6)
        {
            findViewById(R.id.add_player).setEnabled(false);
        }
    }

}
