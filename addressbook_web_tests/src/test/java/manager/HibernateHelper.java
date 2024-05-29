package manager;

import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateHelper extends HelperBase {

    private final SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                .buildSessionFactory();
    }

    static List<GroupData> convertListGroups(List<GroupRecord> records) {
        return records.stream().map(HibernateHelper::convertGroupDataToGroupRecord).collect(Collectors.toList());
    }

    private static GroupData convertGroupDataToGroupRecord(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convertGroupRecordToGroupData(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    public List<GroupData> getGroupList() {
        return convertListGroups(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    public Long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    public GroupData createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertGroupRecordToGroupData(groupData));
            session.getTransaction().commit();
        });
        return groupData;
    }

    public Long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    public ContactData createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertContactRecordToContactData(contactData));
            session.getTransaction().commit();
        });
        return contactData;
    }

    static List<ContactData> convertListContacts(List<ContactRecord> records) {
        return records.stream().map(HibernateHelper::convertContactDataToContactRecord).collect(Collectors.toList());
    }

    public List<ContactData> getContactList() {
        return convertListContacts(sessionFactory.fromSession(session -> {
            return (session.createQuery("from ContactRecord", ContactRecord.class).list());
        }));
    }

    private static ContactData convertContactDataToContactRecord(ContactRecord record) {
        return new ContactData(
                "" + record.id,
                record.firstName,
                record.lastName,
                record.mobilePhone,
                record.email,
                "",
                record.homePhone,
                record.workPhone,
                record.secondaryPhone,
                record.email2,
                record.email3,
                record.address);
    }

    private static ContactRecord convertContactRecordToContactData(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(
                Integer.parseInt(id),
                data.firstName(),
                data.lastName(),
                data.mobilePhone(),
                data.homePhone(),
                data.workPhone(),
                data.email(),
                data.email2(),
                data.email3(),
                data.address()
        );
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertListContacts(session.get(GroupRecord.class, group.id()).contacts);
        });
    }

    public Integer getContactsInGroupCount(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertListContacts(session.get(GroupRecord.class, group.id()).contacts).size();
        });
    }

    public String getListInfoOfContact(ContactData contact) {
        return Stream.of(
                        contact.address(),
                        contact.email(),
                        contact.email2(),
                        contact.email3(),
                        contact.homePhone(),
                        contact.mobilePhone(),
                        contact.workPhone(),
                        contact.secondaryPhone())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
    }

    public GroupData searchGroupForAddContact() {
        var allGroups = getGroupList();
        GroupData rightGroup = null;
        for (var group : allGroups) {
            var contactsCount = getContactCount();
            var contactsCountInGroup = getContactsInGroupCount(group);
            if (contactsCount > contactsCountInGroup) {
                rightGroup = group;
            }
        }
        return rightGroup;
    }

    public List<ContactData> deleteContactsInGroup(GroupData group) {
        List<ContactData> allContacts = getContactList();
        var contactsInGroup = getContactsInGroup(group);
        allContacts.removeAll(contactsInGroup);
        return allContacts;
    }
}
