<template>
  <div class="card" :class="['figures-' + card.f, 'values-' + card.v]">
    <h1>{{ card.v }}</h1>
    <div class="figures" :class="card.f"></div>
    <h1>{{ card.v }}</h1>
  </div>
</template>

<script>
export default {
  props: ["card"],
};
</script>

<style>
.card {
  height: 70px;
  width: 50px;
  border-radius: 5px;
  display: inline-block;
  position: relative;
  background-color: white;
  font-family: "Houschka Rounded";
  margin: 2px;
}
.card.figures-P,
.card.figures-C {
  color: #515260;
}
.card.figures-H,
.card.figures-D {
  color: #ec6e69;
}
.card h1 {
  font-size: 10px;
  text-align: center;
  position: absolute;
  margin: 0;
}
.card h1:first-child {
  top: 5px;
  left: 5px;
}
.card h1:last-child {
  bottom: 5px;
  right: 5px;
  transform: rotatez(180deg);
}
.card .figures {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
}
.figures {
  width: 20px;
  height: 20px;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/441095/clovers.svg");
  background-size: 40px;
}
.figures.P {
  background-position: 0 0;
}
.figures.D {
  background-position: 20px 0;
}
.figures.C {
  background-position: 20px 20px;
}
.figures.H {
  background-position: 0 20px;
}
</style>