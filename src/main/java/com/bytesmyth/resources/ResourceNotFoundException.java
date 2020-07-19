package com.bytesmyth.resources;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String path) {
        super(String.format("Could not find resource '%s'", path));
    }

    public ResourceNotFoundException(String path, Throwable cause) {
        super(String.format("Could not find resource '%s'", path), cause);
    }
}
