package at.mnm.bbm.core.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public enum RandomNumberGenerator {

    INSTANCE;

    public final double getDouble() {
        double number = 0;

        Random random;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            number = random.nextDouble();
        } catch(final NoSuchAlgorithmException e) {
            // Process the exception in some way or the other
        }

        return number;
    }
}
