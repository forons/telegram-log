package it.forons.utils.telegram;


import it.forons.utils.telegram.wrapper.TelegramBot;
import java.io.IOException;

public class TelegramSender {

  private static String TOKEN;
  private static int CHAT_ID;

  /**
   * Entry point of the application.
   * It receives as input two parameters: <TOKEN> and <CHAT_ID>,
   * where <TOKEN> is the token of the Telegram bot,
   * while <CHAT_ID> is the identifier of the chat to which the messages will be sent.
   * The usage of this program is: telegramBot.jar <TOKEN> <CHAT_ID>
   *
   * @param args, contains two elements: TOKEN and CHAT_ID
   */
  public static void main(String[] args) throws IOException {
    /* Parsing the command line arguments to read the telegram parameters. */
    parseParameters(args);

    /**
     * Option 1: create a TelegramBot class, which stores the token and chat_id information.
     *
     * Fire the message with via calling the function bot.sendMessage(string).
     */
    TelegramBot bot = new TelegramBot(TOKEN, CHAT_ID);
    int responseCode = bot.sendMessage("Option 1: build the class and send the message.");
    System.out.println("Response Code: " + responseCode);

    /**
     * Option 2: invoke the static method TelegramBot.sendMessage, which takes as input
     * the token, the chat_id, and the message that will be sent.
     */
    responseCode = TelegramBot.sendMessage(TOKEN, CHAT_ID, "Option 2: static method.");
    System.out.println("Response Code: " + responseCode);
  }

  /**
   * This method parses and validates the paramenters.
   * The TOKEN needs the be the first element,
   * while the second, the CHAT_ID, needs to also be an integer greater than 0.
   */
  private static void parseParameters(String[] args) {
    try {
      if (args.length != 2) {
        System.err
            .println("The TelegramBot app receives as input two parameters: <TOKEN> <CHAT_ID>");
      }
      TOKEN = args[0];
      CHAT_ID = Integer.parseInt(args[1]);
      if (CHAT_ID <= 0) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException e) {
      System.err.println("The TelegramBot app receives as input two parameters: <TOKEN> <CHAT_ID>");
      System.exit(1);
    }
  }
}
