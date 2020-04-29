package com.zhengjin.java.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zhengjin.apis.testutils.TestConstants;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUtilsDemo {

	/**
	 * Read lines from file.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public List<String> readLinesFromFile(String filePath) throws IOException {
		InputStream fis = null;
		BufferedReader reader = null;
		try {
			fis = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line;
			List<String> lines = new LinkedList<>();
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					lines.add(line);
				}
			}
			return lines;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2020-04-29")
	public void testReadLinesFromFile() throws IOException {
		String filePath = System.getenv("HOME") + File.separator + "Downloads/tmp_files/test.out";
		List<String> lines = this.readLinesFromFile(filePath);
		System.out.println("file content:");
		for (String line : lines) {
			System.out.println(line);
		}
	}

	/**
	 * Read all bytes from file.
	 * 
	 * @throws IOException
	 */
	@Test
	@TestInfo(author = "zhengjin", date = "2019-05-26")
	public void testReadFromFileV2() throws IOException {
		String filepath = System.getenv("HOME") + File.separator + "Downloads/tmp_files/test_log.txt";
		if (!this.isFileExist(filepath)) {
			throw new FileNotFoundException("file not found: " + filepath);
		}

		byte[] data = Files.readAllBytes(Paths.get(filepath));
		System.out.println("file content:\n" + new String(data, StandardCharsets.UTF_8));
	}

	private boolean isFileExist(String path) {
		return new File(path).exists();
	}

	/**
	 * Append lines to file.
	 */
	private void appendLineToFile(String path, String line) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testAppendToFile() {
		final String filepath = System.getenv("HOME") + File.separator + "Downloads/tmp_files/test_append_file.txt";
		List<String> lines = new ArrayList<>(10);
		for (int i = 0; i < 5; i++) {
			lines.add(String.format("this is a string at %d.\n", i));
		}

		for (String line : lines) {
			this.appendLineToFile(filepath, line);
		}
	}

	/**
	 * Append lines to file v2.
	 * 
	 * @throws IOException
	 */
	@Test
	@TestInfo(author = "zhengjin", date = "2019-05-26")
	public void testAppendToFileV2() throws IOException {
		String filepath = System.getenv("HOME") + File.separator + "Downloads/tmp_files/test_log.txt";
		if (!this.isFileExist(filepath)) {
			throw new FileNotFoundException("file not found: " + filepath);
		}

		String content = "Java append text test.\n";
		Files.write(Paths.get(filepath), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		System.out.println("file append done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testGetCurMethodName() {
		String threadName = Thread.currentThread().getStackTrace()[1].getMethodName();
		assertEquals("testGetCurMethodName", threadName);
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testScreenCapture() {
		String filePath = System.getenv("HOME") + File.separator + "Downloads/tmp_files/screencap.png";
		this.screenCapture(filePath);
	}

	private void screenCapture(String filePath) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);

		try {
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image, "png", new File(filePath));
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testPraseXmlFile() {
		final String filePath = File.separator + TestConstants.TEST_DATA_PATH + "test.xml";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		assertNotNull(db);

		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}

		Document doc = null;
		try {
			doc = db.parse(file);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		assertNotNull(doc);
		Element docEle = doc.getDocumentElement();
		System.out.println("Root element of the document: " + docEle.getNodeName());

		NodeList studentList = docEle.getElementsByTagName("student");
		System.out.println("Total students: " + studentList.getLength());
		assertTrue(studentList != null && studentList.getLength() > 0);

		for (int i = 0, len = studentList.getLength(); i < len; i++) {
			Node node = studentList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("=====================");
				Element e = (Element) node;
				NodeList nodeList = e.getElementsByTagName("name");
				System.out.println("Name: " + nodeList.item(0).getChildNodes().item(0).getNodeValue());
				nodeList = e.getElementsByTagName("grade");
				System.out.println("Grade: " + nodeList.item(0).getChildNodes().item(0).getNodeValue());
				nodeList = e.getElementsByTagName("age");
				System.out.println("Age: " + nodeList.item(0).getChildNodes().item(0).getNodeValue());
			}
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testResizeArray() {
		int[] testArray = { 1, 2, 3 };
		testArray = (int[]) resizeArray(testArray, 5);
		testArray[3] = 4;
		testArray[4] = 5;
		System.out.println(Arrays.toString(testArray));
	}

	private Object resizeArray(Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);

		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		return newArray;
	}

}
