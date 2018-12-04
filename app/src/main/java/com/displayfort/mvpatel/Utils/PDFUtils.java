package com.displayfort.mvpatel.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.Model.OrderDetailDao;
import com.displayfort.mvpatel.Model.Room;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pc on 04/12/2018 16:11.
 * MVPatel
 */
public class PDFUtils {
    private TrackerDbHandler dbHandler;
    private Document doc = new Document();
    private File file = null;
    private Context context;
    private PdfPTable table;
    /**/

    public Uri CreateQuotation(Context context, long PRID) throws FileNotFoundException, DocumentException {
        Uri uri = null;
        this.context = context;
        dbHandler = MvPatelApplication.getDatabaseHandler();
        ArrayList<Room> roomArrayList = dbHandler.getRoomList(PRID);
        if (roomArrayList.size() > 0) {
            initializeDoc();
            setDocHeader();
            setSubIndex();
//            addDocFooter();
            EndDocument();
            ImlementRoomDetail(roomArrayList);
            if (Build.VERSION.SDK_INT >= 24) {
                // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
                return FileProvider.getUriForFile(context, "com.displayfort.mvpatel", file);  // use this version for API >= 24
            } else {
                return Uri.fromFile(file);
            }
        }

        return uri;
    }

    private void setSubIndex() {
        BaseColor myColor1 = WebColors.getRGBColor("#767676");
        PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(6);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setColspan(6);

        cell.setBackgroundColor(myColor1);
        /*Header Style */
        cell = new PdfPCell(new Phrase("#"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Code"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Qty"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Rate"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Total"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Image"));
        cell.setBackgroundColor(myColor1);
        table.addCell(cell);
    }


    private void initializeDoc() {
        doc = new Document();
        file = null;
        try {
///data/user/0/com.displayfort.mvpatel/files/MV_PATEL-04122018-3018.pdf
//            Log.e("PDFCreator", "PDF Path: " + path);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-hhmmss");
            file = new File(context.getFilesDir(), "MV_PATEL-" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setDocHeader() throws DocumentException {
        BaseColor myColor1 = WebColors.getRGBColor("#FFFFFF");
//create table
        try {
            PdfPTable pt = new PdfPTable(3);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            pt.setWidths(fl);
            PdfPCell cell = new PdfPCell();
            /*1*/
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            Drawable myImage = context.getResources().getDrawable(R.mipmap.logo);
            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            Image bgImage = Image.getInstance(bitmapdata);
            bgImage.setAbsolutePosition(330f, 642f);
            cell.addElement(bgImage);
            pt.addCell(cell);
            /*2*/
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(new Paragraph("M V Patel & Co"));
            cell.addElement(new Paragraph("15 Meerapath Colony, Dhenu Market Rd, Indore, Madhya Pradesh 452003"));
            cell.addElement(new Paragraph(""));
            pt.addCell(cell);
            /*3*/
            cell = new PdfPCell(new Paragraph("Phone: 097555 40406"));
            cell.setBorder(Rectangle.NO_BORDER);
            pt.addCell(cell);
           /**/

            PdfPTable pTable = new PdfPTable(1);
            pTable.setWidthPercentage(100);
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.addElement(pt);
            pTable.addCell(cell);

            table = new PdfPTable(6);
            float[] columnWidth = new float[]{6, 30, 30, 20, 20, 30};
            table.setWidths(columnWidth);

            cell = new PdfPCell();
            cell.setBackgroundColor(myColor1);
            cell.setColspan(6);
            cell.addElement(pTable);
            table.addCell(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDocFooter() {
        try {
            BaseColor myColor1 = WebColors.getRGBColor("#757575");
            PdfPTable ftable = new PdfPTable(6);
            ftable.setWidthPercentage(100);
            float[] columnWidthaa = new float[]{30, 10, 30, 10, 30, 10};
            ftable.setWidths(columnWidthaa);
            PdfPCell cell = new PdfPCell();
            cell.setColspan(6);
            cell.setBackgroundColor(myColor1);
            cell = new PdfPCell(new Phrase("Total Nunber"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(myColor1);
            ftable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Footer"));
            cell.setColspan(6);
            ftable.addCell(cell);
            cell = new PdfPCell();
            cell.setColspan(6);
            cell.addElement(ftable);
            table.addCell(cell);
            doc.add(table);
            Toast.makeText(context, "created PDF", Toast.LENGTH_LONG).show();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }

    private void EndDocument() {
        try {
            doc.add(table);
            Toast.makeText(context, "created PDF", Toast.LENGTH_LONG).show();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }

    private void ImlementRoomDetail(ArrayList<Room> roomArrayList) {
        for (int i = 0; i < roomArrayList.size(); i++) {
//                     roomListItemViewHolder.mRoomNameTv.setText(roomArrayList.get(i).name);
            final ArrayList<OrderDetailDao> orderDetailDaos = dbHandler.getOrderListByRoom((int) roomArrayList.get(i).id);
            if (orderDetailDaos.size() > 0) {

            }

        }
    }


    public Uri createPDF(Context context) throws FileNotFoundException, DocumentException {

        //create document file
        Document doc = new Document();
        File file = null;
        try {

//            Log.e("PDFCreator", "PDF Path: " + path);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-mmss");
            file = new File(context.getFilesDir(), "MV_PATEL-" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
//create table
            PdfPTable pt = new PdfPTable(3);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            pt.setWidths(fl);
            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            //set drawable in cell
            Drawable myImage = context.getResources().getDrawable(R.mipmap.logo);
            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            try {
                Image bgImage = Image.getInstance(bitmapdata);
                bgImage.setAbsolutePosition(330f, 642f);
                cell.addElement(bgImage);
                pt.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("M V Patel & Co"));

                cell.addElement(new Paragraph("15 Meerapath Colony, Dhenu Market Rd, Indore, Madhya Pradesh 452003"));
                cell.addElement(new Paragraph(""));
                pt.addCell(cell);
                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);
                pt.addCell(cell);

                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setColspan(1);
                cell.addElement(pt);
                pTable.addCell(cell);
                PdfPTable table = new PdfPTable(6);

                float[] columnWidth = new float[]{6, 30, 30, 20, 20, 30};
                table.setWidths(columnWidth);

                BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
                BaseColor myColor1 = WebColors.getRGBColor("#757575");

                cell = new PdfPCell();
                cell.setBackgroundColor(myColor1);
                cell.setColspan(6);
                cell.addElement(pTable);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(6);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(6);

                cell.setBackgroundColor(myColor1);
                /*Header Style */
                cell = new PdfPCell(new Phrase("#"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Code"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Qty"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Rate"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Total"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Image"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(6);

                for (int i = 1; i <= 10; i++) {
                    table.addCell(String.valueOf(i));
                    table.addCell("Header 1 row " + i);
                    table.addCell("Header 2 row " + i);
                    table.addCell("Header 3 row " + i);
                    table.addCell("Header 4 row " + i);
                    table.addCell("Header 5 row " + i);

                }

                PdfPTable ftable = new PdfPTable(6);
                ftable.setWidthPercentage(100);
                float[] columnWidthaa = new float[]{30, 10, 30, 10, 30, 10};
                ftable.setWidths(columnWidthaa);
                cell = new PdfPCell();
                cell.setColspan(6);
                cell.setBackgroundColor(myColor1);
                cell = new PdfPCell(new Phrase("Total Nunber"));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Paragraph("Footer"));
                cell.setColspan(6);
                ftable.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(6);
                cell.addElement(ftable);
                table.addCell(cell);
                doc.add(table);
                Toast.makeText(context, "created PDF", Toast.LENGTH_LONG).show();
            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (IOException e) {
                Log.e("PDFCreator", "ioException:" + e);
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
            return FileProvider.getUriForFile(context, "com.displayfort.mvpatel", file);  // use this version for API >= 24
        } else {
            return Uri.fromFile(file);
        }
    }


}
