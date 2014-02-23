/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONNetworkObject {

	private final String JSON_RPC_VERSION = "2.0";

	private String method = "";
	private HashMap<String, Object> params = new HashMap<String, Object>();

	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public HashMap<String, Object> getParams() {
		return params;
	}
	
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	
	public void addParam(String key, Object value) {
		params.put(key, value);
	}
	
	public void removeParam(String key) {
		params.remove(key);
	}
	
	public JSONObject getJSONObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jsonrpc", JSON_RPC_VERSION);
		jsonObject.put("method", method);
		jsonObject.put("params", params);
		return jsonObject;
	}
}
