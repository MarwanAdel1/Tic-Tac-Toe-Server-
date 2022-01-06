/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author Marwan Adel
 */
public class JsonConverter {

    public static JSONObject convertRegisterMessageToJson(String userName, String password) {
        JSONObject jSONObject=null;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("Header", "Database");
            jSONObject.put("SubHeader", "Register");
            jSONObject.put("Username", userName);
            jSONObject.put("Password", password);

        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(jSONObject);
        return jSONObject;
    }
    
}
