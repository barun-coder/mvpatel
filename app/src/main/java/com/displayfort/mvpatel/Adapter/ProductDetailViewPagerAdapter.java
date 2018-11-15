package com.displayfort.mvpatel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.ImageFullscreenActivity;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

public class ProductDetailViewPagerAdapter extends PagerAdapter {
    private ArrayList<AttachmentListDao> attachmentLists;
    private Context mContext;
    private int[] mResources;

    public ProductDetailViewPagerAdapter(Context context, ArrayList<AttachmentListDao> arrayList) {
        this.mContext = context;
        this.attachmentLists = arrayList;

    }

    public int getCount() {
        return (this.attachmentLists == null) ? 0 : attachmentLists.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.product_detail_item, viewGroup, false);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.img_pager_item);
        if (!((attachmentLists.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.THUMBNAIL.name())
                || (attachmentLists.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.CAD.name())
                || (attachmentLists.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())
        )) {
            //
            Utility.setImage(mContext, this.attachmentLists.get(i).attachmentURL, imageView, 'A');
        }

        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!((attachmentLists.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.THUMBNAIL.name()))) {
                    Intent intent = new Intent(ProductDetailViewPagerAdapter.this.mContext, ImageFullscreenActivity.class);
                    intent.putExtra("URL", ((AttachmentListDao) ProductDetailViewPagerAdapter.this.attachmentLists.get(i)).attachmentURL);
                    intent.putExtra("is_product", true);
                    ActivityOptionsCompat makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation
                            (((Activity) ProductDetailViewPagerAdapter.this.mContext),
                                    imageView,
                                    ProductDetailViewPagerAdapter.this.mContext.getString(R.string.product_image_transition));
                    ProductDetailViewPagerAdapter.this.mContext.startActivity(intent, makeSceneTransitionAnimation.toBundle());
                }
            }
        });
        viewGroup.addView(inflate);
        return inflate;
    }


    public ArrayList<AttachmentListDao> getAllItem() {
        return (this.attachmentLists == null || this.attachmentLists.size() <= 0) ? null : this.attachmentLists;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((FrameLayout) object);
    }


}
