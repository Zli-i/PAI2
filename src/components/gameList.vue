<template>
    <v-row
        align="center"
        justify="center">

      <v-col
        v-for="(game, index) in gameList"
        :key="index"
        cols="6"
        md="4"
        align="center"
        justify="center"
      >
        <v-sheet
            color="white"
            elevation="12"
            height="250"
            rounded
            width="450"
        >
        <v-container
        fill-height
        >
            <v-row 
                align="center"
                justify="center">
            >
                {{game.player1.user.nickname}}
            </v-row>
            <v-row 
                align="center"
                justify="center"
            >
                <v-btn v-on:click="joinToGame(1)"
                dark
                >
                    Dołącz do gry
                </v-btn>
            </v-row>
        </v-container>
            
        </v-sheet>
      </v-col>
    </v-row>
</template>

<script>
import axios from 'axios'
import endpoint from '@/endpoint.json';

  export default {
    data: () => ({
        pokoje : [
            {id: 1, gracze : [
                    { nick : 'piotrek'},
                    { nick :  'wojtek'},
                    { nick :  'mateuszek'}
                ]
            },
            {id: 2, gracze : [
                    { nick : 'maciek'}
                ]
            },
            {id: 3, gracze : [
                    { nick : 'michalek'},
                    { nick : 'peja'}
                ]
            },
            {id: 4, gracze : [
                    { nick : 'mati'},
                    { nick :  'seba'},
                    { nick :  'rychu'},
                    { nick :  'adek'}
                ]
            },
            {id: 5, gracze : [
                    { nick : 'kacper'},
                    { nick :  'melchior'},
                    { nick :  'baltazar'}
                ]
            },
            {id: 6, gracze : [
                    { nick : 'ryszard'},
                    { nick :  'tomek'}
                ]
            },
            {id: 7, gracze : [
                ]
            }
        ],
        gamesList : []

    }),
    methods:
    {
        async TokenTest()
        {
        console.log(localStorage.getItem('token'))

            const response = await axios.get(`${endpoint.url}/users`, {
                headers: {
                    Authorization: localStorage.getItem('token')
                }
        }
        );

        console.log(response)
        },
        async joinToGame(id)
        {
            console.log(id)
            const response = await axios.post(`${endpoint.url}/games/join?Id=` + id,
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                this.$router.push( {path: 'game', query: {room_id: id} })
            ).catch(
                (e) => {
                    console.log("Za mała ilość tokenów "+e);
                }
            )

            console.log(response)
        },

        async createGame()
        {
            console.log("create game")

            const response = await axios.post(`${endpoint.url}/games/create?minTokenAmount=` + 200, 
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            )

            console.log(response)


        }
    },
    async created() {
        console.log("token", localStorage.getItem('token'))
        const response = await axios.get(`${endpoint.url}/games`, {
                headers: {
                    Authorization: localStorage.getItem('token')
                }
        }
        );
        this.gamesList = response.data;
        console.log(response.data[0].player1.user.nickname)
        console.log(this.gamesList)
        /*response.data.forEach((dat) => {
            console.log(dat.id)
            this.gamesList = dat
        })
        this.gamesList.forEach((game) =>
        {
            console.log(game.id)
        })*/
    }
  }
</script>