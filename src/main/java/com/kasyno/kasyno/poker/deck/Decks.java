package com.kasyno.kasyno.poker.deck;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Decks {

    private static String[] colors = new String[]{"S", "H", "C", "D"};
    private static String[] numbers = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K", "A"};

//    public static List<Card> getStandardDeck()
//    {
//        List<Card> cards = new LinkedList<>();
//
//        for (int i = 0; i < colors.length; i++)
//        {
//            for (int j = 0; j < numbers.length; j++)
//            {
//                cards.add(new Card(colors[i], numbers[j]));
//            }
//        }
//
//        return  cards;
//    }

    public static List<String> getStandardDeckS()
    {
        List<String> cards = new LinkedList<>();

        for (int i = 0; i < colors.length; i++)
        {
            for (int j = 0; j < numbers.length; j++)
            {
                cards.add(colors[i]+numbers[j]);
            }
        }

        Collections.shuffle(cards);

        return  cards;
    }
}
