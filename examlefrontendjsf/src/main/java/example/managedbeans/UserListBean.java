package example.managedbeans;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import example.rest.client.RestClient;
import example.rest.model.Address;
import example.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class UserListBean {
    private List<User> users;
    private User user;
    private List<Address> addresses;

    @SneakyThrows
    @PostConstruct
    public void init() {
        addresses=user.getAddresses();
        try {
            users = RestClient.getAllUser();
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
        }
    }

    public void onUserEdit(RowEditEvent<User> event) {
        RestClient.updateHandler(event.getObject());
        init();
    }

    public void onAddEdit(RowEditEvent<Address> event) {
        RestClient.updateHandler(event.getObject());
        init();
    }

    public void deleteUser(User user) {
        RestClient.deleteHandlerUser(user);
        init();
    }

    public void deleteAddress( Address add) {
        user.getAddresses().remove(add);
        RestClient.updateHandler(user);
    }

    public void addAddress() {
        if (user.getAddresses() != null) {
            user.getAddresses().add(new Address());
        } else {
            user.setAddresses(new ArrayList<>());
            user.getAddresses().add(new Address());
        }
      //  RestClient.updateHandler(user);
      //  init();
        users.set(users.indexOf(user),user);
       // user=users.get(users.indexOf(user));
    }

    public void setUser(User usr){
        this.user = usr;
        addresses=user.getAddresses();
    }

}
