package com.example.vaadin.services;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserService {

    private static final List<String> names = List.of(
            "Ace", "Ada", "Adam", "Aaron", "Akira", "Alex", "Alice", "Amber", "Amy",
            "Anja", "Anna", "Anton", "Ari", "Ashley", "Ava", "Ayumi", "Bea", "Beate",
            "Bella", "Ben", "Bernd", "Blake", "Bowen", "Brett", "Brian", "Bruce",
            "Carl", "Chang", "Chase", "Chika", "Chloe", "Colin", "Cole", "Craig",
            "Daiki", "David", "Dean", "Derek", "Diana", "Dylan", "Elena", "Eli",
            "Elias", "Elsa", "Emily", "Emiko", "Emma", "Eric", "Ernst", "Ethan",
            "Eva", "Evan", "Fay", "Felix", "Finn", "Frank", "Gabi", "Grace", "Grant",
            "Guy", "Haley", "Hans", "Harry", "Haruo", "Hazel", "Heidi", "Heinz",
            "Helga", "Henry", "Hiro", "Hoshi", "Huang", "Hugo", "Huan", "Ian",
            "Ichiro", "Ina", "Iris", "Isamu", "Ivy", "Jack", "Jacob", "James",
            "Jason", "Jay", "Jens", "Jiro", "Jonas", "Julia", "Kai", "Kaito",
            "Karin", "Kate", "Katja", "Keith", "Kelly", "Kenji", "Kevin", "Kim",
            "Kira", "Klara", "Klaus", "Kenzo", "Kyoko", "Lars", "Laura", "Leah",
            "Lena", "Leo", "Leon", "Lex", "Lia", "Liam", "Lily", "Ling", "Linus",
            "Lisa", "Liv", "Logan", "Louis", "Luca", "Lucy", "Luke", "Luna", "Lukas",
            "Mae", "Marco", "Maria", "Mark", "Mason", "Maya", "Max", "Meili", "Mia",
            "Midori", "Mila", "Milo", "Nadia", "Naoki", "Nina", "Noah", "Nora",
            "Nicole", "Oscar", "Owen", "Paige", "Paul", "Paula", "Paz", "Peter",
            "Petra", "Quinn", "Ray", "Reiko", "Rex", "Riley", "Rio", "Rose", "Roy",
            "Ruby", "Rue", "Ryan", "Ryu", "Sakura", "Sam", "Sara", "Sarah", "Satoshi",
            "Shane", "Sheng", "Simon", "Sky", "Sofia", "Sonja", "Sven", "Takeo",
            "Tim", "Tina", "Tom", "Tyler", "Uma", "Val", "Vera", "Wes", "Yas",
            "Yoshi", "Yuuki", "Zak", "Zara", "Zoe");

    private final Random random = new Random();

    public String getUsername() {
        return names.get(random.nextInt(names.size()));
    }
}
