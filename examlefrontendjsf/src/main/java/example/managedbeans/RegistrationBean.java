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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class RegistrationBean {
    private User user;

    private PrimeFaces current;

    @ManagedProperty("#{addressBean}")
    private AddressBean addressBean;

    @PostConstruct
    private void init() {
        current = PrimeFaces.current();
        user = new User();
    }

    public void registration() throws IOException {
        String response = RestClient.registrationHandler(user);
        switch (response) {
            case "success": {
                user = RestClient.getUserByUsername(user.getUsername());
                for (Address address : addressBean.getAddresses()) {
                    address.setUser(user);
                    RestClient.addressPersist(address);
                }
                reload();
                break;
            }
            case "unique exception": {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! A felhasználó név már foglalt", response));
                break;
            }
            case "fatal error": {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Hiba! Sikertelen Regisztráció", response));
                break;
            }
        }

        init();
    }

    public void show() {
        addressBean.setUser(null);
        current.executeScript("PF('adduser').show();");
    }

    private void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
