package example.rest.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import example.rest.model.Address;
import example.rest.model.User;

import javax.faces.context.FacesContext;
import javax.ws.rs.core.HttpHeaders;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RestClient {
    private static String baseUrl = "http://localhost:9090/server/";

    private static Client client;

    private static Gson gson;

    private static ClientResponse response;

    static {
        gson = new Gson();
        client = Client.create();
    }

    public static String loginHandler(User user) {
        String json = gson.toJson(user);
        WebResource webResource = client.resource(baseUrl + "user/login");

        try {
            response = webResource.type("application/json")
                    .post(ClientResponse.class, json);
            methodValidator(response.getStatus());

        } catch (Exception e) {
            return "http Error";
        }
        return response.getEntity(String.class);
    }

    public static String registrationHandler(User user) {
        String json = gson.toJson(user);
        WebResource webResource = client.resource(baseUrl + "user/registration");

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .post(ClientResponse.class, json);
        methodValidator(response.getStatus());

        return response.getEntity(String.class);
    }

    public static List<User> getAllUser() {
        WebResource webResource = client.resource(baseUrl + "user/getAll");

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .get(ClientResponse.class);
        methodValidator(response.getStatus());

        String jsonResp = response.getEntity(String.class);
        Type users = new TypeToken<ArrayList<User>>() {
        }.getType();

        return gson.fromJson(jsonResp, users);
    }

    public static <T> String updateHandler(T obj) {
        String json = gson.toJson(obj);
        WebResource webResource = client.resource(baseUrl + obj.getClass().getSimpleName().toLowerCase() + "/update");

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .put(ClientResponse.class, json);
        methodValidator(response.getStatus());

        return response.getEntity(String.class);
    }

    public static <T> void deleteHandlerUser(T obj) {
        String json = gson.toJson(obj);
        WebResource webResource = client.resource(baseUrl + obj.getClass().getSimpleName().toLowerCase() + "/delete");

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .delete(ClientResponse.class, json);
        methodValidator(response.getStatus());
    }

    public static List<Address> getAllAddressByUser(Long userId) {
        WebResource webResource = client.resource(baseUrl + "address/getByUser/" + userId);

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .get(ClientResponse.class);
        methodValidator(response.getStatus());

        String jsonResp = response.getEntity(String.class);
        Type addresses = new TypeToken<ArrayList<Address>>() {
        }.getType();

        return gson.fromJson(jsonResp, addresses);
    }

    public static String addressPersist(Address address) {
        String json = gson.toJson(address);
        WebResource webResource = client.resource(baseUrl + "address/add");

        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .post(ClientResponse.class, json);
        methodValidator(response.getStatus());

        return response.getEntity(String.class);
    }

    public static User getUserByUsername(String username) {
        WebResource webResource = client.resource(baseUrl + "user/getUserByUsername/" + username);
        response = webResource.header(HttpHeaders.AUTHORIZATION, FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("token")).type("application/json")
                .get(ClientResponse.class);
        methodValidator(response.getStatus());

        String jsonResp = response.getEntity(String.class);
        return gson.fromJson(jsonResp, User.class);
    }

    private static void methodValidator(int statusCode) {
        if (!(statusCode + "").startsWith("2")) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + statusCode);
        }
    }
}
