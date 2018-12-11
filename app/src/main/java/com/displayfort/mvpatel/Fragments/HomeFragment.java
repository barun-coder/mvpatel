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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.MasterCategoryAdapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.HomeActivity;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private MasterCategoryAdapter adapter;
    private ArrayList<CategoryDao> categoryDaos;


    public static HomeFragment newInstance() {
        HomeFragment contentFragment = new HomeFragment();
        Bundle bundle = new Bundle();
//        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        setAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        mContext = getActivity();
        return rootView;
    }


    private void setAdapter() {
        homeViewHolder.mRecyclerViewRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        homeViewHolder.mRecyclerViewRv.setHasFixedSize(true);
        homeViewHolder.mRecyclerViewRv.addOnScrollListener(new CenterScrollListener());
        adapter = new MasterCategoryAdapter(mContext, getList());
        homeViewHolder.mRecyclerViewRv.setAdapter(adapter);
        homeViewHolder.mRecyclerViewRv.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ((HomeActivity) getActivity()).addFragment(CategoryFragment.newInstance(categoryDaos.get(position).id), (categoryDaos.get(position).categoryName));
                    }
                }));
    }

    private ArrayList<CategoryDao> getList() {
        categoryDaos = new ArrayList<>();
        String description = "Lorem ipsum.";
        categoryDaos.add(new CategoryDao("Shower", description, R.mipmap.shower, 1));
        categoryDaos.add(new CategoryDao("Wash Basin", description, R.mipmap.washbasin, 2));
        categoryDaos.add(new CategoryDao("Flushing System", description, R.mipmap.sanitaryware, 3));
        categoryDaos.add(new CategoryDao("Premium", description, R.mipmap.premium, 4));
        categoryDaos.add(new CategoryDao("Accesories", description, R.mipmap.accessorie, 5));
        return categoryDaos;
    }

    @Override
    public void takeScreenShot() {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    if (containerView != null) {
                        Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                                containerView.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        containerView.draw(canvas);
                        HomeFragment.this.bitmap = bitmap;
                    }
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

        public HomeViewHolder(View view, View.OnClickListener listener) {
            mRecyclerViewRv = (RecyclerView) view.findViewById(R.id.recyclerView_rv);
        }
    }
}
