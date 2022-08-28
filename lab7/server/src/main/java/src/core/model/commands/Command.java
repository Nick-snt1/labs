package src.core.model.commands;

/** Implementing this interface allows to operate with command like an objecs */
@FunctionalInterface
public interface Command<T> {

    /** Run current command*/
    T execute() throws Exception;
}
