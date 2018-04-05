package practic.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQuery(name = UsersEntity.FIND_USER,
        query = "select u from UsersEntity u where u.login =:login")
public class UsersEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String login;
    @Column
    private String password;
    @Column
    private String rights = "usr";

    public static final String FIND_USER = "UserEntity.FindUser";


    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user", fetch = FetchType.EAGER)
    private List<OrderEntity> userOrders;

    public UsersEntity(){};
    public UsersEntity(String login, String password){
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public List<OrderEntity> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<OrderEntity> userOrders) {
        this.userOrders = userOrders;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
