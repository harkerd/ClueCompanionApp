package games.harker.cluecompanionapp.game;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import games.harker.cluecompanionapp.R;
import games.harker.cluecompanionapp.setup.PlayerBuilder;

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private GameActivity activity;

    public TableAdapter(GameActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case -1:
                LinearLayout headerView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.game_sheet_row_header, parent, false);
                holder = new Header(headerView);
                break;
            case 1:
                LinearLayout rowView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.game_sheet_row, parent, false);
                holder = new DataRow(rowView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (holder.getItemViewType()) {
            case -1:
                Header headerView = (Header)holder;
                headerView.setText(ClueGameSheet.getText(position));
                break;

            case 1:
                DataRow rowView = (DataRow)holder;
                rowView.setText(ClueGameSheet.getText(position), ClueGameSheet.getRow(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ClueGameSheet.isHeader(position) ? -1 : 1;
    }

    @Override
    public int getItemCount() {
        return ClueGameSheet.getModel().length + ClueGameSheet.getModel().numberOfHeaders;
    }

    public static class Header extends RecyclerView.ViewHolder
    {
        private View headerView;

        public Header(View headerView) {
            super(headerView);

            this.headerView = headerView;
        }

        private void setText(String text)
        {
            TextView headerText = headerView.findViewById(R.id.header_text);
            headerText.setText(text);
        }
    }

    public class DataRow extends RecyclerView.ViewHolder
    {
        private int row;
        private int position;
        private ViewSwitcher[] rowData;
        private View rowView;

        public DataRow(View rowView)
        {
            super(rowView);
            this.rowView = rowView;

            LinearLayout rowDataView = rowView.findViewById(R.id.row_data_view);
            rowData = new ViewSwitcher[ClueGameSheet.getModel().numberOfPlayers];

            for(int i = 0; i < ClueGameSheet.getModel().numberOfPlayers; i++)
            {
                final int col = i;
                View columnElementSeparator = new View(rowView.getContext());
                columnElementSeparator.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                columnElementSeparator.setBackgroundColor(Color.BLACK);

                ViewSwitcher buttonContainer = new ViewSwitcher(rowView.getContext());
                buttonContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                buttonContainer.setBackgroundColor(PlayerBuilder.getPlayerByIndex(i).getColor());

                Button columnElement = new Button(rowView.getContext());
                columnElement.setBackgroundColor(Color.TRANSPARENT);
                columnElement.setTextSize(24);
                columnElement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClueGameSheet.getModel().toggleValue(ClueGameSheet.getModel().getSelectedType(), row, col);
                        activity.updateValues(row, col);
                        TableAdapter.this.notifyItemChanged(position);
                    }
                });

                ImageButton seenButton = (ImageButton) LayoutInflater.from(rowView.getContext()).inflate(R.layout.eye_button, null, false);
                seenButton.setBackgroundColor(Color.TRANSPARENT);
                seenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClueGameSheet.getModel().toggleValue(ClueGameSheet.getModel().getSelectedType(), row, col);
                        activity.updateValues(row, col);
                        TableAdapter.this.notifyItemChanged(position);
                    }
                });

                buttonContainer.addView(columnElement);
                buttonContainer.addView(seenButton);

                rowDataView.addView(columnElementSeparator);
                rowDataView.addView(buttonContainer);

                rowData[i] = buttonContainer;
            }
        }

        private void setText(String text, int rowNum, int pos)
        {
            TextView headerText = rowView.findViewById(R.id.row_label);
            headerText.setText(text);
            if(ClueGameSheet.getModel().noOneHas(rowNum))
                headerText.setBackgroundColor(Color.RED);
            else
                headerText.setBackgroundColor(Color.WHITE);

            this.row = rowNum;
            this.position = pos;

            for(int i = 0; i < ClueGameSheet.getModel().numberOfPlayers; i++)
            {
                int type = ClueGameSheet.getModel().getValue(row, i);
                View button = rowData[i].getChildAt(rowData[i].getDisplayedChild());
                switch (type)
                {
                    case ClueGameSheet.SEEN:
                        if(!(button instanceof ImageButton))
                        {
                            rowData[i].showNext();
                        }
                        break;

                    case ClueGameSheet.KNOW_HAS_NOT:
                        if(button instanceof ImageButton)
                        {
                            button = rowData[i].getNextView();
                            rowData[i].showNext();
                        }
                        Button knowHasNotButton = (Button) button;

                        knowHasNotButton.setText("X");
                        break;
                    case ClueGameSheet.MUST_HAVE:
                        if(button instanceof ImageButton)
                        {
                            button = rowData[i].getNextView();
                            rowData[i].showNext();
                        }
                        Button mustHaveButton = (Button) button;

                        mustHaveButton.setText("!");
                        break;
                    case ClueGameSheet.MAYBE_HAS:
                        if(button instanceof ImageButton)
                        {
                            button = rowData[i].getNextView();
                            rowData[i].showNext();
                        }
                        Button maybeHasButton = (Button) button;

                        maybeHasButton.setText("?");
                        break;
                    case ClueGameSheet.UNKNOWN:
                        if(button instanceof ImageButton)
                        {
                            button = rowData[i].getNextView();
                            rowData[i].showNext();
                        }
                        Button unknown = (Button) button;

                        unknown.setText("");
                        break;
                }
            }
        }
    }
}
