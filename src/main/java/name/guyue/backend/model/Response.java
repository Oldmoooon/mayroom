package name.guyue.backend.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import name.guyue.backend.enums.ResponseStatusEnum;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Setter
@Getter
@NoArgsConstructor
public class Response <T> implements Serializable {
    private ResponseStatusEnum status;

    private String message;

    private T data;
}
