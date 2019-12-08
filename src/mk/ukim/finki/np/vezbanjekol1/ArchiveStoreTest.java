package mk.ukim.finki.np.vezbanjekol1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class NonExistingItemException extends Exception {
    NonExistingItemException(String message) {
        super(message);
    }
}

abstract class Archive {
    private int id;
    private Date dateArchived;

    Archive(int id) {
        this.id = id;
    }

    void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    int getId() {
        return this.id;
    }

    public abstract String open(Date d1);
}

class LockedArchive extends Archive {
    private Date dateToOpen;

    LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    private Date getDateToOpen() {
        return dateToOpen;
    }

    @Override
    public String open(Date d1) {
        if (d1.before(dateToOpen)) {
            return "Item " + this.getId() + " cannot be opened before " + this.getDateToOpen().toString().replace("GMT", "UTC");
        } else {
            return "Item " + this.getId() + " opened at " + d1.toString().replace("GMT", "UTC");
        }
    }
}

class SpecialArchive extends Archive {
    private int maxOpen;
    private int totalOpen;

    SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.totalOpen = 0;
    }

    @Override
    public String open(Date d1) {
        if (totalOpen >= maxOpen) {
            return "Item " + this.getId() + " cannot be opened more than " + this.maxOpen + " times";
        } else {
            totalOpen++;
            return "Item " + this.getId() + " opened at " + d1.toString().replace("GMT", "UTC");
        }
    }
}

class ArchiveStore {
    private ArrayList<Archive> archiveList;
    private String log;

    ArchiveStore() {
        archiveList = new ArrayList<>();
        this.log = "";
    }

    void archiveItem(Archive item, Date date) {
        archiveList.add(item);
        item.setDateArchived(date);
        log += "Item " + item.getId() + " archived at " + date.toString().replace("GMT", "UTC") + "\n";
    }

    void openItem(int id, Date date) throws NonExistingItemException {
        Archive temp = null;
        for (Archive archive : archiveList) {
            if (archive.getId() == id) {
                temp = archive;
                log += temp.open(date) + "\n";
            }
        }

        if (temp == null) {
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist", id));
        }
    }

    String getLog() {
        return log;
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}