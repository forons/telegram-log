package it.forons.utils.telegram.appender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class TelegramAppender extends AppenderSkeleton {

  private final static String BASE_URL = "https://api.telegram.org/bot";

  private String TOKEN;
  private int CHAT_ID;

  private HttpsURLConnection createConnection(String token) {
    HttpsURLConnection connection = null;
    if (token != null && !token.isEmpty()) {
      final String request = String.format("%s%s/sendMessage", BASE_URL, token);
      try {
        URL url = new URL(request);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      } catch (IOException e) {
        e.printStackTrace();
        LogLog.warn(String.format("Error while creating the connection with the bot %s", token), e);
      }
    }
    return connection;
  }

  @Override
  protected void append(LoggingEvent event) {
    HttpsURLConnection connection = createConnection(getTOKEN());
    if (connection != null && getCHAT_ID() > 0) {
      String message = String.format("chat_id=%s&text=%s", getCHAT_ID(), layout.format(event));
      byte[] postData = message.getBytes(StandardCharsets.UTF_8);
      int postDataLength = postData.length;

      connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
      connection.setRequestProperty("charset", "utf-8");

      try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
        wr.write(postData);
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
          LogLog.warn("Response is not OK. Maybe, you didn't yet start conversation?");
        }
      } catch (IOException ioe) {
        LogLog.warn(String.format("Failed to send the message: %s", layout.format(event)), ioe);
      }
    }
  }

  @Override
  public void close() {

  }

  @Override
  public boolean requiresLayout() {
    return true;
  }

  public String getTOKEN() {
    return TOKEN;
  }

  public void setTOKEN(String TOKEN) {
    this.TOKEN = TOKEN;
  }

  public int getCHAT_ID() {
    return CHAT_ID;
  }

  public void setCHAT_ID(int CHAT_ID) {
    this.CHAT_ID = CHAT_ID;
  }
}
