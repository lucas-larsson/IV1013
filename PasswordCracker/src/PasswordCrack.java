import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class PasswordCrack {

    public static ArrayList<String> hashedPasswords = new ArrayList<>();
    public static ArrayList<String> dictionary =  new ArrayList<>();
    public static Boolean[] crackedPasswords;
    public static ArrayList<String> hardToCrack =  new ArrayList<>();
    public static String[] salt = new String[21];

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


//          initialize the array to the size of the given passwords.
            crackedPasswords = new Boolean[hashedPasswords.size()];

//          Sets the values of the array to false;
            Arrays.fill(crackedPasswords, false);

//          Extract the salt (first two characters) from the hashed passwords
            Arrays.setAll(salt, i -> hashedPasswords.get(i).substring(0, 2));
//
//            I create a new ListArray to avoid trowing concurrent runtime exception.
            Mangle.commonWords(dictionary);
            dicAttack(dictionary, hashedPasswords);

//            Mangle.mangle2(mangledDictionary);
//            dicAttack2(dictionary);


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

    public static void dicAttack(ArrayList <String> mangledDictionary, ArrayList <String> passwords ) {
        System.out.println( " Number of dictionary words before mangle : [" + dictionary.size() + "]");
        System.out.println(" Number of dictionary words after mangle : [" + mangledDictionary.size()+ "]");
        double dif = ((double)dictionary.size() / mangledDictionary.size());
        System.out.println("The original dictionary is " + String.format("%.5g%n", dif) + " % of the original dictionary, compared in the number of words");
        boolean cracked;

        for (int i = 0; i < passwords.size(); i++) {
            for (int j = 0; j < mangledDictionary.size(); j++) {
                String s = mangledDictionary.get(j);
                String guessedPassword = jcrypt.crypt(salt[i], s);
                cracked = checkPassword(guessedPassword, passwords.get(i), i, s);
                if(cracked) break;
//              indicates progress
                if((j % 1000000) == 0 ) System.out.print(".");
            }
        }

        int bound = crackedPasswords.length;
        for (int i = 0; i < bound; i++) {
            if (!crackedPasswords[i]) {
                hardToCrack.add(passwords.get(i));
            }
        }

        System.out.println(Arrays.toString(crackedPasswords));

        if( Arrays.asList(crackedPasswords).contains(false)){
            dicAttack4Ever();
        }
    }

    private static boolean checkPassword(String guessedPassword, String hashedPassword, int index, String password) {

        if (guessedPassword.equals(hashedPassword)){
            crackedPasswords[index] = true;
            hardToCrack.remove(index);
            int number = index + 1;
            System.out.println(" \n Password number [" + number +"] is cracked");
            System.out.println(" The Hash is :" + guessedPassword);
            System.out.println(" The password is :" + password);
            return true;
        }
        return false;
    }

    public static void dicAttack4Ever() {

        System.out.println( " Number of dictionary words after mangle : [" + dictionary.size() + "]");
        try{
            if (hardToCrack.size() == 0) {return;}
            else {
                System.out.println("before");
                System.out.println(hardToCrack);
                System.out.println(Arrays.toString(crackedPasswords));
                dicAttack( Mangle.mangle(dictionary), hardToCrack );
                System.out.println("after");
                System.out.println(hardToCrack);
                System.out.println(Arrays.toString(crackedPasswords));
            }


        }catch (OutOfMemoryError e){
            System.err.println("now you are out of memory");
            System.exit(0);
        }catch (Exception e){
            System.err.println("some other exception");
            System.out.println(e.getMessage());
        }
    }
}
