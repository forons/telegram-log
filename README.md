## Telegram-Log – Send message to a Telegram Bot or use it as a logger!

*Telegram-Log* is a simple way to interact from Java to a Telegram Bot.
This projects allows the users to both send simple messages and to use the Telegram Bot as an appender for the well known Java logger log4j.

It is released under a Apache License 2.0.

## Step 0: Creation of the bot

Following what is written [here](https://www.shellhacks.com/telegram-api-send-message-personal-notification-bot/),
the first step to create a bot is to start a new conversation with **BotFather**.
With it, create a new bot with `/newbot`, then assign it a name and a unique username following the instructions.

The *BotFather* will answer with a `TOKEN`, which will be needed to later use the Telegram-Log application.

Then, start a conversation with the new created bot, searching it through the global search.

Now, write `/start` and start a conversation.

Go to `https://api.telegram.org/bot<TOKEN>/getUpdates` (replacing `<TOKEN>` with the token of your bot)
and you will receive a JSON that contains something like:


    ..."chat":{"id":123456789,"first_name":"your_name","username":"your_username",type":"private"}...

You also need the chat id in order to use the Telegram-Log application.

Now you are ready to go to the next step!
  
  

## Quickstart

To use the application you need to:

    git clone https://github.com/forons/telegram-log.git
    mvn clean install
    
Then, you will be able to import this project in any of your projects simply by adding in the `pom.xml`:

    	<dependency>
    		<groupId>it.forons.utils</groupId>
    		<artifactId>telegram</artifactId>
    		<version>1.0-SNAPSHOT</version>
    	</dependency>

### Usage 1: Send message

As explained in `src/main/java/it/forons/utils/telegram/TelegramSender.java` there two ways to send messages to a Telegram Bot:
```java    
TelegramBot bot = new TelegramBot(TOKEN, CHAT_ID);
int responseCode = bot.sendMessage("Option 1: build the class and send the message.");
```

or

```java    
TelegramBot.sendMessage(TOKEN, CHAT_ID, "Option 2: static method.");
```

### Usage 2: log4j Appender

To use Telegram-Log as a log4j appender it is just needed to add the maven dependency and to set the following lines into the `log4j.properties` file:
```
# Root logger option
log4j.rootLogger=INFO, telegram

# Direct log messages to the telegram bot
log4j.appender.telegram=it.forons.utils.telegram.appender.TelegramAppender
log4j.appender.telegram.TOKEN=<TOKEN>
log4j.appender.telegram.CHAT_ID=<CHAT_ID>
log4j.appender.telegram.layout=org.apache.log4j.PatternLayout
log4j.appender.telegram.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```

## License
This project is licensed under the MIT License - see the LICENSE.md file for details

## Acknowledgments
[Marco De Nadai](http://www.marcodena.it/) and [Paolo Sottovia](http://github.com/welpaolo/)for the inspiration


-----

*Thank you to everyone who will report bugs, propose extra features, and suggest fixes.*
