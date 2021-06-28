<template>
  <v-app>
    <Navbar :user="user"/>
    <v-main>
      <router-view :user="user"/>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/nav'
import axios from 'axios'
import endpoint from '@/endpoint.json';

export default {
  name: 'App',
  components: {
      Navbar
  },
  data: () => ({
    user : null
  }),
  async created()
  {
      const response = await axios.get(`${endpoint.url}/users/logd`, {
                headers: {
                    Authorization: localStorage.getItem('token')
                }
      })

      this.user = response.data;

      console.log("Data",response.data);
  },
};
</script>
    