package com.fullsail.apolloarchery.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.HistoryRounds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = "HistoryAdapter";

    private static ArrayList<HistoryRounds> historyList;
    public final ArrayList<HistoryRounds> filteredHistory;
    CustomFilter filter;

    private final Context mContext;

    public HistoryAdapter(ArrayList<HistoryRounds> historyList, Context mContext) {
        HistoryAdapter.historyList = historyList;
        this.mContext = mContext;
        filteredHistory = historyList;
    }

    @Override
    public int getCount() {
        return filteredHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_history_list, null);
        }

        ImageView iv = convertView.findViewById(R.id.history_image);
        Picasso.get()
                .load(R.drawable.archery_target)
                .resize(400, 400)
                .centerCrop()
                .into(iv);

        TextView tvDate = convertView.findViewById(R.id.history_date);
        tvDate.setText(filteredHistory.get(position).date);
        TextView tvName = convertView.findViewById(R.id.history_name);
        tvName.setText(filteredHistory.get(position).roundName);
        TextView tvScore = convertView.findViewById(R.id.history_score);
        tvScore.setText(filteredHistory.get(position).totalScore);

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilter();
        }

        Log.i(TAG, "getFilter: " + filter);

        return filter;
    }

    static class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null) {
                if (constraint.length() > 0) {
                    // Constraint to lowercase
                    constraint = constraint.toString().toLowerCase();

                    ArrayList<HistoryRounds> filters = new ArrayList<>();

                    // Filtering
//                    for (int i = 0; i < historyList.size(); i++) {
//                        if (historyList.get(i).date.contains(constraint)) {
//                            HistoryRounds s = new HistoryRounds(historyList.get(0).date, historyList.get(0).roundName,
//                                    historyList.get(0).round, historyList.get(0).totalScore);
//                            filters.add(s);
//                        }
//                    }
                    results.values = filters;
                    results.count = filters.size();
                }else {
                    results.count = historyList.size();
                    results.values = historyList;
                }
                Log.i(TAG, "Filtering Results Count: " + results.count + " Results Value: " + results.values);
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
