<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    height: 100%;
  }

  html,
  body {
    height: 100%;
  }

  .table-cell-green {
    background-color: #0c6 !important;
    color: #fff;
  }

  .table-cell-red {
    background-color: red !important;
    color: #fff;
  }
</style>
