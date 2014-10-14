/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.ndp;

import com.kingmed.dp.ndp.impl.NDPImageServerImpl;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zhengjunjie
 */
public class NDPServeTest {
    private NDPImageServerImpl ndpServe;
    private static String c;
    public NDPServeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass");
    }

    @Before
    public void setUp() {
        System.out.println("@Before");
        ndpServe = new NDPImageServerImpl();
        ndpServe.setProtocl("http");
        ndpServe.setHost("www.kingmed.com.cn");
        ndpServe.setPort(81);
        ndpServe.setUsername("upmcportal");
        ndpServe.setPassword("upmcportalking");
    }

    @After
    public void tearDown() {
        System.out.println("@After");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testSignin() {
        String signinUrl = ndpServe.getUrlSignin();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(signinUrl);

            System.out.println("Executing request " + httpget.getRequestLine());

            String responseBody = httpclient.execute(httpget, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
                    int status = hr.getStatusLine().getStatusCode();
                    if(status>=200&& status<300){
                        Header[] cookies = hr.getHeaders("Set-Cookie");
                        System.out.println("cookies.length="+cookies.length);
                        for(Header cookie:cookies){
                            System.out.println(cookie.getName()+" is "+cookie.getValue());
                            if(!cookie.getValue().endsWith("=")){
                                c = cookie.getValue();
                            }
                        }
                        HttpEntity entity = hr.getEntity();
                        return entity !=null ? EntityUtils.toString(entity):null;
                    }else{
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            });
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //@Test
    public void testQueryItemsAddedAfter() {
        Long itemId=39000L;
        String queryUrl = ndpServe.getUrlItemsAddedAfter(itemId);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(queryUrl);

            System.out.println("Executing request " + httpget.getRequestLine());

            String responseBody = httpclient.execute(httpget, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
                    int status = hr.getStatusLine().getStatusCode();
                    if(status>=200&& status<300){
                        HttpEntity entity = hr.getEntity();
                        return entity !=null ? EntityUtils.toString(entity):null;
                    }else{
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            });
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void testCookie(){
        System.out.println("testCookie,c="+c);
    }
    
    @Test
    public void testQueryItemsAddedAfterWithCookie(){
        Long itemId=43701L;//KM14-000556_S100_3_KM140559   42947
        String cookie="SessionID=580d51b76ee74b01b23b2626a733fbe6";
        String queryUrl = ndpServe.getUrlItemsAddedAfter(itemId);
        System.out.println("queryUrl is "+queryUrl);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(queryUrl);
            get.setHeader("Cookie", cookie);
            
            String responseBody = client.execute(get, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
                    int status = hr.getStatusLine().getStatusCode();
                    if(status>=200&& status<300){
                        HttpEntity entity = hr.getEntity();
                        return entity !=null ? EntityUtils.toString(entity):null;
                    }else{
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            });
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            
            Reader reader = new StringReader(responseBody);
            SAXBuilder builder = new SAXBuilder();
            Document jdomDoc = builder.build(reader);
            XPathFactory xFactory = XPathFactory.instance();
            XPathExpression<Element> expr = xFactory.compile("//items/item[type='slide']", Filters.element());
            List<Element> items = expr.evaluate(jdomDoc);
            for(Element itemElement : items){
                System.out.println("id="+itemElement.getChildText("id")
                                    +",name="+itemElement.getChildText("name")
                                    +",type="+itemElement.getChildText("type"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("error...............................");
        }finally{
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(NDPServeTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //@Test
    public void testQueryItemsAddedAfterWithAuthz() {
        Long itemId=39000L;
        String queryUrl = ndpServe.getUrlItemsAddedAfterWithAuthz(itemId);
        SAXBuilder builder = new SAXBuilder();
        try {
            //Document jdomDoc = builder.build(new File("E:\\zhengjunjie\\kingmed\\dp\\供应商\\ndp\\after10000.xml"));
            Document jdomDoc = builder.build(new URL(queryUrl));
            XPathFactory xFactory = XPathFactory.instance();
            XPathExpression<Element> expr = xFactory.compile("//items/item[type='slide']", Filters.element());
            List<Element> items = expr.evaluate(jdomDoc);
            for(Element itemElement : items){
                System.out.println("id="+itemElement.getChildText("id")
                                    +",name="+itemElement.getChildText("name")
                                    +",type="+itemElement.getChildText("type"));
                
            }
        } catch (JDOMException ex) {
            Logger.getLogger(NDPServeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NDPServeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
