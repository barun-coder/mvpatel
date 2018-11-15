package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryDao> finalList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mMasterCatIv;
        public TextView mNotiTitleTv;
        public TextView mDescriptionTv;

        public ViewHolder(View view) {
            super(view);

            mMasterCatIv = (ImageView) view.findViewById(R.id.master_cat_iv);
            mNotiTitleTv = (TextView) view.findViewById(R.id.noti_title_tv);
            mDescriptionTv = (TextView) view.findViewById(R.id.description_tv);

        }

    }

    public CategoryAdapter(Context context, ArrayList<CategoryDao> searchList) {
        this.finalList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.master_cat_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        CategoryDao categoryDao = finalList.get(position);
        viewHolder.mNotiTitleTv.setText(categoryDao.name);
        viewHolder.mDescriptionTv.setVisibility(View.GONE);
        if (!(categoryDao.attachable == null || categoryDao.attachable.attachmentList == null || categoryDao.attachable.attachmentList.size() <= 0)) {
            for (int i = 0; i < categoryDao.attachable.attachmentList.size(); i++) {
                if (((AttachmentListDao) categoryDao.attachable.attachmentList.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.COVERPIC.name())) {
                    Utility.setImage(context, ((AttachmentListDao) categoryDao.attachable.attachmentList.get(i)).attachmentURL, viewHolder.mMasterCatIv);
                }
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (finalList != null) ? finalList.size() : 0;
    }

    public void setlist(ArrayList<CategoryDao> CategoryDaos) {
        this.finalList = CategoryDaos;
        notifyDataSetChanged();
    }
}
