import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertTrue;

public class Mini_Assign_2 {
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecification1;
    ResponseSpecification responseSpecification;


    @BeforeTest
    public void setup1(){
        requestSpecification = with().baseUri("https://jsonplaceholder.typicode.com").
                header("Content-type","application/json");
    }
    @Test(priority = 1)
    public void setup2(){
        File JsonData=new File("C:\\Users\\praparihar\\IdeaProjects\\Mini_assingment2_Api_Testing\\src\\test\\java\\putdata.json");
        requestSpecification1 = with().baseUri("https://reqres.in/api").body(JsonData).
                header("Content-type", "application/json");
    }
        @Test
        public void get_call() {
            Response response = requestSpecification.get("posts").
                    then().extract().response();
            JSONArray arr = new JSONArray(response.asString());
            assertThat(response.statusCode(), equalTo(200));
            //assertThat("Content-type", equalTo("json"));
            assertTrue((int)arr.getJSONObject(39).get("id")== 40 && ((int)arr.getJSONObject(39).get("userId"))== 4);
            String str = "";
            for (int i= 0; i< arr.length(); i++){
                if (arr.getJSONObject(i).get("title").getClass().getSimpleName()!= str.getClass().getSimpleName()){
                    assertTrue(false);

                }

            }

        }
    @Test

    public void Put(){
        given().
                spec(requestSpecification1).
                when().put("/users").
                then().statusCode(200).body("name",equalTo("Arun")).body("job",equalTo("Manager"));


    }
}
