package games.harker.cluecompanionapp.suggestion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.setup.Settings;

public class SuggestionList extends AppCompatActivity {
    private static SuggestionListAdapter adapter;

    public static void updateList()
    {
        if(Settings.autoPopulate())
        {
            for(int i = SuggestionModel.getListSize() - 1; i >= 0; i--)
            {
                SuggestionModel.get(i).updateResults();
            }
        }
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_list);

        RecyclerView suggestionList = findViewById(R.id.suggestion_list);
        suggestionList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        suggestionList.setLayoutManager(layoutManager);

        adapter = new SuggestionListAdapter();
        suggestionList.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSuggestionDialog newFragment = new AddSuggestionDialog();
                newFragment.show(getFragmentManager(), "new_suggestion");
            }
        });
    }
}
