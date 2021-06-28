package com.kasyno.kasyno.poker.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerInfo {

    private String name;
    private Long tokens;
    private String status;
    private List<String> deck = new LinkedList<String>();
}
