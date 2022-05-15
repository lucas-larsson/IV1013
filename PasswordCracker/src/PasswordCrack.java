import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PasswordCrack {

    public static ArrayList<String> hashedPasswords = new ArrayList<>();
//    CopyOnWriteArrayList is to be used in a Thread based environment
//    where read operations are very frequent and update operations are rare
//    from the
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
            Mangle.commonWords(dictionary); // remember to fix this

//          Extract the salt (first two characters) from the hashed passwords
            Arrays.setAll(salt, i -> hashedPasswords.get(i).substring(0, 2));

            ArrayList mangledDictionary = Mangle.mangle(dictionary);
            dicAttack(mangledDictionary);
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
        System.out.println(dictionary.size());
        System.out.println(mangledDictionary.size());
        for (int i = 0; i < 20; i++) {
            for (int j = 0, dictionarySize = mangledDictionary.size(); j < dictionarySize; j++) {
                String s = mangledDictionary.get(j);
                String guessedPassword = jcrypt.crypt(salt[i], s);
                boolean cracked = checkPassword(guessedPassword, hashedPasswords.get(i), i, s);
//                checkPassword
                if((j % 100000) == 0 ) System.out.print(".");
            }
        }
    }

    private static boolean checkPassword(String guessedPassword, String hashedPassword, int index, String s) {

        if (Objects.equals(guessedPassword, hashedPassword)){
            System.out.println("Password number [" + index +"] is cracked");
            System.out.println(" The password is :" + s);
            return true;
        }
        return false;
    }
}
