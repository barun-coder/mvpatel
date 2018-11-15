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
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class ProductListAapter extends RecyclerView.Adapter<ProductListAapter.ViewHolder> {

    private ArrayList<Product> finalList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mProductCodeTv;
        public ImageView mImgPagerItemIv;
        public ImageView mNewArrivalImageViewIv;
        public TextView mProductNameTextViewTv;

        public ViewHolder(View view) {
            super(view);


            mImgPagerItemIv = (ImageView) view.findViewById(R.id.img_pager_item_iv);
            mNewArrivalImageViewIv = (ImageView) view.findViewById(R.id.newArrivalImageView_iv);
            mProductNameTextViewTv = (TextView) view.findViewById(R.id.productNameTextView_tv);
            mProductCodeTv = (TextView) view.findViewById(R.id.productCodeTextView);


        }

    }

    public ProductListAapter(Context context, ArrayList<Product> searchList) {
        this.finalList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Product product = finalList.get(position);
        viewHolder.mProductNameTextViewTv.setText(product.name);
        viewHolder.mProductCodeTv.setText(product.code);
        viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
        if (!(product.attachable == null || product.attachable.attachmentList == null || product.attachable.attachmentList.size() <= 0)) {
            for (int i = 0; i < product.attachable.attachmentList.size(); i++) {
                if (((AttachmentListDao) product.attachable.attachmentList.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.THUMBNAIL.name())) {
                    Utility.setImage(context, ((AttachmentListDao) product.attachable.attachmentList.get(i)).attachmentURL, viewHolder.mImgPagerItemIv);
                }
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (finalList != null) ? finalList.size() : 0;
    }

    public void setlist(ArrayList<Product> Products) {
        this.finalList = Products;
        notifyDataSetChanged();
    }
}
