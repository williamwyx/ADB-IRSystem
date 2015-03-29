/**
 * Created by qshen on 3/26/15.
 */
import java.util.*;
public class QandAPrinter {
    public static void printAnswer(String query, TreeMap<String, ArrayList<String>> authorMap, TreeMap<String, ArrayList<String>> businessMap, boolean interactive){
        if(interactive)
            printHead(query);
        Iterator<Map.Entry<String, ArrayList<String>>> authorIter = null;
        Map.Entry<String, ArrayList<String>> aEntry = null;
        if(authorMap != null) {
            authorIter = authorMap.entrySet().iterator();
            aEntry = authorIter.next();
        }
        Iterator<Map.Entry<String, ArrayList<String>>> businessIter = null;
        Map.Entry<String, ArrayList<String>> bEntry = null;
        if(businessMap != null){
            businessIter = businessMap.entrySet().iterator();
            bEntry = businessIter.next();
        }
        int count = 1;
        while(aEntry != null && bEntry != null){
            //Author name is smaller than business man name
            if(aEntry.getKey().compareTo(bEntry.getKey()) < 0){
                printAuthor(aEntry, count, interactive);
                count++;
                if(authorIter.hasNext())
                    aEntry = authorIter.next();
                else
                    aEntry = null;
                continue;
            }
            //business man name is smaller than author name
            if(aEntry.getKey().compareTo(bEntry.getKey()) > 0){
                printBusiness(bEntry, count, interactive);
                count++;
                if(businessIter.hasNext())
                    bEntry = businessIter.next();
                else
                    bEntry = null;
                continue;
            }
            //author name and business name are the same
            if(aEntry.getKey().compareTo(bEntry.getKey()) == 0){
                printAuthor(aEntry, count, interactive);
                count++;
                printBusiness(bEntry, count, interactive);
                count++;
                if(authorIter.hasNext())
                    aEntry = authorIter.next();
                else
                    aEntry = null;
                if(businessIter.hasNext())
                    bEntry = businessIter.next();
                else
                    bEntry = null;
            }
        }
        while(aEntry != null){
            printAuthor(aEntry, count, interactive);
            count++;
            if(authorIter.hasNext())
                aEntry = authorIter.next();
            else
                aEntry = null;
        }
        while(bEntry != null){
            printBusiness(bEntry, count, interactive);
            count++;
            if(businessIter.hasNext())
                bEntry = businessIter.next();
            else
                bEntry = null;
        }
    }

    private static void printHead(String query){
        query = "Who created " + query + "?";
        printHyphen();
        int left = (98 - query.length()) / 2;
        int right = 98 - left - query.length();
        System.out.printf("|%-" + left + "s%s%-" + right + "s\n", "", query, "");
        printHyphen();

    }

    private static void printAuthor(Map.Entry<String, ArrayList<String>> aEntry, int count, boolean interactive){
        ArrayList<String> nameList = aEntry.getValue();
        if(!interactive) {
            StringBuilder output = new StringBuilder();
            output.append(count + ". " + aEntry.getKey() + " (as Author) created <" + nameList.get(0) + ">");
            //if the author has more than one books, then print the remaining books' name
            for (int i = 1; i < nameList.size(); i++)
                output.append(", <" + nameList.get(i) + ">");
            output.append("\n");
            System.out.print(output.toString());
        }
        else{
            System.out.printf("| %-20s", aEntry.getKey()+":");
            System.out.println("|As                                | Creation                                |");
            System.out.println("|                     -----------------------------------------------------------------------------");
            for (int i = 1; i < nameList.size(); i++){
                String bookname = nameList.get(i);
                if(bookname.length() > 39)
                    bookname = bookname.substring(0, 36) + "...";
                if(i == 1){
                    System.out.printf("|%-21s|Author                            |","");
                    System.out.printf(" %-39s |\n", bookname);
                }
                else{
                    System.out.print("|                     |                                  |");
                    System.out.printf(" %-39s |\n", bookname);
                }
            }
            printHyphen();
        }
    }

    private static void printBusiness(Map.Entry<String, ArrayList<String>> bEntry, int count, boolean interactive){
        ArrayList<String> nameList = bEntry.getValue();
        if(!interactive) {
            StringBuilder output = new StringBuilder();
            output.append(count + ". " + bEntry.getKey() + " (as Businessperson) created <" + nameList.get(0) + ">");
            //if the author has more than one books, then print the remaining books' name
            for (int i = 1; i < nameList.size(); i++)
                output.append(", <" + nameList.get(i) + ">");
            output.append("\n");
            System.out.print(output.toString());
        }
        else{
            System.out.printf("| %-20s", bEntry.getKey()+":");
            System.out.println("|As                                | Creation                                |");
            System.out.println("|                     -----------------------------------------------------------------------------");
            for (int i = 1; i < nameList.size(); i++){
                String organName = nameList.get(i);
                if(organName.length() > 39)
                    organName = organName.substring(0, 36) + "...";
                if(i == 1){
                    System.out.printf("|%-21s|Business Person                 |","");
                    System.out.printf(" %-39s |\n", organName);
                }
                else{
                    System.out.print("|                     |                                  |");
                    System.out.printf(" %-39s |\n", organName);
                }
            }
            printHyphen();
        }
    }

    private static void printHyphen(){
        System.out.println(" -------------------------------------------------------------------------------------------------- ");
    }
}
