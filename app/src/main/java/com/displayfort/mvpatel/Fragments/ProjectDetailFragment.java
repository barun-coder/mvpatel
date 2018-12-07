package com.displayfort.mvpatel.Fragments;

/**
 * Created by pc on 16/10/2018 15:32.
 * DisplayFortSoftware
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Model.OrderDetailDao;
import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.Model.Room;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.PDFUtils;
import com.displayfort.mvpatel.Utils.Utility;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by Husain on 22.12.2014.
 */
public class ProjectDetailFragment extends BaseFragment implements View.OnClickListener {


    private View containerView;
    protected ImageView mImageView;
    protected long PRID;

    private HomeViewHolder homeViewHolder;
    private Context mContext;
    private String PRNAME = "";
    private ArrayList<Room> roomArrayList;
    private int qty = 0;
    private String TO = "husain@displayfort.com", CC = "husain@displayfort.com";


    public static ProjectDetailFragment newInstance(Long PRID, String ProjectName) {
        ProjectDetailFragment contentFragment = new ProjectDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("PRID", PRID);
        bundle.putString("PRNAME", ProjectName);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PRID = getArguments().getLong("PRID");
        PRNAME = getArguments().getString("PRNAME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_detail, container, false);
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        homeViewHolder = new HomeViewHolder(view, this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dbHandler = MvPatelApplication.getDatabaseHandler();
                init();
            }
        }, 400);


    }

    private void init() {
        homeViewHolder.mProjectNameTv.setText(PRNAME);
        /**/
        roomArrayList = dbHandler.getRoomList(PRID);
        if (roomArrayList.size() > 0) {
            ImlementRoomDetail();
        }

    }

    private void ImlementRoomDetail() {
        homeViewHolder.mOrderDetailLl.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0; i < roomArrayList.size(); i++) {
            View view = inflater.inflate(R.layout.room_list_item_layout, null);
            RoomListItemViewHolder roomListItemViewHolder = new RoomListItemViewHolder(view);
            roomListItemViewHolder.mRoomNameTv.setText(roomArrayList.get(i).name);
            final ArrayList<OrderDetailDao> orderDetailDaos = dbHandler.getOrderListByRoom((int) roomArrayList.get(i).id);
            if (orderDetailDaos.size() > 0) {
                homeViewHolder.mOrderDetailLl.addView(view);
                addOrderDetail(orderDetailDaos, roomArrayList.get(i).id);
            }
        }
        setGrandTaltalAndQty();
    }

    private void setGrandTaltalAndQty() {
        Project project = dbHandler.getProjectDetail(PRID);
        homeViewHolder.mGrandQtyTv.setText("" + ((int) dbHandler.getGrandValues(false, PRID)));
        double grandT = dbHandler.getGrandValues(true, PRID);
        double finalgTotal = grandT;
        double dvalue = project.discountValue;
        if (project.discountType != null) {
            if (project.discountType.equalsIgnoreCase("R")) {
                if (dvalue >= 1) {
                    finalgTotal = grandT - dvalue;
                    homeViewHolder.mDiscountTv.setText("DIS: " + dvalue + "/-" + "");
                }
            } else {
                if (dvalue >= 1) {
                    finalgTotal = grandT - (grandT * (dvalue / 100));
                    homeViewHolder.mDiscountTv.setText("DIS: " + dvalue + "%" + "");
                }
            }
        } else {
            finalgTotal = grandT;
        }
        homeViewHolder.mGrandTotalTv.setText(Utility.showPriceInUK(finalgTotal));

    }


    private void addOrderDetail(final ArrayList<OrderDetailDao> orderDetailDaos, final long Roomid) {
        if (orderDetailDaos.size() > 0) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            inflater = LayoutInflater.from(mContext);
            View inflate = inflater.inflate(R.layout.project_detail_footer, null);
            final ProjectDetailViewHolder projectDetailViewHolder = new ProjectDetailViewHolder(inflate);
            for (int i = 0; i < orderDetailDaos.size(); i++) {
                final View view = inflater.inflate(R.layout.project_detail_list_item, null);
                final ProjectDetailListViewHolder projectDetailListViewHolder = new ProjectDetailListViewHolder(view);
                final OrderDetailDao orderDetailDao = orderDetailDaos.get(i);
                projectDetailListViewHolder.mUpdateProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calculationDailog(Roomid, orderDetailDao.OrderId, orderDetailDao.price,
                                projectDetailListViewHolder.mRate, orderDetailDao.Qty, projectDetailListViewHolder.mPriceTv, projectDetailViewHolder.mPriceTotalTv);
                    }
                });
                projectDetailListViewHolder.mProductCategoryTv.setText(orderDetailDao.name);
                projectDetailListViewHolder.mQuantityTextTv.stepper.setMin(0);
                projectDetailListViewHolder.mProductColorTv.setText(orderDetailDao.colorText);
                projectDetailListViewHolder.mProductTitleTv.setText(orderDetailDao.code);
                Utility.setImage(mContext, orderDetailDao.ImageUrl, projectDetailListViewHolder.mProductIv, 'A');
                final int finalI = i;
                projectDetailListViewHolder.mQuantityTextTv.stepper.setValue(orderDetailDao.Qty);
                projectDetailListViewHolder.mQuantityTextTv.stepper.addStepCallback(new OnStepCallback() {
                    private boolean isFirstRun = false;

                    @Override
                    public void onStep(int value, boolean positive) {
                        if (!isFirstRun) {
                            if (positive) {
                                dbHandler.updateOrderQty(orderDetailDao.roomId, orderDetailDao.OrderId, PRID, value);
                            } else {
                                dbHandler.updateOrderQty(orderDetailDao.roomId, orderDetailDao.OrderId, PRID, value);
                                if (value < 1) {
                                    dbHandler.deleteOrder(orderDetailDao.roomId, orderDetailDao.OrderId, PRID);
                                    homeViewHolder.mOrderDetailLl.removeView(view);
                                }
                            }
                            orderDetailDaos.get(finalI).Qty = value;
                            projectDetailListViewHolder.mPriceTv.setText((orderDetailDao.Qty * orderDetailDao.discountPrice) + "");
                            projectDetailViewHolder.mPriceTotalTv.setText(getParticularRoomTotalAmount(Roomid));
                            setGrandTaltalAndQty();
                        } else {
                            isFirstRun = false;
                        }

                    }
                });
                projectDetailListViewHolder.mQuantityTextTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                projectDetailListViewHolder.mQuantityTextTv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

                //setText(orderDetailDao.Qty + "");
                qty = qty + orderDetailDao.Qty;
                projectDetailListViewHolder.mRate.setText((orderDetailDao.discountPrice) + "");
                projectDetailListViewHolder.mPriceTv.setText((orderDetailDao.Qty * orderDetailDao.discountPrice) + "");
                homeViewHolder.mOrderDetailLl.addView(view);
            }

            /**/
            projectDetailViewHolder.mPriceTotalTv.setText(getParticularRoomTotalAmount(Roomid));
            homeViewHolder.mOrderDetailLl.addView(inflate);
        }

    }

    double finalamount = 0;

    public void calculationDailog(final long roomid, final int orderId, final double amount,
                                  final TextView mRate, final int qty, final TextView mPriceTv, final TextView mPriceTotalTv) {
        Context context = getActivity();
        finalamount = amount;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.calculation_dialog);
        TextView basePriceTv = dialog.findViewById(R.id.base_price_tv);
        basePriceTv.setText("Base: "+Utility.showPriceInUK(amount));
        final TextView messageTv = dialog.findViewById(R.id.message_tv);
        final EditText mDiscountEt = dialog.findViewById(R.id.dicaount_val_et);
        messageTv.setText(Utility.showPriceInUK(finalamount));
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.discount_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkRB) {

                if (checkRB == R.id.ruppes_rb) {
                    String s = mDiscountEt.getText().toString();
                    if (s != null && s.length() > 0) {
                        finalamount = amount - (Double.parseDouble(s));
                        messageTv.setText(Utility.showPriceInUK(finalamount));
                    }
                } else if (checkRB == R.id.percentage_rb) {
                    String s = mDiscountEt.getText().toString();
                    if (s != null && s.length() > 0) {
                        finalamount = amount - (amount * (Double.parseDouble(s) / 100));
                        messageTv.setText(Utility.showPriceInUK(finalamount));
                    }
                }
            }
        });

        mDiscountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int checkRB = radioGroup.getCheckedRadioButtonId();
                if (s != null && s.length() > 0) {
                    if (checkRB == R.id.ruppes_rb) {
                        finalamount = amount - (Double.parseDouble(s.toString()));
                        messageTv.setText(Utility.showPriceInUK(finalamount));
                    } else if (checkRB == R.id.percentage_rb) {
                        finalamount = amount - (amount * (Double.parseDouble(s.toString()) / 100));
                        messageTv.setText(Utility.showPriceInUK(finalamount));
                    }
                } else {
                    finalamount = amount;
                    messageTv.setText(Utility.showPriceInUK(finalamount));
                }
            }
        });
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn);
        cancelBtn.setTag(dialog);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button okBtn = dialog.findViewById(R.id.ok_btn);
        okBtn.setText("YES");
        okBtn.setTag(dialog);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String s = mDiscountEt.getText().toString();
                int checkRB = radioGroup.getCheckedRadioButtonId();
                if (checkRB == R.id.ruppes_rb) {
                    if (s != null && s.length() > 0) {
                        dbHandler.updateOrderDetail(orderId, Double.parseDouble(s), "R", PRID);
                    } else {
                        dbHandler.updateOrderDetail(orderId, 0, "R", PRID);
                    }
                } else if (checkRB == R.id.percentage_rb) {
                    if (s != null && s.length() > 0) {
                        dbHandler.updateOrderDetail(orderId, Double.parseDouble(s), "P", PRID);
                    } else {
                        dbHandler.updateOrderDetail(orderId, 0, "P", PRID);
                    }
                }
                mRate.setText(finalamount + "");
                mPriceTv.setText((qty * finalamount) + "");
                mPriceTotalTv.setText(getParticularRoomTotalAmount(roomid));
                setGrandTaltalAndQty();
            }
        });

        dialog.show();

    }

    /**/
    double finalGrandTotal = 0;

    public void getDicountOnGrandTotal() {
        Project project = dbHandler.getProjectDetail(PRID);

        Context context = getActivity();
        final double GrandTotal = dbHandler.getGrandValues(true, PRID);
        finalGrandTotal = 0;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.calculation_dialog);
        TextView basePriceTv = dialog.findViewById(R.id.base_price_tv);
        basePriceTv.setText("Base: "+Utility.showPriceInUK(GrandTotal));
        final TextView messageTv = dialog.findViewById(R.id.message_tv);
        final EditText mDiscountEt = dialog.findViewById(R.id.dicaount_val_et);
        messageTv.setText(Utility.showPriceInUK(GrandTotal));


        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.discount_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkRB) {

                if (checkRB == R.id.ruppes_rb) {
                    String s = mDiscountEt.getText().toString();
                    if (s != null && s.length() > 0) {
                        finalGrandTotal = GrandTotal - (Double.parseDouble(s));
                        messageTv.setText(Utility.showPriceInUK(finalGrandTotal));
                    }
                } else if (checkRB == R.id.percentage_rb) {
                    String s = mDiscountEt.getText().toString();
                    if (s != null && s.length() > 0) {
                        finalGrandTotal = GrandTotal - (GrandTotal * (Double.parseDouble(s) / 100));
                        messageTv.setText(Utility.showPriceInUK(finalGrandTotal));
                    }
                }
            }
        });

        mDiscountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int checkRB = radioGroup.getCheckedRadioButtonId();
                if (s != null && s.length() > 0) {
                    if (checkRB == R.id.ruppes_rb) {
                        finalGrandTotal = GrandTotal - (Double.parseDouble(s.toString()));
                        messageTv.setText(Utility.showPriceInUK(finalGrandTotal));
                    } else if (checkRB == R.id.percentage_rb) {
                        finalGrandTotal = GrandTotal - (GrandTotal * (Double.parseDouble(s.toString()) / 100));
                        messageTv.setText(Utility.showPriceInUK(finalGrandTotal));
                    }
                } else {
                    finalGrandTotal = GrandTotal;
                    messageTv.setText(Utility.showPriceInUK(finalGrandTotal));
                }
            }
        });
        /**/
        mDiscountEt.setText(project.discountValue + "");
        if (project.discountType != null) {
            if (project.discountType.equalsIgnoreCase("R")) {
                radioGroup.check(R.id.ruppes_rb);
            } else {
                radioGroup.check(R.id.percentage_rb);
            }
        }
        /**/
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn);
        cancelBtn.setTag(dialog);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button okBtn = dialog.findViewById(R.id.ok_btn);
        okBtn.setText("YES");
        okBtn.setTag(dialog);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mDiscountEt.getText().toString();

                int checkRB = radioGroup.getCheckedRadioButtonId();
                if (checkRB == R.id.ruppes_rb) {
                    if (s != null && s.length() > 0) {
                        dbHandler.updateProject(Double.parseDouble(s), "R", PRID);
                    } else {
                        dbHandler.updateProject(0, "R", PRID);
                    }
                } else if (checkRB == R.id.percentage_rb) {
                    if (s != null && s.length() > 0) {
                        dbHandler.updateProject(Double.parseDouble(s), "P", PRID);
                    } else {
                        dbHandler.updateProject(0, "P", PRID);
                    }
                }


                setGrandTaltalAndQty();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private String getParticularRoomTotalAmount(long roomid) {
        return Utility.showPriceInUK(dbHandler.getParticularRoomtotal(roomid));
    }

    private double getTotalAmount(ArrayList<OrderDetailDao> orderDetailDaos) {
        double amount = 0;
        for (int i = 0; i < orderDetailDaos.size(); i++) {
            amount = amount + (orderDetailDaos.get(i).Qty * orderDetailDaos.get(i).price);
        }
        return amount;
    }


    public class ProjectDetailViewHolder {
        public LinearLayout mTotalLl;
        public TextView mPriceTotalTv;

        public ProjectDetailViewHolder(View view) {
            mTotalLl = (LinearLayout) view.findViewById(R.id.total_ll);
            mPriceTotalTv = (TextView) view.findViewById(R.id.priceTotal_tv);

        }
    }

    public class ProjectDetailListViewHolder {
        public ImageView mProductIv;
        public ImageView mUpdateProject;
        public TextView mProductCategoryTv;
        public TextView mProductTitleTv;
        public StepperTouch mQuantityTextTv;
        public EditText mQuantityEt;
        public TextView mPriceTv, mProductColorTv, mRate;

        public ProjectDetailListViewHolder(View view) {
            mProductIv = (ImageView) view.findViewById(R.id.product_iv);
            mUpdateProject = (ImageView) view.findViewById(R.id.update_project_iv);
            mProductCategoryTv = (TextView) view.findViewById(R.id.productCategory_tv);
            mProductTitleTv = (TextView) view.findViewById(R.id.productTitle_tv);
            mQuantityTextTv = (StepperTouch) view.findViewById(R.id.quantityText_tv);
            mQuantityEt = (EditText) view.findViewById(R.id.quantity_et);
            mPriceTv = (TextView) view.findViewById(R.id.price_tv);
            mProductColorTv = (TextView) view.findViewById(R.id.productcolor_tv);
            mRate = (TextView) view.findViewById(R.id.rate_tv);


        }
    }

    public class RoomListItemViewHolder {
        public TextView mRoomNameTv;
        public TextView mQtyTv;
        public TextView mPriceTv;

        public RoomListItemViewHolder(View view) {
            mRoomNameTv = (TextView) view.findViewById(R.id.roomName_tv);
            mQtyTv = (TextView) view.findViewById(R.id.qty_tv);
            mPriceTv = (TextView) view.findViewById(R.id.price_tv);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailProject_btn:
                sendMail();
                break;
            case R.id.discount_tv:
                getDicountOnGrandTotal();
                break;
        }
    }

    public class HomeViewHolder {

        private final LinearLayout mOrderDetailLl;
        private final ScrollView scrollView;
        private final Button mDiscountTv;
        public TextView mMyprojectNameTv;
        public TextView mProjectNameTv;
        public NestedScrollView mProjectDetailsExpandableRv;
        public TextView mGrandQtyTv;
        public TextView mGrandTotalTv;
        public Button mEmailProjectBtn;
        public Button mAddMoreProjectBtn;

        public HomeViewHolder(View view, View.OnClickListener listener) {
            scrollView = (ScrollView) view.findViewById(R.id.scroolview);
            mMyprojectNameTv = (TextView) view.findViewById(R.id.myprojectName_tv);
            mProjectNameTv = (TextView) view.findViewById(R.id.projectName_tv);
            mProjectDetailsExpandableRv = (NestedScrollView) view.findViewById(R.id.projectDetailsExpandable_rv);
            mGrandQtyTv = (TextView) view.findViewById(R.id.grand_qty_tv);
            mGrandTotalTv = (TextView) view.findViewById(R.id.grand_total_tv);
            mEmailProjectBtn = (Button) view.findViewById(R.id.emailProject_btn);
            mAddMoreProjectBtn = (Button) view.findViewById(R.id.add_more_project_btn);
            mDiscountTv = (Button) view.findViewById(R.id.discount_tv);
            mOrderDetailLl = (LinearLayout) view.findViewById(R.id.order_detail_ll);

            mDiscountTv.setOnClickListener(listener);
            mEmailProjectBtn.setOnClickListener(listener);
            mAddMoreProjectBtn.setOnClickListener(listener);
        }
    }

    private void sendMail() {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("image/png");
//
//        startActivity(Intent.createChooser(intent, "Share"));


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain");
        emailIntent.setType("image/jpeg");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromDrawable(getScreenBitmap()));
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        try {
            emailIntent.putExtra(Intent.EXTRA_STREAM, new PDFUtils().CreateQuotation(mContext, PRID));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mv Patel Quotation ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Bitmap getScreenBitmap() {
        /**/
        View view = getView().findViewById(R.id.order_detail_ll);
        Bitmap imageTop = getBitmapFromView(view, view.getHeight(), view.getWidth());
        view = getView().findViewById(R.id.grandTotalLayout);
        Bitmap imageBottom = getBitmapFromView(view, view.getHeight(), view.getWidth());
        return combineImages(imageTop, imageBottom);
    }

    public Bitmap combineImages(Bitmap imageTop, Bitmap imageBottom) {

        int X = imageTop.getWidth();
        int Y = imageTop.getHeight();

        int Scaled_X = imageBottom.getWidth();
        int scaled_Y = imageBottom.getHeight();

        System.out.println("Combined Images");

        System.out.println("Bit :" + X + "/t" + Y);

        System.out.println("SCaled_Bitmap :" + Scaled_X + "\t" + scaled_Y);

        Bitmap overlaybitmap = Bitmap.createBitmap(imageBottom.getWidth(),
                imageBottom.getHeight(), imageBottom.getConfig());
        Canvas canvas = new Canvas(overlaybitmap);
        canvas.drawBitmap(imageBottom, new Matrix(), null);
        canvas.drawBitmap(imageTop, new Matrix(), null);

        Bitmap combineBitmap = Bitmap.createBitmap(Scaled_X, (scaled_Y + Y), Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(combineBitmap);
        comboImage.drawBitmap(imageTop, new Matrix(), null);
        comboImage.drawBitmap(imageBottom, 0f, Y, null);

        return combineBitmap;
    }


    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
//  getActivity(). getWindowManager().getDefaultDisplay().getSize(new Point());

    public Uri getBitmapFromDrawable(Bitmap bmp) {

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(getActivity().getFilesDir(), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT >= 24) {
                // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
                bmpUri = FileProvider.getUriForFile(getActivity(), "com.displayfort.mvpatel", file);  // use this version for API >= 24
            } else {
                bmpUri = Uri.fromFile(file);
            }

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
