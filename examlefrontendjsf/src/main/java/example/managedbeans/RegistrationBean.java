package example.managedbeans;

import example.rest.client.RestClient;
import example.rest.model.Address;
import example.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Getter
@Setter
@SessionScoped
public class RegistrationBean {
    private User user;

    private Address address;

    @PostConstruct
    private void init(){
        user=new User();
        user.setAddresses(new ArrayList<>());
        address=new Address();
    }

    public void registration() throws IOException {
        String response = RestClient.loginAndRegistrationHandler(user,"registration");
        switch (response){
            case "success":{
                reload();
                break;
            }
            case "unique exception":{
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! A felhasználó név már foglalt", response));
                break;
            }
            case "fatal error":{
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! Sikertelen Regisztráció", response));
                break;
            }
        }

        init();
    }

    public void addAddress(){
        user.getAddresses().add(address);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Cím hozzá adva!", address.toString()));
        address=new Address();
    }

    public void show(){
        init();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('adduser').show();");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
