/**
 * Created by qshen on 3/26/15.
 */
import java.util.*;
public class QandAPrinter {
    public static void printAnswer(TreeMap<String, ArrayList<String>> authorMap, TreeMap<String, ArrayList<String>> businessMap){
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
                printAuthor(aEntry, count);
                count++;
                if(authorIter.hasNext())
                    aEntry = authorIter.next();
                else
                    aEntry = null;
                continue;
            }
            //business man name is smaller than author name
            if(aEntry.getKey().compareTo(bEntry.getKey()) > 0){
                printBusiness(bEntry, count);
                count++;
                if(businessIter.hasNext())
                    bEntry = businessIter.next();
                else
                    bEntry = null;
                continue;
            }
            //author name and business name are the same
            if(aEntry.getKey().compareTo(bEntry.getKey()) == 0){
                printAuthor(aEntry, count);
                count++;
                printBusiness(bEntry, count);
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
            printAuthor(aEntry, count);
            count++;
            if(authorIter.hasNext())
                aEntry = authorIter.next();
            else
                aEntry = null;
        }
        while(bEntry != null){
            printBusiness(bEntry, count);
            count++;
            if(businessIter.hasNext())
                bEntry = businessIter.next();
            else
                bEntry = null;
        }
    }

    private static void printAuthor(Map.Entry<String, ArrayList<String>> aEntry, int count){
        ArrayList<String> nameList = aEntry.getValue();
        StringBuilder output = new StringBuilder();
        output.append(count + ". " + aEntry.getKey() + " (as Author) created <" + nameList.get(0) + ">");
        //if the author has more than one books, then print the remaining books' name
        for(int i=1; i < nameList.size(); i++)
            output.append(", <" + nameList.get(i) + ">");
        output.append("\n");
        System.out.print(output.toString());
    }

    private static void printBusiness(Map.Entry<String, ArrayList<String>> bEntry, int count){
        ArrayList<String> nameList = bEntry.getValue();
        StringBuilder output = new StringBuilder();
        output.append(count + ". " + bEntry.getKey() + " (as Businessperson) created <" + nameList.get(0) + ">");
        //if the author has more than one books, then print the remaining books' name
        for(int i=1; i < nameList.size(); i++)
            output.append(", <" + nameList.get(i) + ">");
        output.append("\n");
        System.out.print(output.toString());
    }
}
