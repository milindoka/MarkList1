package in.refort.MarkList;

//import org.w3c.dom.Document;



import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Milind on 3/10/18.
 */

public class WritePDF
{

    ArrayList<String> roll=new ArrayList <String>();//creating new generic arraylist
    ArrayList<String> rollfinal=new ArrayList<String>();//creating new generic arraylist
    ArrayList<String> markfinal=new ArrayList<String>();//creating new generic arraylist

    int strength=147,requiredtables=0;
    String collegename,classdiv,subject,examiner,examination,totalmarks,date;

   void SetData(String collegename,String classdiv,String subject,
                String examiner, String examination,String totalmarks,
                String date) {
       this.collegename = collegename;
       this.classdiv = classdiv;
       this.subject = subject;
       this.examiner = examiner;
       this.examination = examination;
       this.totalmarks = totalmarks;
       this.date = date;
   }

   void SetRollArray(ArrayList<String>  roll,ArrayList mark)
   {this.roll=roll; //this.mark=mark;
        markfinal.removeAll(markfinal);
        rollfinal.removeAll(rollfinal);
    for(int i=0;i<roll.size();i++)
    {String temp,temp2[];
      temp=roll.get(i);
      if(!temp.contains(":")) continue;
      temp2=temp.split(":");
      rollfinal.add(temp2[0]);
      markfinal.add(temp2[1]);

    }
    strength=rollfinal.size();
        //String str=roll.get(i);
   }


    void AddHeader(Document document) throws DocumentException, IOException
    {PdfPTable table = new PdfPTable(3);
        PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.setWidthPercentage(95);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase(collegename));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase(" "));cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Class-Div : " + classdiv));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Subject : "+subject));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Examiner : "+examiner));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Examination : "+examination));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Total : "+totalmarks));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Date : "+date));cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        table.setSpacingAfter(10f);
        document.add(table);

    }



    void FillSubTable(PdfPTable tbl,int index) {
        PdfPCell cell = new PdfPCell(new Phrase("ROLL"));
        cell.setPaddingBottom(4f);

        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tbl.addCell(cell);
        cell = new PdfPCell(new Phrase("MRK"));
        cell.setPaddingBottom(4f);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tbl.addCell(cell);

        for (int i = 0; i < 40; i++) {
            if (i + index * 40 >= strength) break;

            //String str=roll.get(i);
            cell = new PdfPCell(new Phrase(rollfinal.get(i + index * 40)));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setPaddingBottom(3f);
            cell.setPaddingTop(1f);
            tbl.addCell(cell);
            cell = new PdfPCell(new Phrase(markfinal.get(i + index * 40)));
            cell.setPaddingBottom(3f);
            cell.setPaddingTop(1f);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            tbl.addCell(cell);

        }
    }





    void AddBody(Document document) throws DocumentException, IOException
    {PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(95);
        //   table.getDefaultCell().setFixedHeight(150);
        ///Create 5 subtables
        float colwidth[]={6,3};
        PdfPTable [] tab = new PdfPTable[5];
        //initialize subtables..
        for(int i=0;i<5;i++)
        { tab[i] = new PdfPTable(colwidth);
            tab[i].setWidthPercentage(95);

        }

        //Fill and Add Subtables as required, skip extra subtables by inserting empty cell
        for(int j=0;j<5;j++)
        {if(j<requiredtables)
        { FillSubTable(tab[j],j);
            PdfPCell cellfortable = new PdfPCell();
            cellfortable.setPadding(0);
            cellfortable.setBorder(PdfPCell.NO_BORDER);
            cellfortable.addElement(tab[j]);
            table.addCell(cellfortable);
        }
        else
        {
            PdfPCell cell= new PdfPCell(new Phrase(""));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
        }
        }

        document.add(table);


    }



    void AddFooter(Document document) throws DocumentException, IOException
    {PdfPTable table = new PdfPTable(2);


        PdfPCell cell = new PdfPCell(new Phrase("Key : AABBABABAADDDCCC"));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Page Total : "));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.setWidthPercentage(95);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase("Examiner's Signature ; ___________"));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);






        cell = new PdfPCell(new Phrase(" "));
        //  cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);

        table.setSpacingBefore(10f);
        document.add(table);

    }


    public Boolean write(String fname)
    {
        try {
            /* Create file path for Pdf */
            File file = new File(fname);
            if (!file.exists())
                file.createNewFile();

            if(strength>200) return false;
            requiredtables=strength/40;
            if(strength%40!=0) requiredtables++;

            Document document = new Document(PageSize.A4);
            document.setMargins(50, 2, 2, 2);
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            AddHeader(document);
            AddBody(document);
            AddFooter(document);
            document.close();
            return true;
        }
          catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
    }



}
