package games.harker.cluecompanionapp.game;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.Tools;
import games.harker.cluecompanionapp.accusation.AccusationList;
import games.harker.cluecompanionapp.setup.PlayerBuilder;
import games.harker.cluecompanionapp.setup.Settings;

public class GameActivity extends AppCompatActivity
{
    public RecyclerView tableView;
    private RecyclerView.Adapter tableAdapter;
    private View[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(Settings.accessAccusationList())
        {
            buttons = new View[5];
        }
        else
        {
            buttons = new View[4];
        }

        tableView = findViewById(R.id.table_list);
        tableView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tableView.setLayoutManager(layoutManager);

        tableAdapter = new TableAdapter(this);
        tableView.setAdapter(tableAdapter);

        LinearLayout topBar = findViewById(R.id.top_bar);
        TextView emptySpace = new TextView(this, null);
        emptySpace.setWidth(Tools.dpConvertToPixel(125, this));
        topBar.addView(emptySpace);

        for(int i = 0; i < ClueGameSheet.getModel().numberOfPlayers; i++)
        {
            buildTopBarColumn(PlayerBuilder.getPlayerByIndex(i).getName(), topBar);
        }

        LinearLayout cardCountBar = findViewById(R.id.card_count_bar);
        if(Settings.showCardCount())
        {
            cardCountBar.setVisibility(LinearLayout.VISIBLE);
            emptySpace = new TextView(this, null);
            emptySpace.setWidth(Tools.dpConvertToPixel(125, this));
            cardCountBar.addView(emptySpace);

            for(int i = 0; i < ClueGameSheet.getModel().numberOfPlayers; i++)
            {
                Pair<Integer, Integer> cardCount = ClueGameSheet.getModel().getPlayerCardCount(i);
                buildTopBarColumn(cardCount.first + "/" + cardCount.second, cardCountBar);
            }
        }
        else
        {
            cardCountBar.setVisibility(LinearLayout.GONE);
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

        if(Settings.accessAccusationList())
        {
            Button accessAccusationList = new Button(this);
            accessAccusationList.setBackgroundColor(Color.TRANSPARENT);
            accessAccusationList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            accessAccusationList.setTextSize(24);
            accessAccusationList.setText("+");
            accessAccusationList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GameActivity.this, AccusationList.class);
                    startActivity(intent);
                }
            });
            bottomBar.addView(accessAccusationList);
        }

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

    private void buildTopBarColumn(String name, LinearLayout parent)
    {
        View columnElementSeparator = new View(this);
        columnElementSeparator.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        columnElementSeparator.setBackgroundColor(Color.BLACK);

        TextView nameContainer = new TextView(this, null);
        nameContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        nameContainer.setTextSize(20);
        nameContainer.setGravity(Gravity.CENTER);
        nameContainer.setText(name);

        parent.addView(columnElementSeparator);
        parent.addView(nameContainer);
    }

    public void updateValues(int row, int col)
    {
        if(Settings.showCardCount())
        {
            updatePlayerCardCount(col);
        }

        if(Settings.autoPopulate())
        {
            autoPopulateValues(row, col);
        }

        if(Settings.accessAccusationList())
        {
            AccusationList.updateList();
            tableAdapter.notifyDataSetChanged();
        }
    }

    private void updatePlayerCardCount(int playerIndex)
    {
        LinearLayout cardCountBar = findViewById(R.id.card_count_bar);
        int index = 2 * (playerIndex + 1);
        TextView fieldToUpdate = (TextView) cardCountBar.getChildAt(index);
        Pair<Integer, Integer> cardCount = ClueGameSheet.getModel().getPlayerCardCount(playerIndex);
        fieldToUpdate.setText(cardCount.first + "/" + cardCount.second);
    }

    private void autoPopulateValues(int row, int col)
    {
        if(ClueGameSheet.getModel().getValue(row, col) == ClueGameSheet.SEEN)
        {
            ClueGameSheet.getModel().setXOnRow(row);
        }

        Pair cardCount = ClueGameSheet.getModel().getPlayerCardCount(col);
        if(cardCount.first == cardCount.second)
        {
            ClueGameSheet.getModel().setXOnCol(col);
            tableAdapter.notifyDataSetChanged();
        }
    }
}
