<template>
  <div v-if="$store.state.width" id="app">

    <router-view/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      lang: {
        en_US: en_US
      }
    };
  },
  methods: {
    ...mapMutations(["changeColor"]),

    getAccess() {

    },

    initDom() {
      const that = this;
      
      window.onload = setWidth;
      window.onresize = setWidth;

      function setWidth() {
        that.$store.state.width = window.innerWidth;
        that.$store.state.height = window.innerHeight;
        // 900/1050
        that.$store.state.collapsed = window.innerWidth < 1000;
        that.$store.state.collapsedMax = window.innerWidth < 1300;
      }
    },
    checkErrorPage() {
      
      if (this.$route.path === "/500") {
        this.$router.push({path: "/"});
      }
    },
    /**
     * @function setLanguageAndTheme 
     */
    setLanguageAndTheme() {
      let navLanguage = "en_US";
      this.$store.state.locale = localStorage.language ? localStorage.language : navLanguage;
      this.changeColor(localStorage.themeColor || "#13c2c2");
    },
    setIsCarousel() {

      if (Number(window.localStorage.isCarousel) === 0) {
        this.$store.state.isCarousel = 0;
      } else {
        this.$store.state.isCarousel = 1;
      }
    }
  },
  created() {
    this.setLanguageAndTheme();
    this.setIsCarousel();
  },
  mounted() {

    this.initDom();

    this.getAccess();
  }
};
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: auto;
  background-color: #f0f2f5;
}
</style>
