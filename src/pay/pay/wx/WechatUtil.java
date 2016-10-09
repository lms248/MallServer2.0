package pay.wx;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cmge.pay.platform.common.constant.Constant;
import com.cmge.pay.platform.common.util.MD5;
import com.cmge.pay.platform.module.pay.base.constant.PayConstant;
import com.cmge.pay.platform.module.pay.wechat.constant.WechatConstant;

public class WechatUtil {

	private static final int RANDOM_STRING_LENGTH = 30;

	/*public static Map<String, Param> prepay(String s) {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext);
			client = HttpClients.custom().setSSLSocketFactory(factory).build();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(PayConstant.SOCKET_TIMEOUT).setConnectTimeout(PayConstant.CONNECT_TIMEOUT).build();
			HttpPost post = new HttpPost(WechatConstant.UNIFIED_ORDER_URL);
			post.setConfig(requestConfig);
			StringEntity entity = new StringEntity(s,Constant.UTF_8);
			post.setEntity(entity);
			response = client.execute(post);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				return parseXml(response.getEntity().getContent());
			} else {
				throw new RuntimeException("失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (client != null) {
					client.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	public static String signMD5(List<Param> params) {
		Collections.sort(params);
		StringBuilder builder = new StringBuilder();
		for (Param param : params) {
			if (!param.isSign()) {
				continue;
			}
			builder.append(param.info());
			builder.append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(WechatConstant.KEY);
		String sign = MD5.encode(builder.toString(), Constant.UTF_8);
		return sign.toUpperCase();
	}

	public static String getRandomString() {
		String result = UUID.randomUUID().toString();
//		System.out.println(result.length());
		return result.substring(0, RANDOM_STRING_LENGTH);
	}

	public static String getLocalHostIP() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toXml(List<Param> params) {
		StringBuilder builder = new StringBuilder();
		builder.append("<xml>").append(Constant.NEW_LINE);
		for (Param param : params) {
			builder.append(param.xml()).append(Constant.NEW_LINE);
		}
		builder.append("</xml>");
		return builder.toString();
	}

	public static String toXml(Map<String, String> map) {
		StringBuilder builder = new StringBuilder();
		builder.append("<xml>").append(Constant.NEW_LINE);
		for (Entry<String, String> entry : map.entrySet()) {
			builder.append(MessageFormat.format("<{0}><![CDATA[{1}]]></{0}>", entry.getKey(), entry.getValue())).append(Constant.NEW_LINE);
		}
		builder.append("</xml>");
		return builder.toString();
	}

	public static String pairToXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");
			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
			return sb.toString();
	}

	public static Map<String, Param> parseXml(InputStream inputStream) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			Map<String, Param> map = new HashMap<String, Param>();
			for (Object element : root.elements()) {
				String name = ((Element) element).getName();
				String value = ((Element) element).getText();
				System.out.println(name+":"+value);
				map.put(name, Param.valueOf(name, value, "sign".equals(name) ? false : true));
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String genSign(List<NameValuePair> params, String key) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
//		sb.append("key=").append(WechatConstant.KEY);
		sb.append("key=").append(key);
		String appSign = getMessageDigest(sb.toString().getBytes(Constant.UTF_8)).toUpperCase();
		return appSign;
	}

	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getNonceStr() {
		Random random = new Random();
		return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

}
