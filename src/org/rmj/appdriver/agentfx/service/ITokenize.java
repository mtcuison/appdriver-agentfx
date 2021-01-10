package org.rmj.appdriver.agentfx.service;

import org.json.simple.JSONArray;
import org.rmj.appdriver.GRider;

/**
 *
 * @author Michael Cuison
 *      2020.12.03
 */
public interface ITokenize {
    public void setGRider(GRider foValue);
    public void setTransNmbr(String fsValue);
    public void isRndmAprvlx(boolean fbVablue);
    
    public boolean createCodeRequest();
    public String uploadCodeRequest(String fsTransNox, String fsApprType);
    public boolean replyCodeRequest(String fsTransNox, String fsTranStat, String fsApprType);
    public JSONArray loadCodeRequest();
    
    public String getRequestType();
    public String getMessage();
    public int getWeight2Apprv();
}
