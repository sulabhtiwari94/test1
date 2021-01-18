/**
 *
 */
package com.practice.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulabhtiwari
 *
 */
public class ResponseVO<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private T responseVO;
	private String message;
	private List<T> voList;
	private int successCount;

	public ResponseVO() {
		super();
	}

	/**
	 * @param responseVO
	 * @param message
	 */
	public ResponseVO(T responseVO, String message) {
		super();
		this.responseVO = responseVO;
		this.message = message;
	}

	/**
	 * @return the responseVO
	 */
	public T getResponseVO() {
		return responseVO;
	}

	/**
	 * @param responseVO the responseVO to set
	 */
	public void setResponseVO(T responseVO) {
		this.responseVO = responseVO;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the voList
	 */
	public List<T> getVoList() {
		return voList;
	}

	/**
	 * @param voList the voList to set
	 */
	public void setVoList(List<T> voList) {
		this.voList = voList;
	}

	/**
	 * @return the successCount
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

}
