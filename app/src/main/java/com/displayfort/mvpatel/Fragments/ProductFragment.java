package com.displayfort.mvpatel.Fragments;

/**
 * Created by pc on 16/10/2018 15:32.
 * DisplayFortSoftware
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.ProductListAapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.SubCategory;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.HomeActivity;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ProductFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected long SubCatID;
    private Bitmap bitmap;
    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private ProductListAapter adapter;
    private ArrayList<Product> productList;
    private SubCategory subCategoryDetail;
    private TrackerDbHandler dbHandler;


    public static ProductFragment newInstance(Long catId) {
        ProductFragment contentFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("SUBCATID", catId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        setAdapter();
        init();
    }

    private void init() {
        homeViewHolder.mfilterImageView.setOnClickListener(new CallFilter());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubCatID = getArguments().getLong("SUBCATID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_list_fragment, container, false);
        mContext = getActivity();
        return rootView;
    }


    private void setAdapter() {
        homeViewHolder.mRecyclerViewRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        homeViewHolder.mRecyclerViewRv.setHasFixedSize(true);
        homeViewHolder.mRecyclerViewRv.addOnScrollListener(new CenterScrollListener());
        dbHandler = MvPatelApplication.getDatabaseHandler();
        subCategoryDetail = dbHandler.getSubCategoryDetail((int) SubCatID);
        subCategoryDetail.products = dbHandler.getProductList((int) SubCatID, Constant.DEFAULT);
        productList = subCategoryDetail.products;
        homeViewHolder.mSubCategoryNameTv.setText(subCategoryDetail.title);
        homeViewHolder.mCategoryNameTv.setText(subCategoryDetail.about);
        adapter = new ProductListAapter(mContext, productList);
        homeViewHolder.mRecyclerViewRv.setAdapter(adapter);
        homeViewHolder.mRecyclerViewRv.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = productList.get(position);
                        ((HomeActivity) getActivity()).addFragment(ProductDetailFragment.newInstance(product.id), (product.name));
                    }
                }));
        setFilter(Constant.ATOZ);
    }

    private void setFilter(int atoz) {
        subCategoryDetail.products = dbHandler.getProductList((int) SubCatID, atoz);
        productList = subCategoryDetail.products;
        adapter.setlist(productList);

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
                    ProductFragment.this.bitmap = bitmap;
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

    class CallFilter implements View.OnClickListener {
        CallFilter() {
        }

        public void onClick(View view) {
            showPopUp();
        }
    }

    private void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog, null);
        builder.setView(inflate);
        setFilter(inflate, builder);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
    }

    /**/
    private void setFilter(View view, AlertDialog.Builder builder) {
        View view2 = view;
        RelativeLayout relativeLayout = (RelativeLayout) view2.findViewById(R.id.accendingAlphLinearLayout);
        RelativeLayout relativeLayout2 = (RelativeLayout) view2.findViewById(R.id.decendingAlphLinearLayout);
        RelativeLayout relativeLayout3 = (RelativeLayout) view2.findViewById(R.id.accesndingPriceLinearLayout);
        RelativeLayout relativeLayout4 = (RelativeLayout) view2.findViewById(R.id.decendingPriceLinearLayout);
        ImageView imageView = (ImageView) view2.findViewById(R.id.img_name_a_z);
        ImageView imageView2 = (ImageView) view2.findViewById(R.id.img_name_z_a);
        ImageView imageView3 = (ImageView) view2.findViewById(R.id.img_price_asc);
        ImageView imageView4 = (ImageView) view2.findViewById(R.id.img_price_desc);
        ImageView imageView5 = (ImageView) view2.findViewById(R.id.crossImageView);
        int i = new MVPatelPrefrence(mContext).getIntValue("PRODUCT_DETAIL_FILTER");
        final AlertDialog create = builder.create();
        switch (i) {
            case 1:
                imageView.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
                break;
            case 2:
                imageView.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
                break;
            case 3:
                imageView.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.VISIBLE);
                imageView4.setVisibility(View.GONE);
                break;
            case 4:
                imageView.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        create.show();
        imageView5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.cancel();
            }
        });
        final ImageView imageView6 = imageView;
        final ImageView imageView7 = imageView2;
        final ImageView imageView8 = imageView3;
        final ImageView imageView9 = imageView4;
        AlertDialog alertDialog = create;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MVPatelPrefrence(mContext).setIntValue("PRODUCT_DETAIL_FILTER", 1);
                imageView6.setVisibility(View.VISIBLE);
                imageView7.setVisibility(View.GONE);
                imageView8.setVisibility(View.GONE);
                imageView9.setVisibility(View.GONE);
                setFilter(Constant.ATOZ);
//                sortNameList(productList, true);
                create.cancel();
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MVPatelPrefrence(mContext).setIntValue("PRODUCT_DETAIL_FILTER", 2);
                imageView6.setVisibility(View.GONE);
                imageView7.setVisibility(View.VISIBLE);
                imageView8.setVisibility(View.GONE);
                imageView9.setVisibility(View.GONE);
//                sortNameList(productList, false);
                setFilter(Constant.ZTOA);
                create.cancel();
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MVPatelPrefrence(mContext).setIntValue("PRODUCT_DETAIL_FILTER", 3);
                imageView6.setVisibility(View.GONE);
                imageView7.setVisibility(View.GONE);
                imageView8.setVisibility(View.VISIBLE);
                imageView9.setVisibility(View.GONE);
//                sortPriceList(productList, true);
                setFilter(Constant.LTOH);
                create.cancel();

            }
        });
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MVPatelPrefrence(mContext).setIntValue("PRODUCT_DETAIL_FILTER", 4);
                imageView6.setVisibility(View.GONE);
                imageView7.setVisibility(View.GONE);
                imageView8.setVisibility(View.GONE);
                imageView9.setVisibility(View.VISIBLE);
//                sortPriceList(productList, false);
                setFilter(Constant.HTOL);
                create.cancel();

            }
        });
    }

    /*Sorting*/
    private void sortPriceList(ArrayList<Product> arrayList, final boolean LTH) {
        if (arrayList != null && arrayList.size() > 0) {
            Collections.sort(arrayList, new Comparator<Product>() {
                public int compare(Product product, Product product2) {
                    int i = 0, j = 0;
                    if (product == null || product2 == null) {
                        return 0;
                    }
                    if (product.productPrices != null && product.productPrices.size() > 0 && product2.productPrices != null && product2.productPrices.size() > 0) {
                        for (int i2 = 0; i2 < product.productPrices.size(); i2++) {
                            if ((product.productPrices.get(i2)).color != null) {

                                int priceA = (product.productPrices.get(i2)).price.intValue();
                                if (j == 0) {
                                    j = priceA;
                                } else {
                                    if (LTH) {
                                        j = (priceA < j) ? priceA : j;
                                    } else {
                                        j = (priceA > j) ? priceA : j;
                                    }

                                }
                            }
                        }
//                        product = null;
                        for (int i3 = 0; i3 < product2.productPrices.size(); i3++) {
                            if ((product2.productPrices.get(i3)).color != null) {
                                int priceB = (product2.productPrices.get(i3)).price.intValue();
                                if (i == 0) {
                                    i = priceB;
                                } else {
                                    if (LTH) {
                                        i = (priceB < i) ? priceB : i;
                                    } else {
                                        i = (priceB > i) ? priceB : i;
                                    }

                                }
                            }
                        }
                    }
                    /*  return z != null ? product - i : i - product;*/
                    if (LTH) {
                        return (i < j) ? 1 : 0;
                    }
                    return (i < j) ? 0 : 1;
                    /**/
                }
            });
            this.adapter.notifyDataSetChanged();
        }
    }

    private void sortNameList(ArrayList<Product> arrayList, final boolean LTH) {
        if (arrayList != null && arrayList.size() > 0) {
            Collections.sort(arrayList, new Comparator<Product>() {
                public int compare(Product product, Product product2) {
                    if (LTH) {
                        return product.name.compareTo(product2.name);
                    }
                    return product2.name.compareTo(product.name);
                }
            });
            this.adapter.notifyDataSetChanged();
        }
    }


    public class HomeViewHolder {
        public RecyclerView mRecyclerViewRv;
        private final ImageView mfilterImageView;
        public ImageView mProductImageIv;
        public TextView mSubCategoryNameTv, mCategoryNameTv;


        public HomeViewHolder(View view, View.OnClickListener listener) {
            mProductImageIv = (ImageView) view.findViewById(R.id.product_image_iv);
            mSubCategoryNameTv = (TextView) view.findViewById(R.id.title_tv);
            mCategoryNameTv = (TextView) view.findViewById(R.id.about_tv);
            mRecyclerViewRv = (RecyclerView) view.findViewById(R.id.product_list_rv);
            mfilterImageView = (ImageView) view.findViewById(R.id.filterButton);

        }
    }
}
