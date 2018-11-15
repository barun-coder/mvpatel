package com.displayfort.mvpatel.Fragments;

/**
 * Created by pc on 16/10/2018 15:32.
 * DisplayFortSoftware
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.displayfort.mvpatel.Adapter.ProductDetailViewPagerAdapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.Attachable;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.SubCategory;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected long PID;
    private Bitmap bitmap;
    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private ProductDetailViewPagerAdapter adapter;
    private Product productDao;
    private CategoryDao categoryDetailDao;
    private ArrayList<AttachmentListDao> attachmentList;
    private SubCategory subCategory;
    private int CurrentItem = 0;
    private ArrayList<AttachmentListDao> pdfCaadList = new ArrayList<>();


    public static ProductDetailFragment newInstance(Long catId) {
        ProductDetailFragment contentFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("CATID", catId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PID = getArguments().getLong("CATID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail_fragment, container, false);
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        this.productDao = Product.getProductDetail(PID);
        subCategory = SubCategory.getSubCategoryDetail(productDao.subcatid);
        Attachable attachable = productDao.attachable;
        attachmentList = new ArrayList<>(filterList((attachable.attachmentList)));
        init();
        if (pdfCaadList != null && pdfCaadList.size() > 0) {
            homeViewHolder.mTechnicalDetailIb.setVisibility(View.VISIBLE);
        } else {
            homeViewHolder.mTechnicalDetailIb.setVisibility(View.GONE);
        }
    }

    private ArrayList<AttachmentListDao> filterList(ArrayList<AttachmentListDao> attachmentList) {
        ArrayList<AttachmentListDao> list = new ArrayList<>();
        pdfCaadList = new ArrayList<>();
        for (AttachmentListDao attachmentList1 : attachmentList) {
            if (!((attachmentList1).type.equalsIgnoreCase(Constant.IMAGE_TYPES.CAD.name())
                    || (attachmentList1).type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name()))) {
                list.add(0, attachmentList1);
            } else {
                pdfCaadList.add(attachmentList1);
            }
        }
        return list;
    }

    private void init() {
        homeViewHolder.mShareIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AttachmentListDao attachDao = attachmentList.get(CurrentItem);
                Picasso.with(mContext).load(attachDao.attachmentURL).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.STREAM", bitmap);
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.TEXT", productDao.name + " " + attachDao.color.name);
                        startActivity(Intent.createChooser(intent, "Send to"));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

            }
        });

        homeViewHolder.mTechnicalDetailIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTechnicalDrawingDialog();
            }
        });
        adapter = new ProductDetailViewPagerAdapter(mContext, attachmentList);
        homeViewHolder.mProductViewPager.setAdapter(adapter);
        setProductPrice(CurrentItem);
        homeViewHolder.tab_layout.setupWithViewPager(homeViewHolder.mProductViewPager, true);
        homeViewHolder.mProductNameTv.setText(subCategory.name);
        homeViewHolder.mProducttitleTv.setText(productDao.name);
        homeViewHolder.mProductCodeTv.setText(productDao.code);
        homeViewHolder.mProductDetailTv.setText(Html.fromHtml(productDao.detail));
        homeViewHolder.mProductViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                CurrentItem = i;
                setProductPrice(CurrentItem);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void showTechnicalDrawingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_technical_drawing, null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.show();
        ImageView imageView = (ImageView) inflate.findViewById(R.id.crossImageView);
        TextView pdfTextviewTv = (TextView) inflate.findViewById(R.id.pdfTextView);
        TextView cadTextviewTv = (TextView) inflate.findViewById(R.id.cadTexView);
        View findViewById = inflate.findViewById(R.id.pdfView);
        inflate = inflate.findViewById(R.id.cadView);
        for (AttachmentListDao dao : this.pdfCaadList) {
            if (dao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                pdfTextviewTv.setVisibility(View.VISIBLE);
                findViewById.setVisibility(View.GONE);
            } else if (dao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.CAD.name())) {
                cadTextviewTv.setVisibility(View.VISIBLE);
                inflate.setVisibility(View.GONE);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.cancel();
            }
        });
        pdfTextviewTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final AttachmentListDao attachDao = attachmentList.get(CurrentItem);
                for (final AttachmentListDao dao : pdfCaadList) {
                    if (dao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                        Picasso.with(mContext).load(attachDao.attachmentURL).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Spanned spannedText = Html.fromHtml(new StringBuilder()
                                        .append("<p><b>" + productDao.name + "</b></p>")
                                        .append("<small><pDownload PDF file from</p></small>")
                                        .append(" <a href=\"" + dao.attachmentURL + "\">Download PDF</a> ")
                                        .toString());

                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.SEND");
                                intent.putExtra("android.intent.extra.STREAM", bitmap);
                                intent.setType("image/jpeg");
                                intent.putExtra("android.intent.extra.TEXT", spannedText);
                                startActivity(Intent.createChooser(intent, "Send to"));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
                    }
                }
                create.cancel();
            }
        });
        cadTextviewTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final AttachmentListDao attachDao = attachmentList.get(CurrentItem);
                for (final AttachmentListDao dao : pdfCaadList) {
                    if (dao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                        Picasso.with(mContext).load(attachDao.attachmentURL).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Spanned spannedText = Html.fromHtml(new StringBuilder()
                                        .append("<p><b>" + productDao.name + "</b></p>")
                                        .append("<small><pDownload CAD file from</p></small>")
                                        .append(" <a href=\"" + dao.attachmentURL + "\">Download PDF</a> ")
                                        .toString());

                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.SEND");
                                intent.putExtra("android.intent.extra.STREAM", bitmap);
                                intent.setType("image/jpeg");
                                intent.putExtra("android.intent.extra.TEXT", spannedText);
                                startActivity(Intent.createChooser(intent, "Send to"));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
                    }
                }
                create.cancel();
            }
        });
    }


    private void setProductPrice(int i) {
        final Long colorId = attachmentList.get(i).color.id;
        Log.v("COLORID", "ID :- " + colorId + ":" + attachmentList.get(i).color.name);
        homeViewHolder.mProductPriceTv.setText(getString(R.string.Rs) + "Price On Request");
        for (ProductPrice productPrice : productDao.productPrices) {
            Log.v("COLORID_Product", "ID :- " + productPrice.color.id + ":" + productPrice.price);
            if (productPrice.color != null && productPrice.color.id == colorId) {
                homeViewHolder.mProductPriceTv.setText(Utility.showPriceInUK(productPrice.price));
                return;
            }
        }
    }


    @Override
    public void takeScreenShot() {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                            containerView.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    containerView.draw(canvas);
                    ProductDetailFragment.this.bitmap = bitmap;
                }
            };

            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }


    public class HomeViewHolder {

        private final TextView mProductPriceTv;

        public TextView mProductNameTv;
        public ViewPager mProductViewPager;
        public TabLayout tab_layout;
        public ImageButton mAddProjectIb;
        public ImageButton mTechnicalDetailIb;
        public ImageButton mShareIb;
        public ImageButton mFavouriteIb;
        public TextView mProducttitleTv;
        public TextView mProductCodeTv;
        public TextView mProductDetailTv;
        public TextView mColorAvailableTv;


        public HomeViewHolder(View view, View.OnClickListener listener) {

            mProductNameTv = (TextView) view.findViewById(R.id.product_name_tv);
            mProductViewPager = (ViewPager) view.findViewById(R.id.productViewPager_ClickableViewPager);
            tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
            mAddProjectIb = (ImageButton) view.findViewById(R.id.add_project_ib);
            mTechnicalDetailIb = (ImageButton) view.findViewById(R.id.technical_detail_ib);
            mShareIb = (ImageButton) view.findViewById(R.id.share_ib);
            mFavouriteIb = (ImageButton) view.findViewById(R.id.favourite_ib);
            mProducttitleTv = (TextView) view.findViewById(R.id.producttitle_tv);
            mProductCodeTv = (TextView) view.findViewById(R.id.productCode_tv);
            mProductDetailTv = (TextView) view.findViewById(R.id.productDetail_tv);
            mColorAvailableTv = (TextView) view.findViewById(R.id.colorAvailable_tv);
            mProductPriceTv = (TextView) view.findViewById(R.id.productPrice_tv);

        }
    }
}
