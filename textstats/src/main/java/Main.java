
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alexa
 */
public class Main {

    public static void main(String[] args) throws IOException {
        int count = 1;
        String URL = readStringFromURL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
        String lower = URL.toLowerCase();
        String noHTML = removeHTML(lower);
        ArrayList<String> wordList = biglist(noHTML);
            
        // format HashMap
        Map<String, Integer> finalMap = sorted(wordList);
        
       for (Map.Entry<String, Integer> en : finalMap.entrySet()) {
            System.out.println(count + ":" + "\"" + en.getKey().trim() + "\""+ " occured " +  en.getValue() + " times.");
        count++;
       }

    }
// read directly from url
    public static String readStringFromURL(String requestURL) throws IOException {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
    
    public static String removeHTML(String s) {
        //removes long dash
        String removed = s.replaceAll("mdash", " ");
        //removes html
        String html = removed.replaceAll("<[^>]*>", "");
        //removes special characters
        String noCharacters = html.replaceAll("\\p{P}", "");
   

        return noCharacters;

    }

    public static ArrayList<String> biglist(String d) {
       
        
        ArrayList<String> text = new ArrayList<>();
        //narrow down text file to just the poem
        String result = d.substring(d.indexOf("once "), d.lastIndexOf("nevermore") + 9);
        // removes instances of words connected by the previously removed commas 
        String[] werdz = result.split("\\W+");
        //adds to new list
        for (String x : werdz) {
            text.add("\n" + x);

        }

        return text;

    }

    public static Map<String, Integer> sorted(ArrayList<String> list) {
        //puts words of the poem into a hash map counting the frequency of each word
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {

            if (map.containsKey(list.get(i))) {
                map.put(list.get(i), map.get(list.get(i)) + 1);
            } else {
                map.put(list.get(i), 1);
            }
        }
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> hash
                = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        // Sort  list
        Collections.sort(hash, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                -> (o2.getValue()).compareTo(o1.getValue()));
        //new list with the resluts
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : hash) {
           
                temp.put(aa.getKey(), aa.getValue());
            
        }
        return temp;


    }

}
