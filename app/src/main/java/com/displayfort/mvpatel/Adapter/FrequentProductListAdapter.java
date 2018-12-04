package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.InterFaces.OnProductClick;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class FrequentProductListAdapter extends RecyclerView.Adapter<FrequentProductListAdapter.ViewHolder> {

    private ArrayList<Product> finalList = new ArrayList<>();
    private Context context;
    private OnProductClick onProductClick;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mProductCodeTv;
        public ImageView mImgPagerItemIv;
        public ImageView mNewArrivalImageViewIv;
        public TextView mProductNameTextViewTv, productprice_tv;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mImgPagerItemIv = (ImageView) view.findViewById(R.id.img_pager_item_iv);
            mNewArrivalImageViewIv = (ImageView) view.findViewById(R.id.newArrivalImageView_iv);
            mProductNameTextViewTv = (TextView) view.findViewById(R.id.productNameTextView_tv);
            mProductCodeTv = (TextView) view.findViewById(R.id.productCodeTextView_tv);
            productprice_tv = (TextView) view.findViewById(R.id.productprice_tv);
        }

    }

    public FrequentProductListAdapter(Context context, ArrayList<Product> searchList, OnProductClick onProductClick) {
        this.finalList = searchList;
        this.context = context;
        this.onProductClick = onProductClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freq_product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Product product = finalList.get(position);
        viewHolder.mProductNameTextViewTv.setText(product.name);
        viewHolder.mProductCodeTv.setText(product.code);
        try {
            viewHolder.productprice_tv.setText(Utility.showPriceInUK(product.productPrice.price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (product.isSelected) {
            viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
        } else {
            viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
        }
        if ((product.ImageUrl != null)) {
            Utility.setImage(context, product.ImageUrl, viewHolder.mImgPagerItemIv);
        }
        viewHolder.mImgPagerItemIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.mNewArrivalImageViewIv.getVisibility() != View.VISIBLE) {
                    finalList.get(position).isSelected = true;
                    viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
                    onProductClick.OnProductSelected(finalList.get(position), true);
                } else {
                    finalList.get(position).isSelected = false;
                    viewHolder.mNewArrivalImageViewIv.setVisibility(View.GONE);
                    onProductClick.OnProductSelected(finalList.get(position), false);
                }
            }
        });
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClick.OnProductClick(finalList.get(position), position);
            }
        });

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
