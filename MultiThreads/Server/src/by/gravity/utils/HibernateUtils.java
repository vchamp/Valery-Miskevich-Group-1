package by.gravity.utils;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

import by.gravity.entity.Balance;
import by.gravity.entity.User;
import by.gravity.exception.NotEnoughMoneyException;
import by.gravity.exception.UserExistsException;
import by.gravity.exception.UserNotExistsException;

/**
 * Created by Ilya_Shknai on 08-Dec-14.
 */
public class HibernateUtils {

    private SessionFactory sessionFactory;

    private ServiceRegistry serviceRegistry;

    private static HibernateUtils instance;

    private String[] CURRENCIES = new String[]{"BYR","USD"};

    public static HibernateUtils getInstance() {
        if (instance == null) {
            instance = new HibernateUtils();
        }
        return instance;
    }

    public SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public boolean createUser(String userName) throws UserExistsException {
        SessionFactory factory = createSessionFactory();
        Session session = factory.openSession();

        try {
            if (getUser(session, userName) == null) {
                session.beginTransaction();

                User user = new User();
                user.setUserName(userName);
                session.save(user);
                Set<Balance> balances = new LinkedHashSet<>();
                for (int i = 0; i < CURRENCIES.length; i++) {
                    Balance balance = new Balance();
                    balance.setBalance("0");
                    balance.setCurrency(CURRENCIES[i]);
                    balances.add(balance);
                    balance.setUser(user);
                    session.save(balance);
                }

                session.getTransaction().commit();
            } else {
                throw new UserExistsException();
            }
        } finally {
            session.close();
        }

        return true;
    }

    private enum Operation {
        ADD, SUBTRACT
    }

    public String addMoney(String userName, double sum, String currency) throws UserNotExistsException{

        return updateBalance(userName, sum, currency, Operation.ADD);
    }

    public String subtractMoney(String userName, double sum, String currency) throws UserNotExistsException{

        return updateBalance(userName, sum, currency, Operation.SUBTRACT);
    }

    public String updateBalance(String userName, double sum, String currency, Operation operation) throws
            UserNotExistsException {
        SessionFactory factory = createSessionFactory();
        Session session = factory.openSession();
        User user = getUser(session, userName);
        if (user == null) {
            throw new UserNotExistsException();
        }

        session.beginTransaction();

        String resultBalance = "Ваш баланс ";

        LinkedHashSet<Balance> balances = new LinkedHashSet<>(user.getBalances());
        for (Balance balance : balances) {
            if (balance.getCurrency().equals(currency)) {
                double currentUserBalance = Double.parseDouble(balance.getBalance());
                if (operation == Operation.ADD) {
                    balance.setBalance(String.valueOf(currentUserBalance + sum));
                } else if (operation == Operation.SUBTRACT) {
                    balance.setBalance(String.valueOf(currentUserBalance - sum));
                }
            }

            resultBalance += " " + balance.getBalance() + " " + balance.getCurrency() + ";";
        }

        user.setBalances(balances);
        session.update(user);

        session.getTransaction().commit();

        return resultBalance;

    }

    public String getUserBalance(String userName) {
        SessionFactory factory = createSessionFactory();
        Session session = factory.openSession();
        String resultBalance = getUserBalance(session,userName);

        session.close();

        return resultBalance;
    }

    private String getUserBalance(Session session ,String userName){
        User user = getUser(session, userName);
        if (user == null) {
            return null;
        }

        String resultBalance = "Ваш баланс ";
        LinkedHashSet<Balance> balances = new LinkedHashSet<>(user.getBalances());
        for (Balance balance : balances) {
            resultBalance += " " + balance.getBalance() + " " + balance.getCurrency() + ";";
        }

        return resultBalance;
    }

    public String exchange(String userName, String from, String to, Double sum, Double rate) throws UserExistsException, UserNotExistsException,
            NotEnoughMoneyException {

        SessionFactory factory = createSessionFactory();
        Session session = factory.openSession();
        User user = getUser(session, userName);
        if (user == null) {
            throw new UserNotExistsException();
        }

        Double fromValue = 0.0;
        for(Balance balance : user.getBalances()){
            if(balance.getCurrency().equals(from)){
                fromValue = Double.parseDouble(balance.getBalance());
            }
        }

        if (fromValue < sum) {
            throw new NotEnoughMoneyException("Ваш баланс " + fromValue + " " + from);
        }
        session.close();

        subtractMoney(userName, sum, from);
        Double toValue = sum * rate;
        addMoney(userName, toValue, to);

        String balance =  getUserBalance(userName);
        return balance;
    }

    public User getUser(Session session, String userName) {
        Criteria criteria = session.createCriteria(User.class);
        Object user = criteria.add(Restrictions.eq("userName", userName))
                .uniqueResult();
        return (User) user;
    }
}
