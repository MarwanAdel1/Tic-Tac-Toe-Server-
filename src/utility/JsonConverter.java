/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import pojo.Score;

/**
 *
 * @author Marwan Adel
 */
public class JsonConverter {

    public static JSONObject convertRegisterIdMessageToJson(int id) {
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("Header", "Database");
            jSONObject.put("SubHeader", "Register");
            jSONObject.put("Operation", id);

        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(jSONObject);
        return jSONObject;
    }

    public static JSONObject convertLoginIdMessageToJson(int id, String username) {
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("Header", "Database");
            jSONObject.put("SubHeader", "Login");
            jSONObject.put("Operation", id);
            jSONObject.put("Username", username);

        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(jSONObject);
        return jSONObject;
    }

    public static JSONObject convertOnlineUsernameVectorToJson(String username) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {

            for (int i = 0; i < ServerRequestHandling.clientData.size(); i++) {
                jSONArray.put(ServerRequestHandling.clientData.get(i).username);
            }

            jSONObject.put("Header", "Online");
            jSONObject.put("myUsername", username);
            jSONObject.put("OnlinePlayers", jSONArray);

        } catch (JSONException ex) {
            Logger.getLogger(JsonConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jSONObject;
    }

    public static JSONObject convertScoreToJson(Score score) {
        JSONObject jSONObject = new JSONObject();
        try {

            jSONObject.put("Header", "MainPage");
            jSONObject.put("myUsername", score.getUsername());
            jSONObject.put("ID", score.getId());
            jSONObject.put("Played", score.getPlayed());
            jSONObject.put("Win", score.getWin());
            jSONObject.put("Draw", score.getDraw());
            jSONObject.put("Lose", score.getLose());
            jSONObject.put("Total_Score", score.getTotalScore());

        } catch (JSONException ex) {
            Logger.getLogger(JsonConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jSONObject;
    }

    public static JSONObject convertSayByeToJson() {
        JSONObject jSONObject = new JSONObject();
        try {

            jSONObject.put("Header", "Bye");

        } catch (JSONException ex) {
            Logger.getLogger(JsonConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jSONObject;
    }

}
