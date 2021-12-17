package orted.firebasejwt.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuthenticateRequestBody implements Serializable {
    @NotNull(message = "Invalid phone")
    private String phone;
    @NotBlank(message = "invalid id token")
    private String idToken;

    public AuthenticateRequestBody() {
    }

    public AuthenticateRequestBody(String phone, String idToken) {
        this.phone = phone;
        this.idToken = idToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "AuthenticateRequestBody{" +
                "phone='" + phone + '\'' +
                ", idToken='" + idToken + '\'' +
                '}';
    }
}
