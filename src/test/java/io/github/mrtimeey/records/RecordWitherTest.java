package io.github.mrtimeey.records;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecordWitherTest {

  public enum Status {
    ACTIVE,
    INACTIVE
  }

  public record TestRecord(
      String name, String description, int count, boolean active, double score, Status status)
      implements Withable<TestRecord> {}

  private final TestRecord base =
      new TestRecord("Test", "RecordWither", 3, true, 1.5, Status.ACTIVE);

  @Test
  void testWith_integerValue() {
    TestRecord result = base.with(TestRecord::count, 42);

    assertThat(base.count()).isEqualTo(3);
    assertThat(result)
        .isEqualTo(new TestRecord("Test", "RecordWither", 42, true, 1.5, Status.ACTIVE));
  }

  @Test
  void testWith_stringName() {
    TestRecord result = base.with(TestRecord::name, "Neu");

    assertThat(base.name()).isEqualTo("Test");
    assertThat(result)
        .isEqualTo(new TestRecord("Neu", "RecordWither", 3, true, 1.5, Status.ACTIVE));
  }

  @Test
  void testWith_stringDescription() {
    TestRecord result = base.with(TestRecord::description, "NeuDesc");

    assertThat(base.description()).isEqualTo("RecordWither");
    assertThat(result).isEqualTo(new TestRecord("Test", "NeuDesc", 3, true, 1.5, Status.ACTIVE));
  }

  @Test
  void testWith_booleanFlag() {
    TestRecord result = base.with(TestRecord::active, false);

    assertThat(base.active()).isTrue();
    assertThat(result)
        .isEqualTo(new TestRecord("Test", "RecordWither", 3, false, 1.5, Status.ACTIVE));
  }

  @Test
  void testWith_doubleScore() {
    TestRecord result = base.with(TestRecord::score, 9.99);

    assertThat(base.score()).isEqualTo(1.5);
    assertThat(result)
        .isEqualTo(new TestRecord("Test", "RecordWither", 3, true, 9.99, Status.ACTIVE));
  }

  @Test
  void testWith_enumStatus() {
    TestRecord result = base.with(TestRecord::status, Status.INACTIVE);

    assertThat(base.status()).isEqualTo(Status.ACTIVE);
    assertThat(result)
        .isEqualTo(new TestRecord("Test", "RecordWither", 3, true, 1.5, Status.INACTIVE));
  }
}
