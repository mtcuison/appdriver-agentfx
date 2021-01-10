package org.rmj.appdriver.agentfx.service;

/**
 *
 * @author Michael Cuison
 *      2020.12.03 Started creating this object.
 */
public class TokenApprovalFactory {
    public enum TABLE_NAME{
        PO_Master
    }
    
    public enum NOTIFICATION{
        ANDROID,
        MASKING,
        POSTPAID
    }
    
    public enum APPROVAL_TYPE{
        SYSTEM_APPROVAL,
        SMS_APPROVAL,
        API_APPROVAL
    }
    
    public static ITokenize make(String fsTableName){
        switch(fsTableName){
            case "CASys_DBF.PO_Master":
                return new PO_Master();
            default:
                return null;
        }
    }
}
