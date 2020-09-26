package rs.miromaric.dotsandboxes.client.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rs.miromaric.dotsandboxes.client.settings.AppSettings;

/**
 *
 * @author miro
 */
public class WebCommunication {
    
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String API_URL_PROPERTY_NAME = "api.web.url";
    
    public <T> T post(Object payload, Class<T> type, String endpoint) throws Exception {
        String fullPath = AppSettings.getInstance().getProperty(API_URL_PROPERTY_NAME) + 
                          endpoint;
        
        System.out.println(fullPath);
        
        final ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(payload);
        System.out.println(jsonRequest);
        
        OkHttpClient okClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(jsonRequest, JSON);
        Request postRequest = new Request.Builder()
                .url(fullPath)
                .post(requestBody)
                .build();
        
        try(Response response = okClient.newCall(postRequest).execute()) {
            String jsonResponse =  response.body().string();
            return objectMapper.readValue(jsonResponse, type);
        }
    }
    
}
