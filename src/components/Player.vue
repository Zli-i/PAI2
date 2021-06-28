<template>
  <div class="player">
    <div class="avatar" :style="{ backgroundColor: color }">
      <div class="name">
        <p>{{ name }}</p>
      </div>
      <div class="bank">
        <div class="jeton-value">
          <p class="jeton-value-data">{{ bank_value }}</p>
        </div>
        <div class="jeton-image">
          <img src="../assets/mini-zeton.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    id: Number,
    name: String,
    color: String,
    bank_value: Number,
  },
};
</script>

<style>
.player {
  position: absolute;
}
.player .avatar {
  width: 120px;
  height: 120px;
  background-color: lightcoral;
  border-radius: 100%;
  position: relative;
  box-shadow: 2px 10px 0px rgba(0, 0, 0, 0.4);
  z-index: 20;
}
.avatar:after {
  content: "";
  width: 70px;
  height: 70px;
  position: absolute;
  background-color: rgba(0, 0, 0, 0.1);
  top: 50%;
  left: 50%;
  transform: translatex(-50%) translatey(-50%);
  border-radius: 100%;
  box-shadow: 0px 5px 0px rgba(0, 0, 0, 0.2);
}
.name {
  font-family: "Houschka Rounded";
  text-align: center;
  width: 100px;
  color: #62a762;
  padding: 5px 0;
  margin-left: 10px;
  box-sizing: border-box;
  text-overflow: ellipsis;
  font-size: 12px;
  overflow: hidden;
  position: relative;
  top: -30px;
}

.bank {
  position: relative;
  top: -40px;
  left: 130px;
  width: 80px;
  height: 50px;
  align-items: center;
  border: 1px solid black;
  border-radius: 10px;
}
.jeton-value {
  float: left;
  font-family: "Houschka Rounded";
  width: 30px;
  height: 50px;
}

.jeton-value-data {
  text-align: right;
  font-size: 20px;
  margin-top: 15px;
  margin-bottom: 15px;
}
.jeton-image {
  float: left;
  width: 50px;
  height: 50px;
}
img {
  padding: 12.5px;
}
</style>
