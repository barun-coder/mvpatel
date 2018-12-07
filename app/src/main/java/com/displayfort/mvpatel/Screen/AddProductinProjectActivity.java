package com.displayfort.mvpatel.Screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.DialogProjectListAdapter;
import com.displayfort.mvpatel.Adapter.DialogRoomListAdapter;
import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.Model.OrderDetailDao;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.Model.Room;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.RecyclerItemClickListener;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

public class AddProductinProjectActivity extends BaseActivity {
    public static Product productDao;
    public static ProductPrice productPrice;
    private Context context;
    private AddProductToProjectViewHolder addProductToProjectViewHolder;
    protected TrackerDbHandler dbHandler;
    private ArrayList<Project> projectArrayList;
    private ArrayList<Room> roomArrayList = new ArrayList<>();
    private DialogProjectListAdapter adapter;
    private DialogRoomListAdapter listAdapter;
    private Project currentProject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_to_project_layout);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addProductToProjectViewHolder = new AddProductToProjectViewHolder(findViewById(R.id.container_rl), this);
        setAdapter();
    }

    private void setAdapter() {

        listAdapter = new DialogRoomListAdapter(context, roomArrayList);
        addProductToProjectViewHolder.mRoomListRv.setAdapter(listAdapter);
        addProductToProjectViewHolder.mAddNewRoomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProject != null) {
                    addNewRoom();
                } else {
                    Utility.ShowToast("Select Project", context);
                }
            }
        });

        /*Project*/
        addProductToProjectViewHolder.mProjectListRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        addProductToProjectViewHolder.mProjectListRv.setHasFixedSize(true);
        addProductToProjectViewHolder.mProjectListRv.addOnScrollListener(new CenterScrollListener());
        projectArrayList = new ArrayList<>();

        adapter = new DialogProjectListAdapter(context, projectArrayList);
        addProductToProjectViewHolder.mProjectListRv.setAdapter(adapter);
        resetProjectList();
        addProductToProjectViewHolder.mProjectListRv.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {
                            addNewProject();
                        } else {
                            currentProject = projectArrayList.get(position);
                            new MVPatelPrefrence(context).setIntValue("PRID", currentProject.projectId.intValue());
                            setRoomData();
                        }
                    }
                }));

        /*room*/

        addProductToProjectViewHolder.mRoomListRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        addProductToProjectViewHolder.mRoomListRv.setHasFixedSize(true);
        addProductToProjectViewHolder.mRoomListRv.addOnScrollListener(new CenterScrollListener());


        addProductToProjectViewHolder.mAddToProjectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listAdapter.getSelectedRoom() != null) {
                    AddProductToOrder();
                } else {
                    Utility.ShowToast("Select Room", context);
                }
            }
        });

    }

    private void AddProductToOrder() {
        OrderDetailDao orderDao = new OrderDetailDao();
        orderDao.productId = productDao.id;
        orderDao.name = productDao.name;
        orderDao.code = productDao.code;
        orderDao.detail = productDao.detail;
        orderDao.productTypeId = productPrice.id;
        orderDao.price = productPrice.price;
        orderDao.discountPrice = productPrice.price;
        orderDao.colorId = productPrice.colorID;
        orderDao.status = true;
        orderDao.colorText = productPrice.attachmentListDao.type;
        orderDao.attachId = productPrice.attachmentListDao.attachableid;
        orderDao.ImageUrl = productPrice.attachmentListDao.attachmentURL;
        orderDao.created = System.currentTimeMillis();
        long orderID = 0;
        int ORDER_IDavailable = dbHandler.getOrderDetailCount(orderDao.productId, orderDao.colorId, orderDao.productTypeId, orderDao.price);
        if (ORDER_IDavailable == 0) {
            orderID = dbHandler.AddOrderDetail(orderDao);
        } else {
            orderID = ORDER_IDavailable;
        }
        if (orderID > 0) {
            dbHandler.AddOrder(listAdapter.getSelectedRoom().id, orderID);
        }
        Utility.ShowToast(orderDao.name + " added succesfully in " + listAdapter.getSelectedRoom().name, context);
        finishActivityWithAnim();
    }

    private void setRoomData() {
        addProductToProjectViewHolder.mProjectNameTv.setText(currentProject.name);
        resetRoomList();
    }

    public void addNewProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_add_project, null);
        builder.setView(inflate);
        builder.setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final EditText editText = (EditText) inflate.findViewById(R.id.addToProjectTextInputEditText);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.crossImageView);
        Button button = (Button) inflate.findViewById(R.id.addToProjectButton);
        final AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectname = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(projectname) && projectname.length() >= 2 && projectname.length() <= 25) {
                    Project project = new Project(projectname);
                    long res = dbHandler.AddProject(project);
                    if (res != 0) {
                        Utility.ShowToast("Project Added Succesfully", context);
                        resetProjectList();
                        currentProject = new Project(res, projectname);
                        addProductToProjectViewHolder.mProjectNameTv.setText(projectname);
                        resetRoomList();
                        dialog.cancel();
                    } else {
                        Utility.ShowToast("Something went wrong", context);
                    }

                } else {
                    Utility.ShowToast("Enter min 2 char and Max 25 character Name", context);
                }


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }

        });
    }

    private void resetRoomList() {
        roomArrayList = dbHandler.getRoomList(currentProject.projectId);
        roomArrayList.get(0).isSelected = true;
        listAdapter.setlist(roomArrayList);
    }

    public void addNewRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_add_project, null);
        builder.setView(inflate);
        builder.setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final EditText editText = (EditText) inflate.findViewById(R.id.addToProjectTextInputEditText);
        editText.setHint("Enter Room name");
        ImageView imageView = (ImageView) inflate.findViewById(R.id.crossImageView);
        Button button = (Button) inflate.findViewById(R.id.addToProjectButton);
        button.setText("Add Room");
        final AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(roomName) && roomName.length() >= 2 && roomName.length() <= 25) {
                    Room room = new Room(roomName, currentProject.projectId);
                    long res = dbHandler.AddRoom(room);
                    if (res != 0) {
                        Utility.ShowToast("Room Added Succesfully", context);
                        resetRoomList();
                        dialog.cancel();
                    } else {
                        Utility.ShowToast("Something went wrong", context);
                    }

                } else {
                    Utility.ShowToast("Enter min 2-25 character", context);
                }


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }

        });
    }

    private void resetProjectList() {
        dbHandler = MvPatelApplication.getDatabaseHandler();
        projectArrayList = dbHandler.getProjectList();
        projectArrayList.add(0, new Project("Add New"));
        adapter.setlist(projectArrayList);
        int ProjId = new MVPatelPrefrence(context).getIntValue("PRID");
        for (Project project : projectArrayList) {
            if (project.projectId != null && project.projectId == ProjId) {
                currentProject = project;
                setRoomData();
                return;
            }
        }


    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddProductinProjectActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                super.onClick(v);
                break;
        }
    }

    public class AddProductToProjectViewHolder {
        public TextView mToolbarTitle;
        public RecyclerView mProjectListRv;
        public TextView mProjectNameTv, mAddNewRoomTv;
        public RecyclerView mRoomListRv;
        public Button mAddToProjectTv;

        public AddProductToProjectViewHolder(View view, View.OnClickListener listener) {
            mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
            mProjectListRv = (RecyclerView) view.findViewById(R.id.project_list_rv);
            mProjectNameTv = (TextView) view.findViewById(R.id.project_name_tv);
            mAddNewRoomTv = (TextView) view.findViewById(R.id.add_new_room_tv);
            mRoomListRv = (RecyclerView) view.findViewById(R.id.room_list_rv);
            mAddToProjectTv = (Button) view.findViewById(R.id.add_to_project_btn);


            mAddToProjectTv.setOnClickListener(listener);
        }
    }

}
