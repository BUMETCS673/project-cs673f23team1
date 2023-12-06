<template>
  <div class="header-left-content">
    <a-menu v-model="current" :mode="$store.state.collapsed ? 'inline' : 'horizontal'">
    
      <a-menu-item key="frontPage" @click="refresh">{{ $t("common.home") }}</a-menu-item>
    
      <a-menu-item key="boilingPoint" @click="routerLabel">{{ $t("common.label") }}</a-menu-item>
      
      <a-menu-item key="writeArticle" @click="routerWriteArticle" v-if="$store.state.collapsed">{{ $t("common.writeArticle") }}</a-menu-item>
      <a-divider style="margin: 3px 0 3px 0" v-if="$store.state.collapsed"/>
      
    </a-menu>
  </div>
</template>

<script>
  export default {
    name: "",

    data() {
      return {
        current: ['frontPage'],
      }
    },

      
      refresh() {
        
        this.$router.push('/');
      },
      
      routerLabel() {
        this.$router.push("/label");
      },

      routerWriteArticle() {
        this.$router.push("/write");
      },

    mounted() {
      let name = this.$route.name;

      
      this.current.pop();
      
      if (name === 'home') {
        
        this.current.push('frontPage');
      }
      
      if (name === 'label') {
        
        this.current.push('boilingPoint');
      }    
    },
  }
</script>

<style lang="less" scoped>
  .header-left-content {
    display: flex;
    justify-content: left;
    align-items: center;
  }

  
  .ant-menu-horizontal {
    border-bottom: 0;
  }

  .ant-menu-horizontal > .ant-menu-item-active, .ant-menu-horizontal > .ant-menu-item-open, .ant-menu-horizontal > .ant-menu-item-selected, .ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-item:hover, .ant-menu-horizontal > .ant-menu-submenu-active, .ant-menu-horizontal > .ant-menu-submenu-open, .ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu-selected, .ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu {
    border-bottom: 2px solid transparent;
  }
 
  .ant-menu-item, .ant-menu-submenu-title {
    padding: 0 16px;
  }
  
  .ant-menu-inline {
    border-right: 0;
  }
 
  .ant-menu-inline .ant-menu-item::after, .ant-menu-vertical .ant-menu-item::after, .ant-menu-vertical-left .ant-menu-item::after, .ant-menu-vertical-right .ant-menu-item::after {
    border-right: 3px solid transparent;
  }
  
  .ant-menu:not(.ant-menu-horizontal) .ant-menu-item-selected {
    background-color: transparent;
  }

</style>