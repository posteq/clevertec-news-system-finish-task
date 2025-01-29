package by.clevertec.newsapi.exception;

/**
 * Ошибка отсутствия сущности по id
 */

public class EntityFindException extends RuntimeException {
    public EntityFindException(Long id){
        super("News not found with id : " + id);
    }
}
