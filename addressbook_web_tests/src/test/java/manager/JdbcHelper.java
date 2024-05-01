package manager;

import model.ContactData;
import model.GroupData;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper extends HelperBase {

    public JdbcHelper(ApplicationManager manager) { super(manager); }

    public List<GroupData> getGroupList() {
        var groups = new ArrayList<GroupData>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.createStatement();
             var result = statement.executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list")) {
             {
                while (result.next()) {
                groups.add(new GroupData()
                        .withId(result.getString("group_id"))
                        .withName(result.getString("group_name"))
                        .withHeader(result.getString("group_header"))
                        .withFooter(result.getString("group_footer")));
            }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    public List<ContactData> getContactList() {
        var contacts = new ArrayList<ContactData>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.createStatement();
             var result = statement.executeQuery("SELECT id, firstname, lastname,  mobile, email FROM addressbook")) {
            {
                while (result.next()) {
                    contacts.add(new ContactData()
                            .withId(result.getString("id"))
                            .withFirstName(result.getString("firstname"))
                            .withLastName(result.getString("lastname"))
                            .withMobilePhone(result.getString("mobile"))
                            .withEmail(result.getString("email"))
                            .withPhoto(""));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    public void createContact(ContactData contactData) {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.prepareStatement("INSERT INTO addressbook (id, firstname, lastname,  mobile, email) VALUES (?, ?, ?, ?, ?)")) {
            {
                statement.setString(1, contactData.id());
                statement.setString(2, contactData.firstName());
                statement.setString(3, contactData.lastName());
                statement.setString(4, contactData.mobilePhone());
                statement.setString(5, contactData.email());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getContactCount() {
        int count;
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.createStatement();
             var resultSet = statement.executeQuery("SELECT COUNT(id) as count_contacts FROM addressbook")) {
            {
                resultSet.next();
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    public int getContactInGroupCount() {
        int count;
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.createStatement();
             var resultSet = statement.executeQuery("SELECT COUNT(id) as count_contacts FROM address_in_groups")) {
            {
                resultSet.next();
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    public void checkConsistency() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement= conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT * FROM `address_in_groups` ag LEFT JOIN addressbook ab ON  ab.id = ag.id WHERE ab.id IS NULL")) {
            {
                if(result.next()) {
                    throw new IllegalStateException("DB is corrupted");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}