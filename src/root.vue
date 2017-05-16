<template>
  <scroller class="wrapper">
    <child></child>
  </scroller>
</template>

<style>
.wrapper {
  border-style: solid;
  border-color: #A6222C;
  border-width: 3px; 
}
</style>

<script>
  import child from './child.vue'

  export default {
    components : {
      child
    }
  }
</script>
