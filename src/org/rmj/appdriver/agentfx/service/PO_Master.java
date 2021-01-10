package org.rmj.appdriver.agentfx.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;

/**
 *
 * @author Michael Cuison
 *      2020.12.03 Started creating this object.
 */
public class PO_Master implements ITokenize{
    public final String psModuleNm = "PO_Master Tokenized Approval";
    public final String psTableNme = "CASys_DBF.PO_Master";
    public final String psSourceCd = "PO"; //table code
    public final String psRqstType = "EP"; //regular request for approval
    public final String psRqstRndm = "EPR"; //request for randomized approval
    
    GRider poGrider;
    
    String psTransNox;
    String psMessagex;
    boolean pbRndAprvl;
    
    JSONObject poData;
    
    @Override
    public void setGRider(GRider foValue) {
        poGrider = foValue;
    }

    @Override
    public void setTransNmbr(String fsValue) {
        psTransNox = fsValue;
    }

    @Override
    public void isRndmAprvlx(boolean fbVablue) {
        pbRndAprvl = fbVablue;
    }

    @Override
    public boolean createCodeRequest() {
        JSONObject loTrans;
        JSONObject loJSON = isValidTransaction();
        
        if (!"success".equals((String) loJSON.get("result"))){
            loJSON = (JSONObject) loJSON.get("error");
            psMessagex = (String) loJSON.get("message");
            return false;
        }
        
        //check first if requests have been made
        String lsSQL = "SELECT" +
                            "  sTransNox" +
                        " FROM Tokenized_Approval_Request" +
                        " WHERE sSourceNo = " + SQLUtil.toSQL(psTransNox) + 
                            " AND sSourceCd = " + SQLUtil.toSQL(psSourceCd) +
                            " AND sRqstType = " + SQLUtil.toSQL(psRqstType);
        
        ResultSet loRS = poGrider.executeQuery(lsSQL);
        if (MiscUtil.RecordCount(loRS) > 0) return true;

        //prepare to request
        JSONObject loData = new JSONObject();
        loData.put("sSourceNo", psTransNox);
        loData.put("sSourceCD", psSourceCd);
        loData.put("sRqstType", !pbRndAprvl ? psRqstType : psRqstRndm);
        loData.put("sReqstInf", psTransNox + ";" + (String) loJSON.get("dTransact") + ";" + String.valueOf((double) loJSON.get("nTranTotl")) + ";" + (String) loJSON.get("sRemarksx"));
        loData.put("sReqstdBy", poGrider.getEmployeeNo());
        loData.put("nTranTotl", (double) loJSON.get("nTranTotl"));
        
        JSONArray loArr = checkAuthorized(loData);
        
        if (loArr == null || loArr.isEmpty()) return false;
        
        poGrider.beginTrans();
        
        for(Object o: loArr){
            if ( o instanceof JSONObject ) {
                loJSON = (JSONObject) o;
                
                loTrans = loData;
                loTrans.put("sReqstdTo", (String) loJSON.get("sEmployID"));
                
                lsSQL = "INSERT INTO Tokenized_Approval_Request SET" + 
                            "  sTransNox = " + SQLUtil.toSQL(MiscUtil.getNextCode("Tokenized_Approval_Request", "sTransNox", true, poGrider.getConnection(), poGrider.getBranchCode())) + 
                            ", dTransact = " + SQLUtil.toSQL("2020-12-03") + 
                            ", sSourceNo = " + SQLUtil.toSQL((String) loTrans.get("sSourceNo")) +
                            ", sSourceCd = " + SQLUtil.toSQL((String) loTrans.get("sSourceCD")) +
                            ", sRqstType = " + SQLUtil.toSQL((String) loTrans.get("sRqstType")) +
                            ", sReqstInf = " + SQLUtil.toSQL((String) loTrans.get("sReqstInf")) +    
                            ", sReqstdBy = " + SQLUtil.toSQL((String) loTrans.get("sReqstdBy")) +
                            ", sReqstdTo = " + SQLUtil.toSQL((String) loTrans.get("sReqstdTo")) +
                            ", sMobileNo = ''" +
                            ", cApprType = ''" +
                            ", sAuthTokn = ''" +
                            ", sApprCode = ''" +
                            ", cSendxxxx = '0'" + 
                            ", cTranStat = '0'" + 
                            ", sModified = " + SQLUtil.toSQL(poGrider.getUserID()) +
                            ", dModified = " + SQLUtil.toSQL(poGrider.getServerDate());
                
                if (poGrider.executeUpdate(lsSQL) <= 0){
                    poGrider.rollbackTrans();
                    psMessagex = poGrider.getErrMsg() + "\n" + poGrider.getMessage();
                    return false;
                }
            }
            
        }
        
        poGrider.commitTrans();
        
        psMessagex = "Requests has been created successfully.";
        return true;
    }

    @Override
    public JSONArray loadCodeRequest() {
        JSONObject loJSON = isValidTransaction();
        
        if (!"success".equals((String) loJSON.get("result"))){
            loJSON = (JSONObject) loJSON.get("error");
            psMessagex = (String) loJSON.get("message");
            return null;
        }
        
        if (!createCodeRequest()) return null;
        
        String lsSQL = "SELECT" +
                            "  sTransNox" +
                            ", dTransact" +
                            ", sSourceNo" +
                            ", sSourceCd" +
                            ", sRqstType" +
                            ", sReqstInf" +
                            ", sReqstdBy" +
                            ", sReqstdTo" +
                            ", cApprType" +
                            ", sAuthTokn" +
                            ", sApprCode" +
                            ", dApproved" +
                            ", cTranStat" +
                        " FROM Tokenized_Approval_Request" +
                        " WHERE sSourceNo = " + SQLUtil.toSQL(psTransNox) + 
                            " AND sSourceCd = " + SQLUtil.toSQL(psSourceCd) +
                            " AND sRqstType = " + SQLUtil.toSQL(psRqstType);
        
        ResultSet loRS = poGrider.executeQuery(lsSQL);
        
        JSONArray loArray = new JSONArray();
        JSONObject loData;
        
        try {
            while (loRS.next()){
                loJSON = new JSONObject();
                loJSON.put("sTransNox", loRS.getString("sTransNox"));
                loJSON.put("dTransact", SQLUtil.dateFormat(loRS.getDate("dTransact"), SQLUtil.FORMAT_SHORT_DATE));
                loJSON.put("sSourceNo", loRS.getString("sSourceNo"));
                loJSON.put("sSourceCD", loRS.getString("sSourceCd"));
                loJSON.put("sRqstType", loRS.getString("sRqstType"));
                loJSON.put("sReqstInf", loRS.getString("sReqstInf"));
                loJSON.put("sReqstdBy", loRS.getString("sReqstdBy"));
                loJSON.put("sReqstdTo", loRS.getString("sReqstdTo"));
                loJSON.put("dApproved", loRS.getString("dApproved"));
                loJSON.put("cApprType", loRS.getString("cApprType"));
                
                loData = TokenRestAPI.CheckCodeRequest(poGrider, loJSON);
                
                if ("success".equals((String) loData.get("result"))){
                    if (!loRS.getString("cTranStat").equals((String) loData.get("cTranStat"))){
                        lsSQL = "UPDATE Tokenized_Approval_Request SET" + 
                                    "  sMobileNo = " + SQLUtil.toSQL((String) loData.get("sMobileNo")) +
                                    ", sAuthTokn = " + SQLUtil.toSQL((String) loData.get("sAuthTokn")) +
                                    ", sApprCode = " + SQLUtil.toSQL((String) loData.get("sApprCode")) +
                                    ", cTranStat = " + SQLUtil.toSQL((String) loData.get("cTranStat")) + 
                                    ", sModified = " + SQLUtil.toSQL(poGrider.getUserID()) + 
                                    ", dModified = " + SQLUtil.toSQL(poGrider.getServerDate()) + 
                                " WHERE sTransNox = " + SQLUtil.toSQL((String) loJSON.get("sTransNox"));
                        
                        if (poGrider.executeUpdate(lsSQL) <= 0){
                            psMessagex = poGrider.getErrMsg() + "\n" + poGrider.getMessage();
                            return null;
                        }
                        
                        loJSON.put("sEmployID", (String) loData.get("sReqstdTo"));
                        loJSON.put("sAuthTokn", (String) loData.get("sAuthTokn"));
                        loJSON.put("sApprCode", (String) loData.get("sApprCode"));
                        loJSON.put("cTranStat", (String) loData.get("cTranStat"));
                    } else {
                        loJSON.put("sEmployID", loRS.getString("sReqstdTo"));
                        loJSON.put("sAuthTokn", loRS.getString("sAuthTokn"));
                        loJSON.put("sApprCode", loRS.getString("sApprCode"));
                        loJSON.put("cTranStat", loRS.getString("cTranStat"));
                    }
                } else {
                    loJSON.put("sEmployID", loRS.getString("sReqstdTo"));
                    loJSON.put("sAuthTokn", loRS.getString("sAuthTokn"));
                    loJSON.put("sApprCode", loRS.getString("sApprCode"));
                    loJSON.put("cTranStat", loRS.getString("cTranStat"));
                }
                
                loArray.add(loJSON);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            psMessagex = ex.getMessage();
            return null;
        }
        
        return loArray;
    }
    
    @Override
    public String uploadCodeRequest(String fsTransNox, String fsApprType) {
        String lsSQL = "SELECT * FROM Tokenized_Approval_Request WHERE sTransNox = " + SQLUtil.toSQL(fsTransNox);
        ResultSet loRS = poGrider.executeQuery(lsSQL);
        
        try {
            JSONObject loData = new JSONObject();
            
            if (loRS.next()){                    
                loData.put("sTempTNox", loRS.getString("sTransNox"));
                loData.put("dTransact", SQLUtil.dateFormat(loRS.getDate("dTransact"), SQLUtil.FORMAT_SHORT_DATE));
                loData.put("sSourceNo", loRS.getString("sSourceNo"));
                loData.put("sSourceCD", loRS.getString("sSourceCd"));
                loData.put("sRqstType", loRS.getString("sRqstType"));
                loData.put("sReqstdBy", loRS.getString("sReqstdBy"));
                loData.put("sReqstdTo", loRS.getString("sReqstdTo"));
                loData.put("sReqstInf", loRS.getString("sReqstInf"));                
                loData.put("cApprType", fsApprType);                
                
                loData = TokenRestAPI.UploadCodeRequest(poGrider, loData);
                
                if (!"success".equalsIgnoreCase((String) loData.get("result"))){
                    loData = (JSONObject) loData.get("error");
                    psMessagex = (String) loData.get("message");                    
                    return "";
                }
                
                lsSQL = "UPDATE Tokenized_Approval_Request SET" + 
                            "  sTransNox = " + SQLUtil.toSQL((String) loData.get("sTransNox")) + 
                            ", cApprType = " + SQLUtil.toSQL(fsApprType) +
                            ", cSendxxxx = '1'" + 
                            ", dSendDate = " + SQLUtil.toSQL(poGrider.getServerDate()) +
                            ", sModified = " + SQLUtil.toSQL(poGrider.getUserID()) +
                            ", dModified = " + SQLUtil.toSQL(poGrider.getServerDate()) + 
                        " WHERE sTransNox = " + SQLUtil.toSQL(loRS.getString("sTransNox"));
                
                poGrider.beginTrans();
                
                if (poGrider.executeUpdate(lsSQL) <= 0){
                    poGrider.rollbackTrans();
                    psMessagex = poGrider.getErrMsg() + "\n" + poGrider.getMessage();
                    return "";
                }
                
                poGrider.commitTrans();
            }
            
            psMessagex = "Request has been uploaded successfully.";
            return (String) loData.get("sTransNox");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            psMessagex = ex.getMessage();
            return "";
        }
    }
    
    @Override
    public boolean replyCodeRequest(String fsTransNox, String fsTranStat, String fsApprType) {        
        if (fsTranStat.equals("1") && fsApprType.isEmpty()){
            psMessagex = "APPROVAL TYPE must not be empty if the request was APPROVED.";
            return false;
        }
        
        JSONObject loJSON = new JSONObject();
        loJSON.put("sTransNox", fsTransNox);
        loJSON.put("cTranStat", fsTranStat);
        loJSON.put("cApprType", fsApprType);
        
        loJSON = TokenRestAPI.ReplyCodeRequest(poGrider, loJSON);
        
        if (!"success".equals((String) loJSON.get("result"))){
            loJSON = (JSONObject) loJSON.get("error");
            psMessagex = (String) loJSON.get("message");
            return false;
        }        
        
        switch (fsTranStat){
            case "1":
                psMessagex = "Request APPROVAL was sent successfully.";
                break;
            case "2":
                psMessagex = "Request POSTING was sent successfully.";
                break;
            case "3":
                psMessagex = "Request DISAPPROVAL was sent successfully.";
                break;
            case "4":
                psMessagex = "Request VOID was sent successfully.";
                break;
            default:
                psMessagex = "";
        }
        
        return true;
    }
    
    @Override
    public int getWeight2Apprv() {
        String lsSQL;
        ResultSet loRS;
        JSONObject loJSON = isValidTransaction();
        
        if (!"success".equals((String) loJSON.get("result"))){
            loJSON = (JSONObject) loJSON.get("error");
            psMessagex = (String) loJSON.get("message");
            return -1;
        }
        
        //get the level of purchase amount
        lsSQL = "SELECT nAuthEqlx" +
                " FROM Purchase_Level" +
                " WHERE nAmntThru >= " + (double) loJSON.get("nTranTotl") + 
                " ORDER BY nAmntThru" +
                " LIMIT 1";
        
        loRS = poGrider.executeQuery(lsSQL);
        
        try {
            //no record was selected
            if (!loRS.next()){
                psMessagex = "Purchase amount reached the maximum level or no purchase level set.";
                return -1;
            }
            
            return loRS.getInt("nAuthEqlx");
        } catch (SQLException ex) {
            ex.printStackTrace();
            psMessagex = ex.getMessage();
            return -1;
        }
    }
    
    private JSONArray checkAuthorized(JSONObject foJSON) {        
        int lnLevel;
        String lsSQL;
        ResultSet loRS;
        
        //get the level of purchase amount
        lsSQL = "SELECT nLevelxxx" +
                " FROM Purchase_Level" +
                " WHERE nAmntThru >= " + (double) foJSON.get("nTranTotl") + 
                " ORDER BY nAmntThru" +
                " LIMIT 1";
        
        loRS = poGrider.executeQuery(lsSQL);
        
        try {
            //no record was selected
            if (!loRS.next()){
                psMessagex = "Purchase amount reached the maximum level or no purchase level set.";
                return null;
            }
            
            lnLevel = loRS.getInt("nLevelxxx");
        } catch (SQLException ex) {
            ex.printStackTrace();
            psMessagex = ex.getMessage();
            return null;
        }
        
        JSONObject loJSON = new JSONObject();
        loJSON.put("sRqstType", (String) foJSON.get("sRqstType"));
        loJSON.put("nLevelxxx", lnLevel);
        
        loJSON = TokenRestAPI.RequestAuthorized(poGrider, loJSON);
        
        if ("success".equalsIgnoreCase((String) loJSON.get("result"))){
            return (JSONArray) loJSON.get("detail");
        } else{
            loJSON = (JSONObject) loJSON.get("error");
            
            psMessagex = (String) loJSON.get("message");            
        }
        
        return null;
    }

    @Override
    public String getRequestType() {
        return !pbRndAprvl ? psRqstType : psRqstRndm;
    }
    
    @Override
    public String getMessage() {
        return psMessagex;
    }
    
    //added methods
    private JSONObject isValidTransaction(){              
        String lsSQL = "SELECT" +
                            "  dTransact" +
                            ", nTranTotl" +
                            ", sRemarksx" +
                            ", cTranStat" +
                        " FROM " + psTableNme +
                        " WHERE sTransNox = " + SQLUtil.toSQL(psTransNox);
        
        ResultSet loRS = poGrider.executeQuery(lsSQL);
        
        try {
            if (!loRS.next()){
                psMessagex = "No record found.";
                return null;
            }
            
            if (!loRS.getString("cTranStat").equals("0")){
                psMessagex = "Transaction is already APPROVED/POSTED/CANCELLED/VOID. \n\n" +
                            "Unable to create request.";
                return null;
            }
            
            JSONObject loJSON = new JSONObject();
            loJSON.put("result", "success");
            loJSON.put("dTransact", SQLUtil.dateFormat(loRS.getDate("dTransact"), SQLUtil.FORMAT_SHORT_DATE));
            loJSON.put("nTranTotl", loRS.getDouble("nTranTotl"));
            loJSON.put("sRemarksx", loRS.getString("sRemarksx"));

            return loJSON;
        } catch (SQLException ex) {
            ex.printStackTrace();
            psMessagex = ex.getMessage();
            return null;
        }
    }
}