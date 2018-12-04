package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.displayfort.mvpatel.Model.Room;
import com.displayfort.mvpatel.R;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class DialogRoomListAdapter extends RecyclerView.Adapter<DialogRoomListAdapter.ViewHolder> {

    private ArrayList<Room> projectArrayList = new ArrayList<>();
    private Context context;
    public int lastPosition = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView favroutedNameTextView;
        private View view;
        private ImageView selectedIv;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            favroutedNameTextView = (TextView) view.findViewById(R.id.favroutedNameTextView);
            selectedIv = view.findViewById(R.id.selected_iv);
        }

    }

    public DialogRoomListAdapter(Context context, ArrayList<Room> searchList) {
        this.projectArrayList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_room_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Room room = projectArrayList.get(position);
        viewHolder.favroutedNameTextView.setText(room.name);
        viewHolder.favroutedNameTextView.setTextColor(context.getResources().getColor(R.color.dark_text_color));


        viewHolder.selectedIv.setVisibility((room.isSelected) ? View.VISIBLE : View.GONE);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition == (-1)) {
                    lastPosition = position;
                } else {
                    projectArrayList.get(lastPosition).isSelected = false;
                    lastPosition = position;
                    projectArrayList.get(lastPosition).isSelected = true;
                }
                notifyDataSetChanged();
            }
        });

    }

    public Room getSelectedRoom() {
        if (lastPosition != (-1)) {
            return projectArrayList.get(lastPosition);
        }
        return null;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (projectArrayList != null) ? projectArrayList.size() : 0;
    }

    public void setlist(ArrayList<Room> Rooms) {
        this.projectArrayList = Rooms;
        notifyDataSetChanged();
    }
}
