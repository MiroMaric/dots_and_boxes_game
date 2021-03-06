package rs.miromaric.dotsandboxes.common.request;

import java.io.Serializable;
import java.util.Objects;
import rs.miromaric.dotsandboxes.common.domain.GeneralEntity;

/**
 *
 * @author miro
 */
public class Request implements Serializable{
    
    private RequestOperation operation;
    private GeneralEntity data;

    public Request() {
    }

    public Request(RequestOperation operation, GeneralEntity data) {
        this.operation = operation;
        this.data = data;
    }

    public RequestOperation getOperation() {
        return operation;
    }

    public void setOperation(RequestOperation operation) {
        this.operation = operation;
    }

    public GeneralEntity getData() {
        return data;
    }

    public void setData(GeneralEntity data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.operation);
        hash = 59 * hash + Objects.hashCode(this.data);
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
        final Request other = (Request) obj;
        if (this.operation != other.operation) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{operation=").append(operation);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
    
}
