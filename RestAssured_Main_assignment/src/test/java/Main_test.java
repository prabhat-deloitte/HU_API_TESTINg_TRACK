import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;

public class Main_test {
    String Token;
    String Email_data_base;
    public String Token_PArse(Response res){
        return res.jsonPath().getString("token");
    }

    @Test(priority = 1)
    public void create_user(){

        File jd= new File("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\Register.json");
        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/register").
                header("content-type","application/json").
                body(jd).
                when().
                post().
                then().extract().response();
        Token = Token_PArse(response);
        System.out.println(Token);
        //validate
        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\Register.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject)obj;
            Email_data_base = (String)jsonObject.get("email");



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Email_resp.equalsIgnoreCase(Email_data_base)){
            assertTrue(true);
        }
        else{
            System.out.println(Email_resp + Email_data_base);
            assertTrue(false);
        }
    }

    @Test(priority = 2)
    public void Login(){File jd= new File("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\login.json");
        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/login").
                header("content-type","application/json").
                body(jd).
                when().
                post().
                then().extract().response();
        //validate
        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\login.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject)obj;
            Email_data_base = (String)jsonObject.get("email");



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Email_resp.equalsIgnoreCase(Email_data_base)){
            assertTrue(true);
        }
        else{
            System.out.println(Email_resp + Email_data_base);
            assertTrue(false);
        }

    }

    @Test(priority = 3)
    public void Creating_Tasks(){

        File Task = new File("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\CreateTask.json");
        String temp_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjQ5NTMzNzE2YTc0ZTAwMTc0NWZlZWEiLCJpYXQiOjE2NDg5NzI1OTl9.m1zS_tE3LXb4EXQ-lb31Fx7INfsvziTOiGEqTDCCnGc";
        given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + temp_token).header("content-type","application/json").
                    body(Task)
                    .when()
                    .post()
                    .then()
                    .statusCode(201);
        }
        @Test
    public void Validating_paging(){

        try{
        String temp_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjQ5NTMzNzE2YTc0ZTAwMTc0NWZlZWEiLCJpYXQiOjE2NDg5NzI1OTl9.m1zS_tE3LXb4EXQ-lb31Fx7INfsvziTOiGEqTDCCnGc";
        int num = 2;
       Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task?limit=2").
                header("Authorization", "Bearer " + temp_token).header("content-type","application/json")
                .when()
                .get()
                .then()
                .statusCode(200).extract().response();
       JSONObject arr = new JSONObject(response.asString());
       int Count = (int)arr.get("count");
       if (num == Count){
           assertTrue(true);

       }}catch (Exception e){
            System.out.println(e);
        }

        }



    }





