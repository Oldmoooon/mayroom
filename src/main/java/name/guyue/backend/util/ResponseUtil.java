package name.guyue.backend.util;

import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.model.Response;

/**
 * @author hujia
 * @date 2019-03-25
 */
public class ResponseUtil {

    public static boolean isOk(Response response) {
        if (response == null) {
            return false;
        }
        return response.getStatus().equals(ResponseStatusEnum.Ok);
    }
}
