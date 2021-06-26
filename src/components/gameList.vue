<template>
    <v-row
        align="center"
        justify="center">

      <v-col
        v-for="pokoj in pokoje"
        :key="pokoj"
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
            v-for="gracz in pokoj.gracze"
                :key="gracz"
                align="center"
                justify="center">
            >
                {{gracz.nick}}
            </v-row>
            <v-row 
                v-if="pokoj.gracze.length < 4"
                align="center"
                justify="center"
            >
                <v-btn v-on:click="TokenTest"
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
        ]
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
        }
    }
  }
</script>