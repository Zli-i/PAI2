<template>
  <div id="app">
    <div class="table">
      <div class="card-place">
        <card
          v-for="(card, index) in fiveCards"
          :key="index"
          :card="card"
        ></card>
      </div>
    </div>
    <div class="players-section">
      <player
        v-for="p in players"
        :key="p.id"
        :name="p.name"
        :color="p.color"
        :class="['player-' + p.id]"
      ></player>
    </div>
  </div>
</template>

<script>
import Card from "./components/Card.vue";
import Player from "./components/Player.vue";

export default {
  name: "App",
  components: {
    card: Card,
    player: Player,
  },
  data() {
    return {
      players: [
        { id: 1, name: "Vesternesse", color: "blue" },
        { id: 2, name: "Ilijach", color: "yellow" },
        { id: 3, name: "Kędzior", color: "red" },
        { id: 4, name: "Andrzej", color: "green" },
      ],
      figures: ["P", "H", "C", "D"],
      values: [
        "A",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "J",
        "D",
        "K",
      ],
    };
  },
  computed: {
    cards() {
      let all = [];
      for (let figure of this.figures) {
        for (let value of this.values) {
          all.push({
            f: figure,
            v: value,
          });
        }
      }
      return all;
    },

    fiveCards() {
      let fives = [];
      for (let i = 0; i < 5; i++) {
        let rand_id = parseInt(Math.random() * this.cards.length);
        fives.push(this.cards[rand_id]);
      }
      return fives;
    },
  },
};
</script>

<style>
.table {
  width: 600px;
  height: 300px;
  background-color: #4aad4a;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  border-radius: 150px;
  position: absolute;
  border: 15px solid #a95555;
}

.card-place {
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  box-sizing: border-box;
}

.players-section {
  width: 600px;
  height: 300px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  z-index: 100;
}
.player-1 {
  top: 0px;
  left: 50%;
  transform: translatex(-50%) translatey(-50%);
}
.player-2 {
  bottom: 0px;
  left: 50%;
  transform: translatex(-50%) translatey(50%) rotatez(180deg);
}
.player-2 .name {
  transform: rotatez(180deg);
}

.player-3 {
  top: 50%;
  left: 0px;
  transform: translatex(-50%) translatey(-50%) rotatez(-90deg);
}
.player-3 .name {
  transform: rotatez(0deg);
}
.player-4 {
  top: 50%;
  right: 0px;
  transform: translatex(50%) translatey(-50%) rotatez(90deg);
}
.player-4 .name {
  transform: rotatez(0deg);
}
</style>
