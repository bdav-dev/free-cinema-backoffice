package utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import pvt.PrivateKeys;

public class CryptographicUtility {

    private final char[] ALPHABET = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',

            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',

            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private SecureRandom rng;

    private static CryptographicUtility instance;

    private CryptographicUtility() {
        rng = new SecureRandom();
    }

    public static CryptographicUtility getInstance() {
        if (instance == null)
            instance = new CryptographicUtility();

        return instance;
    }

    public String encrypt(String clearText, EncryptionKeys key) {
        return useVigenere(clearText, key, true);
    }

    public String decrypt(String cipherText, EncryptionKeys key) {
        return useVigenere(cipherText, key, false);
    }

    public String getSaltedPasswordHash(String password) {
        return getSHA512Hash(password + EncryptionKeys.SALT);
    }

    private String getSHA512Hash(String text) {
        return getHash(text.getBytes(), "SHA-512");
    }

    private String useVigenere(String text, EncryptionKeys key, boolean encrypt) {
        StringBuilder builder = new StringBuilder();
        int alphabetLength = ALPHABET.length;
        int indexOfOTP = 0;

        for (int i = 0; i < text.length(); i++) {
            if (indexOfOTP == key.getKey().length())
                indexOfOTP = 0;

            int shift = indexInAlphabet(key.getKey().charAt(indexOfOTP));

            if (indexInAlphabet(text.charAt(i)) == -1) {
                builder.append(text.charAt(i));
                continue;
            }

            int finalIndex;

            if (encrypt) {
                finalIndex = (indexInAlphabet(text.charAt(i)) + shift) % alphabetLength;
            } else {
                finalIndex = (indexInAlphabet(text.charAt(i)) - shift) % alphabetLength;

                if (finalIndex < 0)
                    finalIndex += alphabetLength;
            }

            builder.append(ALPHABET[finalIndex]);

            indexOfOTP++;
        }

        return builder.toString();
    }

    private int indexInAlphabet(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c)
                return i;
        }
        return -1;
    }

    private String generatePassword(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++)
            builder.append(ALPHABET[getRandomNumber(0, ALPHABET.length - 1)]);


        return builder.toString();

    }

    private int getRandomNumber(int lowerBound, int upperBound) {
        return rng.nextInt(upperBound + 1 - lowerBound) + lowerBound;
    }

    private String getHash(byte[] inputBytes, String algorithm) {
        String hashValue = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();

            BigInteger bigInt = new BigInteger(1, digestedBytes);

            hashValue = bigInt.toString(16);
        } catch (Exception e) {
        }

        return hashValue;
    }

    public static enum EncryptionKeys {
        REGISTRY(PrivateKeys.REGISTRY), SALT(PrivateKeys.SALT);

        private String key;

        private EncryptionKeys(String key) {
            this.key = key;
        }

        private String getKey() {
            return key;
        }
    }

}
