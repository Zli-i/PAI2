<template>
    <div>
        <div v-if="!user" >
            <v-card
                color="warning"
                rounded
                 class="mx-auto spacing-playground mt-12 pa-6"
                width="50%"
                >
                <h1 class="error--text text-center mb-2" bigger>
                     Zaloguj się przed zakupem żetonów!
                 </h1>
            </v-card>
        </div>
        <div v-if="user">
            <v-row
                class="mt-4"
                align="center"
                justify="center">

                <v-col
                    v-for="paczka in paczki"
                    :key="paczka"
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
                        Cena : {{paczka.price}} Ilość tokenów: {{paczka.token}}
                        <v-btn v-on:click="handlePaypalRequest(paczka.bundle)">
                            Kup teraz!
                        </v-btn>
                        
                    </v-container>
                        
                    </v-sheet>
                </v-col>
            </v-row>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import endpoint from '@/endpoint.json';

export default {
    props: ['user'],
    data: () => ({
        paczki: [
            {
                id: '1',
                price: '50',
                token: '500',
                bundle: '1'
            },
            {
                id: '2',
                price: '99',
                token: '1000',
                bundle: '2'
            }
        ]
    }),
    methods:
    {
        async handlePaypalRequest(bundle) 
        {
            console.log("bundle",bundle);
            console.log(localStorage.getItem('token'))

            const response = await axios.post(`${endpoint.url}/paypal/bundle?bundle=` + bundle, {} ,
            { 
                headers: {
                    Authorization: localStorage.getItem('token')
                }
            })

            console.log(response.data.redirect_url);

            window.location.href = response.data.redirect_url;
        }
    }
}
</script>
