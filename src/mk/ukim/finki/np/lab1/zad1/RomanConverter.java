package mk.ukim.finki.np.lab1.zad1;

public class RomanConverter {
    public static String toRoman(int n) {
        /*
            Da go zabelezime slednoto:
                1. Do 1000 sekoj rimski broj si ima nekakva kombinacija od ostanati.
                2. Znaeme deka 1000 e M;
                3. Sekoja naredna iledarka se pisuva MMMMMM, toest onolku M kolku iledarki
                4. Nema povisok "apoen od" 1000 => kako zaklucok od gorenavedenite zabeleski;
            Od ovie zabeleski mozeme problemot da go resime kako Greedy resenie na coin problem
         */
        String[] romanRepresentation = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicRepresentation = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String retValue = "";
        int temp;
        for (int i = 0; i < arabicRepresentation.length; i++) {
            if (n >= arabicRepresentation[i]) {
                temp = n / arabicRepresentation[i]; //go zemame celiot del
                n -= temp * arabicRepresentation[i];

                while (temp > 0) {
                    retValue += romanRepresentation[i]; //dodavame n pati po apoenite;
                    temp--;
                }
            }
        }
        return retValue;
    }
}
