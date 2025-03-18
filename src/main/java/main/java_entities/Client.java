package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_client_id_seq") // Изменено!
    @SequenceGenerator(name = "clients_client_id_seq", sequenceName = "clients_client_id_seq", allocationSize = 1)
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId);
    }
}
