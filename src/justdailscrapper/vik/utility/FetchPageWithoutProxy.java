/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdailscrapper.vik.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Random;
import justdailscrapper.vik.proxy.ProxyImport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FetchPageWithoutProxy {

    public static String fetchPageSourcefromClientGoogle(URI newurl, List<ProxyImport> proxyList) throws IOException, InterruptedException {

        Random r = new Random();

        ProxyImport obj = proxyList.get(r.nextInt(proxyList.size()));

        String ip = obj.proxyIP;
        int portno = Integer.parseInt(obj.proxyPort);
        String username = "";
        String password = "";
        if (obj.proxyLen > 2) {
            username = obj.proxyUserName;
            password = obj.proxyPassword;
        }

//        int portNo = generateRandomPort();
        CredentialsProvider credsprovider = new BasicCredentialsProvider();
        credsprovider.setCredentials(
                new AuthScope(ip, portno),
                new UsernamePasswordCredentials(username, password));
        HttpHost proxy = new HttpHost(ip, portno);
        //-----------------------------------------------------------------------
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsprovider)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0")
                .setDefaultRequestConfig(requestConfig)
                .setProxy(proxy)
                .build();
        String responsebody = "";
        String responsestatus = null;
        int count = 0;
        try {
            HttpGet httpget = new HttpGet(newurl);
            httpget.addHeader("Accept", "*/*");
            httpget.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpget.addHeader("Accept-Language", "en-US,en;q=0.5");
            httpget.addHeader("Host", "www.justdial.com");
            httpget.addHeader("Referer", "https://www.justdial.com/Bangalore/Yamaha-Two-Wheeler-Dealers");
            httpget.addHeader("Connection", "keep-alive");
            httpget.addHeader("X-Requested-With", "XMLHttpReques");

            System.out.println("Response status " + httpget.getRequestLine());
            CloseableHttpResponse resp = httpclient.execute(httpget);
            responsestatus = resp.getStatusLine().toString();
            if (responsestatus.contains("503") || responsestatus.contains("502") || responsestatus.contains("403")
                    || responsestatus.contains("400") || responsestatus.contains("407") || responsestatus.contains("401")
                    || responsestatus.contains("402") || responsestatus.contains("404") || responsestatus.contains("405")
                    || responsestatus.contains("SSLHandshakeException") || responsestatus.contains("999")
                    || responsestatus.contains("ClientProtocolException") || responsestatus.contains("SocketTimeoutException")
                    || "".equals(responsestatus)) {
                Thread.sleep(10000);
                do {
                    count++;
                    responsebody = fetchPageSourcefromClientGoogleSecond(newurl, proxyList);
                    if (responsebody == null) {
                        Thread.sleep(10000);
                        System.out.println("PROX FAILURE");
                    }
                    if (count > 20) {
                        Thread.sleep(1000);
                        break;
                    }
                } while (responsebody == null || "".equals(responsebody));
            } else {
                HttpEntity entity = resp.getEntity();
                System.out.println(resp.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responsebody = new StringBuilder().append(responsebody).append(inputLine).toString();
                    }
                    // writeResponseFile(responsebody, pagename);
                }
                EntityUtils.consume(entity);
            }
        } catch (IOException | IllegalStateException e) {
            System.out.println("Exception = " + e);
            do {
                count++;
                responsebody = fetchPageSourcefromClientGoogleSecond(newurl, proxyList);
                if (responsebody == null) {
                    System.out.println("PROX FAILURE");
                }
                if (count > 15) {
                    Thread.sleep(50000);
//                    responsebody = fetchPageSourcefromClientGoogleSecond(newurl);
                    break;
                }
            } while (responsebody == null || "".equals(responsebody));
        } finally {
            httpclient.close();
        }
        return responsebody;
    }

    public static String fetchPage(URI newurl, List<ProxyImport> proxyList) throws IOException, InterruptedException {

        Random r = new Random();

        ProxyImport obj = proxyList.get(r.nextInt(proxyList.size()));

        String ip = obj.proxyIP;
        int portno = Integer.parseInt(obj.proxyPort);
        String username = "";
        String password = "";
        if (obj.proxyLen > 2) {
            username = obj.proxyUserName;
            password = obj.proxyPassword;
        }

//        int portNo = generateRandomPort();
        CredentialsProvider credsprovider = new BasicCredentialsProvider();
        credsprovider.setCredentials(
                new AuthScope(ip, portno),
                new UsernamePasswordCredentials(username, password));
        HttpHost proxy = new HttpHost(ip, portno);
        //-----------------------------------------------------------------------
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsprovider)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0")
                .setDefaultRequestConfig(requestConfig)
                .setProxy(proxy)
                .build();
        String responsebody = "";
        String responsestatus = null;
        int count = 0;
        try {
            HttpGet httpget = new HttpGet(newurl);
            httpget.addHeader("Accept", "*/*");
            httpget.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpget.addHeader("Accept-Language", "en-US,en;q=0.5");
            httpget.addHeader("Host", "www.justdial.com");
            httpget.addHeader("Referer", "https://www.justdial.com/Bangalore/Yamaha-Two-Wheeler-Dealers");
            httpget.addHeader("Connection", "keep-alive");
            httpget.addHeader("X-Requested-With", "XMLHttpReques");
            System.out.println("Response status " + httpget.getRequestLine());
            CloseableHttpResponse resp = httpclient.execute(httpget);
            responsestatus = resp.getStatusLine().toString();
            if (responsestatus.contains("503") || responsestatus.contains("502") || responsestatus.contains("403")
                    || responsestatus.contains("400") || responsestatus.contains("407") || responsestatus.contains("401")
                    || responsestatus.contains("402") || responsestatus.contains("404") || responsestatus.contains("405")
                    || responsestatus.contains("SSLHandshakeException") || responsestatus.contains("999")
                    || responsestatus.contains("ClientProtocolException") || responsestatus.contains("SocketTimeoutException")
                    || "".equals(responsestatus)) {
                Thread.sleep(10000);
                do {
                    count++;
                    responsebody = fetchPageSourcefromClientGoogleSecond(newurl, proxyList);
                    if (responsebody == null) {
                        Thread.sleep(10000);
                        System.out.println("PROX FAILURE");
                    }
                    if (count > 20) {
                        Thread.sleep(1000);
                        break;
                    }
                } while (responsebody == null || "".equals(responsebody));
            } else {
                HttpEntity entity = resp.getEntity();
                System.out.println(resp.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responsebody = new StringBuilder().append(responsebody).append(inputLine).toString();
                    }
                    // writeResponseFile(responsebody, pagename);
                }
                EntityUtils.consume(entity);
            }
        } catch (IOException | IllegalStateException e) {
            System.out.println("Exception = " + e);
            do {
                count++;
                responsebody = fetchPageSourcefromClientGoogleSecond(newurl, proxyList);
                if (responsebody == null) {
                    System.out.println("PROX FAILURE");
                }
                if (count > 15) {
                    Thread.sleep(50000);
//                    responsebody = fetchPageSourcefromClientGoogleSecond(newurl);
                    break;
                }
            } while (responsebody == null || "".equals(responsebody));
        } finally {
            httpclient.close();
        }
        return responsebody;
    }

    public static String fetchPageSourcefromClientGoogleSecond(URI newurl, List<ProxyImport> proxyList) throws IOException {

        Random r = new Random();

        ProxyImport obj = proxyList.get(r.nextInt(proxyList.size()));

        String ip = obj.proxyIP;
        int portno = Integer.parseInt(obj.proxyPort);
        String username = "";
        String password = "";
        if (obj.proxyLen > 2) {
            username = obj.proxyUserName;
            password = obj.proxyPassword;
        }

//        int portNo = generateRandomPort();
        CredentialsProvider credsprovider = new BasicCredentialsProvider();
        credsprovider.setCredentials(
                new AuthScope(ip, portno),
                new UsernamePasswordCredentials(username, password));
        HttpHost proxy = new HttpHost(ip, portno);
        //-----------------------------------------------------------------------
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsprovider)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0")
                .setDefaultRequestConfig(requestConfig)
                .setProxy(proxy)
                .build();
        String responsebody = "";
        String responsestatus = null;
        try {
            HttpGet httpget = new HttpGet(newurl);
            httpget.addHeader("Accept", "*/*");
            httpget.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpget.addHeader("Accept-Language", "en-US,en;q=0.5");
            httpget.addHeader("Host", "www.justdial.com");
            httpget.addHeader("Referer", "https://www.justdial.com/Bangalore/Yamaha-Two-Wheeler-Dealers");
            httpget.addHeader("Connection", "keep-alive");
            httpget.addHeader("X-Requested-With", "XMLHttpReques");

            System.out.println("Response status" + httpget.getRequestLine());
            CloseableHttpResponse resp = httpclient.execute(httpget);
            responsestatus = resp.getStatusLine().toString();
            if (responsestatus.contains("503") || responsestatus.contains("502") || responsestatus.contains("400")
                    || responsestatus.contains("401") || responsestatus.contains("402") || responsestatus.contains("403")
                    || responsestatus.contains("407") || responsestatus.contains("404") || responsestatus.contains("405")
                    || responsestatus.contains("SSLHandshakeException") || responsestatus.contains("999")
                    || responsestatus.contains("ClientProtocolException") || responsestatus.contains("SocketTimeoutException")
                    || responsestatus == null || "".equals(responsestatus)) {
                return null;
            } else {
                HttpEntity entity = resp.getEntity();
                System.out.println(resp.getStatusLine());
                if (entity != null) {
//                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responsebody = new StringBuilder().append(responsebody).append(inputLine).toString();
                    }
                }
                EntityUtils.consume(entity);
            }
        } catch (IOException | IllegalStateException e) {
            return null;
        } finally {
            httpclient.close();
        }
        return responsebody;
    }

    public static int generateRandomPort() {

        int portNo;
        Random random = new Random();
        int[] portList = new int[98];
        int portBegin = 1601;   //1601

        for (int k = 0; k < portList.length; k++) {
            portList[k] = portBegin;
            portBegin = portBegin + 1;
        }

        int num = random.nextInt(98);
        portNo = portList[num];
        return portNo;
    }

}
