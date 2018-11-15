package com.displayfort.mvpatel.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.MasterCategoryAdapter;
import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class OldHomeActivity extends BaseActivity implements View.OnClickListener {


    private HomeViewHolder homeViewHolder;
    private MasterCategoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        context = this;
        homeViewHolder = new HomeViewHolder(findViewById(R.id.container_rl), this);
        setAdapter();
    }



    private void setAdapter() {
        homeViewHolder.mRecyclerViewRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        homeViewHolder.mRecyclerViewRv.setHasFixedSize(true);
        homeViewHolder.mRecyclerViewRv.addOnScrollListener(new CenterScrollListener());
        adapter = new MasterCategoryAdapter(context, getList());
        homeViewHolder.mRecyclerViewRv.setAdapter(adapter);
        homeViewHolder.mRecyclerViewRv.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                }));
    }

    private ArrayList<CategoryDao> getList() {
        ArrayList<CategoryDao> ITEMS = new ArrayList<>();
        String description = "Lorem ipsum.";
        ITEMS.add(new CategoryDao("Shower", description, R.mipmap.shower));
        ITEMS.add(new CategoryDao("Wash Basin", description, R.mipmap.washbasin));
        ITEMS.add(new CategoryDao("Flushing System", description, R.mipmap.sanitaryware));
        ITEMS.add(new CategoryDao("Premium", description, R.mipmap.premium));
        ITEMS.add(new CategoryDao("Accesories", description, R.mipmap.accessorie));
        return ITEMS;
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, OldHomeActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public class HomeViewHolder {
        public RecyclerView mRecyclerViewRv;

        public HomeViewHolder(View view, View.OnClickListener listener) {
            mRecyclerViewRv = (RecyclerView) view.findViewById(R.id.recyclerView_rv);

            mRecyclerViewRv.setOnClickListener(listener);
        }
    }

}