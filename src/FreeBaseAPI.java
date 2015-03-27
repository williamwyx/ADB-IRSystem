import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class FreeBaseAPI {
	public static Properties properties = new Properties();

	private static ArrayList<String> interestedTypes = new ArrayList<String>(
			Arrays.asList("Person", "Author", "Actor", "Business Person",
					"League", "Sports Team"));

	private static InfoboxPrinter ipr = new InfoboxPrinter();

	public void infobox(String query) {
		try {
			getFBKey();
			JSONArray topics = searchFB(query);
			for (int i = 0; i < topics.length(); i++) {
				String mid = topics.getJSONObject(i).getString("mid");
				if (topicFB(mid))
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    public boolean QandA(String query){
        query = query.toLowerCase();
        if(!query.startsWith("who created "))
            return false;
        //check if query contains '?'
        if(query.contains("?") && (query.lastIndexOf('?') == query.length()-1))
            query = query.substring(12, query.length()-1);
        else
            query = query.substring(12);
        /**
         * Create query for author.
         */
        StringBuilder queryAuthor = new StringBuilder();
        queryAuthor.append("[{" +
                "\"/book/author/works_written\": [{" +
                "\"a:name\": null," +
                "\"name~=\": \"");
        queryAuthor.append(query + "\"");
        queryAuthor.append("}]," +
                "\"id\": null," +
                "\"name\": null," +
                "\"type\": \"/book/author\"" +
                "}]");
        //System.out.println(queryAuthor.toString());
        /**
         * Create query for business man.
         */
        StringBuilder queryBusiness = new StringBuilder();
        queryBusiness.append("[{" +
                "\"/organization/organization_founder/organizations_founded\": [{" +
                "\"a:name\": null," +
                "\"name~=\": \"");
        queryBusiness.append(query + "\"");
        queryBusiness.append("}]," +
                "\"id\": null," +
                "\"name\": null," +
                "\"type\": \"/organization/organization_founder\"" +
                "}]");

        JSONArray authors = queryFB(queryAuthor.toString());
        JSONArray businessPerson = queryFB(queryBusiness.toString());
        if(authors.length() == 0 && businessPerson.length() == 0) {
            System.out.println("It seems no one created " + query + "!!!");
            return true;
        }
        if(authors.length() > 0)
            extractAnswers(authors, "authors");
        if(authors.length() > 0)
            extractAnswers(authors, "businessman");

        return true;
    }

    private boolean extractAnswers(JSONArray answerArray, String type){
        HashMap<String, ArrayList<String>> answerHashMap = new HashMap<String, ArrayList<String>>(); //Key is author name or business name. arraylist stores books name or organization's name

        for(int i = 0; i < answerArray.length(); i++) {
            try {
                JSONObject entry = answerArray.getJSONObject(i);
                answerHashMap.put(entry.getString("name"), new ArrayList<String>()); //put the key into answers
                /**
                 * check the type and then put the book name or organization name into answers
                 */
                JSONArray names = null;
                if(type.equals("authors"))
                    names = entry.getJSONArray("/book/author/works_written");
                if(type.equals("businessman"))
                    names = entry.getJSONArray("/organization/organization_founder/organizations_founded");
                ArrayList<String> nameList = answerHashMap.get(entry.getString("name"));
                for(int j = 0; j < names.length(); j++){
                    JSONObject tmp = names.getJSONObject(j);
                    nameList.add(tmp.getString("a:name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        TreeMap<String, ArrayList<String>> answerTreeMap = new TreeMap<String, ArrayList<String>>(answerHashMap);

        return true;
    }

	private void getFBKey() {
		try {
			properties.load(new FileInputStream(
					"../Properties/freebase.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    private JSONArray queryFB(String query){
        JSONArray results = null;
        try {
            HttpTransport httpTransport = new NetHttpTransport();
            HttpRequestFactory requestFactory = httpTransport
                    .createRequestFactory();
            GenericUrl url = new GenericUrl(
                    "https://www.googleapis.com/freebase/v1/mqlread");
            url.put("query", query);
            url.put("key", properties.get("API_KEY"));
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse httpResponse = request.execute();
            JSONObject response = new JSONObject(new JSONTokener(
                    httpResponse.parseAsString()));
            // System.out.println(response.toString());
            results = response.getJSONArray("result");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }

	private JSONArray searchFB(String query) {
		JSONArray results = null;
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			GenericUrl url = new GenericUrl(
					"https://www.googleapis.com/freebase/v1/search");
			url.put("query", URLEncoder.encode(query, "UTF-8"));
			url.put("key", properties.get("API_KEY"));
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = new JSONObject(new JSONTokener(
					httpResponse.parseAsString()));
			// System.out.println(response.toString());
			results = response.getJSONArray("result");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return results;
	}

	private boolean topicFB(String mid) {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			GenericUrl url = new GenericUrl(
					"https://www.googleapis.com/freebase/v1/topic" + mid);
			url.put("key", properties.get("API_KEY"));
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = new JSONObject(new JSONTokener(
					httpResponse.parseAsString()));
			// System.out.println(response.toString());
			return parseAndDisplay(response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private boolean parseAndDisplay(JSONObject entity) throws JSONException {
		HashMap<String, String> FBTypes = EntityProperties.getFBTypes();

		boolean ret = false;
		JSONArray entityTypes = entity.getJSONObject("property")
				.getJSONObject("/type/object/type").getJSONArray("values");
		HashSet<String> validEntityTypes = new HashSet<String>();

		for (int i = 0; i < entityTypes.length(); i++) {
			String typePath = entityTypes.getJSONObject(i).getString("id");
			// Check if this entity has types we are interested
			if (FBTypes.containsKey(typePath)) {
				ret = true;
				// Add a valid type to the set
				validEntityTypes.add(FBTypes.get(typePath));
			}
		}

		if (ret) {
			// Get entity name and print it
			String name = entity.getJSONObject("property")
					.getJSONObject("/type/object/name").getJSONArray("values")
					.getJSONObject(0).getString("text");
			ipr.print();
			ipr.print(name, validEntityTypes);

			for (String interestedType : interestedTypes) {
				if (validEntityTypes.contains(interestedType)) {
					displayInfo(entity, interestedType);
				}
			}
			ipr.print();
		}
		return ret;
	}

	private void displayInfo(JSONObject entity, String iType) {
		HashMap<String, List<String>> types = EntityProperties.getTypes();
		HashMap<String, String> FBProperties = EntityProperties
				.getFBProperties();
		HashMap<String, List<String>> subTypes = EntityProperties.getSubTypes();

		// Get property list of this iType
		List<String> typeProperties = types.get(iType);
		// Iterate through each property this iType has
		for (String typeProperty : typeProperties) {
			String FBProperty = FBProperties.get(iType + "?" + typeProperty);
			try {
				// If the entity doesn't have this property, the exception will
				// be caught and ignored
				JSONArray propertyInfo = entity.getJSONObject("property")
						.getJSONObject(FBProperty).getJSONArray("values");
				// Check if this property has subtypes
				if (subTypes.containsKey(iType + "?" + typeProperty)) {
					for (int i = 0; i < propertyInfo.length(); i++) {
						ArrayList<String> values = new ArrayList<String>();
						// List of subtype names
						List<String> entitySubTypes = subTypes.get(iType + "?"
								+ typeProperty);
						for (String entitySubType : entitySubTypes) {
							String subTypePath = FBProperties.get(iType + "?"
									+ typeProperty + "?" + entitySubType);
							if (propertyInfo.getJSONObject(i)
									.getJSONObject("property").has(subTypePath)) {
								JSONArray subInfo = propertyInfo
										.getJSONObject(i)
										.getJSONObject("property")
										.getJSONObject(subTypePath)
										.getJSONArray("values");
								StringBuilder value = new StringBuilder();
								for (int j = 0; j < subInfo.length(); j++) {
									String tmp = null;
									if (subInfo.getJSONObject(j).has("value")) {
										tmp = subInfo.getJSONObject(j)
												.getString("value");
									} else {
										tmp = subInfo.getJSONObject(j)
												.getString("text");
									}
									if (j > 0)
										value.append(", ");
									value.append(tmp);
								}
								if (subTypePath.endsWith("from")) {
									String toPath = subTypePath.substring(0,
											subTypePath.length() - 4) + "to";
									if (propertyInfo.getJSONObject(i)
											.getJSONObject("property")
											.has(toPath)) {
										JSONArray toInfo = propertyInfo
												.getJSONObject(i)
												.getJSONObject("property")
												.getJSONObject(toPath)
												.getJSONArray("values");
										value.append("/" + toInfo.getJSONObject(0)
												.getString("text"));
									} else {
										value.append("/now");
									}
								}
								values.add(value.toString());
							} else {
								values.add("");
							}
						}
						if (i == 0) {
							ipr.print();
							ipr.print(typeProperty, entitySubTypes);
							ipr.print82();
						}
						ipr.print("", values);
					}
				}
				// otherwise
				else {
					for (int i = 0; i < propertyInfo.length(); i++) {
						String content = null;
						if (FBProperty.equals("/people/person/sibling_s")) {
							content = propertyInfo
									.getJSONObject(i)
									.getJSONObject("property")
									.getJSONObject(
											"/people/sibling_relationship/sibling")
									.getJSONArray("values").getJSONObject(0)
									.getString("text");
						} else if (FBProperty.equals("/people/person/spouse_s")) {
							content = propertyInfo.getJSONObject(i)
									.getJSONObject("property")
									.getJSONObject("/people/marriage/spouse")
									.getJSONArray("values").getJSONObject(0)
									.getString("text");
						} else if (propertyInfo.getJSONObject(i).has("value")) {
							content = propertyInfo.getJSONObject(i).getString(
									"value");
						} else {
							content = propertyInfo.getJSONObject(i).getString(
									"text");
						}
						// Print info of this property
						if (i == 0) {
							ipr.print();
							ipr.print(typeProperty, content);
						} else {
							ipr.print("", content);
						}
					}
				}
			} catch (JSONException e) {
			}
		}
	}
}
