package io.github.mrtimeey.records;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Functional interface representing a serializable accessor for a record component.
 * <p>
 * {@code Getter} extends {@link Function} and {@link Serializable}, allowing
 * it to be used as a method reference to record component accessors in
 * combination with {@link Withable#with(Getter, Object)} or
 * {@link RecordWither#with(Record, Getter, Object)}.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * public record User(String name, int age) implements Withable<User> {}
 *
 * User user = new User("Alice", 30);
 * // Using a getter method reference
 * Getter<User, Integer> ageGetter = User::age;
 *
 * User older = user.with(ageGetter, 31);
 * }</pre>
 *
 * @param <T> the type of the record
 * @param <R> the type of the component accessed by this getter
 */
@FunctionalInterface
public interface Getter<T, R> extends Function<T, R>, Serializable {}
