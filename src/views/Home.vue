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
            <div class="display-3">
              Tmp banner
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
    <gameList />
  </div>
</template>

<script>
  import gameList from '../components/gameList.vue'

  export default {
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
      }
    },
  }
</script>