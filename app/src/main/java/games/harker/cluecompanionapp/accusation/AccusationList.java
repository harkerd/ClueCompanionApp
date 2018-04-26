package games.harker.cluecompanionapp.accusation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.game.ClueGameSheet;
import games.harker.cluecompanionapp.setup.Settings;

public class AccusationList extends AppCompatActivity {
    private static AccusationListAdapter adapter;

    public static void updateList()
    {
        if(Settings.autoPopulate())
        {
            for(int i = AccusationModel.getListSize() - 1; i >= 0; i--)
            {
                AccusationModel.get(i).updateResults();
            }
        }
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accusation_list);

        RecyclerView accusationList = findViewById(R.id.accusation_list);
        accusationList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        accusationList.setLayoutManager(layoutManager);

        adapter = new AccusationListAdapter();
        accusationList.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAccusationDialog newFragment = new AddAccusationDialog();
                newFragment.show(getFragmentManager(), "new_accusation");
            }
        });
    }
}
