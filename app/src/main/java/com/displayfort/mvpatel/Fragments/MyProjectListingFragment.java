package com.displayfort.mvpatel.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.displayfort.mvpatel.Adapter.ProjectListAdapter;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.InterFaces.OnProjectClick;
import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Screen.HomeActivity;
import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

/**
 * Created by pc on 19/11/2018 10:50.
 * MVPatel
 */
public class MyProjectListingFragment extends BaseFragment {

    private View containerView;
    private Context mContext;
    private HomeViewHolder homeViewHolder;
    private ProjectListAdapter adapter;
    private ArrayList<Project> projectArrayList = new ArrayList<>();

    public static MyProjectListingFragment newInstance() {
        MyProjectListingFragment contentFragment = new MyProjectListingFragment();
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new MyProjectListingFragment.HomeViewHolder(view, this);
        setAdapter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_project_listing, container, false);
        mContext = getActivity();
        return rootView;
    }

    private void setAdapter() {
        homeViewHolder.mMyprojectRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        homeViewHolder.mMyprojectRv.setHasFixedSize(true);
        homeViewHolder.mMyprojectRv.addOnScrollListener(new CenterScrollListener());
        dbHandler = MvPatelApplication.getDatabaseHandler();
        projectArrayList = dbHandler.getProjectList();
        adapter = new ProjectListAdapter(mContext, projectArrayList, new OnProjectClick() {
            @Override
            public void OnProjectClick(Project project, boolean isEditable) {
                if (!isEditable) {
                    ((HomeActivity) getActivity()).addFragment(ProjectDetailFragment.newInstance(project.projectId, project.name), "ProjectDetail");
                } else {
                    resetProjectList();
                }
            }
        });
        homeViewHolder.mMyprojectRv.setAdapter(adapter);
        homeViewHolder.mAddNewProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoProject(true);
            }
        });

    }

    private void resetProjectList() {
        dbHandler = MvPatelApplication.getDatabaseHandler();
        projectArrayList = dbHandler.getProjectList();
        adapter.setlist(projectArrayList);
    }

    public void addtoProject(boolean isAdd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_project, null);
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
                        Utility.ShowToast("Project Added Succesfully", mContext);
                        resetProjectList();
                        dialog.cancel();
                    } else {
                        Utility.ShowToast("Something went wrong", mContext);
                    }

                } else {
                    Utility.ShowToast("Enter min 2 char and Max 25 character Name", mContext);
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

    public class HomeViewHolder {
        public TextView mMyprojectNameTv;
        public Button mAddNewProjectBtn;
        public RecyclerView mMyprojectRv;

        public HomeViewHolder(View view, View.OnClickListener listener) {
            mMyprojectNameTv = (TextView) view.findViewById(R.id.myprojectName_tv);
            mAddNewProjectBtn = (Button) view.findViewById(R.id.addNewProject_btn);
            mMyprojectRv = (RecyclerView) view.findViewById(R.id.myproject_rv);

            mMyprojectNameTv.setOnClickListener(listener);
            mAddNewProjectBtn.setOnClickListener(listener);
            mMyprojectRv.setOnClickListener(listener);
        }
    }
}
