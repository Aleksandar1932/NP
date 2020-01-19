package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;

class SeatTakenException extends Exception {
    public SeatTakenException() {
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
    }
}

class Sector {
    String code;
    Integer seatsNumber;
    Set<Integer> takenSeats;
    Integer sectorType;

    public Sector(String code, Integer seatsNumber) {
        this.code = code;
        this.seatsNumber = seatsNumber;
        this.takenSeats = new HashSet<>();
        this.sectorType = -1;
    }

    public int getFreeSeats() {
        return seatsNumber - takenSeats.size();
    }

    public String getCode() {
        return code;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }


    public void buyTicket(int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        if (this.sectorType == -1 && type != 0) {
            //Ako na sektorot ne mu e definiran tipot, definiraj go;
            this.sectorType = type;
        }

        if (takenSeats.contains(seat)) {
            throw new SeatTakenException();
        } else {
            if (type == 0 || this.sectorType == type) {
                takenSeats.add(seat);
            } else {
                throw new SeatNotAllowedException();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%2.1f%%",
                this.code,
                this.getFreeSeats(),
                this.getSeatsNumber(),
                (1 - this.getFreeSeats() * 1.0 / getSeatsNumber() * 1.0) * 100
        );
    }
}

class Stadium {
    String name;
    Map<String, Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            sectors.put(sectorNames[i], new Sector(sectorNames[i], sizes[i]));
        }
    }

    void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        sectors.get(sectorName).buyTicket(seat, type);
    }

    public void showSectors() {
        sectors.values().stream()
                .sorted(Comparator
                        .comparingInt(Sector::getFreeSeats).reversed()
                        .thenComparing(Sector::getCode))
                .forEach(System.out::println);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
