package com.displayfort.mvpatel.Fragments;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.SearchProductListAapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.HomeActivity;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;
import com.displayfort.mvpatel.Utils.Utility;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by pc on 21/11/2018 11:48.
 * MVPatel
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private HomeViewHolder homeViewHolder;
    private Bitmap bitmap;
    private View containerView;
    private String str;
    private SearchProductListAapter adapter;
    private ArrayList<Product> productList = new ArrayList<>();

    /**/
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private int limit = 10, offset = 0;
    private SearchView.OnQueryTextListener onQueryTextListener;


    public static SearchFragment newInstance() {
        SearchFragment contentFragment = new SearchFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        dbHandler = MvPatelApplication.getDatabaseHandler();
        setNFC();
        setSearchView();
        setAdapter();
    }

    private void setNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);

        if (nfcAdapter == null) {
            Toast.makeText(mContext, "No NFC", Toast.LENGTH_SHORT).show();
            return;
        }

        pendingIntent = PendingIntent.getActivity(mContext, 0,
                new Intent(mContext, mContext.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private void setAdapter() {
        homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
        homeViewHolder.swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    offset = offset + limit;
                    productList = dbHandler.getSearchProductList(str, offset, limit);
                    if (productList.size() > 0) {
                        adapter.setlist(productList);
                    } else {
                        homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.TOP);
                    }
                }
            }
        });
        homeViewHolder.mRecyclerViewRv.setLayoutManager(new LinearLayoutManager(mContext));
        homeViewHolder.mRecyclerViewRv.setHasFixedSize(true);
        homeViewHolder.mRecyclerViewRv.addOnScrollListener(new CenterScrollListener());
        adapter = new SearchProductListAapter(mContext, productList);
        homeViewHolder.mRecyclerViewRv.setAdapter(adapter);
        homeViewHolder.mRecyclerViewRv.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = productList.get(position);
                        ((HomeActivity) getActivity()).addFragment(ProductDetailFragment.newInstance(product.id), (product.name));
                    }
                }));
    }


    private void setSearchView() {
        homeViewHolder.mSearchViewSv.setIconified(false);
        homeViewHolder.mSearchViewSv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        homeViewHolder.mSearchViewSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 2) {
                    homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
                    str = s;
                    offset = 0;
                    productList = dbHandler.getSearchProductList(s, offset, limit);
                    if (productList.size() > 0) {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.VISIBLE);
                        homeViewHolder.no_data_rl.setVisibility(View.GONE);
                        adapter.setlist(productList);
                    } else {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.GONE);
                        homeViewHolder.no_data_rl.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;

            }
        });
        homeViewHolder.mSearchViewSv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                productList = new ArrayList<>();
                homeViewHolder.mRecyclerViewRv.setVisibility(View.GONE);
                adapter.setlist(productList);
                offset = 0;
                return false;
            }
        });
        onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 2) {
                    homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
                    str = s;
                    offset = 0;
                    productList = dbHandler.getSearchProductList(s, offset, limit);
                    if (productList.size() > 0) {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.VISIBLE);
                        homeViewHolder.no_data_rl.setVisibility(View.GONE);
                        adapter.setlist(productList);
                    } else {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.GONE);
                        homeViewHolder.no_data_rl.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 2) {
                    homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
                    str = s;
                    offset = 0;
                    productList = dbHandler.getSearchProductList(s, offset, limit);
                    if (productList.size() > 0) {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.VISIBLE);
                        homeViewHolder.no_data_rl.setVisibility(View.GONE);
                        adapter.setlist(productList);
                    } else {
                        homeViewHolder.mRecyclerViewRv.setVisibility(View.GONE);
                        homeViewHolder.no_data_rl.setVisibility(View.VISIBLE);
                    }
                }
                return false;

            }
        };
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    SearchFragment.this.bitmap = bitmap;
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
        private final RelativeLayout no_data_rl;
        private final SwipyRefreshLayout swipyrefreshlayout;
        public SearchView mSearchViewSv;
        public RecyclerView mRecyclerViewRv;
        public Button mSearchBtn;

        public HomeViewHolder(View view, View.OnClickListener listener) {
            mSearchViewSv = (SearchView) view.findViewById(R.id.searchView_sv);
            mRecyclerViewRv = (RecyclerView) view.findViewById(R.id.productList_rv);
            no_data_rl = (RelativeLayout) view.findViewById(R.id.no_data_rl);
            swipyrefreshlayout = view.findViewById(R.id.swipyrefreshlayout);
            mSearchBtn = (Button) view.findViewById(R.id.search_btn);
            mRecyclerViewRv.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.VISIBLE);
            mSearchBtn.setOnClickListener(listener);

        }
    }


    public boolean onBackPressed() {
        ((HomeActivity) getActivity()).enableViews(true);

        return true;
    }

    /*NFC*/

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, null, null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    private void showWirelessSettings() {
        Toast.makeText(mContext, "You need to enable NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }


    public void onNewIntent(Intent intent) {
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
//android.nfc.action.NDEF_DISCOVERED
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                onQueryTextSubmit("aquamax");
            } else {
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                long tagId = dumpTagIDData(tag);
//                Product product = new Product(tagId + "");
//                displayNFCTag(product);
                Utility.ShowToast("NEw NFC Tag " + tagId, mContext);
                if ((tagId + "").equalsIgnoreCase("1145151598180225")) {
                    onQueryTextSubmit("KUP-CHR-35011BPM");
                } else  if ((tagId + "").equalsIgnoreCase("1145147303212929")) {
                    onQueryTextSubmit("ALI-CHR-85011B");
                } else {
                    onQueryTextSubmit("aquamax");
                }

            }

        }
    }

    private void onQueryTextSubmit(String s) {
        if (s.length() > 2) {
            homeViewHolder.swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
            str = s;
            offset = 0;
            productList = dbHandler.getSearchProductList(s, offset, limit);
            if (productList.size() > 0) {
                Product product = productList.get(0);
                ((HomeActivity) getActivity()).addFragment(ProductDetailFragment.newInstance(product.id), (product.name));
            }
        }
    }


    private long dumpTagIDData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        long tagId = 0;
        tagId = toReversedDec(id);
        return tagId;
    }


    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }
}
