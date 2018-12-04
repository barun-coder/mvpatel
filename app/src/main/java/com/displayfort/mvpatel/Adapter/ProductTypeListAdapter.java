package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class ProductTypeListAdapter extends RecyclerView.Adapter<ProductTypeListAdapter.ViewHolder> {

    private ArrayList<ProductPrice> finalList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgPagerItemIv;


        public ViewHolder(View view) {
            super(view);
            mImgPagerItemIv = (ImageView) view.findViewById(R.id.product_image_iv);
        }

    }

    public ProductTypeListAdapter(Context context, ArrayList<ProductPrice> searchList) {
        this.finalList = searchList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_color_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        ProductPrice productPrice = finalList.get(position);
        Utility.setImage(context, (productPrice.attachmentListDao.attachmentURL), viewHolder.mImgPagerItemIv);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (finalList != null) ? finalList.size() : 0;
    }

    public void setlist(ArrayList<ProductPrice> ProductPrices) {
        this.finalList = ProductPrices;
        notifyDataSetChanged();
    }
}
