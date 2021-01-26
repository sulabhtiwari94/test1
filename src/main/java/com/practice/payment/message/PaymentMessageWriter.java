/**
 *
 */
package com.practice.payment.message;

import java.util.Calendar;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prowidesoftware.swift.model.BIC;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field50A;
import com.prowidesoftware.swift.model.field.Field59A;
import com.prowidesoftware.swift.model.field.Field71A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class PaymentMessageWriter {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMessageWriter.class);

	public String createMessage() {

		final MT103 message = new MT103();
		message.setSender(new BIC("BBKBBK12345"));
		message.setReceiver(new BIC("CITXXXXXXXX"));
		message.addField(new Field20("REFERENCE"));
		message.addField(new Field23B("CRED"));

		final Field32A field32a = new Field32A();
		field32a.setDate(Calendar.getInstance()).setCurrency(Currency.getInstance("EUR")).setAmount("100");
		message.addField(field32a);
		final Field50A field50a = new Field50A();
		field50a.setAccount("123456");
		field50a.setBIC(new BIC("BBKCXXXXXXX"));
		message.addField(field50a);
		final Field59A field59a = new Field59A();
		field59a.setBIC(new BIC("CITYXXXXXXX"));
		field59a.setAccount("98088");
		message.addField(field59a);
		message.addField(new Field71A("OUR"));
		LOGGER.info(message.message());
		return message.message();
	}

}
