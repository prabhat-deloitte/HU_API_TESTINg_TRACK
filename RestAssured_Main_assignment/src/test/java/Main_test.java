import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import static org.testng.AssertJUnit.assertTrue;
public class Main_test {
    String Token;
    String Email_data_base;
    Excelutil excel  = new Excelutil();
    public String Token_PArse(Response res){
        return res.jsonPath().getString("token");
    }

    @Test(priority = 1)
    //creating User
    public void create_user(){
        try{
      for (int i = 1; i<= Excelutil.Row_no(0); i++) {
          org.json.simple.JSONObject jd = Excelutil.Get_Data(i,1);
          Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/register").
                  header("content-type", "application/json").
                  body(jd.toJSONString()).
                  when().
                  post().
                  then().extract().response();
          Token = Token_PArse(response);

          Excelutil.Write_data_token(Token, i);


        //validating created user using email-id
        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");

        if (Email_resp.equalsIgnoreCase(Excelutil.email_id)){
            assertTrue(true);
        }
        else{

            assertTrue(false);
        }}}catch (Exception e){
            System.out.println(e);
        }
    }

    @Test(priority = 2)

    //login using email-id and password

    public void Login(){
        try{
            for (int i = 1; i<= Excelutil.Row_no(0); i++) {
            org.json.simple.JSONObject jd = Excelutil.Get_Data(i,2);
        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/login").
                header("content-type","application/json").
                body(jd.toJSONString()).
                when().
                post().
                then().extract().response();

        //validating Login using email-id

        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");
        String User_id = (String)arr.getJSONObject("user").get("_id");
        Excelutil.Write_data_id(User_id, i);
        if (Email_resp.equalsIgnoreCase(Excelutil.email_id)){
            System.out.println(jd+"    ----> Validated");
            assertTrue(true);
        }
        else{

            assertTrue(false);
        }}}catch (Exception e){
            System.out.println(e);
        }

    }

    @Test(priority = 3)
    //Creating Task using Auth Bearer Token through Database.xlxs
    public void Creating_Tasks(){
        try{
        for (int i=1; i<=excel.Row_no(0);i++) {
            String temp_token = excel.GET_Token(i);
            for (int j=1; j<=excel.Row_no(1);j++) {
               org.json.simple.JSONObject Task = excel.SET_Task(j);
        given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + temp_token).header("content-type","application/json").
                    body(Task.toJSONString())
                    .when()
                    .post()
                    .then()
                    .statusCode(201);
        }}}catch (Exception e){
            System.out.println(e);
        }}
        @Test(priority = 4)
        //Validating paging 2, 5, 10

    public void Validating_paging(){
        for (int i=1; i<=excel.Row_no(0);i++) {
            try{int num = 2;
                for(int j = 0; j<3; j++){
                String temp_token = excel.GET_Token(i);

                Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                        header("Authorization", "Bearer " + temp_token).header("content-type","application/json")
                        .when()
                        .get("?limit="+num)
                        .then()
                        .statusCode(200).extract().response();
                JSONObject arr = new JSONObject(response.asString());
                int Count = (int)arr.get("count");
                if (num == Count && num==2){
                    System.out.println("assertion of paging  for 2 is successful");
                    num = num+3;

                }
                if (num == Count && num==5){
                    System.out.println("assertion of paging  for 5 is successful");
                    num = num+5;

                }
                if (num== Count && num==10) {
                    System.out.println("assertion of paging  for 10 is successful");
                    assertTrue(true);
                }
            }}catch (Exception e){
                System.out.println(e);}


            }
        }
        @Test(priority = 5)
        //Validating all task using Bearer token
    public void Validating_all_Tasks(){
        try{
            for (int i=1; i<=excel.Row_no(0);i++) {
                String temp_token = excel.GET_Token(i);
                String User_id = excel.GET_User_id(i);
            Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + temp_token).header("content-type","application/json")
                    .when()
                    .get()
                    .then()
                    .statusCode(200).extract().response();
                for (int j =0; j<excel.Row_no(1);j++){
                    String task = excel.GET_Task(j+1);
            JSONObject arr = new JSONObject(response.asString());
            String owner = arr.getJSONArray("data").getJSONObject(j).getString("owner");
            String Description = arr.getJSONArray("data").getJSONObject(j).getString("description");
            if(owner.equals(User_id) && Description.equalsIgnoreCase(task)){
                System.out.println(owner + " "+ Description);
            }
            else{
                assertTrue(false);
            }
        }
            }assertTrue(true);}
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test(priority = 6)
    //Creating negative test case Registering with same name
    public void Register_with_same_name(){
        try{
        for (int i=1; i<=excel.Row_no(0);i++) {
                org.json.simple.JSONObject jd = excel.Get_Data(i,1);
                Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/register").
                        header("content-type", "application/json").
                        body(jd.toJSONString()).
                        when().
                        post().
                        then().statusCode(400).extract().response();
        }}catch (Exception e){
            System.out.println(e);
        }
    }

    @Test(priority = 7)
    //creating Negative test case 2
    public void login_without_registeration(){
        try{
        for (int i=1; i<excel.Row_no(3);i++) {
            org.json.simple.JSONObject jd = excel.GET_invalid_user(i);

            Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/login").
                    header("content-type","application/json").
                    body(jd.toJSONString()).
                    when().
                    post().
                    then().statusCode(400).extract().response();
    }}catch (Exception e){
            System.out.println(e);
        }

}
   @Test(priority = 8)
    //creating Negative Test Case 3
    public void wrong_request_body(){
        try{
       for (int i=1; i<=excel.Row_no(0);i++) {
           String temp_token = excel.GET_Token(i);
           for (int j=1; j<=excel.Row_no(1);j++) {
               org.json.simple.JSONObject Task = excel.SET_Task(j);
               given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                       header("Authorization", "Bearer " + temp_token).header("content-type","application/json").
                       body("description : hello_task")
                       .when()
                       .post()
                       .then()
                       .statusCode(400);
           }}}catch (Exception e){
            System.out.println(e);
        }
   }}








