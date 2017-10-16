import com.sun.corba.se.spi.ior.ObjectKey;
import jdk.nashorn.internal.parser.JSONParser;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.json.JSONStringer;
import us.monoid.web.AbstractContent;
import us.monoid.web.Content;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static us.monoid.web.Resty.data;
import static us.monoid.web.Resty.form;


public class Leaderboard {
    private final String url = "https://virtual-pet.firebaseio.com/";
    private Resty r;

    public Leaderboard() {
        r = new Resty();

    }

    public void ping() {
        try {

            JSONObject test = r.json(url + "Entries/0.json").object();
            System.out.println(test.get("Name") + " is alive");
        } catch (Exception e) {
            System.err.println("IOException : " + e.getMessage());
        }
    }

    public String listEntries() {
        try {
            StringBuilder message = new StringBuilder("Recent Scores: \n");

            JSONObject stem = r.json(url+"Entries.json").toObject();
//            System.out.println(stem.object());
            for(int i = 0; i < stem.length(); i++)
            {
//                System.out.println(stem.get(stem.names().getString(i)));
                JSONObject leaf = stem.getJSONObject(stem.names().getString(i));
                String name = leaf.getString("Name");
                int score = leaf.getInt("Score");
                message.append("Name: " + name  + " Score: " + score + "\n");
            }
            System.out.println(stem.names());
            System.out.println(stem.get(stem.names().getString(0)));

            return message.toString();

        } catch (Exception e) {
            System.err.println("IOException : " + e.getMessage());
            return "School wifi broke this";
        }

    }
    public void postEntry(String name, int score){
        try {

            JSONObject payload = new JSONObject().put("Name",name).put("Score",score);

            JSONResource response = r.json(url+"Entries.json", form(payload.toString()));

//            System.out.println(response);
        } catch (Exception e) {
            System.err.println("IOException : " + e.getMessage());
        }
    }
}