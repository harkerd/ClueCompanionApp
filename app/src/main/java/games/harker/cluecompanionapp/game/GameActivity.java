package games.harker.cluecompanionapp.game;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class GameActivity extends AppCompatActivity
{
    public RecyclerView tableView;
    private RecyclerView.Adapter tableAdapter;
    private View[] buttons = new View[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tableView = findViewById(R.id.table_list);
        tableView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tableView.setLayoutManager(layoutManager);

        tableAdapter = new TableAdapter();
        tableView.setAdapter(tableAdapter);

        LinearLayout topBar = findViewById(R.id.top_bar);
        TextView emptySpace = new TextView(this, null);
        int dps = 125;
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        emptySpace.setWidth(pixels);
        topBar.addView(emptySpace);

        for(int i = 0; i < ClueGameSheet.getModel().numberOfPlayers; i++)
        {
            View columnElementSeparator = new View(this);
            columnElementSeparator.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
            columnElementSeparator.setBackgroundColor(Color.BLACK);

            TextView nameContainer = new TextView(this, null);
            nameContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            nameContainer.setTextSize(20);
            nameContainer.setGravity(Gravity.CENTER);
            nameContainer.setText(PlayerBuilder.getPlayerByIndex(i).getName());


            topBar.addView(columnElementSeparator);
            topBar.addView(nameContainer);
        }


        LinearLayout bottomBar = findViewById(R.id.bottom_bar);

        Button maybeHasButton = new Button(this);
        maybeHasButton.setBackgroundColor(Color.TRANSPARENT);
        maybeHasButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        maybeHasButton.setTextSize(24);
        maybeHasButton.setText("?");
        maybeHasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedButton(ClueGameSheet.MAYBE_HAS);

            }
        });
        buttons[ClueGameSheet.MAYBE_HAS - 1] = maybeHasButton;

        Button mustHaveButton = new Button(this);
        mustHaveButton.setBackgroundColor(Color.TRANSPARENT);
        mustHaveButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        mustHaveButton.setTextSize(24);
        mustHaveButton.setText("!");
        mustHaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedButton(ClueGameSheet.MUST_HAVE);
            }
        });
        buttons[ClueGameSheet.MUST_HAVE - 1] = mustHaveButton;

        Button knowHasNotButton = new Button(this);
        knowHasNotButton.setBackgroundColor(Color.TRANSPARENT);
        knowHasNotButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        knowHasNotButton.setTextSize(24);
        knowHasNotButton.setText("X");
        knowHasNotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedButton(ClueGameSheet.KNOW_HAS_NOT);
            }
        });
        buttons[ClueGameSheet.KNOW_HAS_NOT - 1] = knowHasNotButton;


        ImageButton seenButton = (ImageButton) LayoutInflater.from(this).inflate(R.layout.eye_button, bottomBar, false);
        seenButton.setBackgroundColor(Color.TRANSPARENT);
        seenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedButton(ClueGameSheet.SEEN);
            }
        });
        buttons[ClueGameSheet.SEEN - 1] = seenButton;


        bottomBar.addView(maybeHasButton);
        bottomBar.addView(mustHaveButton);
        bottomBar.addView(knowHasNotButton);
        bottomBar.addView(seenButton);

        int currentlySelected = ClueGameSheet.getModel().getSelectedType();
        if(currentlySelected != ClueGameSheet.UNKNOWN)
            buttons[currentlySelected - 1].setBackgroundColor(Color.YELLOW);
    }

    private void updateSelectedButton(int selectedType)
    {
        int currentlySelected = ClueGameSheet.getModel().getSelectedType();
        if(currentlySelected != ClueGameSheet.UNKNOWN)
            buttons[currentlySelected - 1].setBackgroundColor(Color.TRANSPARENT);

        ClueGameSheet.getModel().setSelectedType(selectedType);
        buttons[selectedType - 1].setBackgroundColor(Color.YELLOW);
    }
}