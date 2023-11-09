<template>
  <a-layout>
    <a-layout id="about-index">
      <IndexHeader class="header"/>
      <a-layout-content>
        <main class="about_content" :style="$store.state.collapsed ? 'width: 100%;' : 'width: 800px;'">
          <div style="background: #fff; margin-bottom: 10px">
            <section class="mission">
              <div class="inner">
                <div class="mission-content">
                  <div class="title">Our task</div>
                  <div class="sub">Terriors Mall, a mall focus on used items, furnitures, books, and help students easy to find somebody who is willing to help.</div>
                </div>
                <div class="vision-content">
                  <div class="title">Our wish</div>
                  <div class="sub">
                    Every student can get furniture easily and cheaper.<br>
                    Every student can always found someone who is willing to help.
                  </div>
                </div>
                <div class="author-content">
                  <div class="title">Author</div>
                  <div class="sub">Ace Team</div>
                  
                </div>
                <div class="code-content">

                </div>
                <div class="code-content">

                </div>
                <div class="code-content">

                </div>
              </div>
            </section>
          </div>
        </main>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import IndexHeader from "@/components/index/head/IndexHeader";

export default {
  name: "About",

  components: {IndexHeader},

  mounted() {
    
    this.$nextTick(() => {
      let dom = document.querySelector('#app');
      if (dom !== null) {
        dom.scrollTop = 0;
      }
    })
  }
};
</script>


<style lang="less">
#about-index {
  .header {
    position: fixed;
    width: 100%;
    z-index: 999;
    background: #fff;
    border-bottom: 1px solid #00000021;
  }

  .about_content {
    margin-top: 64px;
  }

  .ant-layout-header, .ant-layout-content {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .ant-layout-header {
    background: #fff;
    height: auto;
    line-height: 2.3;
  }

  .mission {
    padding: 30px 30px;
    box-sizing: border-box;

    .title {
      font-size: 32px;
      font-weight: bold;
    }

    .sub {
      margin: 10px 0;
      font-size: 16px;
      color: #999;
    }
  }
}

</style>
