package ir.asta.wise.core.response;

import java.util.List;

public class ManageUserResponse {
    private List<UserResponseManager> manages;
    private List<UserAcceptResponse> accepts;

    public ManageUserResponse(List<UserResponseManager> manages, List<UserAcceptResponse> accepts) {
        this.manages = manages;
        this.accepts = accepts;
    }

    public ManageUserResponse(){

    }

    public List<UserResponseManager> getManages() {
        return manages;
    }

    public void setManages(List<UserResponseManager> manages) {
        this.manages = manages;
    }

    public List<UserAcceptResponse> getAccepts() {
        return accepts;
    }

    public void setAccepts(List<UserAcceptResponse> accepts) {
        this.accepts = accepts;
    }
}
