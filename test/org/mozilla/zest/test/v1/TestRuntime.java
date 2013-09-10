package org.mozilla.zest.test.v1;

import javax.script.ScriptEngineFactory;

import org.mozilla.zest.core.v1.ZestRequest;
import org.mozilla.zest.core.v1.ZestResponse;
import org.mozilla.zest.core.v1.ZestRuntime;
import org.mozilla.zest.core.v1.ZestVariables;
import org.mozilla.zest.impl.ZestScriptEngineFactory;

public class TestRuntime implements ZestRuntime{
	private ZestRequest request;
	private ZestResponse response;
	private ZestVariables vars = new ZestVariables();
	
	public TestRuntime() {
	}

	public TestRuntime(ZestRequest req) {
		this(req, null);
	}


	public TestRuntime(ZestResponse resp) {
		this(null, resp);
	}

	public TestRuntime(ZestRequest req, ZestResponse resp) {
		this.request = req;
		this.response = resp;
		this.setStandardVariables(req);
		this.setStandardVariables(resp);
	}

	@Override
	public String getVariable(String name) {
		return vars.getVariable(name);
	}

	@Override
	public void setVariable(String name, String value) {
		vars.setVariable(name, value);
	}

	@Override
	public ZestResponse getLastResponse() {
		return response;
	}

	@Override
	public String replaceVariablesInString(String str, boolean urlEncode) {
		return vars.replaceInString(str, urlEncode);
	}

	@Override
	public void output(String str) {
		System.out.println(str);
	}

	@Override
	public ZestRequest getLastRequest() {
		return this.request;
	}

	@Override
	public ScriptEngineFactory getScriptEngineFactory() {
		return  new ZestScriptEngineFactory();
	}

	@Override
	public void setStandardVariables(ZestRequest request) {
		if (request != null) {
			if (request.getUrl()!= null) {
				this.setVariable(ZestVariables.REQUEST_URL, request.getUrl().toString());
			}
			this.setVariable(ZestVariables.REQUEST_HEADER, request.getHeaders());
			this.setVariable(ZestVariables.REQUEST_METHOD, request.getMethod());
			this.setVariable(ZestVariables.REQUEST_BODY, request.getData());
		}
	}

	@Override
	public void setStandardVariables(ZestResponse response) {
		if (response != null) {
			if (response.getUrl() != null) {
				this.setVariable(ZestVariables.RESPONSE_URL, response.getUrl().toString());
			}
			this.setVariable(ZestVariables.RESPONSE_HEADER, response.getHeaders());
			this.setVariable(ZestVariables.RESPONSE_BODY, response.getBody());
		}
	}
}
