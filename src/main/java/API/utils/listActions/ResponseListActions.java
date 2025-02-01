package API.utils.listActions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Perform Actions on Response Lists
 */
public class ResponseListActions {

    /**
     * Extract "First Level" List of values from response. for example (list of "serviceId" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param firstLevelField name of the "First Level" field in json response that needs to be extracted as list.
     *                        it should be "value" not "list" or "object". for example ("serviceId").
     * @return list of values
     */
    private static List<?> extractList_helper(List<?> responseObjectList, String firstLevelField) {
        return responseObjectList.stream()
                .flatMap(obj -> {
                    try {
                        Field field = obj.getClass().getDeclaredField(firstLevelField);
                        field.setAccessible(true);
                        Object value = field.get(obj);

                        // Handle both List and Object cases for the first level
                        if (value instanceof List) {
                            return ((List<?>) value).stream(); // Flatten list directly
                        } else {
                            return Stream.of(value); // Single element stream for object
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        // Log the exception for debugging
                        System.err.println("Error accessing field: " + e.getMessage());
                        return Stream.ofNullable(null); // Return a stream containing null
                    }
                })
                .filter(Objects::nonNull) // Filter out null values
                .collect(Collectors.toList());
    }

    /**
     * Extract "Second Level" List of values from response. for example (list of "billingAccount.id" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param firstLevelField name of the "First Level" list or object in json response.
     *                        it should be "list" or "object". for example ("productCharacteristics").
     * @param secondLevelField name of the "Second Level" field in json response that needs to be extracted as list. for example ("id").
     *                         it should be "value" not "list" or "object"
     * @return list of values
     */
    private static List<?> extractList_helper(List<?> responseObjectList, String firstLevelField, String secondLevelField) {
        return responseObjectList.stream()
                .flatMap(obj -> {
                    try {
                        Field field = obj.getClass().getDeclaredField(firstLevelField);
                        field.setAccessible(true);
                        Object value = field.get(obj);

                        if (value instanceof List) {
                            return ((List<?>) value).stream()
                                    .flatMap(innerObj -> {
                                        try {
                                            Field innerField = innerObj.getClass().getDeclaredField(secondLevelField);
                                            innerField.setAccessible(true);
                                            Object innerValue = innerField.get(innerObj);

                                            // Handle both List and Object cases for the second level
                                            if (innerValue instanceof List) {
                                                return ((List<?>) innerValue).stream();
                                            } else {
                                                return Stream.of(innerValue); // Single element stream
                                            }
                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            System.err.println("Error accessing field: " + e.getMessage());
                                            return Stream.ofNullable(null); // Return a stream containing null
                                        }
                                    })
                                    .filter(Objects::nonNull); // Filter out null values
                        } else {
                            // Handle object case at the first level
                            if (value != null) {
                                Field innerField = value.getClass().getDeclaredField(secondLevelField);
                                innerField.setAccessible(true);
                                Object innerValue = innerField.get(value);
                                return Stream.of(innerValue); // Single element stream
                            } else {
                                return Stream.empty(); // Return empty stream for null values
                            }
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        System.err.println("Error accessing field: " + e.getMessage());
                        return Stream.ofNullable(null); // Return a stream containing null
                    }
                })
                .filter(Objects::nonNull) // Filter out null values from previous levels
                .collect(Collectors.toList());
    }

    /**
     * Extract "Third Level" List of values from response. for example (list of "productTerms.validFor.endDate" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param firstLevelField name of the "First Level" list or object in json response.
     *                        it should be "list" or "object". for example ("productTerms").
     * @param secondLevelField name of the "Second Level" field in json response that needs to be extracted as list. for example ("validFor").
     *                         it should be "list" or "object"
     * @param thirdLevelField name of the "Second Level" field in json response that needs to be extracted as list. for example ("endDate").
     *      *                 it should be "value" not "list" or "object"
     * @return list of values
     */
    private static List<?> extractList_helper(List<?> responseObjectList, String firstLevelField, String secondLevelField, String thirdLevelField) {
        return extractList_helper(
                extractList_helper(responseObjectList,firstLevelField, secondLevelField),
                thirdLevelField);
    }

    /**
     * Extract "Fourth Level" List of values from response. for example (list of "productTerms.validFor.endDate.value" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param firstLevelField name of the "First Level" list or object in json response.
     *                        it should be "list" or "object". for example ("productTerms").
     * @param secondLevelField name of the "Second Level" field in json response that needs to be extracted as list. for example ("validFor").
     *                         it should be "list" or "object"
     * @param thirdLevelField name of the "Third Level" field in json response that needs to be extracted as list. for example ("endDate").
     *                        it should be "list" or "object"
     * @param fourthLevelField name of the "Fourth Level" field in json response that needs to be extracted as list. for example ("value").
     *                        it should be "value" not "list" or "object"
     * @return list of values
     */
    private static List<?> extractList_helper(List<?> responseObjectList, String firstLevelField, String secondLevelField, String thirdLevelField, String fourthLevelField) {
        return extractList_helper(
                extractList_helper(responseObjectList,firstLevelField, secondLevelField,thirdLevelField),
                fourthLevelField);
    }

    /**
     * Extract "Fifth Level" List of values from response. for example (list of "productTerms.validFor.endDate.value.unit" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param firstLevelField name of the "First Level" list or object in json response.
     *                        it should be "list" or "object". for example ("productTerms").
     * @param secondLevelField name of the "Second Level" field in json response that needs to be extracted as list. for example ("validFor").
     *                         it should be "list" or "object"
     * @param thirdLevelField name of the "Third Level" field in json response that needs to be extracted as list. for example ("endDate").
     *                        it should be "list" or "object"
     * @param fourthLevelField name of the "Fourth Level" field in json response that needs to be extracted as list. for example ("value").
     *                        it should be "list" or "object"
     * @param fifthLevelField name of the "Fifth Level" field in json response that needs to be extracted as list. for example ("unit").
     *                        it should be "value" not "list" or "object"
     * @return list of values
     */
    private static List<?> extractList_helper(List<?> responseObjectList, String firstLevelField, String secondLevelField, String thirdLevelField,
                                              String fourthLevelField, String fifthLevelField) {
        return extractList_helper(
                extractList_helper(responseObjectList,firstLevelField, secondLevelField,thirdLevelField,fourthLevelField),
                fifthLevelField);
    }

    /**
     * used to count the json path attributes. for example "test.test1.test2" contains 3 attributes.
     */
    private static int countAttributes(String jsonPath) {
        String[] attributes = jsonPath.split("\\.");
        return attributes.length;
    }

    /**
     * Extract List of values from response using jsonPath. for example (list of "billingAccount.id" values).
     * @param responseObjectList response object list. for example ("productSelectorResponseList").
     * @param jsonPath json path to the desired attribute.
     *                 for example ("product.id") ("product.name.value") ("productTerms.validFor.endDate.value")
     *                 ("productTerms.validFor.endDate.value.unit")
     * @return list of values
     */
    public static List<?> extractList(List<?> responseObjectList, String jsonPath) {
        int attributeCount = countAttributes(jsonPath);

        return switch (attributeCount) {
            case 1 -> extractList_helper(responseObjectList, jsonPath);
            case 2 -> {
                String[] attributes2 = jsonPath.split("\\.", 2);
                yield extractList_helper(responseObjectList, attributes2[0], attributes2[1]);
            }
            case 3 -> {
                String[] attributes3 = jsonPath.split("\\.", 3);
                yield extractList_helper(responseObjectList, attributes3[0], attributes3[1], attributes3[2]);
            }
            case 4 -> {
                String[] attributes4 = jsonPath.split("\\.", 4);
                yield extractList_helper(responseObjectList, attributes4[0], attributes4[1], attributes4[2], attributes4[3]);
            }
            case 5 -> {
                String[] attributes5 = jsonPath.split("\\.", 5);
                yield extractList_helper(responseObjectList, attributes5[0], attributes5[1], attributes5[2], attributes5[3], attributes5[4]);
            }
            default -> {
                System.err.println("Invalid json path: " + jsonPath);
                yield Collections.emptyList();
            }
        };
    }

        /**
         * sort/order list. used when asserting two lists regardless of their order.
         * @param unsortedList
         * @return sorted list
         */
    public static List<?> sortList(List<?> unsortedList){
        return unsortedList.stream().sorted().toList();
    }

    /**
     * Extract list using filter. for example "$[*].productCharacteristic[?(@.name=='productType')].value".
     * this means get list of "productCharacteristic.value" where "productCharacteristic.name" == "productType".
     * @param referenceFilterList the reference filter list. "productCharacteristic.name" from the above example.
     * @param targetList the target list "before being filtered". "productCharacteristic.value" from above example.
     * @param filterValues the value of the filter. "productType" from above example.
     *                    it could be multiple values like this ("value1","value2)
     * @return filtered list based on specified filter.
     */
    public static List<?> extractFilteredList(List<?> referenceFilterList, List<?> targetList, String... filterValues) {
        return IntStream.range(0, referenceFilterList.size())
                .filter(i -> Arrays.asList(filterValues).contains(referenceFilterList.get(i)))
                .mapToObj(i -> targetList.get(i))
                .collect(Collectors.toList());
        //todo: supports Lists only for now (object not supported)
    }

    /**
     * Filter list using regex.
     * @param unfilteredList the list before being filtered. for example ["usageType": "data.NationaleRoaming"]
     * @param regex the regular expression. for example "([^.]+)" which means extract the value before the dot
     * @return filtered list based on specified regex.
     */
    public static List<?> filterListByRegex(List<?> unfilteredList, String regex) {
        return unfilteredList.stream().filter(obj -> obj instanceof String)
                .map(obj -> {
                    String str = (String) obj;
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(str);
                    return matcher.find() ? matcher.group(1) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}