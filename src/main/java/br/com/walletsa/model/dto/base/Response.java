package br.com.walletsa.model.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Builder
public class Response<M> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("code")
    private Integer code = null;

    @JsonProperty("messages")
    private Collection<String> messages = null;

    @JsonProperty("result")
    private M result = null;

    public Response() {
        super();
    }

    public Response(String status, Integer code, Collection<String> messages, M result) {
        super();
        this.status = status;
        this.code = code;
        this.messages = messages;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Collection<String> getMessages() {
        return messages;
    }

    public void setMessages(Collection<String> messages) {
        this.messages = messages;
    }

    public M getResult() {
        return result;
    }

    public void setResult(M result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, messages, result, status);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Response other = (Response) obj;
        return Objects.equals(code, other.code) && Objects.equals(messages, other.messages)
                && Objects.equals(result, other.result) && Objects.equals(status, other.status);
    }

    @Override
    public String toString() {
        return "Response [code=" + code + ", messages=" + messages + ", result=" + result + ", status=" + status + "]";
    }

}
