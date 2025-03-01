package DAO;

import java_entities.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java_entities.Order;

import java.util.List;

@Repository
@Transactional
public class OrderDAO extends GenericDAO<Order,Integer> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAO() {
        super(Order.class);
    }
    @Transactional
    public List<Order> findByClient(Client client) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("FROM Order WHERE client = :client", Order.class);
        query.setParameter("client", client);
        return query.list();
    }
}