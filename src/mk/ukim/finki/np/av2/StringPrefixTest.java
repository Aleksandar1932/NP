package mk.ukim.finki.np.av2;

import java.util.stream.IntStream;

public class StringPrefixTest {

    public static boolean isPrefix(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrefixStream(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }
        return IntStream.range(0, str1.length())
                .allMatch(i -> str1.charAt(i) == str2.charAt(i));
    }

    public static boolean isPrefixPreDefinedMethod(String str1, String str2) {
        return str2.startsWith(str1);
    }

    public static void main(String[] args) {
        String str1 = "Aleksk";
        String str2 = "Aleksandar";

        System.out.println(isPrefix(str1, str2));
        System.out.println(isPrefixStream(str1, str2));
        System.out.println(isPrefixPreDefinedMethod(str1, str2));
    }
}
