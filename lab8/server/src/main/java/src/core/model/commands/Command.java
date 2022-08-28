package src.core.model.commands;

@FunctionalInterface
public interface Command<T> {

    T execute() throws Exception;
}
