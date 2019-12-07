/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albionprices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Ronaldo
 */
public class AlbionPrices {

    /*
    function CURRENTPRICE(name, location, quality) {
    name = encodeURI(name);
    location = encodeURI(location);
    quality = encodeURI(quality);
    var response = UrlFetchApp.fetch("http://www.albion-online-data.com/api/v2/stats/Prices/" + name + "?locations=" + location + "&qualities=" + quality);
    var w = JSON.parse(response.getContentText());
  
      for(var i = 0; i < w.length; i++) {
          var obj = w[i];
      
          console.log(obj.sell_price_min);
          console.log(obj.sell_price_max);
      }
  
    return w[0].sell_price_min;
}
     */
    
    
    public static void main(String[] args) throws MalformedURLException, IOException, JSONException {
        //System.out.println(itemInfo("T4_BAG", "2"));
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new albionPricesframe().setVisible(true);
            }
        });
    }
    
    
    public static ArrayList<itemValues> itemInfo(String name, String quality) throws MalformedURLException, IOException, JSONException{
        String url = "https://www.albion-online-data.com/api/v2/stats/Prices/" + name + "?locations=" + "&qualities=" + quality;
        URL link = new URL(url);
        
        HttpURLConnection con = (HttpURLConnection) link.openConnection();
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        
        //JSONObject object = new JSONObject(response.toString());
        JSONArray objectarr = new JSONArray(response.toString());
        //JSONArray objectarr = object.getJSONArray("");
        
        //System.out.println(objectarr);
        //System.out.println(Arrays.toString(getPrices(objectarr)));
        
        return getPrices(objectarr);
    }
    public static class itemValues {
            String name;
            String location;
            int maxprice;
            int minprice;
            public itemValues(String name, String location, int maxprice, int minprice) {
                this.name = name;
                this.location = location;
                this.maxprice = maxprice;
                this.minprice = minprice;
            }
            @Override
            public String toString() {
                return "{ " + name + ", " + location + ", " + maxprice + ", " + minprice + " }";
            }
        }
    public static ArrayList<itemValues> getPrices(JSONArray arr) throws JSONException{
        
        ArrayList<itemValues> list = new ArrayList<>();
        System.out.println("getting...");
        for(int i = 0; i <= arr.length()-1; i++){
            
            //itemValues i = itemValues(arr.getJSONObject(0).getString("item_id"),arr.getJSONObject(0).getString("city"),arr.getJSONObject(0).getInt("sell_price_max"),arr.getJSONObject(0).getInt("sell_price_min"));
            list.add(new itemValues(arr.getJSONObject(i).getString("item_id"),arr.getJSONObject(i).getString("city"),arr.getJSONObject(i).getInt("sell_price_max"),arr.getJSONObject(i).getInt("sell_price_min")));
            //System.out.println(list.get(i).toString());
            
        }
        return list;
    }
    
}
