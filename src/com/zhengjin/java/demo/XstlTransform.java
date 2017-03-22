package com.zhengjin.java.demo;

import java.io.File;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public final class XstlTransform {

	private XstlTransform() {
	}

	public static String XmlXstlHtml(String xmlFileName, String xslFileName,
			String htmlFileName) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		StreamSource source = new StreamSource(new File(xslFileName));
		Transformer tx = factory.newTransformer(source);

		Properties properties = tx.getOutputProperties();
		properties.setProperty(OutputKeys.ENCODING, "UTF-8");
		properties.setProperty(OutputKeys.METHOD, "html");
		tx.setOutputProperties(properties);

		StreamSource xmlSource = new StreamSource(new File(xmlFileName));
		File targetFile = new File(htmlFileName);
		StreamResult result = new StreamResult(targetFile);

		tx.transform(xmlSource, result);

		return targetFile.getAbsolutePath();
	}

}
