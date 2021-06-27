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
                        <v-btn dark v-on:click="handleClickLogin">
                            <v-icon >mdi-google</v-icon>
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
import GoogleSignInButton from 'vue-google-signin-button-directive';


  export default {
    directives: {
      GoogleSignInButton,
    },
    
    data: () => ({
      dialog: false,
      username: '',
      password: '',
      clientId: '232184996385-jlgvk9t282s6tf0hl0maaqvu506elsi9.apps.googleusercontent.com'
      
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

        console.log("Token" , response.headers.authorization)

        
      },
      async googleAuthPost(authCode)
      {
          const promise = axios.post(`${endpoint.url}/auth/google`, { code : authCode });

          const headerPromise = promise.then((response) => response.headers.authorization)

          return headerPromise;
      },

      handleClickLogin() {
      this.$gAuth
        .getAuthCode()
        .then((authCode) => {
          console.log("authCode", authCode);

          this.googleAuthPost(authCode)
            .then(data => {
                localStorage.setItem('token', data);
                console.log("Token",data);
            }) 
        })
        .catch((error) => {
          console.log("error", error)
        });
      },


      /*OnGoogleAuthSuccess(idToken) {
      console.log(idToken);
      axios.post(`${endpoint.url}/auth/google/${idToken}`)
          .then((response) => {
            if (response.status === 200) {
              console.log(this.dataToDashboard)
              console.log(response)
              sessionStorage.setItem('loggedIn', JSON.stringify(response.data))
            }
          })
          .catch(() => {
            this.info = 'Niepoprawne dane do logowania';
          });
      },
      OnGoogleAuthFail(error) {
        console.log(error)
      },*/

    }
  }
//272331398761-ougmqhm1r97sbjonavrhpljcbn0a37km.apps.googleusercontent.com

//yVJN8faS6sdDusLGV5yhfJEJ
</script>

<style>
.google-signin-button {
  color: white;
  background-color: red;
  height: 50px;
  font-size: 16px;
  border-radius: 10px;
  padding: 10px 20px 25px 20px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>