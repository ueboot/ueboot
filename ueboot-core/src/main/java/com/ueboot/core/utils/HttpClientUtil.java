package com.ueboot.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 使用apache httpclient 4.3以上的版本jar 提供httpclient连接工具类
 *
 * @author yangkui
 */
public class HttpClientUtil {

	private static final String CHARSET = "UTF-8";

	/***
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	/***
	 * 连接管理
	 */
	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	static {
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setBufferSize(1024).setCharset(Charset.forName(CHARSET)).build();
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(30000).build();
		cm.setMaxTotal(500);
		cm.setDefaultMaxPerRoute(500);
		cm.setDefaultSocketConfig(socketConfig);
		cm.setDefaultConnectionConfig(connectionConfig);
	}

	/**
	 * 通过get方式获取指定地址的内容
	 *
	 * @param url     需要访问的地址如：http://www.baidu.com
	 * @param socketTime  链接超时时间
	 * @param connectTimeout  链接超时时间
	 * @param charset 字符编码，将地址返回的内容进行字符编码，如果为空则默认为：UTF-8
	 * @return 地址对应的内容
	 */
	public static String get(String url, int socketTime, int connectTimeout, String charset)
			throws  IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).setConnectionRequestTimeout(30000).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			if (StringUtils.isEmpty(charset)) {
				charset = CHARSET;
			}
			String responseBody = EntityUtils.toString(entity1, charset);
			EntityUtils.consume(entity1);
			StatusLine statusLine = response1.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			logger.info("statusCode:{}", statusCode);
			if (statusCode != 200) {
				writeLog(statusCode, responseBody);
			}
			return responseBody;
		} finally {
			IOUtils.closeQuietly(response1);
		}
	}


	private static void writeLog(int statusCode, String responseBody) throws IOException {
		byte[] contextByte = Base64.encodeBase64(responseBody.getBytes(), true);
		String responseBodyLog = new String(contextByte);
		logger.error("current request url error,satusCode:{},responseBody:{}", statusCode, responseBodyLog);
		throw new IOException("request url statusCode is 500!");
	}

	/**
	 * 使用post方式提交参数
	 *
	 * @param url url
	 * @param params         提交的参数已key,value的形式保存在map当中
	 * @param socketTime 链接超时时间
	 * @param connectTimeout 链接超时时间
	 * @param charset 字符集
	 * @return 返回值
	 * @throws ClientProtocolException ClientProtocolException
	 * @throws IOException IOException
	 */
	public static String post(String url, Map<String, String> params, int socketTime, int connectTimeout,
	                          String charset) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTime <= 0 ? 120000 : socketTime)
				.setConnectTimeout(connectTimeout <= 0 ? 120000 : connectTimeout)
				.setConnectionRequestTimeout(connectTimeout <= 0 ? 120000 : connectTimeout)
				.build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET;
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpPost);
			HttpEntity entity1 = response1.getEntity();
			String responseBody = EntityUtils.toString(entity1, charset);
			EntityUtils.consume(entity1);
			StatusLine statusLine = response1.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			logger.info("statusCode:{}", statusCode);
			if (statusCode != 200) {
				writeLog(statusCode, responseBody);
			}
			return responseBody;
		} finally {
			IOUtils.closeQuietly(response1);
		}
	}

	/**
	 * 使用post格式提交请求一段整体内容，可以是xml或json格式
	 *
	 * @param url            请求路径
	 * @param content        xml报文
	 * @param socketTime     连接时间（单位毫秒）
	 * @param connectTimeout 连接等待时间（单位毫秒）
	 * @param charset        请求报文字符编码，默认为UTF-8
	 * @param requestCharset 返回报文字符编码，默认为UTF-8
	 * @param contentType    http内容类型
	 * @return 返回值
	 * @throws ClientProtocolException ClientProtocolException
	 * @throws IOException IOException
	 */
	public static String post(String url, String content, int socketTime, int connectTimeout, String requestCharset, String charset, String contentType)
			throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(120000)
				.setConnectTimeout(120000)
				.setConnectionRequestTimeout(120000)
				.build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET;
		}
		StringEntity myEntity = new StringEntity(content, charset);
		httpPost.addHeader("Content-Type", contentType);
		httpPost.setEntity(myEntity);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpPost);

			HttpEntity entity1 = response1.getEntity();

			String responseBody = EntityUtils.toString(entity1, charset);

			int statusCode = response1.getStatusLine().getStatusCode();

			logger.info("statusCode:{}", statusCode);

			if (statusCode != 200) {
				writeLog(statusCode, responseBody);
			}
			EntityUtils.consume(entity1);
			return responseBody;
		} finally {
			IOUtils.closeQuietly(response1);
		}
	}

	/**
	 * 使用默认参数发送xml格式内容
	 * 默认：
	 * 1.超时时间为30秒
	 * 2.编码为utf-8
	 * @param content content
	 * @param url  url
	 * @throws IOException IOException
	 * @return 返回值
	 */
	public static String postXml(String url, String content) throws IOException {
		return post(url, content, 30000, 30000, "UTF-8", "UTF-8", "application/xml;charset=UTF-8");
	}

	/**
	 * 使用默认参数发送JSON格式内容
	 * 默认：
	 * 1.超时时间为30秒
	 * 	 2.编码为utf-8
	 * 	 @param content content
	 * 	 @param url  url
	 * 	 @throws IOException IOException
	 * 	 @return 返回值
	 */
	public static String postJSON(String url, String content) throws IOException {
		return post(url, content, 30000, 30000, "UTF-8", "UTF-8", "application/json; charset=UTF-8");
	}

	public static void main(String[] args) throws Exception {
//        StringBuffer sb = new StringBuffer();
//        sb.append("{\"timestamp\":\"201611240835\",\"count\":\"2\",\"winners\":");
//        sb.append("[{\"winBuyerBelongDspId\":\"55\",\"winBuyerId\":\"66\",\"winBuyPrice\":\"3\",");
//        sb.append(" \"winBuyType\":\"2\",\"winStrategyId\":\"111\",\"adType\":\"1\",\"pubPrice\":\"2\",\"time\":\"20161119\",");
//        sb.append("\"adspaceId\":\"3\",\"visitorId\":\"222\",\"realIp\":\"222222\",\"userAgent\":\"3233\",\"browser\":\"32\",");
//        sb.append("\"os\":\"tt\",\"deviceId\":\"\",\"devicetype\":\"\",\"brand\":\"\",\"modelNo\":\"\",\"resolution\":\"\",");
//        sb.append("\"mac\":\"\",\"idfa\":\"\",\"ouid\":\"\",\"vdid\":\"\",\"imei\":\"\",\"anid\":\"\",\"net\":\"\",\"mobileNetworkCoding\":\"\",");
//        sb.append("\"longitude\":\"\",\"latitude\":\"\"},");
//        sb.append("{\"winBuyerBelongDspId\":\"55\",\"winBuyerId\":\"66\",\"winBuyPrice\":\"3\",");
//        sb.append(" \"winBuyType\":\"2\",\"winStrategyId\":\"111\",\"adType\":\"1\",\"pubPrice\":\"2\",\"time\":\"20161119\",");
//        sb.append("\"adspaceId\":\"3\",\"visitorId\":\"222\",\"realIp\":\"222222\",\"userAgent\":\"3233\",\"browser\":\"32\",");
//        sb.append("\"os\":\"tt\",\"deviceId\":\"\",\"devicetype\":\"\",\"brand\":\"\",\"modelNo\":\"\",\"resolution\":\"\",");
//        sb.append("\"mac\":\"\",\"idfa\":\"\",\"ouid\":\"\",\"vdid\":\"\",\"imei\":\"\",\"anid\":\"\",\"net\":\"\",\"mobileNetworkCoding\":\"\",");
//        sb.append("\"longitude\":\"\",\"latitude\":\"\"}]}");
//        String result = postJSON("http://localhost:8080/winbuyers.htm?token=IXFhenhzd0AzZWRjdmZyJA==", sb.toString());
//        System.out.print(result);
		testJson();
	}

	public static void testXml() {
		String retval = "";
		//String reqContent = "<PackageList><Package><Header><Version>2</Version><RequestType>230</RequestType><SessionId>WX_211307</SessionId><InsureType>100</InsureType><SendTime>2014-05-20 11:40:12</SendTime><SellerId>GW000001</SellerId><Status>100</Status><ErrorMessage>?б????</ErrorMessage></Header><Response><Order><TBOrderId>211307</TBOrderId><Premium>852028.00</Premium><PayNo></PayNo><SubOrderList><SubOrder type=\"biz\"><TBOrderId>211307</TBOrderId><ItemId>211307</ItemId><Premium>791528.00</Premium>					<ProposalNo>TDDK201464010899000001</ProposalNo><PolicyNo>PDDK201464010899000001</PolicyNo></SubOrder><SubOrder type=\"force\"><TBOrderId>211307</TBOrderId><ItemId>211307</ItemId><Premium>60500.00</Premium><ProposalNo>TDDK201464010899000001</ProposalNo><PolicyNo>PDDK201464010899000001</PolicyNo></SubOrder></SubOrderList></Order></Response><Sign/></Package></PackageList> ";
		String xml = "" +
				"<xml>\n" +
				"  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
				"  <attach><![CDATA[支付测试]]></attach>\n" +
				"  <bank_type><![CDATA[CFT]]></bank_type>\n" +
				"  <fee_type><![CDATA[CNY]]></fee_type>\n" +
				"  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
				"  <mch_id><![CDATA[10000100]]></mch_id>\n" +
				"  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
				"  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
				"  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
				"  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
				"  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
				"  <sign><![CDATA[93D42280CBAE1DE7684686DEBC1DBDE3]]></sign>\n" +
				"  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
				"  <time_end><![CDATA[20140903131540]]></time_end>\n" +
				"  <total_fee>1</total_fee>\n" +
				"  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
				"  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
				"</xml>";
		try {
			retval = HttpClientUtil.postXml("http://localhost:8080/chuangdao/pay/wx/notify.do", xml);
		} catch (Exception e) {
			//logger.error("request error", e);
		}

		System.out.println("#####" + retval);
	}

	public static void testJson() {
		String retval = "";
		String json = "";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("touser", "oznXBviosXdISFhAzIrxtZxR9V0g");
		jsonMap.put("template_id", "eFqLLddk4BCSxuQMrDJRBzBlDETR-zT38g6-XXU2pA0");
		jsonMap.put("url", "http://www.baidu.com");
		Map<String, Object> dataMap = new HashMap<String, Object>();

		Map<String, String> valueMap0 = new HashMap<String, String>();
		valueMap0.put("value", "恭喜您购买成功!");
		valueMap0.put("color", "#173177");
		dataMap.put("first", valueMap0);

		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("value", "123165467");
		valueMap.put("color", "#173177");
		dataMap.put("orderNo", valueMap);

		Map<String, String> valueMap2 = new HashMap<String, String>();
		valueMap.put("value", "100.56");
		valueMap.put("color", "#173177");
		dataMap.put("amount", valueMap2);

		jsonMap.put("data", dataMap);
		try {
//			String access_token = HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx5a87a16cc030e471&secret=e36b66a13de9733e5f3ee4129620c146",30000,30000,"UTF-8");
//			System.out.println(access_token);
//			Map<String,String> map  = (Map<String, String>) JSON.parse(access_token);
//
//			System.out.println("access_token:"+map.get("access_token"));
//			json = JSON.toJSONString(jsonMap);
//			System.out.println(json);
//			retval = HttpClientUtil.postJSON("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+map.get("access_token"), json );
			System.out.println("#####" + retval);
			Map<String, String> jm = new HashMap<String, String>();
			//jm.put("name", "aaaa");
			//jm.put("mobile", "13523003179");
			//jm.put("vin", "Hjjjjjjjjjjjjjjjj");
			jm.put("wechatName", "o4-Cns0unITS4i_yRtW_NQAbntmg");
			//jm.put("rzbd_state", "1");//1表示认证，2表示绑定
			retval = HttpClientUtil.post("http://test.changanfordwechat.com:8080/club_mobile/carhistory/carHistoryList.htm", jm, 1200000, 1200000, "UTF-8");
			System.out.println("#####" + retval);

		} catch (Exception e) {
			//logger.error("request error", e);
		}

	}

	public static String sendHTTP(String urlStr, String content, Map<String, String> requestParams, String charSet, boolean zip) throws Exception {
		if (urlStr.toLowerCase().startsWith("https")) {
			HttpsURLConnection.setDefaultSSLSocketFactory(SSLContext.getDefault().getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		}
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		if (requestParams != null) {
			for (Map.Entry<String, String> en : requestParams.entrySet()) {
				connection.setRequestProperty(en.getKey(), en.getValue());
			}
		}
		OutputStream out = null;
		try {
			if (zip) {
				connection.setRequestProperty("Content-Encoding", "gzip");
				connection.setRequestProperty("Accept-Encoding", "gzip");
				out = new GZIPOutputStream(connection.getOutputStream());
			} else {
				out = connection.getOutputStream();
			}
			out.write(content.getBytes(charSet));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
		int code = connection.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
			ByteArrayOutputStream o1 = null;
			InputStream in1 = null;
			try {
				o1 = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = 0;
				if (zip) {
					in1 = new GZIPInputStream(connection.getInputStream());
				} else {
					in1 = connection.getInputStream();
				}
				while ((len = in1.read(buff)) != -1) {
					o1.write(buff, 0, len);
				}
				o1.flush();
				return new String(o1.toByteArray(), charSet);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				closeQuietly(o1);
				closeQuietly(in1);
			}
		} else {
			return null;
		}
	}

	public static void closeQuietly(Closeable o) {
		if (o != null) {
			try {
				o.close();
			} catch (Exception e) {
			}
		}
	}

}
