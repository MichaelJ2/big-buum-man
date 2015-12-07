package at.bbm.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestLog4J2 {

    public static final Logger logger = LogManager.getLogger(TestLog4J2.class.getName());

    @Test
    public void testLog4J2() {
        logger.error("?");
    }

}
