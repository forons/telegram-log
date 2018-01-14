package it.forons.utils.telegram.wrapper;

import java.io.DataOutputStream;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TelegramBot {

  private String token;
  private int chat_id;

  public TelegramBot(String token, int chat_id) {
    this.token = token;
    this.chat_id = chat_id;
  }

  public int sendMessage(String message) throws IOException {
    return TelegramBot.sendMessage(getToken(), getChat_id(), message);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getChat_id() {
    return chat_id;
  }

  public void setChat_id(int chat_id) {
    this.chat_id = chat_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TelegramBot that = (TelegramBot) o;
    return Objects.equals(token, that.token) &&
        Objects.equals(chat_id, that.chat_id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(token, chat_id);
  }

  @Override
  public String toString() {
    return "TelegramBot{" +
        "token='" + token + '\'' +
        ", chat_id='" + chat_id + '\'' +
        '}';
  }

  public static int sendMessage(String token, int chat_id, String message) throws IOException {
    String request = String.format("https://api.telegram.org/bot%s/sendMessage", token);
    URL url = new URL(request);
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

    String params = String.format("chat_id=%s&text=%s", chat_id, message);
    byte[] postData = params.getBytes(StandardCharsets.UTF_8);
    int postDataLength = postData.length;

    connection.setDoOutput(true);
    connection.setInstanceFollowRedirects(false);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setRequestProperty("charset", "utf-8");
    connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
    connection.setUseCaches(false);

    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
      wr.write(postData);
      wr.flush();
      wr.close();
    }

    return connection.getResponseCode();
  }
}
