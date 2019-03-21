package name.guyue.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hujia
 * @date 2019-03-21
 */
@Entity
@Setter
@Getter
public class DemoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    protected DemoUser() { }

    public DemoUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override public String toString() {
        return "DemoUser{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }
}
