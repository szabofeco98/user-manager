package example.rest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private List<Address> addresses;
}
