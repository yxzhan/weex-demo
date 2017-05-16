<template>
<div>
    <image style="width:750px; height:750px;"
      @load="onloadImg($event)"
      :src="imageUrl"/>
  <div v-for="(item, index) in rollingInTheDeep" :key="index">
    <text class="text-row" @click="go2NextFrame">{{item[currentFrame]}}</text>
    <input class="input" type="text" />
  </div>
</div>
</template>

<style>

  .text-row,
  .input {
    color: #fff;
    background-color: #193556;
    /*border-style: solid;*/
    /*border-color: #F0B23E;*/
    /*border-width: 5px;*/
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
 
  export default {
    data () {
      const chars = ['-', '\\', '|', '/']
      const rows = 10
      const rowLength = 1
      const rollingInTheDeep = []
      for (let a = 0; a < rows; a++) {
        const i = a % 4
        rollingInTheDeep.push(chars)
      }
      return {
        imageUrl: 'https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg',      
        currentFrame: 0,
        rollingInTheDeep
      }
    },
    mounted () {

    },
    methods: {
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
