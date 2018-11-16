package com.displayfort.mvpatel.Fragments;

/**
 * Created by pc on 16/10/2018 15:32.
 * DisplayFortSoftware
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.SubCategoryListAapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.SubCategory;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.HomeActivity;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class SubCategoryFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected int CatID;
    private Bitmap bitmap;
    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private SubCategoryListAapter adapter;
    private ArrayList<SubCategory> subcategoryDaoArrayList;
    private CategoryDao categoryDetailDao;


    public static SubCategoryFragment newInstance(int catId) {
        SubCategoryFragment contentFragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CATID", catId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
        categoryDetailDao = dbHandler.getCategoryDetail(CatID);
        categoryDetailDao.subCategories = dbHandler.getSubCategoryList(CatID);
        init();
        setAdapter();

    }

    private void init() {
        if (!(categoryDetailDao.attachable == null || categoryDetailDao.attachable.attachmentList == null || categoryDetailDao.attachable.attachmentList.size() <= 0)) {
            for (int i = 0; i < categoryDetailDao.attachable.attachmentList.size(); i++) {
                if (((AttachmentListDao) categoryDetailDao.attachable.attachmentList.get(i)).type.equalsIgnoreCase(Constant.IMAGE_TYPES.COVERPIC.name())) {
                    Utility.setImage(mContext, ((AttachmentListDao) categoryDetailDao.attachable.attachmentList.get(i)).attachmentURL, homeViewHolder.mProductImageIv);
                }
            }
        }
        homeViewHolder.mProductNameTv.setText(categoryDetailDao.name);
        subcategoryDaoArrayList = categoryDetailDao.subCategories;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CatID = getArguments().getInt("CATID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.subcategory_list_fragment, container, false);
        mContext = getActivity();
        return rootView;
    }


    private void setAdapter() {
        homeViewHolder.mRecyclerViewRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        homeViewHolder.mRecyclerViewRv.setHasFixedSize(true);
        homeViewHolder.mRecyclerViewRv.addOnScrollListener(new CenterScrollListener());
        adapter = new SubCategoryListAapter(mContext, subcategoryDaoArrayList);
        homeViewHolder.mRecyclerViewRv.setAdapter(adapter);
        homeViewHolder.mRecyclerViewRv.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SubCategory dao = subcategoryDaoArrayList.get(position);
                        ((HomeActivity) getActivity()).addFragment(ProductFragment.newInstance(dao.id), (dao.name));
                    }
                }));
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
                    SubCategoryFragment.this.bitmap = bitmap;
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
        public RecyclerView mRecyclerViewRv;

        public ImageView mProductImageIv;
        public TextView mProductNameTv;
        public Button mFeatureBtn;
        public Button mTechnologyBtn;

        public HomeViewHolder(View view, View.OnClickListener listener) {
            mProductImageIv = (ImageView) view.findViewById(R.id.product_image_iv);
            mProductNameTv = (TextView) view.findViewById(R.id.product_name_tv);
            mFeatureBtn = (Button) view.findViewById(R.id.feature_btn);
            mTechnologyBtn = (Button) view.findViewById(R.id.technology_btn);
            mRecyclerViewRv = (RecyclerView) view.findViewById(R.id.product_list_rv);

            mFeatureBtn.setOnClickListener(listener);
            mTechnologyBtn.setOnClickListener(listener);
        }
    }
}
