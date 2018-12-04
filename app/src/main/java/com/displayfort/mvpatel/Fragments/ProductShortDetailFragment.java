package com.displayfort.mvpatel.Fragments;

/**
 * Created by pc on 16/10/2018 15:32.
 * DisplayFortSoftware
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.FrequentProductListAdapter;
import com.displayfort.mvpatel.Adapter.IntrestedProductAdapter;
import com.displayfort.mvpatel.Adapter.ProductTypeListAdapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.SubCategory;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.AddProductinProjectActivity;
import com.displayfort.mvpatel.Screen.ImageFullscreenActivity;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;
import com.displayfort.mvpatel.Utils.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ProductShortDetailFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected long PID;
    private Bitmap bitmap;
    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private ProductTypeListAdapter productTypeListAdapter;
    private FrequentProductListAdapter frequentProductListAdapter;
    private IntrestedProductAdapter intrestedProductAdapter;
    private Product productDao;
    private CategoryDao categoryDetailDao;
    private SubCategory subCategory;
    private int CurrentItem = 0;
    private ArrayList<ProductPrice> pdfCaadList = new ArrayList<>();
    private ArrayList<ProductPrice> productPriceList;


    public static ProductShortDetailFragment newInstance(Long catId) {
        ProductShortDetailFragment contentFragment = new ProductShortDetailFragment();
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
        View rootView = inflater.inflate(R.layout.product_short_detail_fragment, container, false);
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);

        dbHandler = MvPatelApplication.getDatabaseHandler();
        this.productDao = dbHandler.getProductDetail((int) PID);
        subCategory = dbHandler.getSubCategoryDetail(productDao.subcatid.intValue());
        init();
        setAdapter();

    }

    private void setAdapter() {
        /*Color Type*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productPriceList = dbHandler.getProductList(productDao.id, true);
                if (productPriceList != null && productPriceList.size() > 0) {
                    homeViewHolder.mProductListRv.setVisibility(View.VISIBLE);
                    homeViewHolder.mProductListRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    homeViewHolder.mProductListRv.setHasFixedSize(true);
                    homeViewHolder.mProductListRv.addOnScrollListener(new CenterScrollListener());
                    productTypeListAdapter = new ProductTypeListAdapter(mContext, productPriceList);
                    homeViewHolder.mProductListRv.setAdapter(productTypeListAdapter);
                    homeViewHolder.mProductListRv.addOnItemTouchListener(
                            new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    CurrentItem = position;
                                    setProductPrice(CurrentItem);
                                }
                            }));
                    setProductPrice(CurrentItem);
                } else {
                    homeViewHolder.mProductListRv.setVisibility(View.GONE);
                }
            }
        }, 200);



    }


    private void init() {

        homeViewHolder.mShareIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProductPrice productPrice = productPriceList.get(CurrentItem);
                Picasso.with(mContext).load(productPrice.attachmentListDao.attachmentURL).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.SEND");
                        if (bitmap != null) {
                            intent.putExtra("android.intent.extra.STREAM", bitmap);
                        }
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.TEXT", productDao.name + " " + productPrice.attachmentListDao.type);
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

        homeViewHolder.mAddProjectIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });
        homeViewHolder.mProductNameTv.setText(subCategory.name);
        homeViewHolder.mProducttitleTv.setText(productDao.name);
        homeViewHolder.mProductCodeTv.setText(productDao.code);
        homeViewHolder.mProductDetailTv.setText(Html.fromHtml(productDao.detail));


    }

    private void AddProduct() {
        if (productPriceList != null && productPriceList.size() > 0 && productPriceList.get(CurrentItem) != null) {
            AddProductinProjectActivity.productDao = productDao;
            AddProductinProjectActivity.productPrice = productPriceList.get(CurrentItem);
            startActivityWithAnim(getActivity(), new Intent(mContext, AddProductinProjectActivity.class));
        }
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
        for (ProductPrice dao : this.pdfCaadList) {
            if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                pdfTextviewTv.setVisibility(View.VISIBLE);
                findViewById.setVisibility(View.GONE);
            } else if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.CAD.name())) {
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
                for (final ProductPrice dao : pdfCaadList) {
                    if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                        if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                            Picasso.with(mContext).load(dao.attachmentListDao.attachmentURL).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    Spanned spannedText = Html.fromHtml(new StringBuilder()
                                            .append("<p><b>" + productDao.name + "</b></p>")
                                            .append("<small><pDownload PDF file from</p></small>")
                                            .append(" <a href=\"" + dao.attachmentListDao.attachmentURL + "\">Download PDF</a> ")
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
                }
                create.cancel();
            }
        });
        cadTextviewTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                for (final ProductPrice dao : pdfCaadList) {
                    if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.PDF.name())) {
                        if (dao.attachmentListDao.type.equalsIgnoreCase(Constant.IMAGE_TYPES.CAD.name())) {
                            Picasso.with(mContext).load(dao.attachmentListDao.attachmentURL).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    Spanned spannedText = Html.fromHtml(new StringBuilder()
                                            .append("<p><b>" + productDao.name + "</b></p>")
                                            .append("<small><pDownload CAD file from</p></small>")
                                            .append(" <a href=\"" + dao.attachmentListDao.attachmentURL + "\">Download CAD</a> ")
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
                }
                create.cancel();
            }
        });
    }


    private void setProductPrice(final int i) {
        homeViewHolder.mProductPriceTv.setText(getString(R.string.Rs) + "Price On Request");
        Utility.setImage(mContext, productPriceList.get(i).attachmentListDao.attachmentURL, homeViewHolder.mProductImageIv);
        homeViewHolder.productcolor_tv.setText("(" + productPriceList.get(i).attachmentListDao.type + ")");

        homeViewHolder.mProductImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageFullscreenActivity.class);
                intent.putExtra("URL", productPriceList.get(i).attachmentListDao.attachmentURL);
                intent.putExtra("is_product", true);
                ActivityOptionsCompat makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation
                        (((Activity) mContext),
                                homeViewHolder.mProductImageIv,
                                mContext.getString(R.string.product_image_transition));
                mContext.startActivity(intent, makeSceneTransitionAnimation.toBundle());
            }
        });
        if (productPriceList.get(i) != null && productPriceList.get(i).price != 0) {
            homeViewHolder.mProductPriceTv.setText(Utility.showPriceInUK(productPriceList.get(i).price));
            return;
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
                    ProductShortDetailFragment.this.bitmap = bitmap;
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


        private final LinearLayout mAdditionalLayoutLl;
        public TextView mProductNameTv;
        public ImageView mProductImageIv;
        public RecyclerView mProductListRv;
        public ImageButton mAddProjectIb;
        public ImageButton mTechnicalDetailIb;
        public ImageButton mShareIb;
        public ImageButton mFavouriteIb;
        public TextView mProducttitleTv;
        public TextView mProductCodeTv;
        public TextView mProductDetailTv;
        public TextView mProductPriceTv, productcolor_tv;


        public HomeViewHolder(View view, View.OnClickListener listener) {

            mAdditionalLayoutLl = (LinearLayout) view.findViewById(R.id.add_layout_ll);
            mProductNameTv = (TextView) view.findViewById(R.id.product_name_tv);
            mProductImageIv = (ImageView) view.findViewById(R.id.product_image_iv);
            mProductListRv = (RecyclerView) view.findViewById(R.id.product_list_rv);
            mAddProjectIb = (ImageButton) view.findViewById(R.id.add_project_ib);
            mTechnicalDetailIb = (ImageButton) view.findViewById(R.id.technical_detail_ib);
            mShareIb = (ImageButton) view.findViewById(R.id.share_ib);
            mFavouriteIb = (ImageButton) view.findViewById(R.id.favourite_ib);
            mProducttitleTv = (TextView) view.findViewById(R.id.producttitle_tv);
            mProductCodeTv = (TextView) view.findViewById(R.id.productCode_tv);
            mProductDetailTv = (TextView) view.findViewById(R.id.productDetail_tv);

                    mProductPriceTv = (TextView) view.findViewById(R.id.productPrice_tv);
            productcolor_tv = (TextView) view.findViewById(R.id.productcolor_tv);


        }
    }
}
