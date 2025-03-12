package main.DAO;

import main.java_entities.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ClientDAO extends GenericDAO<Client,Integer> {
    public ClientDAO() {
        super(Client.class);
    }

    public List<Client> findByName(String name) {
        Session session = getCurrentSession();

        String hql = "FROM Client WHERE fullName LIKE :name";

        Query<Client> query = session.createQuery(hql, Client.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }
    public List<Client> findByPhone(String phone) {
        Session session = getCurrentSession();

        String hql = "FROM Client WHERE phone =:phone";

        Query<Client> query = session.createQuery(hql, Client.class);
        query.setParameter("phone",  phone);

        return query.list();
    }
}
