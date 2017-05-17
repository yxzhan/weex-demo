<template>
<div class="child">
  <!--<image style="width:750px; height:750px; background-color:#ff0;"
    @load="onloadImg($event)"
    :src="imageUrl"/>-->
  <div class="bee-head" @click="changeEmotion">
    <text class="tentacle">{{tentacle}}</text>
    <text class="bee-face">{{faces[currentFace]}}</text>
  </div>
  <div v-for="(item, index) in rollingInTheDeep" :key="index">
    <text class="text-row" @click="go2NextFrame">{{item[currentFrame]}}</text>
    <input class="input" type="text" />
  </div>
</div>
</template>

<style>
  .child {
    background-color: #ff0; 
  }
  .border {
    border-width: 1px;
    border-color: #ff0;
  }
  .bee-head {
    text-align: center;
    height: 550px;
    align-items: center;
    justify-content: center;    
  }
  .tentacle {
    font-size: 60px;
    font-weight: bold;
  }
  .bee-face {
    font-size: 130px;
  }
  .tentacle,
  .bee-face {
    color: #333;
    text-align: center;
  }
  .child {
    text-align: center;
  }
  .text-row,
  .input {
    color: #fff;
    background-color: #193556;
    text-align: center;
    font-size: 64px;
    height: 90px;
  }
  .input {
    background-color: #ff0;
    color: #333;
  }

</style>

<script>
  const tentacle = '\\                 /'
  const faces = [
    ' (●ﾟωﾟ●)',
    '@ __ @',
    '(￣.￣)',
    '(￣︶￣)',
    '(￣３￣)',
  ]
  export default {
    data () {
      const chars = ['-', '\\', '|', '/']
      const rows = 100
      const rollingInTheDeep = []
      for (let a = 0; a < rows; a++) {
        const i = a % 4
        rollingInTheDeep.push(chars)
      }
      return {
        imageUrl: 'https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg',      
        currentFrame: 0,
        currentFace: 0,
        faces,
        tentacle,
        rollingInTheDeep
      }
    },
    methods: {
      changeEmotion () {
        this.currentFace = (this.currentFace + 1) % faces.length        
      },
      onloadImg (e) {
        console.log('image on load')
      },
      go2NextFrame () {
        console.log('hey yo! next frame ah~')
        this.currentFrame = (this.currentFrame + 1)%4        
      }
    }
  }
</script>
