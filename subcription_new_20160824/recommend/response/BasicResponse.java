package com.meizu.flyme.calendar.subcription_new.recommend.response;

/**
 * Author : Sven.J, created on 15-5-13.
 */
public class BasicResponse {
    private int code;
    private String message;
    private String redirect;

    /**
     *
     * @return
     *     The code
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @param code
     *     The code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     *     The redirect
     */
    public String getRedirect() {
        return redirect;
    }

    /**
     *
     * @param redirect
     *     The redirect
     */
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
