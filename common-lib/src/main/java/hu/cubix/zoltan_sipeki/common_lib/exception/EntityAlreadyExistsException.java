package hu.cubix.zoltan_sipeki.common_lib.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    
    public EntityAlreadyExistsException(String entity, String field, Object value) {
        super(String.format("Entity %s with \"%s\": %s already exists", entity, field, value.toString()));
    }
}
