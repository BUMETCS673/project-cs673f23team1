<template>
  <div class="container">
    <div class="message-content" v-show="list.length">
      <div v-for="(item, index) of list" :key="index" class="message-item">
        <div class="icon">
          <img src="@/assets/img/" alt=""/>
        </div>
        <div class="content">
          <a-tooltip @mouseenter="onMouseOver" :title="showTitle ? item.message : null" class="title">{{
              item.message
            }}
          </a-tooltip>
          <div class="title-copy">{{ item.message }}</div>
          <div class="time">
            {{ $moment(item.createTime).format("YYYY-MM-DD HH:mm:ss") }}
          </div>
        </div>
      </div>
      <div class="tip" v-if="!hasNext && finish">{{ $t("common.noAgain") }}</div>
    </div>
    <div class="empty" v-if="!list.length">
      <a-empty></a-empty>
    </div>
    <div class="footer">
      <div>
        <a :disabled="!list.length" @click="makeAllRead" href="">{{ $t("common.makeAllRead") }}</a>
      </div>
      <div>
        <a href="" @click="viewMore">{{ $t("common.more") }}</a>
      </div>
    </div>
  </div>
</template>

<script>
  import messageService from "@/service/messageService";

  export default {
    props: ["visible"],
    data() {
      return {
        list: [],
        hasNext: false,
        finish: false,
        current: 1,
        size: 10,
        showTitle: false
      };
    },
    methods: {
      
      viewMore(e) {
        e.preventDefault();
        this.$emit("closeDropdown");
        window.open(this.$store.state.manageDomain + '/messages', '_blank');
      },
      
      onMouseOver(e) {
        this.showTitle = this.$utils.isClamp(e);
      },
      
      makeAllRead(e) {
        e.preventDefault();
        messageService.makeAllRead({type: 1})
            .then(() => {
              this.getMessageList();
            })
            .catch(err => {
              this.$message.error(err.desc);
            });
      },
      
      loadMore() {
        this.current++;
        this.getMessageList(true);
      },
      
      getMessageList(isLoadMore) {
        if (!isLoadMore) {
          this.current = 1;
        }
        this.finish = false;
        messageService.getMessageList({current: this.current, size: this.size, type: 1, isRead: false})
            
            .then(res => {
              if (isLoadMore) {
                this.list = this.list.concat(res.data.list);
              } else {
                this.list = res.data.list;
              }
              this.finish = true;
              this.hasNext = res.data.list.length;
            })
            .catch(err => {
              this.finish = true;
              this.$message.error(err.desc);
            });
      }
    },

    mounted() {
      this.getMessageList();
      
      this.$utils.scroll.call(this, this.$el.querySelector(".message-content"));
    },

    watch: {
      visible: function () {
        if (this.visible) {
          this.getMessageList();
        }
      }
    }
  };
</script>
