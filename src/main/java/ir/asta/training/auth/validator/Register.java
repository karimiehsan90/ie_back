package ir.asta.training.auth.validator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class Register {
    @NotNull(message = "لطفا ایمیل خود را وارد نمایید")
    @Pattern(regexp = "^[a-z]([a-z0-9]|_[a-z0-9]|.[a-z0-9])+@[a-z0-9_]+([.][a-z0-9]+)+$", message = "ایمیل معتبر نیست")
    @FormParam("email")
    private String email;

    @NotNull(message = "لطفا نام خود را وارد نمایید")
    @FormParam("name")
    private String name;

    @NotNull(message = "لطفا پسورد را وارد نمایید")
    @Size(min = 6, message = "پسورد ضعیف است")
    @FormParam("password")
    private String password;

    @NotNull(message = "لطفا پسورد را دوباره وارد نمایید")
    @FormParam("re_password")
    private String re_password;

    @NotNull(message = "لطفا نقش خود را وارد نمایید")
    @Pattern(regexp = "^(student|teacher)$", message = "لطفا نقش خود را وارد نمایید")
    @FormParam("role")
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
