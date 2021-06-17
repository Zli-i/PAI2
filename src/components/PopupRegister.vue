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
              <v-col cols="10">
                <v-text-field
                  label="Powtórz hasło*"
                  type="password"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
                    <v-btn dark>
                        <span left> Rejestracja </span>
                        <v-icon right>mdi-account-box</v-icon>
                    </v-btn>
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