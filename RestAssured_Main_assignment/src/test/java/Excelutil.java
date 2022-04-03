import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excelutil {

    static int Sheet_no ;
    static String name = null;
    static String email_id = null;
    static String password = null;
    static String age = null;
    static String token = null;
    static String user_id = null;
    public static org.json.simple.JSONObject Get_Data(int i, int value){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
                row = sheet.getRow(i);
                for (int j =0; j< row.getLastCellNum(); j++){
                    cell = row.getCell(j);
                    if (j==0){
                        name = cell.getStringCellValue();
                    }
                    if (j==1){
                        email_id = cell.getStringCellValue();
                    }
                    if (j==2){
                        password = cell.getStringCellValue();
                    }
                    if (j==3){
                       age  = cell.getRawValue();
                    }
                    if (j==4){
                        token = cell.getStringCellValue();
                    }
                    if (j==5){
                        user_id = cell.getStringCellValue();
                    }

                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (value == 1){
            return Create_user_json(name,email_id,password,age);
        }
        if (value == 2){
            return Login_json(email_id,password);
        }
        return Create_user_json(name,email_id,password,age);
}

  public  static  org.json.simple.JSONObject Create_user_json(String Name,String Email,String Pass,String Age){
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("name",Name);
        obj.put("email",Email);
        obj.put("password",Pass);
        obj.put("age",Age);
        return obj;
    }
    public  static  org.json.simple.JSONObject Login_json(String Email,String Pass){
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();

        obj.put("email",Email);
        obj.put("password",Pass);

        return obj;
    }
    public  static  org.json.simple.JSONObject Task_json(String Description){
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();

        obj.put("description",Description);

        return obj;
    }


    public static int Row_no(int value){

        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFSheet sheet1 = workbook.getSheetAt(1);
            Sheet_no = sheet.getLastRowNum();
           int Sheet2_no = sheet1.getLastRowNum();
           if (value == 1){
               return Sheet2_no;
           }
            if (value == 0){
                return Sheet_no;
            }
    }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}
        return Sheet_no;
}
       public static void Write_data_token(String Token, int i) {
           String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
           try {
               FileInputStream fis = new FileInputStream(Excel_file_path);
               XSSFWorkbook workbook = new XSSFWorkbook(fis);
               XSSFSheet sheet = workbook.getSheetAt(0);
               XSSFRow row = null;
               XSSFCell cell = null;
               row = sheet.getRow(i);

                       System.out.println("token is printing");
                       cell = row.createCell(4);
                       cell.setCellValue(Token);
                       fis.close();
                     FileOutputStream fos = new FileOutputStream(Excel_file_path);
                     workbook.write(fos);
                     //fos.flush();
                     fos.close();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    public static void Write_data_id(String id, int i) {
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);

            System.out.println("Writing in Excel");
            cell = row.createCell(5);
            cell.setCellValue(id);
            fis.close();
            FileOutputStream fos = new FileOutputStream(Excel_file_path);
            workbook.write(fos);
            //fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String GET_Token(int i){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);
            cell = row.getCell(4);
            return cell.getStringCellValue();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return "unable to fetch";

    }

    public String GET_User_id(int i){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);
            cell = row.getCell(5);
            return cell.getStringCellValue();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return "unable to fetch";

    }


    public String GET_(int i){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);
            cell = row.getCell(5);
            return cell.getStringCellValue();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return "unable to fetch";

    }
    public String GET_Task(int i){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(1);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);
            cell = row.getCell(0);
            return cell.getStringCellValue();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return "unable to fetch";

    }


    public org.json.simple.JSONObject SET_Task(int i){
        String Excel_file_path = ("C:\\Users\\praparihar\\Desktop\\Database.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(1);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);
            cell = row.getCell(0);
            return Task_json(cell.getStringCellValue());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return Task_json("unable_to_fetch");

    }
    }



