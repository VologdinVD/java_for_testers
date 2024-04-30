package manager;

import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

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
        List<GroupData> result = new ArrayList<>();
        for (var record: records) {
            result.add(convertGroupDataToGroupRecord(record));
        }
        return result;
    }

    private static GroupData convertGroupDataToGroupRecord(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convertGroupRecordToGroupData(GroupData data) {
        var id = data.id();
        if("".equals(id)) {
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

    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertGroupRecordToGroupData(groupData));
            session.getTransaction().commit();
        });
    }

    public Long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    public void createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertContactRecordToContactData(contactData));
            session.getTransaction().commit();
        });
    }

    static List<ContactData> convertListContacts(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record: records) {
            result.add(convertContactDataToContactRecord(record));
        }
        return result;
    }

    public List<ContactData> getContactList() {
        return convertListContacts(sessionFactory.fromSession(session -> {
            return (session.createQuery("from ContactRecord", ContactRecord.class).list());
        }));
    }

    private static ContactData convertContactDataToContactRecord(ContactRecord record) {
        return new ContactData("" + record.id, record.firstName, record.lastName, record.mobilePhone, record.email, "");
    }

    private static ContactRecord convertContactRecordToContactData(ContactData data) {
        var id = data.id();
        if("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(Integer.parseInt(id), data.firstName(), data.lastName(), data.mobilePhone(), data.email());
    }

}
