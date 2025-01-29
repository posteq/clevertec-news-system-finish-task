package by.clevertec.commentapi.exception;

/**
 * Ошибка отсутствия комментария по id
 */

public class EntityFindException extends RuntimeException {
    public EntityFindException(Long id){
        super("Comment not found with id : " + id);
    }
}
