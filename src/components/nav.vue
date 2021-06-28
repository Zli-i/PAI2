<template>
    <nav>
        <v-toolbar app dark >
            <div class="d-flex align-center">
            </div>
            <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

            <h3 v-if="user">Witaj, {{user.email}}, posiadasz {{user.tokens}} tokenów</h3>

            <v-spacer></v-spacer>

            <!--<v-btn
                href="https://github.com/vuetifyjs/vuetify/releases/latest"
                target="_blank"
                text
            >
                <span class="mr-2">Latest Release</span>
                <v-icon>mdi-open-in-new</v-icon>
            </v-btn>-->
            <div v-if="!user">
                <PopupLogin />
                <PopupReg />
            </div>
            <div v-if="user">
                <v-btn dark v-on:click="handleLogout">
                    Wyloguj
                </v-btn>
            </div>
            
        </v-toolbar>

        <v-navigation-drawer v-model="drawer" temporary absolute class="secondary">

            <v-list tile>
                <v-list-item-group>
                    <v-list-item v-for="el in list" :key="el.text" router :to="el.route">
                        <v-list-item-icon>
                            <v-icon class="white--text">{{el.icon}}</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title class="white--text">  {{el.text}}   </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>

        </v-navigation-drawer>
    </nav>
</template>

<script>
import PopupLogin from './PopUpLogin'
import PopupReg from './PopupRegister'

  export default {
    props: ['user'],
    components: {PopupLogin, PopupReg},
    data: () => ({
      drawer: false,
      group: null,
      list: [
          { icon: 'mdi-home', text: 'Strona główna', route: '/'},
          { icon: 'mdi-person', text: 'Profil', route: '/'},
          { icon: 'mdi-credit-card', text: 'Doładuj tokeny', route: '/paypal'},
          { icon: 'mdi-fact-check', text: 'Regulamin', route: '/'},
          { icon: 'mdi-login', text: 'Logowanie', route: '/'},
          { icon: 'mdi-account-box', text: 'Rejestracja', route: '/'}
      ]
    }),

    watch: {
      group () {
        this.drawer = false
      },
    },
    methods: {
        handleLogout() 
        {
            localStorage.removeItem('token');
            this.$router.push('/');
            location.reload();
        }
    }
  }
</script>