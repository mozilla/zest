/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.zest.core.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ZestRequest.
 */
public class ZestRequest extends ZestStatement {

	/** The url. */
	private URL url;
	
	/** The url token. */
	private String urlToken;	// String version of the URL including tokens - as these wont be a valid urls
	
	/** The data. */
	private String data;	// TODO this dependant on protocol, eg http / ws / ??
	
	/** The method. */
	private String method;
	
	/** The headers. */
	private String headers;
	
	/** The response. */
	private ZestResponse response;
	
	/** The assertions. */
	private List<ZestAssertion> assertions = new ArrayList<ZestAssertion>();
	
	/** If true follow redirects, otherwise do not */
	private boolean followRedirects = true;
	
	/**
	 * Instantiates a new zest request.
	 *
	 * @param index the index
	 */
	public ZestRequest(int index) {
		super(index);
	}
	
	/**
	 * Instantiates a new zest request.
	 */
	public ZestRequest() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#deepCopy()
	 */
	@Override
	public ZestRequest deepCopy () {
		ZestRequest zr = new ZestRequest(this.getIndex());
		zr.setUrl(this.url);
		zr.setUrlToken(this.urlToken);
		zr.setData(this.data);
		zr.setMethod(this.method);
		zr.setHeaders(this.headers);
		zr.setFollowRedirects(this.followRedirects);
		
		if (this.getResponse()!= null) {
			zr.setResponse(this.getResponse().deepCopy());
		}
		for (ZestAssertion zt : this.getAssertions()) {
			zr.addAssertion((ZestAssertion)zt.deepCopy());
		}
		return zr;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		if (url == null && urlToken != null) {
			try {
				// We're assuming that the token has been replaced by this time ;)
				return new URL(this.urlToken);
			} catch (MalformedURLException e) {
				// Ignore - assume it includes an unexpanded token
			}
		}
		return url;
	}
	
	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
	/**
	 * Gets the url token.
	 *
	 * @return the url token
	 */
	public String getUrlToken() {
		return urlToken;
	}
	
	/**
	 * Sets the url token.
	 *
	 * @param urlToken the new url token
	 */
	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public String getHeaders() {
		return headers;
	}
	
	/**
	 * Sets the headers.
	 *
	 * @param headers the new headers
	 */
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	
	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * Adds the assertion.
	 *
	 * @param assertion the assertion
	 */
	public void addAssertion (ZestAssertion assertion) {
		this.assertions.add(assertion);
	}
	
	/**
	 * Removes the assertion.
	 *
	 * @param assertion the assertion
	 */
	public void removeAssertion (ZestAssertion assertion) {
		this.assertions.remove(assertion);
	}
	
	/**
	 * Gets the assertions.
	 *
	 * @return the assertions
	 */
	public List<ZestAssertion> getAssertions() {
		return assertions;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public ZestResponse getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(ZestResponse response) {
		this.response = response;
	}
	
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	/**
	 * Move up.
	 *
	 * @param ze the ze
	 */
	public void moveUp (ZestElement ze) {
		int i;
		if (ze instanceof ZestAssertion) {
			ZestAssertion za = (ZestAssertion) ze;
			i = this.assertions.indexOf(za);
			if (i > 0) {
				this.assertions.remove(za);
				this.assertions.add(i-1, za);
			}
		} else {
			throw new IllegalArgumentException("Unrecognised element class: " + ze.getClass().getCanonicalName());
		}
	}

	/**
	 * Move down.
	 *
	 * @param ze the ze
	 */
	public void moveDown (ZestElement ze) {
		int i;
		if (ze instanceof ZestAssertion) {
			ZestAssertion za = (ZestAssertion) ze;
			i = this.assertions.indexOf(za);
			if (i >= 0 && i < this.assertions.size()) {
				this.assertions.remove(za);
				this.assertions.add(i+1, za);
			}
		} else {
			throw new IllegalArgumentException("Unrecognised element class: " + ze.getClass().getCanonicalName());
		}
	}

	/**
	 * Replace tokens.
	 *
	 * @param tokens the tokens
	 */
	public void replaceTokens(ZestVariables tokens) {
		if (this.url != null) {
			try {
				this.setUrl(new URL(tokens.replaceInString(this.url.toString(), false)));	// TODO Work in progress
			} catch (MalformedURLException e) {
				// Ignore
			}
		} else if (this.urlToken != null) {
			this.setUrlToken(tokens.replaceInString(this.urlToken, false));	// TODO Work in progress
			try {
				this.setUrl(new URL(this.getUrlToken()));
			} catch (MalformedURLException e) {
				// Ignore
			}
		}
		this.setMethod(tokens.replaceInString(this.getMethod(), false));
		this.setHeaders(tokens.replaceInString(this.getHeaders(), false));
		this.setData(tokens.replaceInString(this.getData(), false));
	}
	
	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#isSameSubclass(org.mozilla.zest.core.v1.ZestElement)
	 */
	@Override
	public boolean isSameSubclass(ZestElement ze) {
		return ze instanceof ZestRequest;
	}
	
	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#setPrefix(java.lang.String, java.lang.String)
	 */
	@Override
	public void setPrefix(String oldPrefix, String newPrefix) throws MalformedURLException {
		if (this.getUrl() != null && this.getUrl().toString().startsWith(oldPrefix)) {
			String urlStr = newPrefix + getUrl().toString().substring(oldPrefix.length());
			this.setUrl(new URL(urlStr));
		} else {
			throw new IllegalArgumentException("Request url " + getUrl() + " does not start with " + oldPrefix);
		}
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#isPassive()
	 */
	@Override
	public boolean isPassive() {
		return false;
	}

}
