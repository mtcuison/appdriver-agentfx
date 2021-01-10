package org.rmj.appdriver.agentfx.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.WebClient;

/**
 *
 * @author Michael Cuison
 *      2020.11-27 - started creating this object
 */
public class TokenRestAPI {
    private final static String REQUEST_AUTHORIZED = "system/token_approval/request_authorized.php";
    private final static String UPLOAD_CODE_REQUEST = "system/token_approval/upload_code_request.php";
    private final static String CHECK_CODE_REQUEST = "system/token_approval/check_code_request.php";
    private final static String FETCH_CODE_REQUEST = "system/token_approval/fetch_code_request.php";
    private final static String REPLY_CODE_REQUEST = "system/token_approval/reply_code_request.php";
    private final static String SMS_REQUEST_MASKING = "notification/send_text_masking.php";
    private final static String SMS_REQUEST_POSTPAID = "notification/send_text_postpaid.php";
    private final static String DOWNLOAD_EMPLOYEE_INFO = "system/param/download_employee_info.php";
    
    /**
     * Requests authorized personnel to approve the token approval request.
     * 
     * @param foGRider GhostRider application driver
     * @param foJSON {sRqstType, nLevelxxx}
     * @return
     */
    public static JSONObject RequestAuthorized(GRider foGRider, JSONObject foJSON){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + REQUEST_AUTHORIZED;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    
                
        try {
            String response = WebClient.sendHTTP(sURL, foJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            foJSON = (JSONObject) parser.parse(response);
            return foJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    /**
     * Uploads approval code request to the server.
     * 
     * @param foGRider GhostRider application driver
     * @param foJSON {sTempTNox, dTransact, sSourceNo, sSourceCD, sRqstType, sReqstdBy, sReqstdTo, sReqstInf}
     * @return 
     */
    public static JSONObject UploadCodeRequest(GRider foGRider, JSONObject foJSON){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + UPLOAD_CODE_REQUEST;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    
                
        try {
            String response = WebClient.sendHTTP(sURL, foJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            foJSON = (JSONObject) parser.parse(response);
            return foJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    /**
     * Fetch the status of approval request.
     * 
     * @param foGRider GhostRider application driver
     * @param foJSON {sSourceNo, sSourceCD, sRqstType, sReqstdTo}
     * @return 
     */
    public static JSONObject CheckCodeRequest(GRider foGRider, JSONObject foJSON){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + CHECK_CODE_REQUEST;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    
                
        try {
            String response = WebClient.sendHTTP(sURL, foJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            foJSON = (JSONObject) parser.parse(response);
            return foJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    /**
     * Fetch the code request for the approving officer / specific request.
     * 
     * @param foGRider GhostRider application driver
     * @param foJSON {sReqstdTo, cTranStat} or {sSourceNo, sSourceCD, sRqstType}
     * @return 
     */
    public static JSONObject FetchCodeRequest(GRider foGRider, JSONObject foJSON){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + FETCH_CODE_REQUEST;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    
                
        try {
            String response = WebClient.sendHTTP(sURL, foJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            foJSON = (JSONObject) parser.parse(response);
            return foJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    /**
     * Fetch the code request for the approving officer / specific request.
     * 
     * @param foGRider GhostRider application driver
     * @param foJSON {sTransNox, cTranStat} or {sTransNox, cTranStat, cApprType}
     * @return 
     */
    public static JSONObject ReplyCodeRequest(GRider foGRider, JSONObject foJSON){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + REPLY_CODE_REQUEST;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    
                
        try {
            String response = WebClient.sendHTTP(sURL, foJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            foJSON = (JSONObject) parser.parse(response);
            return foJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    /**
     * Fetch the name of the approving officer.
     * 
     * @param foGRider GhostRider application driver
     * @param fsEmployID Employee ID
     * @return 
     */
    public static JSONObject downloadEmployeeInfo(GRider foGRider, String fsEmployID){      
        String sURL = CommonUtils.getConfiguration(foGRider, "WebSvr") + DOWNLOAD_EMPLOYEE_INFO;
                
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap<String, String>();
        
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "IntegSys");
        headers.put("g-api-imei", MiscUtil.getPCName());
        
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));        
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-client", foGRider.getClientID());    
        headers.put("g-api-user", foGRider.getUserID());    
        headers.put("g-api-log", "");    
        headers.put("g-api-token", "");    

        JSONObject loJSON = new JSONObject();
        loJSON.put("sEmployID", fsEmployID);
        try {
            String response = WebClient.sendHTTP(sURL, loJSON.toJSONString(), (HashMap<String, String>) headers);           
            
            if(response == null){
                JSONObject err_detl = new JSONObject();
                err_detl.put("message", "Server has no response.");
                
                JSONObject err_mstr = new JSONObject();
                err_mstr.put("result", "error");
                err_mstr.put("error", err_detl);
                return err_mstr;
            } 
        
            JSONParser parser = new JSONParser();
            
            loJSON = (JSONObject) parser.parse(response);
            return loJSON;
            
        } catch (IOException | ParseException ex) {
            JSONObject err_detl = new JSONObject();
            err_detl.put("message", ex.getMessage());

            JSONObject err_mstr = new JSONObject();
            err_mstr.put("result", "error");
            err_mstr.put("error", err_detl);
            return err_mstr;
        }
    }
    
    public static JSONObject Request_SMS_Masking(){
        return null;
    }
    
    public static JSONObject Request_SMS_Postpaid(){
        return null;
    }
}
