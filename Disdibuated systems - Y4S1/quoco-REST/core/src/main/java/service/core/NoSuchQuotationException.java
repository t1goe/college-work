import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.Httpstatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchQuotationException extends RuntimeException {
    static final long serialVersionUID = -6516152229878843037L;
} 