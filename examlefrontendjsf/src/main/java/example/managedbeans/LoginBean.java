package example.managedbeans;

import example.rest.client.RestClient;
import example.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@Getter
@Setter
public class LoginBean {
    private User user;

    @PostConstruct
    public void init() {
        user = new User();
    }

    @SneakyThrows
    public String login() {
        String response = RestClient.loginHandler(user);
        switch (response) {
            case "wrong username": {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! Rossz Felhasználó név", response));
                break;
            }
            case "wrong password": {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! Rossz Jelszó", response));
                break;
            }
            case "http Error": {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Hiba! Szerver hiba", response));
                break;
            }
            default: {

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                        .put("token", response);
                FacesContext.getCurrentInstance()
                        .getExternalContext().redirect("userlist.jsf");
                break;
            }
        }

        return "login";
    }
}
