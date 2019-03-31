package name.guyue.backend.controller;

import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.model.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<Void> handle(Exception e) {
        Response<Void> response = new Response<>();
        response.setStatus(ResponseStatusEnum.UnsolvedException);
        response.setMessage(e.getMessage());
        return response;
    }
}
