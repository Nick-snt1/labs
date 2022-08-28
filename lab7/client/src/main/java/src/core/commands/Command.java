package src.core.commands;

@FunctionalInterface
public interface Command<T> {

    T execute() throws Exception;
}
