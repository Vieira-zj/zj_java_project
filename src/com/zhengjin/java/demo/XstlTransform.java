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

	public static String XmlXstlHtml(String xmlFilePath, String xslFilePath,
			String htmlFilePath) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		StreamSource xslSource = new StreamSource(new File(xslFilePath));
		Transformer tx = factory.newTransformer(xslSource);

		Properties properties = tx.getOutputProperties();
		properties.setProperty(OutputKeys.ENCODING, "UTF-8");
		properties.setProperty(OutputKeys.METHOD, "html");
		tx.setOutputProperties(properties);

		StreamSource xmlSource = new StreamSource(new File(xmlFilePath));
		File targetFile = new File(htmlFilePath);
		StreamResult result = new StreamResult(targetFile);

		tx.transform(xmlSource, result);

		return targetFile.getAbsolutePath();
	}

}
