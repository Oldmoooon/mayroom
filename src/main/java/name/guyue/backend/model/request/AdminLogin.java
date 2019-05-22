package name.guyue.backend.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hujia
 * @date 2019-05-22
 */
@Setter
@Getter
@NoArgsConstructor
public class AdminLogin {
    Long id;
    String openGid;
    String password;
}
