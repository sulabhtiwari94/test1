/**
 *
 */
package com.practice.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class AsyncUtil {

	@Async
	public void print(String print) {
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(print);
	}
}
