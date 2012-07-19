package net.chrisrichardson.banking.web.tests;

import java.net.URI;
import java.util.Collections;

import net.chrisrichardson.banking.web.dtos.AccountInfo;
import net.chrisrichardson.umangite.JettyLauncher;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BankingWebTests {

  private static JettyLauncher jetty;
  private static RestTemplate restTemplate;

  @BeforeClass
  public static void initialize() throws Exception {
    jetty = new JettyLauncher();
    jetty.setContextPath("webapp");
    jetty.setPort(-1);
    jetty.setSrcWebApp("src/main/webapp");
    jetty.start();
    restTemplate = new RestTemplate();
  }

  @AfterClass
  public static void tearDown() throws InterruptedException {
    jetty.stop();
  }

  @Test
  public void testCreate() {
    URI accountUrl = restTemplate.postForLocation(makeUrl("accounts"),
        new AccountInfo(78, "abc" + System.currentTimeMillis(), 567.8));
    System.out.println("accountUrl=" + accountUrl);
    ResponseEntity<AccountInfo> s = restTemplate.getForEntity(accountUrl,
        AccountInfo.class);
    System.out.println(s);
    System.out.println(s.getBody());
  }

  @Test
  public void testGet() {
    ResponseEntity<AccountInfo> s = restTemplate.getForEntity(
        makeUrl("accounts/{accountId}"), AccountInfo.class,
        Collections.singletonMap("accountId", "xyz"));
    System.out.println("Got account");
    System.out.println(s);
    System.out.println(s.getBody());
  }

  private String makeUrl(String suffix) {
    String s = String.format("http://localhost:%d/webapp/%s",
        jetty.getActualPort(), suffix);
    System.out.println("url=" + s);
    return s;
  }
}
