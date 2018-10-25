package Models;

import Models.Constants;

import java.io.Serializable;

public class Request implements Serializable {
	// Auth parameters
    public String username;

    // Response parameters
    public int status = Constants.UNKNOWN_STATUS;

}
