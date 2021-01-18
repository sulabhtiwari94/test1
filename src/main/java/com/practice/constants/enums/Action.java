/**
 *
 */
package com.practice.constants.enums;

/**
 * @author sulabhtiwari
 *
 */
public enum Action {

	INSERT("INSERT", "inserted"), UPDATE("UPDATE", "updated"), DELETE("DELETE", "deleted");

	private final String action;
	private final String actionPerformed;

	private Action(String action, String actionPerformed) {
		this.action = action;
		this.actionPerformed = actionPerformed;

	}

	public String getMessageCode() {
		return action;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}
}
