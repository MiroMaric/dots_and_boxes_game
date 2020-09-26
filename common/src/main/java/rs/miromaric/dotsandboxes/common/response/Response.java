package rs.miromaric.dotsandboxes.common.response;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author miro
 */
public class Response implements Serializable {
    
    private ResponseStatus status;
    private String message;
    private Exception exception;
    private Object payload;

    public Response() {
    }

    
    public Response(ResponseStatus type, String message, Object payload) {
        this.status = type;
        this.message = message;
        this.payload = payload;
    }

    public Response(ResponseStatus status, String message, Exception exception, Object payload) {
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.payload = payload;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.message);
        hash = 89 * hash + Objects.hashCode(this.payload);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Response other = (Response) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.payload, other.payload)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Response{" + "type=" + status + ", message=" + message + ", payload=" + payload + ", exception=" + exception + '}';
    }
    
    
}
