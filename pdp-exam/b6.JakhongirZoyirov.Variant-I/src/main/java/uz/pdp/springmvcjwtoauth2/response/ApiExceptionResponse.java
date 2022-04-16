package uz.pdp.springmvcjwtoauth2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {
    private int status;
    private String message;
}
