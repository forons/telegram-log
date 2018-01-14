package it.forons.utils.telegram;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TelegramAppenderTest {

  private static final Logger LOG = Logger.getLogger(TelegramAppenderTest.class);

  @Test
  public void test() {

    new TelegramAppenderTest();

    LOG.info("Test");
    LOG.error("Error");
    LOG.fatal("Fatal");
  }
}
