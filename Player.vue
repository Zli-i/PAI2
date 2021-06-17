<template>
  <div class="player">
    <div class="avatar" :style="{ backgroundColor: color }"></div>
    <div class="name">
      <p>{{ name }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    id: Number,
    name: String,
    color: String,
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
  color: #96e296;
  padding: 5px 0;
  margin-left: 10px;
  box-sizing: border-box;
  border: 2px solid #96e296;
  border-radius: 5px;
  margin-top: 15px;
  text-overflow: ellipsis;
  font-size: 11px;
  overflow: hidden;
  position: relative;
}
</style>