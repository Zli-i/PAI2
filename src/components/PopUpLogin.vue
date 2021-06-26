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
            <span left> Zaloguj </span>
            <v-icon right>mdi-login</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Zaloguj się do serwisu</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form @submit.prevent="handleSubmit">
              <v-row justify="center">
                <v-col cols="10">
                    <v-text-field
                      label="Email*"
                      v-model="username"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="10">
                    <v-text-field
                      label="Hasło*"
                      v-model="password"
                      type="password"
                      required
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                    <v-col>
                      <v-checkbox label="Zapamiętaj mnie">
                      </v-checkbox>
                    </v-col>
                </v-row>
                <v-row justify="center">
                    <v-col cols="2">
                        <v-btn dark>
                            <v-icon right>mdi-facebook</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="2" class="offset-md-1">
                        <v-btn dark>
                            <v-icon right>mdi-google</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="4" class="offset-md-3">
                        <v-btn dark v-on:click="handleSubmit">
                            <span left> Zaloguj </span>
                            <v-icon right>mdi-login</v-icon>
                        </v-btn>
                    </v-col>
              </v-row>
            </v-form>
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
      password: '',
    }),
    methods : {
      async handleSubmit()
      {
        const response = await axios.post(`${endpoint.url}/login`, {
          username: this.username,
          password: this.password
        });

        console.log(response);

        localStorage.setItem('token', response.headers.authorization);

        console.log(response.headers.authorization + "\nZapisano token")

        
      }
    }
  }


</script>