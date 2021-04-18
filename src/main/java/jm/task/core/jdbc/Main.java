package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        List<User> users = new ArrayList<>();
        users.add(new User("Petr", "Ivanov", (byte) 32));
        users.add(new User("Ivan", "Petrov", (byte) 30));
        users.add(new User("Maria", "Ivanova", (byte) 22));
        users.add(new User("Daria", "Petrova", (byte) 20));

        userService.createUsersTable();

        for (User us : users) {
            userService.saveUser(us.getName(), us.getLastName(), us.getAge());
            System.out.println("User с именем - " + us.getName() + " добавлен в базу данных");
        }

        for (User us : userService.getAllUsers()) {
            System.out.println(us.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.getSessionFactory().close();
    }
}