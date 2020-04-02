package example.managedbeans;

import example.rest.client.RestClient;
import example.rest.model.Address;
import example.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class UserListBean {
    private List<User> users;
    private User user;
    private List<Address> addresses;
    private String newPass;

    @SneakyThrows
    @PostConstruct
    public void init() {
        try {
            newPass = "";
            users = RestClient.getAllUser();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
        }
    }

    public void onUserEdit(RowEditEvent<User> event) {
        if (!newPass.equals("")) {
            event.getObject().setPassword(newPass);
        }

        String resp = RestClient.updateHandler(event.getObject());
        if ("unique exception".equals(resp)) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "A felhasználó név foglalt!", ""));
        }
        init();
    }

    public void deleteUser(User user) {
        RestClient.deleteHandlerUser(user);
        init();
    }

    @SneakyThrows
    public void logOut() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("token");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");

    }

    public void setUser(User usr) {
        this.user = usr;
    }

}
