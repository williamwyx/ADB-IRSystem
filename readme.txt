=====================================================
a) Name and UNI

Name: Qiuyang Shen, Yuxuan Wang		
UNI: qs2147, yw2666


=====================================================
b) List of Files
├── bin
│   ├── EntityProperties$1.class
│   ├── EntityProperties$2.class
│   ├── EntityProperties$3.class
│   ├── EntityProperties$4.class
│   ├── EntityProperties.class
│   ├── FreeBaseAPI.class
│   ├── IRSystem.class
│   ├── InfoboxPrinter.class
│   └── QandAPrinter.class
├── lib
│   │───── libs
│   │  	    ├── google-http-client-1.18.0-rc.jar
│   │───── libs-sources
│   │       ├── google-http-client-1.18.0-rc-sources.jar
│   └── java-json.jar
├── readme.txt
├── run.sh
├── 6111Proj2.jar
└── src
    ├── EntityProperties.java
    ├── FreeBaseAPI.java
    ├── IRSystem.java
    ├── InfoboxPrinter.java
    └── QandAPrinter.java

=====================================================
c) How to run the program
./run.sh -key <Freebase API key> -q <query> -t <infobox|question>
./run.sh -key <Freebase API key> -f <file of queries> -t <infobox|question>
./run.sh -key <Freebase API key>

=====================================================
d) Internal Design
The system has two major parts. One is creating infobox according to query. Another one is Question Answering.

Infobox Creation:
	1. The implementation lies in two classes FreeBaseAPI and InfoboxPrinter.
	2. The query string is passed to a methoed called infobox in FreeBaseAPI class as input parameter.
	3. We use Freebase search API to get a list of mid. The function is called searchFB.
	4. For each mid in order, we use Freebase topic API to get information related to this mid. The function is called topicFB and parseAndDisplay.
	5. We first check if the types of this entity have overlaps with our interests. If so, we call a function called displayInfo and use the mapping decribed later to generate an infobox. We make the printing process as general as possible and deal with some special cases to mimic the format of reference implementation.
	6. Only the first valid entity will be printed.

Question Answering:
	1. The implementaion lies in two classes, FreeBaseAPI and QandAPrinter. 
	2. The query string is passed to a methoed called QandA in FreeBaseAPI class as input parameter. I first check if the query is valid. 
	3. Then I create two JSON objects to query FreeBase. One object queries Author, one boject queries BusinessPerson. 
	4. After getting the JSONArray returned by FreeBase, I pass them to a method called extractAnswers to extract the information we need. 
	5. The extractAnswers method return a ordered TreeMap whose key is the name of author or businessman, value is a list containing the books or organization that person created. 
	6. Finally I pass the two TreeMap to class QandAPrinter to output formated result. 

The mapping that I use to map from Freebase properties to the entity properties of interest that you return. They are manully annotated. The map is as follows. Every two strings in a bracket are in mapping relations.
	("Person?Name", "/type/object/name");
	("Person?Birthday", "/people/person/date_of_birth");
	("Person?Place of Birth", "/people/person/place_of_birth");
	("Person?Date of Death", "/people/deceased_person/date_of_death");
	("Person?Place of Death", "/people/deceased_person/place_of_death");
	("Person?Cause of Death", "/people/deceased_person/cause_of_death");
	("Person?Siblings", "/people/person/sibling_s");
	("Person?Spouses", "/people/person/spouse_s");
	("Person?Description", "/common/topic/description");
	// Author
	("Author?Books", "/book/author/works_written");
	("Author?Books About", "/book/book_subject/works");
	("Author?Influenced", "/influence/influence_node/influenced");
	("Author?Influenced by", "/influence/influence_node/influenced_by");
	// Actor
	("Actor?Films", "/film/actor/film");
	// Business Person
	("Business Person?Leadership", "/business/board_member/leader_of");
	("Business Person?Board Member", "/business/board_member/organization_board_memberships");
	("Business Person?Founded", "/organization/organization_founder/organizations_founded");
	// League
	("League?Name", "/type/object/name");
	("League?Championship", "/sports/sports_league/championship");
	("League?Sport", "/sports/sports_league/sport");
	("League?Slogan", "/organization/organization/slogan");
	("League?Official Website", "/common/topic/official_website");
	("League?Description", "/common/topic/description");
	("League?Teams", "/sports/sports_league/teams");
	// Sports Team
	("Sports Team?Name", "/type/object/name");
	("Sports Team?Description", "/common/topic/description");
	("Sports Team?Sport", "/sports/sports_team/sport");
	("Sports Team?Arena", "/sports/sports_team/arena_stadium");
	("Sports Team?Championships", "/sports/sports_team/championships");
	("Sports Team?Coaches", "/sports/sports_team/coaches");
	("Sports Team?Founded", "/sports/sports_team/founded");
	("Sports Team?Leagues", "/sports/sports_team/league");
	("Sports Team?Locations", "/sports/sports_team/location");
	("Sports Team?Players Roster", "/sports/sports_team/roster");
	// Sub-Types
	("Actor?Films?Character", "/film/performance/character");
	("Actor?Films?Film Name", "/film/performance/film");
	("Business Person?Leadership?Organization", "/organization/leadership/organization");
	("Business Person?Leadership?Role", "/organization/leadership/role");
	("Business Person?Leadership?Title", "/organization/leadership/title");
	("Business Person?Leadership?From-To", "/organization/leadership/from");
	("Business Person?Board Member?Organization", "/organization/organization_board_membership/organization");
	("Business Person?Board Member?Role", "/organization/organization_board_membership/role");
	("Business Person?Board Member?Title", "/organization/organization_board_membership/title");
	("Business Person?Board Member?From-To", "/organization/organization_board_membership/from");
	("Sports Team?Coaches?Name", "/sports/sports_team_coach_tenure/coach");
	("Sports Team?Coaches?Position", "/sports/sports_team_coach_tenure/position");
	("Sports Team?Coaches?From-To", "/sports/sports_team_coach_tenure/from");
	("Sports Team?Players Roster?Name", "/sports/sports_team_roster/player");
	("Sports Team?Players Roster?Position", "/sports/sports_team_roster/position");
	("Sports Team?Players Roster?Number", "/sports/sports_team_roster/number");
	("Sports Team?Players Roster?From-To", "/sports/sports_team_roster/from");

f) Your Freebase API Key
key = AIzaSyD7zn47zSINj5uiePDUl4jZ3L4fJsmSIeY
requests per second = 100

