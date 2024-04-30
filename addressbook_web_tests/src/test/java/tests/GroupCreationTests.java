package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    /*public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        var xml = Files.readString(Paths.get("groups.xml"));
        var mapper = new XmlMapper();
        var value = mapper.readValue(xml, new TypeReference<List<GroupData>>() {});
        result.addAll(value);
        return result;
    }*/

    public static List<GroupData> singleRandomGroup() {
        return List.of(new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30)));
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
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var maxId = newGroups.get(newGroups.size() - 1).id();

        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(groupData.withId(maxId));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
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
