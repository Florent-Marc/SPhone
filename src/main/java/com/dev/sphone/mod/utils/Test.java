package com.dev.sphone.mod.utils;

public class Test {

    public static String regex1 = "04@@.@@.@@.@@";
    private static String regex2 = "555-@@@@";
    private static String regex3 = "@@@@ @@@";
    private static String regex4 = "(@@@) @@@-@@@@";
    private static String regex5 = "06 @@ @@ @@ @@";

    public static void main(String[] args) {
        String n1 = "0478386239";

        System.out.println("Regex: "+regex1);
        System.out.println(format(regex1, n1)+"\n");
        System.out.println("Generated: number with regex");
        System.out.println(generate(regex1));
        System.out.println(generate(regex2));
        System.out.println(generate(regex3));
        System.out.println(generate(regex4));
        System.out.println(generate(regex5));
        System.out.println("\nUnformat number");
        System.out.println(unFormat(generate(regex4)));
        System.out.println("\nRegex patern");
        System.out.println(stringConstructor(regex1, "78322"));
    }


    //formate string with regex
    public static String format(String regex,String input) {
        String output = "";
        int i = 0;
        for (char c : regex.toCharArray()) {
            if (c == '@') {
                output += input.charAt(i);
                i++;
            } else {
                output += c;
            }
        }
        return output;

    }

    //generate random number with regex
    public static String generate(String regex) {
        String output = "";
        for (char c : regex.toCharArray()) {
            if (c == '@') {
                output += UtilsServer.getRandomNumber(0, 9);
            } else {
                output += c;
            }
        }
        return output;
    }

    //transform number to int and remove all non digit char
    public static String unFormat(String number){
        String output = "";
        for (char c : number.toCharArray()) {
            if (Character.isDigit(c)) {
                output += c;
            }
        }
        return output;
    }

    //transform input to regex
    public static String stringConstructor(String regex, String input) {
        String output = "";
        int i = -1;
        for (char c : regex.toCharArray()) {
            if (c == '@') {
                output += input.charAt(i);
                i++;
                if (i == input.length()) break;
            } else {
                output += c;
            }
        }
        return output;
    }

}
