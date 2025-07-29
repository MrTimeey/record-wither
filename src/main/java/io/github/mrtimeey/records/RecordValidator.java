package io.github.mrtimeey.records;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

/**
 * Utility class for validating {@link java.lang.Record} classes
 * before they are used in reflection-based frameworks (e.g. {@code RecordWither}).
 *
 * <p>The validator ensures that:
 * <ul>
 *   <li>The given class is actually a {@code record}.</li>
 *   <li>The record is not a non-static nested type (which would require an enclosing instance).</li>
 *   <li>The canonical constructor of the record is accessible for reflective instantiation.</li>
 * </ul>
 *
 * <p>This class is intended to guard against common pitfalls when
 * working with records reflectively, especially in utility libraries
 * where records are instantiated without compile-time knowledge of their shape.
 *
 * <p><strong>Example usage:</strong>
 * <pre>{@code
 * RecordValidator.ensureWithRecord(MyRecord.class); // Throws if invalid
 * }</pre>
 *
 * @since 1.0.0
 */
public final class RecordValidator {

    private RecordValidator() {
        // Hide default constructor
    }

    /**
     * Ensures that the given class is a valid and reflectively instantiable {@code record}.
     *
     * <p>This method performs the following checks:
     * <ol>
     *   <li>Verifies the class is declared as a {@code record}.</li>
     *   <li>Checks that the record is not a non-static nested class (which would require an enclosing instance).</li>
     *   <li>Attempts to resolve and access the canonical constructor of the record.</li>
     * </ol>
     *
     * <p>If any of these checks fail, an appropriate {@link IllegalArgumentException},
     * {@link IllegalStateException}, or {@link RuntimeException} is thrown.
     *
     * @param recordClass the class to validate
     * @throws IllegalArgumentException if the class is not a record
     *                                  or if it is a non-static nested record
     * @throws IllegalStateException    if no canonical constructor can be found
     * @throws RuntimeException         if the constructor cannot be accessed
     */
    public static void ensureWithRecord(Class<?> recordClass) {
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException(recordClass + " is not a record.");
        }

        if (requiresEnclosingInstance(recordClass)) {
            throw new IllegalArgumentException("Nested record "
                    + recordClass.getName()
                    + " must be declared static to be reflectively instantiable.");
        }

        try {
            RecordComponent[] components = recordClass.getRecordComponents();
            Constructor<?> ctor = recordClass.getDeclaredConstructor(
                    Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));

            if (!ctor.canAccess(null)) {
                ctor.setAccessible(true);
            }

        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No canonical constructor found for record: " + recordClass.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException("Cannot access constructor of record: " + recordClass.getName(), e);
        }
    }

    /**
     * Determines whether a given class is a non-static nested record
     * that would require an enclosing instance for instantiation.
     *
     * <p>Records of this form cannot be safely instantiated reflectively,
     * so they must be declared {@code static} if nested inside another class.
     *
     * @param cls the class to check
     * @return {@code true} if the class is a member class and not declared {@code static}
     */
    public static boolean requiresEnclosingInstance(Class<?> cls) {
        return cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers());
    }
}
