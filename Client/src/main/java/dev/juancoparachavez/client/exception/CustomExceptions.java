package dev.juancoparachavez.client.exception;

public class CustomExceptions extends RuntimeException{

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String message) {
            super(message);
        }
    }

    public static class GenericException extends RuntimeException {
        public GenericException(String message) { super(message);}
    }
}

