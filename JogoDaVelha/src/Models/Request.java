package Models;

import Models.Constants;

import java.io.Serializable;

public class Request implements Serializable {

    public String username;
    public int status = Constants.UNKNOWN_STATUS;

}
