package DAO;

import java_entities.Client;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ClientDAO extends GenericDAO<Client,Integer> {
    public ClientDAO() {
        super(Client.class);
    }
}
