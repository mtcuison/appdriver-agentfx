package org.rmj.appdriver.agentfx.printer;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.agentfx.WebClient;

public class EPSONPrint {
    public static final String API_ORDER = "";
    public static final String API_BILLING = "";
    public static final String API_INVOICE = "http://«sWebSrver»/escpos-php/src/invoice.php";
    public static final String API_CANCEL_INVOICE = "http://«sWebSrver»/escpos-php/src/invoice_cancel.php";
    public static final String API_XREADING = "http://«sWebSrver»/escpos-php/src/x-reading.php";
    public static final String API_YREADING = "http://«sWebSrver»/escpos-php/src/y-reading.php";
    public static final String API_ZREADING = "http://«sWebSrver»/escpos-php/src/z-reading.php";
    
    public static JSONObject XReading(JSONObject foJSON){
        try {
            String lsResult = WebClient.sendHTTP(API_XREADING.replace("«sWebSrver»", 
                                                    (String) foJSON.get("webserver")), 
                                                    foJSON.toJSONString(), 
                                                    null);
            
            JSONParser oParser = new JSONParser();
            
            if(lsResult == null || lsResult.isEmpty()){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "No response from server.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                
                return err_mstr;
            }
            
            return (JSONObject) oParser.parse(lsResult);
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());
            
            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    public static JSONObject ZReading(JSONObject foJSON){
        try {
            String lsResult = WebClient.sendHTTP(API_ZREADING.replace("«sWebSrver»", 
                                                    (String) foJSON.get("webserver")), 
                                                    foJSON.toJSONString(), 
                                                    null);
            
            JSONParser oParser = new JSONParser();
            
            if(lsResult == null || lsResult.isEmpty()){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "No response from server.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                
                return err_mstr;
            }
            
            return (JSONObject) oParser.parse(lsResult);
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());
            
            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    public static JSONObject YReading(JSONObject foJSON){
        try {
            String lsResult = WebClient.sendHTTP(API_YREADING.replace("«sWebSrver»", 
                                                    (String) foJSON.get("webserver")), 
                                                    foJSON.toJSONString(), 
                                                    null);
            
            JSONParser oParser = new JSONParser();
            
            if(lsResult == null || lsResult.isEmpty()){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "No response from server.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                
                return err_mstr;
            }
            
            return (JSONObject) oParser.parse(lsResult);
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());
            
            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    public static JSONObject Invoice(JSONObject foJSON){
        try {
            String lsResult = WebClient.sendHTTP(API_INVOICE.replace("«sWebSrver»", 
                                                    (String) foJSON.get("webserver")), 
                                                    foJSON.toJSONString(), 
                                                    null);
            
            JSONParser oParser = new JSONParser();
            
            if(lsResult == null || lsResult.isEmpty()){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "No response from server.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                
                return err_mstr;
            }
            
            return (JSONObject) oParser.parse(lsResult);
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());
            
            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    public static JSONObject CancelInvoice(JSONObject foJSON){
        try {
            String lsResult = WebClient.sendHTTP(API_CANCEL_INVOICE.replace("«sWebSrver»", 
                                                    (String) foJSON.get("webserver")), 
                                                    foJSON.toJSONString(), 
                                                    null);
            
            JSONParser oParser = new JSONParser();
            
            if(lsResult == null || lsResult.isEmpty()){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "No response from server.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                
                return err_mstr;
            }
            
            return (JSONObject) oParser.parse(lsResult);
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());
            
            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
}
