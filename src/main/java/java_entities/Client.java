package java_entities;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "contact_info", nullable = false, columnDefinition = "text[]")
    private String[] contactInfo;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
