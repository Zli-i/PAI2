<template>
    <v-dialog
      v-model="dialog"
      max-width="500px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          dark
          v-bind="attrs"
          v-on="on"
        >
            <span left> Rejestracja </span>
            <v-icon right>mdi-account-box</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Zarejestruj się do serwisu</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <form 
            @submit.prevent="handleSubmit"

            >
              <v-row justify="center">
                <v-col cols="10">
                  <v-text-field
                    v-model="username"
                    label="Nazwa"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="10">
                  <v-text-field
                  v-model="email"
                    label="Email*"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="10">
                  <v-text-field
                    v-model="password"
                    label="Hasło*"
                    type="password"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="10">
                  <v-text-field
                    v-model="password_confirm"
                    label="Powtórz hasło*"
                    type="password"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row justify="center">
                      <v-btn dark v-on:click="handleSubmit">
                          <span left> Rejestracja </span>
                          <v-icon right>mdi-account-box</v-icon>
                      </v-btn>
              </v-row>
            </form>
          </v-container>
        </v-card-text>
        <v-card-actions>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>

import endpoint from '@/endpoint.json';
import axios from 'axios';

  export default {
    
    data: () => ({
      dialog: false,
      username: '',
      email: '',
      email_confirm: '',
      password: '',
      password_confirm: ''
    }),
    methods:
    {
      async handleSubmit()
      { 
        const response = await axios.post(`${endpoint.url}/registration`, {
          username: this.username,
          email: this.email,
          password: this.password
        });

        console.log(response)
      },
    }
  }
</script>