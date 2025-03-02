package hu.cubix.zoltan_sipeki.common_lib.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String... fieldValuePairs) {
        super(buildMessage(entity, fieldValuePairs));
    }

    private static String buildMessage(String entity, String... fieldValuePairs) {
        var builder = new StringBuilder();
        builder.append(entity);
        builder.append(" with ");
        for (int i = 0; i < fieldValuePairs.length; i += 2) {
            builder.append(fieldValuePairs[i]);
            builder.append(": ");
            builder.append(fieldValuePairs[i + 1]);
            if (i + 2 < fieldValuePairs.length) {
                builder.append(", ");
            }
        }
        builder.append(" not found");
        return builder.toString();
    }
}
