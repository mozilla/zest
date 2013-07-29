/**
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * @author Alessandro Secco: seccoale@gmail.com
 */
package org.mozilla.zest.core.v1;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class ZestLoop.
 *
 * @param <T> the generic type
 */
public class ZestLoop<T> extends ZestStatement implements ZestContainer, Enumeration<ZestStatement> {
	
	/** contains all the statement inside the loop. */
	private List<ZestStatement> statements;
	
	/** contains the snapshot of the current state of the loop. */
	private ZestLoopState<T> currentState;
	/**
	 * contains the index of the current statement considered.
	 */
	private int stmtIndex=0;
	
	/**
	 * Instantiates a new zest loop.
	 */
	protected ZestLoop(){
		this(null, new LinkedList<ZestStatement>());
	}
	
	/**
	 * Instantiates a new zest loop.
	 *
	 * @param initializationState the initialization state
	 */
	protected ZestLoop(ZestLoopState<T> initializationState){
		this(initializationState, new LinkedList<ZestStatement>());
	}
	
	/**
	 * Instantiates a new zest loop.
	 *
	 * @param initializationState the initialization state
	 * @param stmts the stmts
	 */
	protected ZestLoop(ZestLoopState<T> initializationState, List<ZestStatement> stmts){
		super();
		this.setState(initializationState);
		this.statements=stmts;
	}

/**
 * Main construptor with the initialization state.
 *
 * @param index the index
 * @param initializationState the initialization state (first value and the set of values)
 */
	protected ZestLoop(int index, ZestLoopState<T> initializationState) {
		this(index, initializationState, new LinkedList<ZestStatement>());
	}

/**
 * Construptor with initialization state and the list of statement inside the loop.
 *
 * @param index the index
 * @param initializationState the initialization state (first value and the set of values)
 * @param statements all the statements inside the loop
 */
	protected ZestLoop(int index, ZestLoopState<T> initializationState,
			List<ZestStatement> statements) {
		super(index);
		this.currentState = initializationState;
		this.statements = statements;
	}

/**
 * protected empty method for subclasses.
 *
 * @param index the index
 */
	protected ZestLoop(int index) {
		this(index, null, new LinkedList<ZestStatement>());
	}

/**
 * sets the current state to the new one (for subclasses).
 *
 * @param newState the new state
 */
	protected void setState(ZestLoopState<T> newState) {
		this.currentState = newState;
	}
	
	/**
	 * Sets the statements.
	 *
	 * @param stmts the stmts
	 * @return the list
	 */
	protected List<ZestStatement> setStatements(List<ZestStatement> stmts){
		List<ZestStatement> oldStatements=this.statements;
		this.statements=stmts;
		return oldStatements;
	}

/**
 * increase the current state (all the statements are compiuted for this loop, lets start a new one).
 *
 * @return the new state (of the following loop)
 */
	public boolean loop() {
		return this.currentState.increase();
	}

/**
 * ends the loops and set the state to the final value.
 */
	public void endLoop() {
		this.currentState.toLastState();
	}

/**
 * adds a new statement inside the loop.
 *
 * @param stmt the new statement to add
 */
	public void addStatement(ZestStatement stmt) {
		statements.add(stmt);
	}

/**
 * returns the current state of the loop.
 *
 * @return the current state
 */
	public ZestLoopState<T> getCurrentState() {
		return this.currentState;
	}

/**
 * return the current token considered inside the loop.
 *
 * @return the current token considered inside the loop
 */
	public T getCurrentToken() {
		return this.currentState.getCurrentToken();
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestContainer#getLast()
	 */
	@Override
	public ZestStatement getLast() {
		if (statements == null || statements.isEmpty()) {
			return this;
		}
		return statements.get(statements.size() - 1);
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestContainer#getStatement(int)
	 */
	@Override
	public ZestStatement getStatement(int index) {
		for (ZestStatement zr : this.statements) {
			if (zr.getIndex() == index) {
				return zr;
			}
			if (zr instanceof ZestContainer) {
				ZestStatement stmt = ((ZestContainer) zr).getStatement(index);
				if (stmt != null) {
					return stmt;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestContainer#getIndex(org.mozilla.zest.core.v1.ZestStatement)
	 */
	@Override
	public int getIndex(ZestStatement child) {
		if (statements.contains(child)) {
			return statements.indexOf(child);
		} else {
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestContainer#move(int, org.mozilla.zest.core.v1.ZestStatement)
	 */
	@Override
	public void move(int index, ZestStatement stmt) {
		if (this.statements.contains(stmt)) {
			this.statements.remove(stmt);
			this.statements.add(index, stmt);
		} else {
			throw new IllegalArgumentException("Not a direct child: " + stmt);
		}
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#isSameSubclass(org.mozilla.zest.core.v1.ZestElement)
	 */
	@Override
	public boolean isSameSubclass(ZestElement ze) {
		return ze instanceof ZestLoop<?>;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestContainer#getChildBefore(org.mozilla.zest.core.v1.ZestStatement)
	 */
	@Override
	public ZestStatement getChildBefore(ZestStatement child) {
		if (this.statements.contains(child)) {
			int childIndex = this.statements.indexOf(child);
			if (childIndex > 1) {
				return this.statements.get(childIndex - 1);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#getTokens(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> getTokens(String tokenStart, String tokenEnd) {
		Set<String> tokens = new HashSet<String>();
		for (ZestStatement stmt : this.statements) {
			tokens.addAll(stmt.getTokens(tokenStart, tokenEnd));
		}
		return tokens;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#setPrefix(java.lang.String, java.lang.String)
	 */
	@Override
	public void setPrefix(String oldPrefix, String newPrefix)
			throws MalformedURLException {
		for (ZestStatement stmt : this.statements) {
			stmt.setPrefix(oldPrefix, newPrefix);
		}
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#setUpRefs(org.mozilla.zest.core.v1.ZestScript)
	 */
	@Override
	void setUpRefs(ZestScript script) {
		for (ZestStatement stmt : this.statements) {
			stmt.setUpRefs(script);
		}
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#getTransformations()
	 */
	@Override
	public List<ZestTransformation> getTransformations() {
		List<ZestTransformation> xforms = new ArrayList<ZestTransformation>();
		for (ZestStatement stmt : this.statements) {
			xforms.addAll(stmt.getTransformations());
		}
		return xforms;
	}

	/* (non-Javadoc)
	 * @see org.mozilla.zest.core.v1.ZestStatement#deepCopy()
	 */
	@Override
	public ZestLoop<T> deepCopy() {
		ZestLoop<T> copy = new ZestLoop<>(this.getIndex());
		copy.currentState = this.currentState.deepCopy();
		if (this.statements == null) {
			return copy;
		}
		copy.statements = new LinkedList<>();
		for (int i = 0; i < this.statements.size(); i++) {
			copy.statements.add(this.statements.get(i).deepCopy());
		}
		return copy;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Enumeration#hasMoreElements()
	 */
	@Override
	public boolean hasMoreElements() {
		boolean isLastLoop=this.getCurrentState().isLastState();
		boolean isLastStmt=this.stmtIndex==statements.size()-1;
		boolean isBreakStatement=this.statements.get(stmtIndex).getClass().equals(ZestLoopBreak.class);
		return !isLastLoop && !isLastStmt && !isBreakStatement;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Enumeration#nextElement()
	 */
	@Override
	public ZestStatement nextElement() {
		++stmtIndex;
		if(stmtIndex==statements.size()){
			this.currentState.increase();
			stmtIndex=0;
		}
		ZestStatement newStatement=statements.get(stmtIndex);
		if(newStatement instanceof ZestLoopBreak){
			this.currentState.toLastState();
			this.stmtIndex=statements.size();
			return null;
		}
		else if(newStatement instanceof ZestLoopNext){
			this.currentState.increase();
			this.stmtIndex=0;
		}
		return statements.get(stmtIndex);
	}
	
	/**
	 * Equals.
	 *
	 * @param otherLoop the other loop
	 * @return true, if successful
	 */
	public boolean equals(ZestLoop<T> otherLoop){
		return this.currentState.equals(otherLoop.currentState) && this.statements.equals(otherLoop.statements) && this.stmtIndex==otherLoop.stmtIndex;
	}
}
