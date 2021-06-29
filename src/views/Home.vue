<template>
  <div>
    <v-carousel
      cycle
      height="280"
      hide-delimiter-background
      show-arrows-on-hover
    >
      <v-carousel-item
        v-for="(color, i) in colors"
        :key="i"
      >
        <v-sheet
          :color="colors[i]"
          height="100%"
        >
          <v-row
            class="fill-height"
            align="center"
            justify="center"
          >
            <div class="display-2">
              {{texts[i]}}
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
    <gameList :user="user"/>
  </div>
</template>

<script>
import gameList from '../components/gameList.vue'

  export default {
    props: ['user'],
    components: {gameList},
    data () {
      
      return {
        colors: [
          'primary',
          'secondary',
          'success',
          'warning',
          'error',
        ],
        texts: [
          'Od zera do klasy średniej, w 5 lata!',
          'Tutaj kasyno zawsze wygrywa',
          '"Najlepsze kasyno jakie istnieje!"-Kolega ze studiów',
          '"Aż chce się grać"-Inny kolega ze studiów',
          '"Przegrałem oszczędności życia w tym kasynie"-Ten sam kolega ze studiów'
        ]
      }
    }
  }
</script>