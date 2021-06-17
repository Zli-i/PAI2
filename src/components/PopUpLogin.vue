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
            <v-row justify="center">
              <v-col cols="10">
                <v-text-field
                  label="Email*"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="10">
                <v-text-field
                  label="Hasło*"
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
                    <v-btn dark>
                        <span left> Zaloguj </span>
                        <v-icon right>mdi-login</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
  export default {
    data: () => ({
      dialog: false,
    }),
  }
</script>