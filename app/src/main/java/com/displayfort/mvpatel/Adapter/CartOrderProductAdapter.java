package com.displayfort.mvpatel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.displayfort.mvpatel.InterFaces.OnOrderClick;
import com.displayfort.mvpatel.Model.OrderDetailDao;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;


/**
 * Created by husain on 9/6/17.
 */

public class CartOrderProductAdapter extends RecyclerView.Adapter<CartOrderProductAdapter.ViewHolder> {

    private ArrayList<OrderDetailDao> finalList = new ArrayList<>();
    private Context context;
    private OnOrderClick onOrderClick;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mOrderDetailDaoCodeTv;
        public ImageView mImgPagerItemIv;
        public ImageView mCloseIv;
        public TextView mOrderDetailDaoNameTextViewTv, productprice_tv;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mImgPagerItemIv = (ImageView) view.findViewById(R.id.img_pager_item_iv);
            mCloseIv = (ImageView) view.findViewById(R.id.newArrivalImageView_iv);
            mOrderDetailDaoNameTextViewTv = (TextView) view.findViewById(R.id.productNameTextView_tv);
            mOrderDetailDaoCodeTv = (TextView) view.findViewById(R.id.productCodeTextView_tv);
            productprice_tv = (TextView) view.findViewById(R.id.productprice_tv);
        }

    }

    public CartOrderProductAdapter(Context context, ArrayList<OrderDetailDao> searchList, OnOrderClick onOrderClick) {
        this.finalList = searchList;
        this.context = context;
        this.onOrderClick = onOrderClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final OrderDetailDao orderDetailDao = finalList.get(position);
        viewHolder.mOrderDetailDaoNameTextViewTv.setText(orderDetailDao.name);
        viewHolder.mOrderDetailDaoCodeTv.setText(orderDetailDao.code);
        try {
            viewHolder.productprice_tv.setText(Utility.showPriceInUK(orderDetailDao.price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((orderDetailDao.ImageUrl != null)) {
            Utility.setImage(context, orderDetailDao.ImageUrl, viewHolder.mImgPagerItemIv);
        }
        viewHolder.mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finalList.remove(position);
//                notifyDataSetChanged();
                onOrderClick.OnOrderClick(orderDetailDao, position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (finalList != null) ? finalList.size() : 0;
    }

    public void setlist(ArrayList<OrderDetailDao> categoryDaos) {
        this.finalList = categoryDaos;
        notifyDataSetChanged();
    }
}
