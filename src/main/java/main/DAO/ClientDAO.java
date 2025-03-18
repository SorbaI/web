package main.DAO;

import main.java_entities.Order;
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
    public List<Client> searchClients(String name,String phone){
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("FROM Client b WHERE TRUE");
        if (name != null && !name.isEmpty()) {
            hql.append(" AND b.fullName LIKE:name");
        }
        if (phone != null && !phone.isEmpty()) {
            hql.append(" AND b.phone LIKE:phone");
        }

        Query<Client> query = session.createQuery(hql.toString(), Client.class);

        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            query.setParameter("phone", "%" + phone + "%");
        }
        return query.list();
    }
    public List<Order> ordersById(Integer clientId) {
        Session session = getCurrentSession();

        String hql = "FROM Order o WHERE o.client.clientId = :clientId";

        Query<Order> query = session.createQuery(hql, Order.class);

        query.setParameter("clientId",clientId);
        return query.list();
    }

}
