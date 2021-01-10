package org.rmj.appdriver.agentfx.thread;
import java.io.IOException;
import org.rmj.appdriver.agentfx.listener.OnEventListener;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GProperty;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.agent.GRiderX;

public class MySQLConnectionThread extends Thread{    
    public MySQLConnectionThread(String fsProdctID, OnEventListener foCallBack, int fnTimeOut){
        psProdctID = fsProdctID;
        mCallBack = foCallBack;
        pnTimeOut = fnTimeOut;
        bRun = false;
        
        getConfig();
    }    
    
    @Override
    public void run(){
        Connection loConn;
        JSONObject loJSON = new JSONObject();
        bRun = true;
        
        try {
            while (bRun){
                loJSON = new JSONObject();
                
                loConn = MiscUtil.getConnection(psDBSrvrNm, psDBNameXX, psDBUserNm, psDBPassWD, psDBPortNo);
                
                if (loConn == null){
                    loJSON.put("code", "-1");
                    loJSON.put("message", "MySQL connection time out...");
                    mCallBack.onFailure(loJSON);    
                } else{
                    loJSON.put("message", "MySQL connection is alive...");
                    mCallBack.onSuccess(loJSON);
                    MiscUtil.close(loConn);
                }
                
                if (pnTimeOut > 0)
                    Thread.sleep(pnTimeOut);
            }
        } catch (ClassNotFoundException | InterruptedException | SQLException ex) {
            loJSON = new JSONObject();
            loJSON.put("code", "ex001");
            loJSON.put("message", ex.getMessage());
            mCallBack.onFailure(loJSON);
        }
    }
    
    public void End(){
        bRun = false;
        System.out.println("The thread was forced stop.");
    }
    
    public boolean isRunning(){
        return bRun;
    }
    
    private void getConfig(){
        //connect to the application driver
        oApp = new GRiderX("gRider");

        //get configuration
       GProperty loGProp = new GProperty("GhostRiderXP");

       psDBSrvrMn = loGProp.getConfig(psProdctID + "-MainServer");
       psDBSrvrNm = loGProp.getConfig(psProdctID + "-ServerName");
       psDBNameXX = loGProp.getConfig(psProdctID + "-Database");;
       psDBPassWD = oApp.Decrypt(loGProp.getConfig(psProdctID + "-Password"));
       psDBUserNm = oApp.Decrypt(loGProp.getConfig(psProdctID + "-UserName"));
       psDBPortNo = loGProp.getConfig(psProdctID + "-Port");        
    }
        
    private GRiderX oApp;
    private OnEventListener mCallBack;
    private boolean bRun;
    private String psProdctID;
    
    //Database config variables
    private String psDBSrvrMn; //Main Server
    private String psDBSrvrNm; //Server Name
    private String psDBNameXX; //Database Name
    private String psDBPassWD; //Database Password
    private String psDBUserNm; //Database Username
    private String psDBPortNo; //Port Number
    
    private int pnTimeOut = 0;
}
