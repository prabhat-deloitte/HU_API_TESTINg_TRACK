import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;
public class Main_test {
    String Token;
    String Email_data_base;
    Excelutil excel  = new Excelutil();
    public String Token_PArse(Response res){
        return res.jsonPath().getString("token");
    }

    @Test(priority = 1)
    public void create_user(){
      for (int i=1; i<=excel.Row_no(0);i++) {
          org.json.simple.JSONObject jd = excel.Get_Data(i,1);
          Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/register").
                  header("content-type", "application/json").
                  body(jd.toJSONString()).
                  when().
                  post().
                  then().extract().response();
          Token = Token_PArse(response);
          //System.out.println(Token);
          excel.Write_data_token(Token, i);


        //validate
        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");

        if (Email_resp.equalsIgnoreCase(excel.email_id)){
            assertTrue(true);
        }
        else{

            assertTrue(false);
        }}
    }

    @Test(priority = 2)
    public void Login(){
        for (int i=1; i<=excel.Row_no(0);i++) {
            org.json.simple.JSONObject jd = excel.Get_Data(i,2);

        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/login").
                header("content-type","application/json").
                body(jd.toJSONString()).
                when().
                post().
                then().extract().response();
        //validate
        JSONObject arr = new JSONObject(response.asString());
        String Email_resp = (String)arr.getJSONObject("user").get("email");
        String User_id = (String)arr.getJSONObject("user").get("_id");
        excel.Write_data_id(User_id, i);
        if (Email_resp.equalsIgnoreCase(excel.email_id)){
            System.out.println(jd+"    ----> Validated");
            assertTrue(true);
        }
        else{

            assertTrue(false);
        }}

    }

    @Test(priority = 3)
    public void Creating_Tasks(){
        for (int i=1; i<=excel.Row_no(0);i++) {
            String temp_token = excel.GET_Token(i);
            for (int j=1; j<=excel.Row_no(1);j++) {
               org.json.simple.JSONObject Task = excel.GET_Task(j);
        //File Task = new File("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\RestAssured_Main_assignment\\src\\test\\java\\CreateTask.json");
        //String temp_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjQ5NTMzNzE2YTc0ZTAwMTc0NWZlZWEiLCJpYXQiOjE2NDg5NzI1OTl9.m1zS_tE3LXb4EXQ-lb31Fx7INfsvziTOiGEqTDCCnGc";
        given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + temp_token).header("content-type","application/json").
                    body(Task.toJSONString())
                    .when()
                    .post()
                    .then()
                    .statusCode(201);
        }}}
        @Test(priority = 4)
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
}







