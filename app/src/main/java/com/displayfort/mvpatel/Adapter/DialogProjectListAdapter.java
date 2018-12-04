package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.InterFaces.OnProjectClick;
import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class DialogProjectListAdapter extends RecyclerView.Adapter<DialogProjectListAdapter.ViewHolder> {

    private ArrayList<Project> projectArrayList = new ArrayList<>();
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView favroutedNameTextView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            favroutedNameTextView = (TextView) view.findViewById(R.id.favroutedNameTextView);
        }

    }

    public DialogProjectListAdapter(Context context, ArrayList<Project> searchList) {
        this.projectArrayList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_project_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Project product = projectArrayList.get(position);
        viewHolder.favroutedNameTextView.setText(product.name);
        if (position == 0) {
            viewHolder.favroutedNameTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.favroutedNameTextView.setTextColor(context.getResources().getColor(R.color.dark_text_color));
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (projectArrayList != null) ? projectArrayList.size() : 0;
    }

    public void setlist(ArrayList<Project> Projects) {
        this.projectArrayList = Projects;
        notifyDataSetChanged();
    }
}
