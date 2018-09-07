<template>
    <div id="app">
        <transition name="fade"
                    mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>

<script>
    export default {

    }
</script>

<style>
    *[v-cloak]{
        display: none;
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


