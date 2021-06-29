<template>
    <div id="app">
    <div class="settings">
      <button class="setting-button"
        v-on:click="startGame"
      >
        Start Game
      </button>

      <v-btn 
        v-on:click="callGame"
      >

          Call

      </v-btn>

      <v-btn
        v-on:click="getInfo"
      >
        Get Info
      </v-btn>

      <v-btn
        v-on:click="endGame"
      >
        End Game
      </v-btn>

      <button class="setting-button" @click="rozdajKarty()">
        Rozdaj karty
      </button>
    </div>
    <div class="table">
      <div class="deck-of-cards"></div>
    </div>
    <div class="players-section">
      <player
        v-if="playersList.player1"
        :name="playersList.player1.name"
        :bank_value="playersList.player1.tokens"
        :class="['player-' + 1]"
      >
      </player>
      <player
        v-if="playersList.player2"
        :name="playersList.player2.name"
        :bank_value="playersList.player2.tokens"
        :class="['player-' + 2]"
      >
      </player>
      <player
        v-if="playersList.player3"
        :name="playersList.player3.name"
        :bank_value="playersList.player3.tokens"
        :class="['player-' + 3]"
      >
      </player>
      <player
        v-if="playersList.player4"
        :name="playersList.player4.name"
        :bank_value="playersList.player4.tokens"
        :class="['player-' + 4]"
      >
      </player>
      <div
        v-if="playersList.player1"
        :class="['player-' + 1 + '-card-place']"
      >
        <v-row v-if="playersList.player1.deck">
          <v-card
            v-for="(card, index) in playersList.player1.deck"
            :key="index"
            width="50"
            height="70"
          >
            <v-card-text>
              {{card}}
            </v-card-text>
          </v-card>
        </v-row>
      </div>
      <div
        v-if="playersList.player2"
        :class="['player-' + 2 + '-card-place']"
      >
        <v-row v-if="playersList.player2.deck">
          <v-card
            v-for="(card, index) in playersList.player2.deck"
            :key="index"
            width="50"
            height="70"
          >
            <v-card-text>
              {{card}}
            </v-card-text>
          </v-card>
        </v-row>
      </div>
      <div
        v-if="playersList.player3"
        :class="['player-' + 3 + '-card-place']"
      >
        <v-row v-if="playersList.player3.deck">
          <v-card
            v-for="(card, index) in playersList.player3.deck"
            :key="index"
            width="50"
            height="70"
          >
            <v-card-text>
              {{card}}
            </v-card-text>
          </v-card>
        </v-row>
      </div>
      <div
        v-if="playersList.player4"
        :class="['player-' + 4 + '-card-place']"
      >
        <v-row v-if="playersList.player4.deck">
          <v-card
            v-for="card in playersList.player4.deck"
            :key="card"
            width="50"
            height="70"
          >
            <v-card-text>
              {{card}}
            </v-card-text>
          </v-card>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
//import Card from "../components/Card.vue";
import Player from "../components/Player.vue";
import axios from 'axios'
import endpoint from '@/endpoint.json';

export default {
  name: "App",
  components: {
    player: Player,
  },
  data() {
    return {
      playing_cards: [],
      players_in_game: 4,
      players: [
        { id: 1, name: "Player-1", color: "blue", bank_value: 30, deck: [] },
        { id: 2, name: "Player-2", color: "yellow", bank_value: 31, deck: [] },
        { id: 3, name: "Player-3", color: "red", bank_value: 32, deck: [] },
        { id: 4, name: "Player-4", color: "green", bank_value: 33, deck: [] },
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
      playersList : []
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
  },
  methods: {
    /*
    pobierzLiczbeGraczy();
    pobierzDaneGraczy();
     */
    async startGame()
    {
      await axios.post(`${endpoint.url}/games/start?Id=` + this.$route.query.room_id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                  console.log(response);
                }
            ).catch(
                (e) => {
                    console.log(e);
                }
            )
    },

    async callGame()
    {
      await axios.post(`${endpoint.url}/games/call?Id=` + this.$route.query.room_id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                  console.log(response);
                }
            ).catch(
                (e) => {
                    console.log(e);
                }
            )
    },
    async getInfo()
    {
        await axios.post(`${endpoint.url}/games/info?Id=` + this.$route.query.room_id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                  console.log(response);
                }
            ).catch(
                (e) => {
                    console.log(e);
                }
            )
    },
    async endGame()
    {
      await axios.post(`${endpoint.url}/games/end?Id=` + this.$route.query.room_id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                  console.log(response);
                }
            ).catch(
                (e) => {
                    console.log(e);
                }
            )
    },

    rozdajKarty() {
      console.log("Test");
      this.players.forEach((player) => {
        console.log("Player " + player.id);
        for (let i = 0; i < 5; i++) {
          let rand_id = parseInt(Math.random() * this.cards.length);
          player.deck.push(this.cards[rand_id]);
          console.log(
            "Player-" +
              player.id +
              " f: " +
              player.deck[i].f +
              " v: " +
              player.deck[i].v
          );
        }
      });
    },
    dealCards() {
      let fives = [];
      for (let i = 0; i < 5; i++) {
        let rand_id = parseInt(Math.random() * this.cards.length);
        fives.push(this.cards[rand_id]);
      }
      return fives;
    },
    showCards(index) {
      return this.players[index].deck;
    },
    setNumberofPlayers: function() {
      this.players_in_game++;
      if (this.players_in_game >= 1 && this.players_in_game <= 4) {
        console.log("Liczba graczy jest ok");
      } else if (this.players_in_game > 4) {
        this.players_in_game = 0;
      }
      console.log(this.players_in_game);
    } /*,
    cards() {
      //let all = [];
      for (let figure of this.figures) {
        for (let value of this.values) {
          this.playing_cards.push({
            f: figure,
            v: value,
          });
        }
      }
      return this.playing_cards;
    }*/,
  },
  async created()
  {
    const response = await axios.post(`${endpoint.url}/games/info?Id=` + this.$route.query.room_id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                  console.log(response);
                  this.playersList = response.data
                  console.log('tehee', this.playersList) 
                }
            ).catch(
                (e) => {
                    console.log(e);
                }
            )

            console.log(response)
  }
};
</script>



<style>
.settings {
  width: 160px;
  height: 150px;
  border: 1px solid black;
  float: left;
}

.setting-button {
  display: block;
  padding: 10px;
  margin: 20px 0px 20px 40px;
}
.table {
  width: 750px;
  height: 400px;
  background-color: #4aad4a;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  border-radius: 150px;
  border: 15px solid #a95555;
  float: left;
}

.deck-of-cards {
  width: 50px;
  height: 70px;
  background-image: url("../assets/rewers.jpg");
  border-radius: 5px;
  margin: 2px;
  /*top: 50%;
  left: 50%;*/
  transform: translateX(350px) translateY(160px);
  position: absolute;
}

.deal-button {
  display: block;
  margin: auto;
  text-align: center;
}

.players-section {
  width: 850px;
  height: 450px;
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
.player-2 .bank {
  transform: rotatez(180deg);
}
.player-3 .bank {
  transform: rotatez(90deg);
}
.player-4 .bank {
  transform: translateX(-270%) rotatez(-90deg);
}
/*.card-place {
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 150%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  box-sizing: border-box;
}*/
.player-1-card-place {
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 27%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  box-sizing: border-box;
}
.player-2-card-place {
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 72%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  box-sizing: border-box;
}
.player-3-card-place {
  transform: translatex(-50%) translatey(-10%) rotatez(90deg);
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 40%;
  left: 18%;
  box-sizing: border-box;
}

.player-4-card-place {
  transform: translatex(-50%) translatey(-10%) rotatez(90deg);
  border: 5px solid #63c763;
  height: 100px;
  width: 300px;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  top: 40%;
  left: 82%;
  box-sizing: border-box;
}
</style>
