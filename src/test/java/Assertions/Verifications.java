package Assertions;

import org.testng.asserts.SoftAssert;

import java.util.List;

public class Verifications {

    private static final SoftAssert softAssert = new SoftAssert();

    /**
     * assert if the value of Mandatory field is equal to expected.
     *
     * @param actualValue        the actual value of the field
     * @param expectedValue      the expected value of the field
     * @param mandatoryFieldName the name of the Mandatory field to be displayed in the logs (for example: "service id")
     */
    public static void verifyMandatoryValueEquals(Object actualValue, Object expectedValue, String mandatoryFieldName) {
        if (actualValue == null) {
            softAssert.fail(String.format("Mandatory Field: [%s] is Null", mandatoryFieldName));
        } else {
            softAssert.assertEquals(
                    actualValue,
                    expectedValue,
                    (String.format(
                            "Mandatory Field: [%s] Expected Value [%s] but Found [%s].",
                            mandatoryFieldName, expectedValue, actualValue)));

            System.out.println(String.format(
                    "Assert Mandatory Field: [%s] Expected value [%s] actual value [%s].",
                    mandatoryFieldName, expectedValue, actualValue));
        }
    }

    /**
     * assert if the value of Optional field is equal to expected.
     *
     * @param actualValue        the actual value of the field
     * @param expectedValue      the expected value of the field
     * @param optionalFieldName the name of the Optional field to be displayed in the logs (for example: "service id")
     */
    public static void verifyOptionalValueEquals(Object actualValue, Object expectedValue, String optionalFieldName) {
        if (actualValue != null) {
            softAssert.assertEquals(
                    actualValue,
                    expectedValue,
                    String.format(
                            "Optional Field: [%s] Expected [%s] but found [%s].",
                            optionalFieldName, expectedValue, actualValue));
        }
        System.out.println(String.format(
                "Assert Optional Field: [%s] Expected value [%s] actual value [%s].",
                optionalFieldName, expectedValue, actualValue));
    }

    /**
     * Assert if the values of Mandatory List is equal to expected List.
     * @param actualValue        the actual value of the List
     * @param expectedValue      the expected value of the List
     * @param mandatoryListName the name of the Mandatory List to be displayed in the logs (for example: "service id")
     */
    public static void verifyMandatoryListEquals(List<?> actualValue, List<?> expectedValue, String mandatoryListName) {
        if (actualValue.isEmpty()) {
            softAssert.fail(String.format("Mandatory List: [%s] is Null", mandatoryListName));
        } else {
            softAssert.assertEquals(
                    actualValue,
                    expectedValue,
                    (String.format(
                            "Mandatory List: [%s] Expected Values [%s] but Found [%s].",
                            mandatoryListName, expectedValue, actualValue)));

            System.out.println(String.format(
                    "Assert Mandatory List: [%s] Expected values [%s] actual values [%s].",
                    mandatoryListName, expectedValue, actualValue));
        }
    }

    /**
     * Assert if the values of Mandatory List doesn't contain specific value.
     * @param actualValue the actual value of the List
     * @param value the value that shouldn't be in the list
     * @param mandatoryListName the name of the Mandatory List to be displayed in the logs (for example: "service id")
     */
    public static void verifyMandatoryListNotContains(List<?> actualValue, Object value, String mandatoryListName) {
        if (actualValue.isEmpty()) {
            softAssert.fail(String.format("Mandatory List: [%s] is Null", mandatoryListName));
        } else {
            softAssert.assertTrue(
                    !actualValue.contains(value),
                    (String.format(
                            "Mandatory List: [%s] Expected to NOT contain [%s] but Found [%s].",
                            mandatoryListName, value, actualValue)));

            System.out.println(String.format(
                    "Assert Mandatory List: [%s] Expected to NOT contain [%s] in the actual values [%s].",
                    mandatoryListName, value, actualValue));
        }
    }

    /**
     * Assert if the values of Optional List is equal to expected List.
     * @param actualValue        the actual values of the List
     * @param expectedValue      the expected values of the List
     * @param optionalListName the name of the Optional List to be displayed in the logs (for example: "service id")
     */
    public static void verifyOptionalListEquals(List<?> actualValue, List<?> expectedValue, String optionalListName) {
        if (!actualValue.isEmpty()) {
            softAssert.assertEquals(
                    actualValue,
                    expectedValue,
                    String.format(
                            "Optional List: [%s] Expected [%s] but found [%s].",
                            optionalListName, expectedValue, actualValue));
        }
        System.out.println(String.format(
                "Assert Optional List: [%s] Expected values [%s] actual values [%s].",
                optionalListName, expectedValue, actualValue));
    }

    /**
     * used after all assertions in the test class (at the end of the assertions)
     */
    public static void assertAll() {
        softAssert.assertAll();
    }
}
