/*package org.sly.uitest.utils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerator;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;

public class addAttachment {

	final public static void main(String[] args) throws URISyntaxException, ParseException, IOException, JSONException {
		final String API_ADD_ATTACHMENT = "{SERVER}/public/rest/api/1.0/attachment";
		

		*//** Declare JIRA,Zephyr URL,access and secret Keys *//*

		final String zephyrBaseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
		// zephyr accessKey , we can get from Addons >> zapi section
		final String accessKey = "cHJpdmVmaW5hbmNpYWwuYXRsYXNzaWFuLm5ldCBnbGF1IHByaXZl";
		// zephyr secretKey , we can get from Addons >> zapi section
		String secretKey = "mM1Y0vEXwntnMmtAuFkMBQPg0QQUCQys4irBkJnJUec";

		*//** Declare parameter values here *//*
		final String userName = "devesh.dobriyal";
		final String comment = "Attachment through ZAPI CLoud";
		final String cycleId = "0";			// Cycle Id of the execution - "-1" for AdHoc Cycle
		final String versionId = "-1";			//Version Id of Execution - '-1" for UNSCHEDULED version
		final String projectId = "12203";		//ProjectId of the execution
		final String issueId = "58747";			//Issue Id of the execution
		final String entityName = "execution"; // entityName takes execution/stepResult as parameter value
		final String entityId = "0001516084843016-242ac112-0001";    //execution Id
		final String fileWithAbsolutePath = "F:\\zfj.PNG";   //Absolute path of the file
		int expirationInSec = 360;
		
		// Add Attachment to a testcase ********DO NOT EDIT ANYTHING BELOW**********
		
		
		final ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName)
				.build();
		
		
		JsonGenerator jwtGenerator = client.getJwtGenerator();

		// Initializes the URL data type with strURL created above
		String attachmentUri = API_ADD_ATTACHMENT.replace("{SERVER}", zephyrBaseUrl) + "?issueId=" + issueId
				+ "&versionId=" + versionId + "&entityName=" + entityName + "&cycleId=" + cycleId + "&entityId="
				+ entityId + "&projectId=" + projectId  + "&comment=comment";
		URI uri = new URI(attachmentUri);

		String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
		System.out.println(uri.toString());
		System.out.println(jwt);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		File file = new File(fileWithAbsolutePath);
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("attachment", new FileBody(file));

		HttpPost addAttachmentReq = new HttpPost(uri);
		addAttachmentReq.addHeader("Authorization", jwt);
		addAttachmentReq.addHeader("zapiAccessKey", accessKey);
		addAttachmentReq.setEntity(entity);

		try {
			response = restClient.execute(addAttachmentReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity1 = response.getEntity();
		int statusCode = response.getStatusLine().getStatusCode();
		// System.out.println(statusCode);
		// System.out.println(response.toString());
		if (statusCode >= 200 && statusCode < 300) {
			System.out.println("Attachment added Successfully");
		} else {
			try {
				String string = null;
				string = EntityUtils.toString(entity1);
				System.out.println(string);
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}

	}
}
*/