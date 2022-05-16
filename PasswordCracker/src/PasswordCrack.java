import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PasswordCrack {

    public static ArrayList<String> hashedPasswords = new ArrayList<>();
    public static ArrayList<String> dictionary =  new ArrayList<>();

//    public static String[] onlyPasswords = new String[20];
    public static String[] salt = new String[20];

    public static void main(String[] args) {

        if(args.length != 2) {
            System.err.println("Usage: java PasswordCrack <dictionary> <passwd>");
            System.exit(-1);
        }

//        args assignment
        File dictionaryFile = new File (args[0]);
        File passwordsFile = new File (args[1]);


        try(BufferedReader inDic = new BufferedReader(new FileReader(dictionaryFile));
            BufferedReader inPass = new BufferedReader(new FileReader(passwordsFile))){

//            Extract the Dictionary word
            String line;
            while((line = inDic.readLine()) != null){
                dictionary.add(line);
            }
            System.out.println("Dictionary list is built");

//            Extract Passwords from file
            while((line = inPass.readLine()) != null){
                String [] splittedLine = line.split(":");
                String hash = splittedLine[1];
                String[] name = splittedLine[4].split(" ");
                hashedPasswords.add(hash);

//                Adds first and last name and middle name if exist
                dictionary.add(name[0]);
                dictionary.add(name[1]);
                if(name.length == 3)
                    dictionary.add(name[2]);
            }


//          Extract the salt (first two characters) from the hashed passwords
            Arrays.setAll(salt, i -> hashedPasswords.get(i).substring(0, 2));


            System.out.println(hashedPasswords.get(1));
            System.out.println(hashedPasswords.get(7));
            System.out.println(hashedPasswords.get(16));
            System.out.println(hashedPasswords.get(18));






//            I create a new ListArray to avoid trowing concurrent runtime exception.
            ArrayList mangledDictionary = Mangle.mangle(dictionary);
            Mangle.commonWords(mangledDictionary); // remember to fix this
//            dicAttack(mangledDictionary);

            Mangle.mangle2(mangledDictionary);
            dicAttack2(dictionary);


        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
            System.err.println(e.getMessage());
            System.exit(-2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-4);
        }
    }

    public static void dicAttack(ArrayList <String> mangledDictionary) {
        System.out.println( " Number of dictionary words before mangle : [" + dictionary.size() + "]");
        System.out.println(" Number of dictionary words after mangle : [" + mangledDictionary.size()+ "]");
        double dif = ((double)dictionary.size() / mangledDictionary.size());
        System.out.println("The original dictionary is " + String.format("%.5g%n", dif) + " % of the original dictionary, compared in the number of words");
        boolean cracked;

        for (int i = 0; i < hashedPasswords.size(); i++) {
            for (int j = 0; j < mangledDictionary.size(); j++) {
                String s = mangledDictionary.get(j);
                String guessedPassword = jcrypt.crypt(salt[i], s);
                cracked = checkPassword(guessedPassword, hashedPasswords.get(i), i, s);
                if(cracked) break;
//              indicates progress
                if((j % 1000000) == 0 ) System.out.print(".");
            }
        }
    }

    private static boolean checkPassword(String guessedPassword, String hashedPassword, int index, String s) {

//        if (guessedPassword.equals(hashedPassword)){
        if (guessedPassword.equals(hashedPassword)){
            int number = index + 1;
            System.out.println(" \n Password number [" + number +"] is cracked");
            System.out.println(" The password is :" + s);
            return true;
        }
        return false;
    }

    public static void dicAttack2(ArrayList <String> mangledDictionary) {
        System.out.println( " Number of dictionary words before mangle : [" + dictionary.size() + "]");
        System.out.println(" Number of dictionary words after mangle : [" + mangledDictionary.size()+ "]");

        boolean cracked;

         ArrayList<String> hashedPassword = new ArrayList<>();



         hashedPassword.add(hashedPasswords.get(1));
        hashedPassword.add(hashedPasswords.get(7));
        hashedPassword.add(hashedPasswords.get(16));
        hashedPassword.add(hashedPasswords.get(18));

        System.out.println(hashedPassword);

        for (int i = 0; i < hashedPassword.size(); i++) {
            for (int j = 0; j < mangledDictionary.size(); j++) {
                String s = mangledDictionary.get(j);
                String guessedPassword = jcrypt.crypt(salt[i], s);
                cracked = checkPassword(guessedPassword, hashedPassword.get(i), i, s);
                if(cracked) break;
//              indicates progress
                if((j % 1000000) == 0 ) System.out.print(".");
            }
        }
    }
}
