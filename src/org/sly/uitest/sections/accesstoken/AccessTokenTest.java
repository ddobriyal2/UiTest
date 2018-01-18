package org.sly.uitest.sections.accesstoken;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Lynne Huang
 * @date : 19 Oct, 2015
 * @company Prive Financial
 */
public class AccessTokenTest extends AbstractPage {

	@Test
	public void testValidAccessTokenPage() throws InterruptedException {

		try {

			webDriver.get(
					"http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#invAccountList;token=qPTdfweRT");

		} catch (org.openqa.selenium.TimeoutException e) {
			assertTrue(pageContainsStr("Investments"));
		}

	}

	@Test
	public void testInvalidAccessTokenPage() throws InterruptedException {

		try {

			webDriver.get("http://192.168.1.107:8080/SlyAWS/?locale=en#investmentList;token=NotValidToken");

		} catch (org.openqa.selenium.TimeoutException e) {
			assertTrue(pageContainsStr("Error"));
			assertTrue(pageContainsStr("Invalid access token."));
		}

	}

	/**
	 * Test for changing csv to xml
	 */
	@Test
	public void testCsvToXml() {
		List<String> headers = new ArrayList<String>(5);

		File file = new File("/home/bleung/Desktop/abc.csv");
		BufferedReader reader = null;

		try {

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

			Document newDoc = domBuilder.newDocument();
			// Root element
			Element rootElement = newDoc.createElement("XMLCreators");
			newDoc.appendChild(rootElement);

			reader = new BufferedReader(new FileReader(file));
			int line = 0;

			String text = null;
			while ((text = reader.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(text, ",", false);
				String[] rowValues = new String[st.countTokens()];
				int index = 0;
				while (st.hasMoreTokens()) {

					String next = st.nextToken();
					rowValues[index++] = next;

				}
				for (String aa : rowValues) {
					log(aa);
				}
				// String[] rowValues = text.split(",");

				if (line == 0) { // Header row
					for (String col : rowValues) {
						headers.add(col);
					}
				} else { // Data row
					Element rowElement = newDoc.createElement("row");
					rootElement.appendChild(rowElement);
					for (int col = 0; col < headers.size(); col++) {
						String header = headers.get(col);
						String value = null;

						if (col < rowValues.length) {
							value = rowValues[col];
						} else {
							// ?? Default value
							value = "";
						}

						Element curElement = newDoc.createElement(header);
						curElement.appendChild(newDoc.createTextNode(value));
						rowElement.appendChild(curElement);
					}
				}
				line++;
			}

			ByteArrayOutputStream baos = null;
			OutputStreamWriter osw = null;

			try {
				baos = new ByteArrayOutputStream();
				osw = new OutputStreamWriter(baos);

				TransformerFactory tranFactory = TransformerFactory.newInstance();
				Transformer aTransformer = tranFactory.newTransformer();
				aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
				aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
				aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				Source src = new DOMSource(newDoc);
				Result result = new StreamResult(osw);
				aTransformer.transform(src, result);

				osw.flush();
				System.out.println(new String(baos.toByteArray()));
			} catch (Exception exp) {
				exp.printStackTrace();
			} finally {
				try {
					osw.close();
				} catch (Exception e) {
				}
				try {
					baos.close();
				} catch (Exception e) {
				}
				try {
					FileOutputStream out = new FileOutputStream("/home/bleung/Desktop/test.xml");
					out.write(baos.toByteArray());
					out.close();
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
