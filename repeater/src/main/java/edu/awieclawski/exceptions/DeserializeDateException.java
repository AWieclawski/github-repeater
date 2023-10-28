package edu.awieclawski.exceptions;

public class DeserializeDateException extends java.io.IOException {
    private static final long serialVersionUID = 6415303318396233370L;

    public DeserializeDateException(String msg) {
        super(msg);
    }

}
