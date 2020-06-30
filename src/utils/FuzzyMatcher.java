package src.utils;

public class FuzzyMatcher {
    public static int getEditDistance(String a, String b){

        a = a.toLowerCase();
        b = b.toLowerCase();

        if(a.equals(b)){
            return 0;
        }

        if(a.contains(b) || b.contains(a)){
            int diff = a.length()-b.length();
            return diff>0?diff:0-diff; //returns the absolute value of dif
        }
        return levenshtein(a, b);
    }

    public static int getBestMatchIndex(String query, String[] strings){
        int[] editDistances = new int[strings.length];
        for(int i = 0; i < strings.length; i++){
            editDistances[i] = getEditDistance(query, strings[i]);
        }

        int index = 0;
        int smallestEditDistance = editDistances[0];
        for(int i = 1; i < strings.length; i++){
            if(editDistances[i] < smallestEditDistance){
                smallestEditDistance = editDistances[i];
                index = i;
            }
        }
        return index;
    }

    private static int levenshtein(String a, String b){
        //geklaut von wikipedia
        //i wish i was using LISP right now

        //if one of the strings is empty, levenshtein distance is the length of the other string (all insert operations)
        if(a.length() == 0){
            return b.length();
        }
        if(b.length() == 0){
            return a.length();
        }
        //removes all leading characters that are the same
        //edit distances between "test123"/"test456" and "123"/"456" are the same
        if(a.charAt(0) == b.charAt(0)){
            return levenshtein(a.substring(1), b.substring(1));
        }

        //three operations are: replace, insert, delete:
        int replace = levenshtein(a.substring(1), b.substring(1)); //making the first characters equal is the same as deleting both first characters
        int insert = levenshtein(a, b.substring(1)); //deleting from string b is the same as inserting the first char from b as first character of a
        int delete = levenshtein(a.substring(1), b);

        int min = Math.min(Math.min(replace, insert), delete) + 1;
        return min;
    }
}
