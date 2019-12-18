package mk.ukim.finki.np.lab7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception {
    String roomName;

    public NoSuchRoomException(String roomName) {
        this.roomName = roomName;
    }
}

class NoSuchUserException extends Exception {
    String username;

    public NoSuchUserException(String username) {
        this.username = username;
    }
}

class ChatRoom {
    String roomName;
    Set<String> roomUsers;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.roomUsers = new TreeSet<>();
    }

    public void addUser(String username) {
        roomUsers.add(username);
    }

    public void removeUser(String username) {
        roomUsers.remove(username);
    }

    public boolean hasUser(String username) {
        return roomUsers.contains(username);
    }

    public int numUsers() {
        return roomUsers.size();
    }

    @Override
    public String toString() {
        if (roomUsers.isEmpty()) {
            return String.format("%s\n%s\n", this.roomName, "EMPTY");
        } else {
            return String.format("%s\n%s\n", roomName,String.join("\n", roomUsers));
        }
    }
}

class ChatSystem {
    Map<String, ChatRoom> chatRooms;
    Set<String> allUsers;


    //====== Room Management ======
    public ChatSystem() {
        this.chatRooms = new TreeMap<>();
        this.allUsers = new TreeSet<>();
    }

    public void addRoom(String roomName) {
        chatRooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) {
        chatRooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if (chatRooms.containsKey(roomName)) {
            return chatRooms.get(roomName);
        } else {
            throw new NoSuchRoomException(roomName);
        }
    }

    //====== User Management ======
    public void register(String userName) {
        allUsers.add(userName); //Register the user  in the system;
        //Get the room with smallest number of users;
        Optional<ChatRoom> roomToAdd = chatRooms.values()
                .stream()
                .min(Comparator.comparing(ChatRoom::numUsers));

        roomToAdd.ifPresent(chatRoom -> chatRoom.addUser(userName));
    }

    public void registerAndJoin(String userName, String roomName) {
        allUsers.add(userName);
        chatRooms.get(roomName).addUser(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {

        if (allUsers.contains(userName)) {
            if (chatRooms.containsKey(roomName)) {
                chatRooms.get(roomName).addUser(userName);
            } else {
                throw new NoSuchRoomException(roomName);
            }
        } else {
            throw new NoSuchUserException(userName);
        }

    }

    public void leaveRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (allUsers.contains(userName)) {
            if (chatRooms.containsKey(roomName)) {
                chatRooms.get(roomName).removeUser(userName);
            } else {
                throw new NoSuchRoomException(roomName);
            }
        } else {
            throw new NoSuchUserException(userName);
        }
    }

    public void followFriend(String userName, String friendUserName) throws NoSuchUserException {
        if (!allUsers.contains(userName)) {
            throw new NoSuchUserException(userName);
        }

        chatRooms.values().forEach(room -> {
            if (room.hasUser(friendUserName)) {
                room.addUser(userName);
            }
        });
    }
}

public class ChatSystemTest {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, params);
                    }
                }
            }
        }
    }
}
