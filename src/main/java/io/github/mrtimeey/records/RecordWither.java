package io.github.mrtimeey.records;

import java.io.Serializable;
import java.lang.Record;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;


/**
 * Utility class providing "wither"-style functionality for {@link java.lang.Record} types.
 *
 * <p>Records are immutable by design. The {@code RecordWither} enables creation of
 * new record instances where a single field is replaced with a new value while all
 * other fields remain unchanged.
 *
 * <p>This is achieved reflectively by:
 * <ul>
 *   <li>Extracting the field name from a method reference getter (e.g. {@code MyRecord::field}).</li>
 *   <li>Reading all component values of the record.</li>
 *   <li>Replacing the value of the targeted component.</li>
 *   <li>Invoking the canonical constructor with the modified values.</li>
 * </ul>
 *
 * <p><strong>Example usage:</strong>
 * <pre>{@code
 * public record Person(String name, int age) implements Withable<Person> {}
 *
 * Person alice = new Person("Alice", 30);
 * Person olderAlice = alice.with(Person::age, 31);
 *
 * // Result: Person[name=Alice, age=31]
 * }</pre>
 *
 * @since 1.0.0
 */
public final class RecordWither {

  private RecordWither() {
    // Hide default constructor
  }

  /**
   * Creates a new record instance based on an existing record, replacing
   * exactly one component value.
   *
   * <p>This method is intended to be used via the {@link Withable} interface
   * to allow a fluent, type-safe syntax such as:
   * <pre>{@code
   * MyRecord newRecord = oldRecord.with(MyRecord::field, newValue);
   * }</pre>
   *
   * @param record   the original record instance
   * @param getter   a method reference to the component accessor (e.g. {@code MyRecord::field})
   * @param newValue the replacement value for the given component
   * @param <T>      the record type
   * @param <R>      the component type
   * @return a new record instance with the modified value
   * @throws RuntimeException if reflective instantiation fails
   */
  @SuppressWarnings("unchecked")
  public static <T extends Record, R> T with(T record, Getter<T, R> getter, R newValue) {
    try {
      Class<?> recordClass = record.getClass();
      RecordValidator.ensureWithRecord(recordClass);
      String fieldName = extractFieldName(getter);
      RecordComponent[] components = recordClass.getRecordComponents();

      Object[] values = new Object[components.length];
      for (int i = 0; i < components.length; i++) {
        Method accessor = components[i].getAccessor();
        values[i] = accessor.invoke(record);
        if (components[i].getName().equals(fieldName)) {
          values[i] = newValue;
        }
      }

      Constructor<?> ctor =
          recordClass.getDeclaredConstructor(
              Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));

      if (!ctor.canAccess(null)) {
        ctor.setAccessible(true);
      }

      return (T) ctor.newInstance(values);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static SerializedLambda extractLambda(Serializable lambda) {
    try {
      Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
      writeReplace.setAccessible(true);
      return (SerializedLambda) writeReplace.invoke(lambda);
    } catch (Exception e) {
      throw new RuntimeException("Failed to extract SerializedLambda", e);
    }
  }

  private static String extractFieldName(Serializable getter) {
    SerializedLambda lambda = extractLambda(getter);
    String implMethodName = lambda.getImplMethodName();
    return methodToField(implMethodName);
  }

  private static String methodToField(String methodName) {
    if (methodName.startsWith("get") && methodName.length() > 3) {
      return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }
    return methodName;
  }
}
