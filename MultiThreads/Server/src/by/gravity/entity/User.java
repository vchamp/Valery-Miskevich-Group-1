package by.gravity.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Ilya_Shknai on 08-Dec-14.
 */
@Entity
@Table(name = "users_table")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "userName")
    private String userName;

    @OneToMany(mappedBy = "user")
    private Set<Balance> balances;

    public User(Balance balance) {
        balance = balance;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Set<Balance> getBalances() {
        return balances;
    }

    public void setBalances(Set<Balance> balances) {
        this.balances = balances;
    }

}
