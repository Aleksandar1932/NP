package mk.ukim.finki.np.lab2.zad2;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

abstract class Contact {
    private String date;

    public Contact(){}

    public Contact(String date) {
        this.date = date;
    }

    public Contact(Contact contact) {
        this.date = contact.date;
    }

    public String getDate() {
        return date;
    }

    public boolean isNewerThan(Contact c) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCurrent = sdf.parse(this.date);
        Date dateToCompare = sdf.parse(c.getDate());
        return dateCurrent.after(dateToCompare);
    }

    public abstract String getType();
    public abstract String getContact();
}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public EmailContact(EmailContact emailContact) {
        super(emailContact);
        this.email = emailContact.email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    @Override
    public String getContact() {
        return getEmail();
    }
}

enum Operator {
    TMOBILE, ONE, VIP;
}

class PhoneContact extends Contact {
    private String phone;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
    }

    public PhoneContact(PhoneContact phoneContact) {
        super(phoneContact);
        this.phone = phoneContact.phone;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        String prefix = phone.substring(2, 3);
        //Nema potreba da se zema pozivniot broj bidejki site se vo format 07X, kade sto X e ID za operator.

        if (prefix.equals("0") || prefix.equals("1") || prefix.equals("2")) {
            return Operator.TMOBILE;
        } else if (prefix.equals("5") || prefix.equals("6")) {
            return Operator.ONE;
        } else if (prefix.equals("7") || prefix.equals("8")) {
            return Operator.VIP;
        }
        return null;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    @Override
    public String getContact() {
        return getPhone();
    }

}

class Student {
    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;
    private Contact[] contacts;

    private int emailContacts;
    private int phoneContacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new Contact[0];
        this.emailContacts = 0;
        this.phoneContacts = 0;

    }

    public void addEmailContact(String date, String email) {
        Contact[] temp = new Contact[contacts.length + 1];
        int i;
        for (i = 0; i < contacts.length; i++) {
            temp[i] = contacts[i];
        }

        temp[i] = new EmailContact(date, email);
        contacts = temp;
        emailContacts++;
    }

    public void addPhoneContact(String date, String phone) {
        Contact[] temp = new Contact[contacts.length + 1];
        int i;
        for (i = 0; i < contacts.length; i++) {
            temp[i] = contacts[i];
        }

        temp[i] = new PhoneContact(date, phone);
        contacts = temp;
        phoneContacts++;
    }

    public Contact[] getEmailContacts() {
        Contact[] retEmail = new Contact[emailContacts];
        int retArrayCounter = 0;
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getType().equals("Email")) {
                retEmail[retArrayCounter] = new EmailContact((EmailContact) contacts[i]);
                retArrayCounter++;
            }
        }

        return retEmail;
    }

    public Contact[] getPhoneContacts() {
        Contact[] retPhone = new Contact[phoneContacts];
        int retArrayCounter = 0;
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getType().equals("Phone")) {
                retPhone[retArrayCounter] = new PhoneContact((PhoneContact) contacts[i]);
                retArrayCounter++;
            }
        }
        return retPhone;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        String retString = "";
        retString += this.firstName.toUpperCase() + this.lastName.toUpperCase();
        return retString;
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() throws ParseException {
        Contact newestContact = contacts[0];

        for (Contact contact : contacts) {
            if (contact.isNewerThan(newestContact)) {
                newestContact = contact;
            }
        }
        return newestContact;
    }

    private static String withSign(String a) {
        return "\"" + a + "\"";
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(withSign("ime")).append(":").append(withSign(firstName)).append(", ").append(withSign("prezime"));
        sb.append(":").append(withSign(lastName)).append(", ").append(withSign("vozrast")).append(":").append(age);
        sb.append(", ").append(withSign("grad")).append(":").append(withSign(city)).append(", ").append(withSign("indeks"));
        sb.append(":").append(index).append(", ").append(withSign("telefonskiKontakti")).append(":[");
        int counter =0;
        for (int i=0; i < contacts.length; ++i) {
            if (contacts[i].getType().equals("Phone")) {
                sb.append(withSign(contacts[i].getContact()));
                ++counter;
                if (counter < phoneContacts)
                    sb.append(", ");
            }
            if (counter == phoneContacts){
                sb.append("]");
                break;
            }
        }
        sb.append(", ");
        sb.append(withSign("emailKontakti")).append(":[");
        counter =0;
        for (int i=0; i < contacts.length; ++i) {
            if (contacts[i].getType().equals("Email")) {
                sb.append(withSign(contacts[i].getContact()));
                ++counter;
                if (counter < emailContacts)
                    sb.append(", ");
            }
            if (counter == emailContacts) {
                sb.append("]}");
                break;
            }

        }
        return sb.toString();
    }

    public int getNumberOfContacts() {
        return emailContacts + phoneContacts;
    }
}

class Faculty {
    private String name;
    private Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        int retCounter = 0;
        for (Student student : students) {
            if (student.getCity().equals(cityName)) {
                retCounter++;
            }
        }
        return retCounter;
    }

    public Student getStudent(long index) {
        for (Student student : students) {
            if (student.getIndex() == index) {
                return student;
            }
        }
        return null;
    }

    public double getAverageNumberOfContacts() {
        int sum = 0;
        for (int i = 0; i < students.length; i++) {
            sum += students[i].getNumberOfContacts();
        }
        return sum * 1.0 / students.length;
    }

    public Student getStudentWithMostContacts() {
        int maxContacts = students[0].getNumberOfContacts();
        int maxContactsIndex = 0;

        for (int i = 1; i < students.length; i++) {
            if (students[i].getNumberOfContacts() > maxContacts) {
                maxContacts = students[i].getNumberOfContacts();
                maxContactsIndex = i;
            }
        }
        return students[maxContactsIndex];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"fakultet\":\"").append(name).append("\", \"studenti\":[");
        for (int i=0; i<students.length; ++i){
            sb.append(students[i].toString());
            if (i+1 != students.length)
                sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}

public class ContactsTest {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
