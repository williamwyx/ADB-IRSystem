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


    private static final HashMap<String, String> temp = new HashMap<String, String>() {
        {
            //Business Person
            put("Business Person?Leadership", "/business/board_member/leader_of");
            put("Business Person?Board Member", "/business/board_member/organization_board_memberships");
            put("Business Person?Founded", "/organization/organization_founder/organizations_founded");
            //League
            put("League?Name", "/type/object/name");
            put("League?Championship", "/sports/sports_league/championship");
            put("League?Sport", "/sports/sports_league/sport");
            put("League?Slogan", "/organization/organization/slogan");
            put("League?Official Website", "/common/topic/official_website");
            put("League?Description", "/common/topic/description");
            put("League?Teams", "/sports/sports_league/teams");
            //Sports Team
            put("Sports Team?Name", "/type/object/name");
            put("Sports Team?Description", "/common/topic/description");
            put("Sports Team?Sport", "/sports/sports_team/sport");
            put("Sports Team?Arena", "/sports/sports_team/arena_stadium");
            put("Sports Team?Championships", "/sports/sports_team/championships");
            put("Sports Team?Coaches", "/sports/sports_team/coaches");
            put("Sports Team?Founded", "/sports/sports_team/founded");
            put("Sports Team?Leagues", "/sports/sports_team/league");
            put("Sports Team?Locations", "/sports/sports_team/location");
            put("Sports Team?Players Roster", "/sports/sports_team/roster");
        }
    };

}
