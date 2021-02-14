/*
 * Serena Wisneski Herter
 * 2/14/2021
 * CSC 340 HW #2: API Application
 * Dad Joke API Prototype
 * Prints JSON string as well as a setup and punchline for a randomly generated Dad Joke
 */
package dadjokeapiprototype;

// import libraries
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class DadJokeAPI {

    public static void getDadJoke() {
        //Create a HTTP Connection.
        String baseUrl = "https://dad-jokes.p.rapidapi.com";
        String callAction = "/random/joke";
        String apiKey = "1465a87b1fmsh4354ab3c3468a86p15867cjsn39dd8a681bc7";
        String urlString = baseUrl + callAction + "?rapidapi-key=" + apiKey + "&language=en-US";
        URL url;
        try {
            // Make the connection.
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Examine the response code.
            int status = con.getResponseCode();
            if (status != 200) {
                System.out.printf("Error: Could not load riddle: " + status);
            } else {
                // Parsing input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close the connections.
                in.close();
                con.disconnect();
                // Print out our complete JSON string.
                System.out.println("Output: " + content.toString());
                // Parse that object into a usable Java JSON object.
                JSONObject obj = new JSONObject(content.toString());
                // Get joke data array.
                JSONArray body = obj.getJSONArray("body");
                // initialize variables
                String setup = "";
                String punchline = "";
                // set values
                for (int i = 0; i < body.length(); ++i) {
                    JSONObject rec = body.getJSONObject(i);
                    setup = rec.getString("setup");
                    punchline = rec.getString("punchline");

                }
                // Print Setup and Punchline
                System.out.println("Dad Joke: \n" + setup + " \n" + punchline);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }

}
