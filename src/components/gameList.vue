<template>
    <div>
        <div v-if="user">
            <v-row
                align="center"
                justify="center">

            <v-col
                v-for="item in gamesList"
                :key="item.id"
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
                        {{item.player1.user.nickname}}
                    </v-row>
                    <v-row 
                        v-if="item.player2"
                        align="center"
                        justify="center">
                    >
                        {{item.player2.user.nickname}}
                    </v-row>
                    <v-row 
                        v-if="item.player3"
                        align="center"
                        justify="center">
                    >
                        {{item.player3.user.nickname}}
                    </v-row>
                    <v-row 
                        v-if="item.player4"
                        align="center"
                        justify="center">
                    >
                        {{item.player4.user.nickname}}
                    </v-row>
                    <v-row 
                        v-if="!item.player4"
                        align="center"
                        justify="center"
                    >
                        <v-btn v-on:click="joinToGame(item.id)"
                        dark
                        >
                            Dołącz do gry
                        </v-btn>
                    </v-row>
                </v-container>
                    
                </v-sheet>
            </v-col>

            <v-col
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
                        justify="center"
                    >
                        <v-btn v-on:click="createGame()"
                        dark
                        >
                            Stwórz nową grę
                        </v-btn>
                    </v-row>
                </v-container>
                    
                </v-sheet>
            </v-col>

            </v-row>
        </div>
        <div v-if="!user">
            <h1 
                align="center"
                justify="center"
            >
                Zaloguj się, aby uzyskać dostęp do serwisu.
            </h1>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import endpoint from '@/endpoint.json';

  export default {
    props: ['user'],
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
        gamesList : [],
        newgame : null

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

            await axios.post(`${endpoint.url}/games/create?minTokenAmount=` + 200, 
                {},
                {
                    headers: {
                    Authorization: localStorage.getItem('token')
                    }
                }
            ).then(
                (response) => {
                this.$router.push( {path: 'game', query: {room_id: response.data} })
                }
            ).catch(
                (e) => {
                    console.log('error', e)
                }
            )
        }
    },
    async created() {
        //console.log("token", localStorage.getItem('token'))
            const response = await axios.get(`${endpoint.url}/games`, {
                headers: {
                    Authorization: localStorage.getItem('token')
                }
            }
            );
            this.gamesList = response.data;
        
        //console.log(response.data[0].player1.user.nickname)
        //console.log(this.gamesList[0].player1.user.nickname)
    }
  }
</script>