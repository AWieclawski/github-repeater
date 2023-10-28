package edu.awieclawski.exceptions;

/**
 * This http error code field name must be the same
 * in properties of `edu.awieclawski.exception-handler` project / package
 */
public class GitHubErrorResponseException extends RuntimeException {

    private static final long serialVersionUID = -4285303318396233061L;

    private Integer errorCode;

    public GitHubErrorResponseException(String msg) {
        super(msg);
    }

    public GitHubErrorResponseException(String msg, Integer errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
