package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupCreationTests extends TestBase {

    /*public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        var xml = Files.readString(Paths.get("groups.xml"));
        var mapper = new XmlMapper();
        var value = mapper.readValue(xml, new TypeReference<List<GroupData>>() {});
        result.addAll(value);
        return result;
    }*/

    public static Stream<GroupData> singleRandomGroup() {
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30));
        return Stream.generate(randomGroup).limit(5);

    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    public void createGroupTest(GroupData groupData) {
        var oldGroups = app.hbm().getGroupList();
        app.groups().createGroup(groupData);
        var newGroups = app.hbm().getGroupList();
        var maxId = newGroups.get(newGroups.size() - 1).id();
        var extraGroups = newGroups.stream().filter(g -> ! oldGroups.contains(g)).collect(Collectors.toList());
        var newId = extraGroups.get(0).id();

        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(groupData.withId(newId));
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroupTest(GroupData groupData) {
        var oldGroups = app.hbm().getGroupList();
        app.groups().createGroup(groupData);
        var newGroups = app.hbm().getGroupList();
        Assertions.assertEquals(oldGroups, newGroups);
    }
}
