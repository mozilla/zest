/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.zest.core.v1;


// TODO: Auto-generated Javadoc
/**
 * The Class ZestActionFail.
 */
public class ZestActionFail extends ZestAction {
	
	/**
	 * The Enum Priority.
	 */
	public enum Priority {/** The info. */
INFO, /** The low. */
 LOW, /** The medium. */
 MEDIUM, /** The high. */
 HIGH};

	/** The message. */
	private String message;
	
	/** The priority. */
	private String priority;
	
	/**
	 * Instantiates a new zest action fail.
	 */
	public ZestActionFail() {
		super();
	}

	/**
	 * Instantiates a new zest action fail.
	 *
	 * @param index the index
	 */
	public ZestActionFail(int index) {
		super(index);
	}

	/**
	 * Instantiates a new zest action fail.
	 *
	 * @param message the message
	 */
	public ZestActionFail(String message) {
		super();
		this.message = message;
	}

	/**
	 * Instantiates a new zest action fail.
	 *
	 * @param message the message
	 * @param priority the priority
	 */
	public ZestActionFail(String message, String priority) {
		super();
		this.message = message;
		this.setPriority(priority);
	}

	/**
	 * Instantiates a new zest action fail.
	 *
	 * @param message the message
	 * @param priority the priority
	 */
	public ZestActionFail(String message, Priority priority) {
		super();
		this.message = message;
		this.setPriority(priority);
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestAction#isSameSubclass(org.mozilla.zest.core.v1.ZestElement)
	 */
	@Override
	public boolean isSameSubclass(ZestElement ze) {
		return ze instanceof ZestActionFail;
	}
	
	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestAction#invoke(org.mozilla.zest.core.v1.ZestResponse)
	 */
	public String invoke(ZestResponse response) throws ZestActionFailException {
		throw new ZestActionFailException(this, this.message);
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Priority priority) {
		this.priority = priority.name();
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(String priority) {
		try {
			Priority.valueOf(priority);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unsupported priority: " + priority);
		}
		this.priority = priority;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#deepCopy()
	 */
	@Override
	public ZestActionFail deepCopy() {
		ZestActionFail copy = new ZestActionFail(this.getIndex());
		copy.message = message;
		copy.priority = priority;
		return copy;
	}

}
