package games.bevs.library.commons.utils.mojang;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class MojangUtil {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
    public static final String FULL_PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

    public static MojangProfile[] getPartialProfile(String... names) throws Exception {
        List<MojangProfile> result = new ArrayList<>();

        // Construct payload
        JsonArray payload = new JsonArray();
        for (String name : names) {
            payload.add(new JsonPrimitive(name));
        }

        // Execute payload
        HttpURLConnection connection = createConnection();
        OutputStream stream = connection.getOutputStream();
        stream.write(GSON.toJson(payload).getBytes());
        stream.flush();
        stream.close();
        JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream()));
        JsonParser jp = new JsonParser();
        JsonArray response = jp.parse(reader).getAsJsonArray();

        // Process response
        for (JsonElement profile : response)
            result.add(GSON.fromJson(profile, MojangProfile.class));

        if (result.isEmpty())
            throw new IllegalArgumentException("No profile found.");

        return result.toArray(new MojangProfile[0]);
    }

    private static HttpURLConnection createConnection() throws Exception {
        URL url = new URL(PROFILE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    public static MojangProfile getProfile(UUID id) throws Exception {
        URL url = new URL(FULL_PROFILE_URL + id.toString().replace("-", "") + "?unsigned=false");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        JsonParser jp = new JsonParser();
        JsonObject response = jp.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
        return GSON.fromJson(response, MojangProfile.class);
    }

    public static UUID convertUuid(String id) {
        return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
    }
    
    public static void main(String[] args) {
        try {
            MojangProfile[] mps = getPartialProfile("LaakeCam");
            for (MojangProfile mp : mps){
                MojangProfile newP = getProfile(mp.getUUID());
                for (Property p : newP.getProperties()) {
                    System.out.println(p.name);
                    System.out.println("   Value: " + p.value);
                    System.out.println("   Signature: " + p.signature);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
