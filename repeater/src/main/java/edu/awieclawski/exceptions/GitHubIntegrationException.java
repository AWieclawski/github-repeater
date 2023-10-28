package edu.awieclawski.exceptions;

public class GitHubIntegrationException extends RuntimeException {

    private static final long serialVersionUID = -4285303318396233061L;

    public GitHubIntegrationException(String msg) {
        super(msg);
    }

}
