package org.rmj.appdriver.agentfx.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GRider;

/**
 *
 * @author Michael Cuison
 *      2020.11.29 Started creating this object.
 */
public class TokenizeApproval {
    GRider poGrider;
    
    String psTableNme;
    String psTransNox;
    boolean pbRndAprvl;
    
    String psMessage;
    
    public enum NOTIFICATION{
        ANDROID,
        MASKING,
        POSTPAID
    }
    
    public TokenizeApproval(GRider foGRider){
        poGrider = foGRider;
    }
    
    public TokenizeApproval(GRider foGRider, String fsTableNme, String fsTransNox){
        poGrider = foGRider;
        psTableNme = fsTableNme;
        psTransNox = fsTransNox;
    }
    
    public TokenizeApproval(GRider foGRider, String fsTableNme, String fsTransNox, boolean fbRndAprvl){
        poGrider = foGRider;
        psTableNme = fsTableNme;
        psTransNox = fsTransNox;
        pbRndAprvl = fbRndAprvl;
    }
    
    public void setTableName(String fsValue){
        psTableNme = fsValue;
    }
    
    public void setTransactNo(String fsValue){
        psTransNox = fsValue;
    }
    
    public void isRandomApproval(boolean fbValue){
        pbRndAprvl = fbValue;
    }
    
    public JSONArray CheckCodeRequest(JSONObject foTrans, double fnTranTotl){
        JSONObject loJSON;
        JSONObject loTrans;
        JSONObject loResult;

        JSONArray laResult = new JSONArray();
        JSONArray loArr = checkAuthorized((String) foTrans.get("sRqstType"), fnTranTotl);
        
        if (loArr == null || loArr.isEmpty()) return null;
        
        for(Object o: loArr){
            if ( o instanceof JSONObject ) {
                loJSON = (JSONObject) o;
                
                loTrans = foTrans;
                loTrans.put("sReqstdTo", (String) loJSON.get("sEmployID"));
                
                loJSON = TokenRestAPI.CheckCodeRequest(poGrider, loTrans);
                
                if (!"success".equalsIgnoreCase((String) loJSON.get("result"))){
                    loJSON = (JSONObject) loJSON.get("error");
                    
                    if (!"40026".equalsIgnoreCase((String) loJSON.get("code"))){ //no record found
                        setMessage((String) loJSON.get("message"));
                        return null;
                    }
                } else {
                    loResult = new JSONObject();        
                    loResult.put("sSourceNo", (String) loTrans.get("sSourceNo"));
                    loResult.put("sSourceCD", (String) loTrans.get("sSourceCD"));
                    loResult.put("sRqstType", (String) loTrans.get("sRqstType"));
                    loResult.put("sReqstdTo", (String) loTrans.get("sReqstdTo"));
                    loResult.put("sApprCode", (String) loJSON.get("sApprCode"));
                    loResult.put("cTranStat", (String) loJSON.get("cTranStat"));

                    laResult.add(loResult);
                }
            }
        }
        
        return laResult;
    }
    
    public boolean UploadCodeRequest(JSONObject foTrans, double fnTranTotl){
        JSONObject loTrans;
        JSONObject loJSON;
        JSONArray loArr = checkAuthorized((String) foTrans.get("sRqstType"), fnTranTotl);
        
        if (loArr == null || loArr.isEmpty()) return false;
        
        for(Object o: loArr){
            if ( o instanceof JSONObject ) {
                loJSON = (JSONObject) o;
                
                loTrans = foTrans;
                loTrans.put("sReqstdTo", (String) loJSON.get("sEmployID"));
                
                loJSON = TokenRestAPI.UploadCodeRequest(poGrider, loTrans);
                
                if (!"success".equalsIgnoreCase((String) loJSON.get("result"))){
                    loJSON = (JSONObject) loJSON.get("error");
                    setMessage((String) loJSON.get("message"));
                    return false;
                }
            }
        }
        
        setMessage("Request has been uploaded successfully.");
        return true;
    }
    
    private JSONArray checkAuthorized(String fsRqstType, double fnTranTotl){
        int lnLevel;
        String lsSQL;
        ResultSet loRS;
        
        //get the level of purchase amount
        lsSQL = "SELECT nLevelxxx" +
                " FROM Purchase_Level" +
                " WHERE nAmntThru >= " + fnTranTotl + 
                " ORDER BY nAmntThru" +
                " LIMIT 1";
        
        loRS = poGrider.executeQuery(lsSQL);
        
        try {
            //no record was selected
            if (!loRS.next()){
                setMessage("Purchase amount reached the maximum level or no purchase level set.");
                return null;
            }
            
            lnLevel = loRS.getInt("nLevelxxx");
        } catch (SQLException ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
            return null;
        }
        
        JSONObject loJSON = new JSONObject();
        loJSON.put("sRqstType", fsRqstType);
        loJSON.put("nLevelxxx", lnLevel);
        
        loJSON = TokenRestAPI.RequestAuthorized(poGrider, loJSON);
        
        if ("success".equalsIgnoreCase((String) loJSON.get("result"))){
            return (JSONArray) loJSON.get("detail");
        } else{
            loJSON = (JSONObject) loJSON.get("error");
            
            setMessage((String) loJSON.get("message"));
        }
        
        return null;
    }
    
    public String getMessage(){
        return psMessage;
    }
    
    private void setMessage(String fsValue){
        psMessage = fsValue;
    }
    
}
