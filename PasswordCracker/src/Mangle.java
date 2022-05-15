import java.util.ArrayList;
import java.util.Collections;

/**
 * Author:      Lucas Larsson
 * Date:        2022-05-15
 * Description:
 **/
public class Mangle {


//    public static String prependC(String word, int c) {
//        char character = lettersArray[c];
//        return character + word;
//    }
//
//    public static String appendC(String word, int c) {
//        char character = lettersArray[c];
//        return word + character;
//    }

    public static String deleteFirst(String word) {
        return word.substring(1);
    }

    public static String deleteLast(String word) {
        return word.substring(0, word.length() - 1);
    }

    public static String reverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    public static String duplicate(String word) {
        return word + word;
    }

    public static String reflect1(String word) {
        return word + reverse(word);
    }

    public static String reflect2(String word) {
        return reverse(word) + word;
    }

    public static String upperCase(String word) {
        return word.toUpperCase();
    }

    public static String lowerCase(String word) {
        return word.toLowerCase();
    }

    public static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String ncapitalize(String word) {
        return word.substring(0, 1).toLowerCase() + word.substring(1).toUpperCase();
    }

    // ToGgLe
    public static String toggleUp(String word) {
        StringBuilder toggle = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (i % 2 == 0) {
                toggle.append(word.substring(i, i + 1).toUpperCase());
            } else {
                toggle.append(word.charAt(i));
            }
        }
        return toggle.toString();
    }

    // tOgGlE
    public static String toggleDown(String word) {
        StringBuilder toggle = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (i % 2 != 0) {
                toggle.append(word.substring(i, i + 1).toUpperCase());
            } else {
                toggle.append(word.substring(i, i + 1));
            }
        }
        return toggle.toString();
    }

    public static void commonWords (ArrayList<String> dictionary) {

        String[] listOfCommonWords = { "sdf","sdf", "123456", "password","12345678", "qwerty", "123456789","12345","1234"," 111111" , "1234567", "dragon","123123","baseball","abc123","football","monkey","letmein","696969","shadow","master","666666","qwertyuiop","123321","mustang","1234567890","michael","654321","pussy","superman","1qaz2wsx","7777777","fuckyou","121212","000000","qazwsx","123qwe","killer","trustno1","jordan","jennifer","zxcvbnm","asdfgh","hunterv","buster","soccer","harley","batman","andrew","tigger","sunshine","iloveyou","fuckme","2000","charlie","robert","thomas","hockey","ranger","daniel","starwars","klaster","112233","george","asshole","computer","michelle","jessica","pepper","1111","zxcvbn","555555","11111111","131313","freedom","777777","pass","fuck","maggie","159753","aaaaaa","ginger","princess","joshua","cheese","amanda","summer","love","ashley","6969","nicole","chelsea","biteme","matthew","access","yankees","987654321","dallas","austin","thunder","taylor","matrix","minecraft"};

        for (String listOfCommonWord : listOfCommonWords) {
            dictionary.add(listOfCommonWord + '\n');
        }
        Collections.addAll(dictionary, listOfCommonWords);
    }

    public static ArrayList mangle(ArrayList<String> dictionary) {

        ArrayList<String> mangledDictionary =  new ArrayList<>();

        for (String s : dictionary) {
            mangledDictionary.add(reverse(s));
//            mangledDictionary.add(lowerCase(s));
            mangledDictionary.add(upperCase(s));
            mangledDictionary.add(capitalize(s));
            mangledDictionary.add(ncapitalize(s));
            mangledDictionary.add(toggleDown(s));
            mangledDictionary.add(toggleUp(s));
            mangledDictionary.add(duplicate(s));
            mangledDictionary.add(deleteFirst(s));
            mangledDictionary.add(deleteLast(s));
            mangledDictionary.add(reflect1(s));
            mangledDictionary.add(reflect2(s));
        }

        System.out.println("all dics are built ");
        return mangledDictionary;
    }
}
