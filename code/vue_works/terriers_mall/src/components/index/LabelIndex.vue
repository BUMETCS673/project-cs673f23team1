<template>
  <a-layout id="label-index">
    <IndexHeader class="header"/>
    <a-layout-content>
      <main class="content">
        
      </main>
    </a-layout-content>
  
  </a-layout>
</template>

<script>
  import IndexHeader from "@/components/index/head/IndexHeader";


  export default {
    components: {IndexHeader},
    data() {
      return {
        listData: [],
  
        searchContent: '',
        hasNext: false,
        finish: false,
        params: {currentPage: 1, pageSize: 25},
      };
    },

    methods: {
     
      loadMore() {
        this.params.currentPage++;
        this.getLabelList(this.params, true);
      },

      
      getLabelList(params, isLoadMore) {
        if (!isLoadMore) {
          this.params.currentPage = 1;
        }
        labelService.getLabelList(params)
            .then(res => {
              if (isLoadMore) {
                this.listData = this.listData.concat(res.data.list);
              } else {
                this.listData = res.data.list;
              }
              this.finish = true;
              this.hasNext = res.data.list.length !== 0;
            })
            .catch(err => {
              this.finish = true;
              this.$message.error(err.desc);
            });
      },

      
      refresh(labelName) {
        this.searchContent = labelName;
        this.params = {currentPage: 1, pageSize: 25};
        if (labelName) {
          this.params.labelName = labelName;
        }
        this.getLabelList(this.params);
      },
    },

    mounted() {
      this.getLabelList(this.params);
      
      this.$utils.scroll.call(this, document.querySelector('#app'));
    },

  };
</script>


<style>
  #label-index .header {
    position: fixed;
    z-index: 999;
    width: 100%;
    background: #fff;
    border-bottom: 1px solid #00000021;
  }

  #label-index .content {
    margin-top: 64px;
    width: 900px;
  }

  #label-index .ant-layout-header, .ant-layout-content {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #label-index .ant-layout-header {
    background: #fff;
    height: auto;
    line-height: 2.3;
  }

  .index-drawer-wrap .ant-drawer-content-wrapper {
    width: 250px !important;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color 0.3s;
  }

  #components-layout-demo-custom-trigger .trigger:hover {
    color: #1890ff;
  }

  #components-layout-demo-custom-trigger .logo {
    height: 64px;
    background: rgba(255, 255, 255, 0.2);
    margin: 0;
  }
</style>