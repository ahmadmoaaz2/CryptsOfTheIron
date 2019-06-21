package ca.crypts;

public interface Authenticates {
    boolean rememberUser = false;
    String name = null;
    String password = null;
    void authenticateUser() throws Exception;
}
