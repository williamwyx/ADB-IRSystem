/**
 * Created by qshen on 3/22/15.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EntityProperties {

    public static HashMap<String, List<String>> getTypes(){
        return types;
    }

    public static HashMap<String, List<String>> getSubTypes(){
        return subTypes;
    }

    public static HashMap<String, String> getFBTypes(){
        return FBTypes;
    }

    public static HashMap<String, String> getFBProperties(){
        return FBProperties;
    }

	private static final HashMap<String, List<String>> types = new HashMap<String, List<String>>() {
		{
			put("Person",
					new ArrayList<String>(Arrays.asList("Name", "Birthday",
							"Place of Birth", "Date of Death", "Place of Death", "Cause of Death", "Siblings", "Spouses",
							"Description")));
			put("Author",
					new ArrayList<String>(Arrays.asList("Books", "Books About",
							"Influenced", "Influenced by")));
			put("Actor", new ArrayList<String>(Arrays.asList("Films")));
			put("Business Person",
					new ArrayList<String>(Arrays.asList("Leadership",
							"Board Member", "Founded")));
			put("League",
					new ArrayList<String>(Arrays.asList("Name", "Championship",
							"Sport", "Slogan", "Official Website",
							"Description", "Teams")));
			put("Sports Team",
					new ArrayList<String>(Arrays
							.asList("Name", "Description", "Sport", "Arena",
                                    "Championships", "Coaches", "Founded",
                                    "Leagues", "Locations", "Players Roster")));
		}
	};
	
	private static final HashMap<String, List<String>> subTypes = new HashMap<String, List<String>>() {
		{
            put("Actor?Films", new ArrayList<String>(Arrays.asList("Character", "Film Name")));
            put("Business Person?Leadership", new ArrayList<String>(Arrays.asList("Organization", "Role", "Title", "From-To")));
            put("Business Person?Board Member", new ArrayList<String>(Arrays.asList("Organization", "Role", "Title", "From-To")));
            put("Sports Team?Coaches", new ArrayList<String>(Arrays.asList("Name", "Position", "From-To")));
            put("Sports Team?Players Roster", new ArrayList<String>(Arrays.asList("Name", "Position", "Number", "From-To")));
		}
	};

	private static final HashMap<String, String> FBTypes = new HashMap<String, String>() {
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

	private static final HashMap<String, String> FBProperties = new HashMap<String, String>() {
		{
			// Person
			put("Person?Name", "/type/object/name");
			put("Person?Birthday", "/people/person/date_of_birth");
			put("Person?Place of Birth", "/people/person/place_of_birth");
			put("Person?Date of Death", "/people/deceased_person/date_of_death");
			put("Person?Place of Death", "/people/deceased_person/place_of_death");
			put("Person?Cause of Death", "/people/deceased_person/cause_of_death");
			put("Person?Siblings", "/people/person/sibling_s");
			put("Person?Spouses", "/people/person/spouse_s");
			put("Person?Description", "/common/topic/description");
			// Author
			put("Author?Books", "/book/author/works_written");
			put("Author?Books About", "/book/book_subject/works");
			put("Author?Influenced", "/influence/influence_node/influenced");
			put("Author?Influenced by", "/influence/influence_node/influenced_by");
			// Actor
			put("Actor?Films", "/film/actor/film");
			// Business Person
            put("Business Person?Leadership", "/business/board_member/leader_of");
            put("Business Person?Board Member", "/business/board_member/organization_board_memberships");
            put("Business Person?Founded", "/organization/organization_founder/organizations_founded");
            // League
            put("League?Name", "/type/object/name");
            put("League?Championship", "/sports/sports_league/championship");
            put("League?Sport", "/sports/sports_league/sport");
            put("League?Slogan", "/organization/organization/slogan");
            put("League?Official Website", "/common/topic/official_website");
            put("League?Description", "/common/topic/description");
            put("League?Teams", "/sports/sports_league/teams");
            // Sports Team
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
            // Sub-Types
            put("Actor?Films?Character", "/film/performance/character");
            put("Actor?Films?Film Name", "/film/performance/film");
            put("Business Person?Leadership?Organization", "/organization/leadership/organization");
            put("Business Person?Leadership?Role", "/organization/leadership/role");
            put("Business Person?Leadership?Title", "/organization/leadership/title");
            put("Business Person?Leadership?From-To", "/organization/leadership/from");
            put("Business Person?Board Member?Organization", "/organization/organization_board_membership/organization");
            put("Business Person?Board Member?Role", "/organization/organization_board_membership/role");
            put("Business Person?Board Member?Title", "/organization/organization_board_membership/title");
            put("Business Person?Board Member?From-To", "/organization/organization_board_membership/from");
            put("Sports Team?Coaches?Name", "/sports/sports_team_coach_tenure/coach");
            put("Sports Team?Coaches?Position", "/sports/sports_team_coach_tenure/position");
            put("Sports Team?Coaches?From-To", "/sports/sports_team_coach_tenure/from");
            put("Sports Team?Players Roster?Name", "/sports/sports_team_roster/player");
            put("Sports Team?Players Roster?Position", "/sports/sports_team_roster/position");
            put("Sports Team?Players Roster?Number", "/sports/sports_team_roster/number");
            put("Sports Team?Players Roster?From-To", "/sports/sports_team_roster/from");
		}
	};
}
