<template>
  <div id="resource-content" :style="$store.state.collapsed ? 'margin: 0 10px;' : ''"
       v-if="categoryList.length !== 0 && data.length !== 0">
    <div class="tabs">
      <div>
        <a-radio-group :value="size" @change="handleSizeChange" v-for="(item, index) of categoryList" :key="item.id">
          <a-radio-button value="all" style="margin-left: 10px;" v-if="index === 0">
            All resources
          </a-radio-button>
          <a-radio-button :value="item" style="margin: 10px 0 0 10px;" v-else>
            {{ item }}
          </a-radio-button>
        </a-radio-group>
      </div>
      <a-popover v-model="resourceAddVisible" :title="$t('common.resourceAdd')" trigger="click" placement="bottomRight">
        <div slot="content" style="width: 500px;">
          <ResourceCreate
              @hideResourceVisibleFn="hideResourceVisibleFn"
              @refresh="refresh"/>
        </div>
      </a-popover>
      <a-button class="add-item" type="primary" style="height: 30px;" v-text="$t('common.add')"
                @click="resourceAddCheck" v-if="$store.state.isManage"></a-button>
    </div>
    <div>
      <div class="tag">
        <a-badge class="info-box" @click="routerLink(item.link)" style="cursor: pointer"
                 :style="$store.state.collapsed ? 'width:100%;' : 'width:25%;border-right: 20px solid #f0f2f5;'"
                 v-for="item of data" :key="item.id">
          <div class="head-name">
            <a-avatar class="avatar" :size="35" :src="item.logo"/>
            <div class="title">{{ item.resourceName }}</div>
          </div>
          <div class="meta-article">{{ item.desc }}</div>
          <a-popover v-model="resourceEditVisible[item.id]" :title="$t('common.resourceEdit')" trigger="click"
                     placement="bottom">
            <div slot="content" style="width: 500px;">
              <ResourceCreate
                  @hideResourceVisibleFn="hideResourceVisibleFn"
                  :resourceLogoInit="item.logo"
                  :resourceId="item.id"
                  :resourceName="item.resourceName"
                  :category="item.category"
                  :desc="item.desc"
                  :link="item.link"
                  @refresh="refresh"/>
            </div>
          </a-popover>
        </a-badge>
      </div>
    </div>
  </div>
</template>

<script>
import resourceService from "@/service/resourceService";
import ResourceCreate from "@/components/resource/ResourceCreate";

export default {
  name: "ResourceContent",

  components: {ResourceCreate},

  props: {
    data: {type: Array, default: []},
  },

  data() {
    return {
      size: 'all',
      categoryList: [],
      resourceAddVisible: false,
      resourceEditVisible: {},
    }
  },

  methods: {
    getCategorys() {
      resourceService.getCategorys()
          .then(res => {
            this.categoryList.push('all');
            res.data.forEach(value => {
              this.categoryList.push(value);
            });
          })
          .catch(err => {
            this.$message.error(err.desc);
          });
    },


    resourceAddCheck() {
      if (this.isLoginFn()) {
        this.resourceAddVisible = true;
      }
    },


    resourceUpdateCheck(resourceId) {
      if (this.isLoginFn()) {
        this.resourceEditVisible[resourceId] = true;
      }
    },


    hideResourceVisibleFn(resourceId) {
      this.resourceAddVisible = false;
      this.$set(this.resourceEditVisible, resourceId, false)
    },


    resourceDelete(resourceId) {
      if (this.isLoginFn()) {
        this.$confirm({
          centered: true,
          title: this.$t("common.deleteResourceTitle"),
          content: this.$t("common.deletePrompt"),
          onOk: () => {
            resourceService.resourceDelete(resourceId)
                .then(res => {
                  this.refresh();
                })
                .catch(err => {
                  this.$message.error(err.desc);
                });
          },
        });
      }
    },

    refresh() {
      this.$emit("refresh");
    },

    isLoginFn() {
      if (this.$store.state.isLogin) {
        return true;
      } else {
        this.$store.state.loginVisible = true;
      }
    },

    updateResourceEditVisible() {
      const resourceEditVisibleNew = {};
      this.data.forEach(value => {
        resourceEditVisibleNew[value.id] = false
      });

      this.resourceEditVisible = resourceEditVisibleNew;
    },

    handleSizeChange(e) {
      let value = e.target.value;
      this.size = value;
      if (value === 'all') {
        this.$emit("refresh", null)
      } else {
        this.$emit("refresh", value)
      }
    },

    routerLink(link) {
      window.open(link, '_blank');
    }
  },

  mounted() {
    this.getCategorys();
    this.updateResourceEditVisible();
  },

  watch: {

    data: {
      handler(newVal, oldVal) {
        this.updateResourceEditVisible();
      }
    },
  }
}
</script>

<style lang="less" scoped>
#resource-content .tabs {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  padding: 20px 0 30px 0;

  label.ant-radio-button-wrapper:hover {
    color: #fff;
    background: #a2c217;
  }

  .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled) {
    color: #fff;
    background: #a2c217;
  }
}

#resource-content .tag {
  display: flex;
  flex-wrap: wrap;
}

#resource-content .info-box {
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: inherit;

  background: #fff;
  border-bottom: 20px solid #f0f2f5;

  .head-name {
    display: flex;
    align-items: center;

    .title {
      padding-left: 10px;
      font-size: 18px;
      color: #333;
    }
  }

  .edit {
    margin-top: 5px;
  }

  .meta-article {
    font-size: 13px;
    padding: 10px 0;
    line-height: 20px;
    color: #909090;
  }
}

#resource-content .info-box:hover {
  background: #ffffff;
  box-shadow: inset 24px 24px 42px #ededed,
    inset -24px -24px 42px #ffffff;
}
</style>