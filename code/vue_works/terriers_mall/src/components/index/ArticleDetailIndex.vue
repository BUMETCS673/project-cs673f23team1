<template>
  <a-layout>
    <a-layout id="article-detail-index">
      <IndexHeader class="header"/>
      <a-layout-content>
        
        <main class="content" :style="$store.state.collapsed ? 'width: 100%;' : 'width: 100%;max-width: 1100px;'">
          <div class="article-left-buttons">
          
            <LeftButtons
                @articleCommentCountFn="articleCommentCountFn"
                ref="child"/>
          </div>
          <a-col :span="$store.state.collapsed ? 24 : 18"
                 :style="$store.state.collapsed ? '' : 'border-right: 20px solid #f0f2f5'">
   
            <ArticleDetail
                @initLabelIds="initLabelIds"
                style="background: #fff;"/>
            <br/>
           
          </a-col>
          <a-col v-if="!$store.state.collapsed" :span="6">
         
            <AuthorBlock
                v-if="finishArticleDetail"
                :userId="userId"
                style="background: #fff;"/>
            <a-row>
              <a-col :span="24" style="height: 10px;"/>
            </a-row>

            <Toc v-if="articleHtml"
                 :articleHtml="articleHtml"
                 style="background: #fff;"/>
            <a-row>
              <a-col :span="24" style="height: 10px;"/>
            </a-row>

          </a-col>
        </main>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
  import IndexHeader from "@/components/index/head/IndexHeader";
  import ArticleDetail from "@/components/article/ArticleDetail";
  import LeftButtons from "@/components/article/LeftButtons";
  import ArticleComment from "@/components/comment/ArticleComment";
  import AuthorBlock from "@/components/right/AuthorBlock";
  import RelatArticle from "@/components/right/RelatArticle";
  import Toc from "@/components/right/MarkdownToc";

  export default {
    components: {
      IndexHeader,
      ArticleDetail,
      AuthorBlock,
      LeftButtons,
      ArticleComment,
      RelatArticle,
      Toc
    },

    data() {
      return {
        articleHtml: '',
        finishArticleDetail: false,
        labelIds: [],
        
        userId: 0,
      
        articleCommentCount: 0,
      };
    },

    methods: {
    
      initLabelIds(labelIds, finishArticleDetail, userId, articleHtml) {
        this.labelIds = labelIds;
        this.finishArticleDetail = finishArticleDetail;
        this.userId = userId;
        this.articleHtml = articleHtml;
      },

      refresh() {
   
        this.$refs.child.getArticleCountById()
      },

      articleCommentCountFn(commentCount) {
        this.articleCommentCount = commentCount;
      }
    }

  };
</script>


<style>
  #article-detail-index .header {
    position: fixed;
    width: 100%;
    z-index: 999;
    background: #fff;
    border-bottom: 1px solid #00000021;
  }

  #article-detail-index .content {
    margin-top: 64px;
  }

  #article-detail-index .ant-layout-header {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #article-detail-index .ant-layout-content {
    display: flex;
    justify-content: center;
  }

  #article-detail-index .article-left-buttons {
    position: relative;
    right: 70px;
    top: 140px;
  }

  #article-detail-index .ant-layout-header {
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