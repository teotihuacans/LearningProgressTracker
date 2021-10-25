import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner in = new Scanner(System.in);

        List<Character> str = new ArrayList<>();
        List<Character> cipher = new ArrayList<>();
        String encode = "";
        String decode = "";

        for (Character ch : in.nextLine().toCharArray()) {
            str.add(ch);
        }

        for (Character ch : in.nextLine().toCharArray()) {
            cipher.add(ch);
        }

        for (Character ch : in.nextLine().toCharArray()) {
            encode += cipher.get(str.indexOf(ch));
        }

        for (Character ch : in.nextLine().toCharArray()) {
            decode += str.get(cipher.indexOf(ch));
        }

        System.out.println(encode + "\n" + decode);
    }
}