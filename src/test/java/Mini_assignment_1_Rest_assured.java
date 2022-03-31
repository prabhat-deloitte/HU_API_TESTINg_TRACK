
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;

public class Mini_assignment_1_Rest_assured {
   @Test
    public void get_call() {
       Response response = given().
               baseUri("https://jsonplaceholder.typicode.com").
               header("conten-type", "application/json").
               when().
               get("/posts").
               then().extract().response();
       JSONArray arr = new JSONArray(response.asString());
      //System.out.println((int)arr.getJSONObject(0).get("id"));

       assertTrue((int)arr.getJSONObject(39).get("id")== 40 && ((int)arr.getJSONObject(39).get("userId"))== 4);
       String str = "";
      for (int i= 0; i< arr.length(); i++){
         if (arr.getJSONObject(i).get("title").getClass().getSimpleName()!= str.getClass().getSimpleName()){
            assertTrue(false);

         }

      }

   }
   @Test
   public void put(){
      File jasondata = new File("src//test//java//putdata.json");
       given().
              baseUri("https://reqres.in/api").
              body(jasondata).
              header("conten-type", "application/json").
              when().
              put("/users").
              then().statusCode(200).body("name",equalTo("Arun")).body("job",equalTo("Manager"));

   }


   }






