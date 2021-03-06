package systems.comodal.jsoniter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import systems.comodal.jsoniter.factories.JsonIteratorFactory;

import java.math.BigDecimal;
import java.time.Instant;

import static java.math.BigDecimal.ZERO;
import static java.time.Instant.ofEpochSecond;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

final class TestFloat {

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#markableFactories")
  void testUnscaled(final JsonIteratorFactory factory) {
    assertEquals(12300L, factory.create("123").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("123.").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("123.0").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("123.00").readUnscaledAsLong(2));
    assertEquals(12345L, factory.create("123.45").readUnscaledAsLong(2));
    assertEquals(12345L, factory.create("123.456").readUnscaledAsLong(2));

    assertEquals(0L, factory.create("0.0").readUnscaledAsLong(2));
    assertEquals(0L, factory.create("0.00").readUnscaledAsLong(2));
    assertEquals(4L, factory.create("0.045").readUnscaledAsLong(2));
    assertEquals(45L, factory.create("0.45").readUnscaledAsLong(2));
    assertEquals(45L, factory.create("0.456").readUnscaledAsLong(2));

    assertEquals(12300L, factory.create("\"123\"").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("\"123.\"").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("\"123.0\"").readUnscaledAsLong(2));
    assertEquals(12300L, factory.create("\"123.00\"").readUnscaledAsLong(2));
    assertEquals(12345L, factory.create("\"123.45\"").readUnscaledAsLong(2));
    assertEquals(12345L, factory.create("\"123.456\"").readUnscaledAsLong(2));

    assertEquals(0L, factory.create("\"0.0\"").readUnscaledAsLong(2));
    assertEquals(0L, factory.create("\"0.00\"").readUnscaledAsLong(2));
    assertEquals(4L, factory.create("\"0.045\"").readUnscaledAsLong(2));
    assertEquals(45L, factory.create("\"0.45\"").readUnscaledAsLong(2));
    assertEquals(45L, factory.create("\"0.456\"").readUnscaledAsLong(2));

    var ji = factory.create("[\"123\",123]");
    assertEquals(123000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"123.\",123.]");
    assertEquals(123000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"123.0\",123.0]");
    assertEquals(123000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"123.00\",123.00]");
    assertEquals(123000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"123.45\",123.45]");
    assertEquals(123450L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123450L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"123.4567\",123.4567]");
    assertEquals(123456L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(123456L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.0\",0.0]");
    assertEquals(0L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(0L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.00\",0.00]");
    assertEquals(0L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(0L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.045\",0.045]");
    assertEquals(45L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(45L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.45\",0.45]");
    assertEquals(450L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(450L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.456\",0.456]");
    assertEquals(456L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(456L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"45e2\",45E2]");
    assertEquals(4500_000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(4500_000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"45e-2\",45E-2]");
    assertEquals(450L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(450L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.45e2\",0.45E2]");
    assertEquals(45_000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(45_000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"1.45e2\",1.45E2]");
    assertEquals(145_000L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(145_000L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"1.456789e2\",1.456789E2]");
    assertEquals(145_678L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(145_678L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"0.45e-2\",0.45E-2]");
    assertEquals(4L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(4L, ji.continueArray().readUnscaledAsLong(3));

    ji = factory.create("[\"1.45e-2\",1.45E-2]");
    assertEquals(14L, ji.openArray().readUnscaledAsLong(3));
    assertEquals(14L, ji.continueArray().readUnscaledAsLong(3));
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testStripTrailingZeroes(final JsonIteratorFactory factory) {
    assertNull(factory.create("\"\"").readBigDecimalDropZeroes());

    var expected = new BigDecimal("123.456");
    assertEquals(expected, factory.create("123.456").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.4560").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.45600").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.456000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.456000000000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0123.456").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0123.4560").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0123.45600").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0123.456000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0123.456000000000").readBigDecimalDropZeroes());

    assertEquals(new BigDecimal("123456"), factory.create("123456").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("1234560"), factory.create("1234560").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("12345600"), factory.create("12345600").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("123456000"), factory.create("123456000").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("123456000000000"), factory.create("123456000000000").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("123456"), factory.create("000123456").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("1234560"), factory.create("0001234560").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("12345600"), factory.create("00012345600").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("123456000"), factory.create("000123456000").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("123456000000000"), factory.create("000123456000000000").readBigDecimalDropZeroes());

    expected = new BigDecimal("0.123456");
    assertEquals(expected, factory.create("0.123456").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0.1234560").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0.12345600").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0.123456000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("0.123456000000000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("00.123456").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("00.1234560").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("00.12345600").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("00.123456000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("00.123456000000000").readBigDecimalDropZeroes());

    expected = new BigDecimal("123");
    assertEquals(expected, factory.create("123.").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.0").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.00").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("123.000000000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("000123.0").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("000123.00").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("000123.000").readBigDecimalDropZeroes());
    assertEquals(expected, factory.create("000123.000000000").readBigDecimalDropZeroes());

    assertEquals(ZERO, factory.create("0").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.0").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.00").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.000").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.000000000").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("00000").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("00000.").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("00000.0").readBigDecimalDropZeroes());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testReadMaxDouble(final JsonIteratorFactory factory) {
    var maxDouble = "1.7976931348623157e+308";
    assertEquals(maxDouble, factory.create(maxDouble).readNumberAsString());

    assertEquals(maxDouble, factory.create("\"1.7976931348623157e+308\"").readNumberOrNumberString());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testApplyNumberChars(final JsonIteratorFactory factory) {
    final CharBufferFunction<Instant> fractionalEpochParser = (buf, offset, len) -> {
      final int end = offset + len;
      long seconds = 0;
      for (char c; offset < end; ) {
        if ((c = buf[offset++]) == '.') {
          int i = end - offset;
          long nanoAdjustment = 0;
          do {
            nanoAdjustment *= 10;
            nanoAdjustment += Character.digit(buf[offset++], 10);
          } while (offset < end);
          while (i++ < 9) {
            nanoAdjustment *= 10;
          }
          return ofEpochSecond(seconds, nanoAdjustment);
        }
        seconds *= 10;
        seconds += Character.digit(c, 10);
      }
      return ofEpochSecond(seconds);
    };

    var factionalEpoch = "1567130406.123";
    var instant = factory.create(factionalEpoch).applyNumberChars(fractionalEpochParser);
    assertEquals(ofEpochSecond(1567130406L, 123_000_000L), instant);

    factionalEpoch = "1567130406.123456789";
    instant = factory.create(factionalEpoch).applyNumberChars(fractionalEpochParser);
    assertEquals(ofEpochSecond(1567130406L, 123_456_789L), instant);

    factionalEpoch = "1567130406";
    instant = factory.create(factionalEpoch).applyNumberChars(fractionalEpochParser);
    assertEquals(ofEpochSecond(1567130406L), instant);
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void test_positive_negative(final JsonIteratorFactory factory) {
    // positive
    assertEquals(12.3f, factory.create("12.3,").readFloat());
    assertEquals(729212.0233f, factory.create("729212.0233,").readFloat());
    assertEquals(12.3d, factory.create("12.3,").readDouble());
    assertEquals(729212.0233d, factory.create("729212.0233,").readDouble());
    // negative
    assertEquals(-12.3f, factory.create("-12.3,").readFloat());
    assertEquals(-12.3d, factory.create("-12.3,").readDouble());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void test_long_double(final JsonIteratorFactory factory) {
    assertEquals(4593560419846153055d, factory.create("4593560419846153055").readDouble());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void test_ieee_754(final JsonIteratorFactory factory) {
    assertEquals(0.00123f, factory.create("123e-5,").readFloat());
    assertEquals(0.00123d, factory.create("123e-5,").readDouble());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void test_decimal_places(final JsonIteratorFactory factory) {
    assertEquals(Long.MAX_VALUE, factory.create("9223372036854775807,").readFloat());
    assertEquals(Long.MAX_VALUE, factory.create("9223372036854775807,").readDouble());
    assertEquals(Long.MIN_VALUE, factory.create("-9223372036854775808,").readDouble());
    assertEquals(9923372036854775807f, factory.create("9923372036854775807,").readFloat());
    assertEquals(-9923372036854775808f, factory.create("-9923372036854775808,").readFloat());
    assertEquals(9923372036854775807d, factory.create("9923372036854775807,").readDouble());
    assertEquals(-9923372036854775808d, factory.create("-9923372036854775808,").readDouble());
    assertEquals(720368.54775807f, factory.create("720368.54775807,").readFloat());
    assertEquals(-720368.54775807f, factory.create("-720368.54775807,").readFloat());
    assertEquals(720368.54775807d, factory.create("720368.54775807,").readDouble());
    assertEquals(-720368.54775807d, factory.create("-720368.54775807,").readDouble());
    assertEquals(72036.854775807f, factory.create("72036.854775807,").readFloat());
    assertEquals(72036.854775807d, factory.create("72036.854775807,").readDouble());
    assertEquals(720368.547758075f, factory.create("720368.547758075,").readFloat());
    assertEquals(720368.547758075d, factory.create("720368.547758075,").readDouble());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void test_combination_of_dot_and_exponent(final JsonIteratorFactory factory) {
    assertEquals(Double.parseDouble("8.37377E9"), factory.create("8.37377E9").readDouble());
    assertEquals(Float.parseFloat("8.37377E9"), factory.create("8.37377E9").readFloat());
    assertEquals(Double.parseDouble("8.37377E9"), factory.create("8.37377E9").readFloat(), 1000d);
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testReadBigDecimalStripTrailingZeroesCornerCase(final JsonIteratorFactory factory) {
    final var json = "{\"U\":\"2019-02-25T02:57:39.118962Z\",\"f\":\"1\"}";
    final var ji = factory.create(json);
    assertEquals("2019-02-25T02:57:39.118962Z", ji.skipObjField().readString());
    assertEquals(BigDecimal.ONE, ji.skipObjField().readBigDecimalDropZeroes());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testBigDecimal(final JsonIteratorFactory factory) {
    assertNull(factory.create("\"\"").readBigDecimal());

    assertEquals(new BigDecimal("100.100"), factory.create("100.100").readBigDecimal());
    assertEquals(new BigDecimal("100.1"), factory.create("100.1000").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("100"), factory.create("100.000").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("1000"), factory.create("1000").readBigDecimalDropZeroes());
    assertEquals(BigDecimal.ONE.movePointRight(10).toPlainString(), factory.create("1e10").readBigDecimalDropZeroes().toPlainString());
    assertEquals(ZERO, factory.create("0000").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.000").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.0").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("0.").readBigDecimalDropZeroes());

    assertEquals(new BigDecimal("100.100"), factory.create("\"100.100\"").readBigDecimal());
    assertEquals(new BigDecimal("100.1"), factory.create("\"100.1000\"").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("100"), factory.create("\"100.000\"").readBigDecimalDropZeroes());
    assertEquals(new BigDecimal("1000"), factory.create("\"1000\"").readBigDecimalDropZeroes());
    assertEquals(BigDecimal.ONE.movePointRight(10).toPlainString(), factory.create("\"1e10\"").readBigDecimalDropZeroes().toPlainString());
    assertEquals(ZERO, factory.create("\"0000\"").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("\"0.000\"").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("\"0.0\"").readBigDecimalDropZeroes());
    assertEquals(ZERO, factory.create("\"0.\"").readBigDecimalDropZeroes());
  }

  @ParameterizedTest
  @MethodSource("systems.comodal.jsoniter.TestFactories#factories")
  void testInfinity(final JsonIteratorFactory factory) {
    assertEquals(Double.NEGATIVE_INFINITY, factory.create("\"-Infinity\"").readDouble());
    assertEquals(Float.NEGATIVE_INFINITY, factory.create("\"-Infinity\"").readFloat());
    assertEquals(Double.POSITIVE_INFINITY, factory.create("\"Infinity\"").readDouble());
    assertEquals(Float.POSITIVE_INFINITY, factory.create("\"Infinity\"").readFloat());
    assertEquals(Double.NaN, factory.create("\"NaN\"").readDouble());
    assertEquals(Float.NaN, factory.create("\"NaN\"").readFloat());
  }
}
