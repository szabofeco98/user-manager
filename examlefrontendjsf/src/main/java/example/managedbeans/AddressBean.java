package example.managedbeans;

import example.rest.client.RestClient;
import example.rest.model.Address;
import example.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SessionScoped
@ManagedBean
public class AddressBean {
    private List<Address> addresses;
    private User user;
    private Address address;

    @PostConstruct
    private void init() {
        address = new Address();
    }

    public void setUser(User user) {
        this.user = user;
        addresses = user != null ? RestClient.getAllAddressByUser(user.getId()) : new ArrayList<>();
    }

    public void addAddress() {
        if (user == null) {
            addresses.add(address);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cím hozzá adva:", address.toString()));
            address = new Address();
        } else {
            address.setUser(user);
            RestClient.addressPersist(address);
            addresses = RestClient.getAllAddressByUser(user.getId());
            PrimeFaces.current().ajax().update("addressesFrom:addresstable");
            PrimeFaces.current().executeScript("PF('addAddress').hide()");
            address = new Address();
        }
    }

    public void deleteAddress(Address address) {
        RestClient.deleteHandlerUser(address);
        addresses = RestClient.getAllAddressByUser(user.getId());
    }

    public void onAddressEdit(RowEditEvent<Address> event) {
        RestClient.updateHandler(event.getObject());
    }
}
