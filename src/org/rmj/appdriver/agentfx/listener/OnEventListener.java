package org.rmj.appdriver.agentfx.listener;

import org.json.simple.JSONObject;

public interface OnEventListener {
    public void onSuccess(JSONObject success);
    public void onFailure(JSONObject failure);
}
