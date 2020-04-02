package example.rest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
public class Address {
    private Long id;

    private String street;

    private Integer postalCode;

    private Integer houseNumber;

    private String city;

    private User user;
}
