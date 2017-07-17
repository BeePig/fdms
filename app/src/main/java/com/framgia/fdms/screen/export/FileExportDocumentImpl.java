package com.framgia.fdms.screen.export;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.aspose.words.CellMerge;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.PageSetup;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.framgia.fdms.FDMSApplication;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.aspose.words.ParagraphAlignment.CENTER;
import static com.aspose.words.ParagraphAlignment.LEFT;
import static com.aspose.words.ParagraphAlignment.RIGHT;

/**
 * Created by beepi on 10/07/2017.
 * This class export data to another category, such as .doc, .xls....
 */
public class FileExportDocumentImpl implements FileExportContract {
    private static final String DOCX_FORMAT = ".docx";
    private static final int FONT_SIZE_12 = 12;
    private static final int FONT_SIZE_10 = 10;
    private static final double IMAGE_WIDTH = 100.0;
    private static final double IMAGE_HEIGHT = 29.0;
    private static final double CELL_HEIGHT_50 = 50.0;
    private static final double CELL_HEIGHT_20 = 20.0;
    private static final double WIDTH_220 = 220;
    private static final double WIDTH_170 = 170;
    private static final double HEADER_DISTANCE = 20;
    private static final double FONT_SIZE_11 = 11;
    private Document mDocument;
    private DocumentBuilder mDocumentBuilder;
    private OutputStream mOutputStream;
    private List<Device> mDevices;
    private User mUser;

    public FileExportDocumentImpl(File folderExport, String nameFile, List<Device> devices, User
        user) {
        mDevices = devices;
        mUser = user;
        try {
            mDocument = new Document();
            mDocumentBuilder = new DocumentBuilder(mDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createOutPutStream(folderExport, nameFile);
    }

    private void insertHeader() {
        if (mDocumentBuilder == null) return;
        Section section = mDocumentBuilder.getCurrentSection();
        PageSetup pageSetup = section.getPageSetup();
        pageSetup.setDifferentFirstPageHeaderFooter(true);
        pageSetup.setHeaderDistance(HEADER_DISTANCE);
        mDocumentBuilder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        mDocumentBuilder.getFont().setSize(FONT_SIZE_12);
        mDocumentBuilder.getFont().setBold(true);
        mDocumentBuilder.startTable();
        mDocumentBuilder.getCellFormat().setWidth(IMAGE_WIDTH);
        mDocumentBuilder.getRowFormat().setHeight(CELL_HEIGHT_50);
        mDocumentBuilder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        mDocumentBuilder.getCellFormat().setVerticalAlignment(CENTER);
        /**
         * insert row 1
         */
        mDocumentBuilder.insertCell();
        try {
            if (getLogoFramgia() != null) {
                mDocumentBuilder.insertImage(getLogoFramgia(), IMAGE_WIDTH, IMAGE_HEIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDocumentBuilder.insertCell();
        mDocumentBuilder.getCellFormat().setWidth(WIDTH_220);
        mDocumentBuilder.write(FDMSApplication.getInstant()
            .getString(R.string.title_devices_assignment_report));
        mDocumentBuilder.insertCell();
        mDocumentBuilder.getParagraphFormat().setAlignment(LEFT);
        mDocumentBuilder.getCellFormat().setWidth(WIDTH_170);
        mDocumentBuilder.getFont().setSize(FONT_SIZE_10);
        mDocumentBuilder.getFont().setBold(false);
        mDocumentBuilder
            .writeln(FDMSApplication.getInstant().getString(R.string.title_code_string));
        mDocumentBuilder
            .writeln(FDMSApplication.getInstant().getString(R.string.title_published_time));
        mDocumentBuilder
            .write(FDMSApplication.getInstant().getString(R.string.title_date_publishment) +
                getCurentTime());
        mDocumentBuilder.endRow();
        /**
         * insert row2
         */
        mDocumentBuilder.getRowFormat().setHeight(CELL_HEIGHT_20);
        mDocumentBuilder.getCellFormat().setVerticalAlignment(CENTER);
        mDocumentBuilder.insertCell();
        mDocumentBuilder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        mDocumentBuilder.getCellFormat().setWidth(IMAGE_WIDTH);
        mDocumentBuilder.getFont().setBold(true);
        mDocumentBuilder.getFont().setSize(FONT_SIZE_11);
        mDocumentBuilder
            .write(FDMSApplication.getInstant().getString(R.string.title_framgia_company));
        mDocumentBuilder.insertCell();
        mDocumentBuilder.getCellFormat().setWidth(WIDTH_220);
        mDocumentBuilder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        mDocumentBuilder.insertCell();
        mDocumentBuilder.getCellFormat().setWidth(WIDTH_170);
        mDocumentBuilder.getCellFormat().setHorizontalMerge(CellMerge.NONE);
        mDocumentBuilder.getParagraphFormat().setAlignment(RIGHT);
        mDocumentBuilder.getFont().setSize(FONT_SIZE_10);
        mDocumentBuilder.write(FDMSApplication.getInstant().getString(R.string.title_iso));
        mDocumentBuilder.endRow();
        mDocumentBuilder.endTable();
    }

    private Bitmap getLogoFramgia() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeResource(FDMSApplication.getInstant().getResources(),
            R.drawable.ic_logo_framgia);
    }

    private void inseartAssignmentTable() {
    }

    private void insertDeviceTable() {
    }

    private void insertFooter() {
    }

    private void save() {
        try {
            if (mDocument == null || mOutputStream == null) return;
            mDocument.save(mOutputStream, SaveFormat.DOCX);
            if (mOutputStream != null) mOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOutPutStream(File fileExport, String nameFile) {
        try {
            if (fileExport == null) return;
            File file = new File(fileExport, nameFile + DOCX_FORMAT);
            mOutputStream = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void makeDocument() {
        if (mDocumentBuilder == null) return;
        insertHeader();
        save();
    }

    private String getCurentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(cal.getTime());
    }
}
