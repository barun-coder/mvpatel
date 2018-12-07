package com.displayfort.mvpatel.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.LoginActivity;
import com.displayfort.mvpatel.Utils.Dialogs;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private ArrayList<Project> projectArrayList = new ArrayList<>();
    private Context context;
    private OnProjectClick onProjectClick;

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView threeDotImageView;
        public TextView favroutedNameTextView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            threeDotImageView = (ImageView) view.findViewById(R.id.threeDotImageView);
            favroutedNameTextView = (TextView) view.findViewById(R.id.favroutedNameTextView);
        }

    }

    public ProjectListAdapter(Context context, ArrayList<Project> searchList, OnProjectClick onProjectClick) {
        this.projectArrayList = searchList;
        this.context = context;
        this.onProjectClick = onProjectClick;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Project product = projectArrayList.get(position);
        viewHolder.favroutedNameTextView.setText(product.name);
        viewHolder.threeDotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(viewHolder.view, position, projectArrayList);
            }
        });

        viewHolder.favroutedNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProjectClick.OnProjectClick(projectArrayList.get(position), false);
            }
        });


    }

    private void showPopUp(View view, final int position, final ArrayList<Project> projectArrayList) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_myprojectlisting, popupMenu.getMenu());
        popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                int menuId = menuItem.getItemId();

                if (menuId == R.id.emailImageButton) {
                    //TODO Email
                } else {
                    Dialogs.showYesNolDialog(context, "Confirmation", "Are you sure you want to Delete " + projectArrayList.get(position).name, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((Dialog) v.getTag()).dismiss();
                            TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
                            dbHandler.DeleteProject(projectArrayList.get(position).projectId);
                            onProjectClick.OnProjectClick(projectArrayList.get(position), true);
                        }
                    });

                }
                return true;
            }
        });
        try {
            popupMenu.show();
        } catch (Exception e) {
            Log.w("Menu", "error forcing menu icons to show", e);
            popupMenu.show();
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
