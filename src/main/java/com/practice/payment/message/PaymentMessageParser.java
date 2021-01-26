/**
 *
 */
package com.practice.payment.message;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class PaymentMessageParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMessageWriter.class);

	public MT103 parseMessage(final String message) {
		MT103 mt = null;
		AbstractMT msg = null;
		try {
			msg = AbstractMT.parse(message);
			if (msg.isType(103)) {
				mt = (MT103) msg;
			}
		} catch (final IOException e) {
			LOGGER.error(e.getMessage());
		}
		return mt;

	}

}
