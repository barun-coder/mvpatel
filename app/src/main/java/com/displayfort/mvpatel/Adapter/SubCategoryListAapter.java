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
import com.displayfort.mvpatel.Model.SubCategory;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class SubCategoryListAapter extends RecyclerView.Adapter<SubCategoryListAapter.ViewHolder> {

    private ArrayList<SubCategory> finalList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgPagerItemIv;
        public ImageView mNewArrivalImageViewIv;
        public TextView mProductNameTextViewTv;

        public ViewHolder(View view) {
            super(view);


            mImgPagerItemIv = (ImageView) view.findViewById(R.id.img_pager_item_iv);
            mNewArrivalImageViewIv = (ImageView) view.findViewById(R.id.newArrivalImageView_iv);
            mProductNameTextViewTv = (TextView) view.findViewById(R.id.productNameTextView_tv);

        }

    }

    public SubCategoryListAapter(Context context, ArrayList<SubCategory> searchList) {
        this.finalList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_category_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        SubCategory SubCategory = finalList.get(position);
        viewHolder.mProductNameTextViewTv.setText(SubCategory.name);
        if (!(SubCategory.attachable == null || SubCategory.attachable.attachmentList == null || SubCategory.attachable.attachmentList.size() <= 0)) {
            for (int i = 0; i < SubCategory.attachable.attachmentList.size(); i++) {
                if (((AttachmentListDao) SubCategory.attachable.attachmentList.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.COVERPIC.name())) {
                    Utility.setImage(context, ((AttachmentListDao) SubCategory.attachable.attachmentList.get(i)).attachmentURL, viewHolder.mImgPagerItemIv);
                }
            }
        }
        if (SubCategory.newArrival) {
            viewHolder.mNewArrivalImageViewIv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (finalList != null) ? finalList.size() : 0;
    }

    public void setlist(ArrayList<SubCategory> SubCategorys) {
        this.finalList = SubCategorys;
        notifyDataSetChanged();
    }
}
