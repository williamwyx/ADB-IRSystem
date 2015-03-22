/**
 * Created by qshen on 3/22/15.
 */
import java.lang.reflect.Array;
import java.util.*;
public class EntityProperties {
    private static final HashMap<String, List<String>> types = new HashMap<String, List<String>>(){
        {
            put("Person", new ArrayList<String>(Arrays.asList("Name","Birthday","Place of Birth","Death","Siblings","Spouses","Description")));
            put("Author", new ArrayList<String>(Arrays.asList("Books","Books About","Influenced","Influenced by")));
            put("Actor", new ArrayList<String>(Arrays.asList("Films")));
            put("Business Person", new ArrayList<String>(Arrays.asList("Leadership","Board Member","Founded")));
            put("League", new ArrayList<String>(Arrays.asList("Name","Championship","Sport","Slogan","Official Website","Description","Teams")));
            put("Sports Team", new ArrayList<String>(Arrays.asList("Name", "Description", "Sport","Arena","Championships","Coaches","Founded","Leagues","Locations","Players Roster")));
        }
    };

    private static final HashMap<String, String> FBTypes = new HashMap<String, String>(){
        {
            put("/people/person", "Person");
            put("/book/author", "Author");
            put("/film/actor", "Actor");
            put("/tv/tv_actor", "Actor");
            put("/organization/organization_founder", "Business Person");
            put("/business/board_member", "Business Person");
            put("/sports/sports_league", "League");
            put("/sports/sports_team", "Sports Team");
            put("/sports/professional_sports_team", "Sports Team");
        }
    };



}
