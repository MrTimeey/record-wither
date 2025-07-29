package io.github.mrtimeey.records;

/**
 * Marker interface for Java {@link Record records} that enables the usage of the
 * {@link RecordWither#with(Record, Getter, Object)} functionality as a default method.
 * <p>
 * By implementing {@code Withable}, a record automatically gains a convenient
 * {@link #with(Getter, Object)} method, which allows creating modified copies
 * of the record in an immutable way.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * public record User(String name, int age) implements Withable<User> {}
 *
 * User user = new User("Alice", 30);
 * User older = user.with(User::age, 31);
 * // older is now a new record instance with age=31, name="Alice"
 * }</pre>
 *
 * @param <T> the record type implementing this interface
 */
@SuppressWarnings("unchecked")
public interface Withable<T extends Record> {

    /**
     * Creates a copy of this record with the given component replaced by a new value.
     * <p>
     * The component to replace is identified via a method reference to its accessor.
     * All other components remain unchanged.
     *
     * @param getter the accessor method reference of the record component to replace
     * @param value the new value to set for the given component
     * @param <R> the type of the record component
     * @return a new record instance with the specified component replaced
     * @throws RuntimeException if the reflective instantiation of the record fails
     */
    default <R> T with(Getter<T, R> getter, R value) {
        return RecordWither.with((T) this, getter, value);
    }
}
