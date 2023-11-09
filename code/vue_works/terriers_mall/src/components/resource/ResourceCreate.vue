<template>
  <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 17 }" @submit="handleSubmit">

    <a-form-item :label="$t('common.resourceName')">
      <a-input v-decorator="['resourceName', validatorRules.resourceName]"
               :placeholder="$t('common.pleaseResource')"/>
    </a-form-item>
    <a-form-item :label="$t('common.resourceCategory')">
      <a-input v-decorator="['category', validatorRules.resourceCategory]"
               :placeholder="$t('common.pleaseCategory')"/>
    </a-form-item>
    <a-form-item :label="$t('common.resourceDesc')">
      <a-input v-decorator="['desc', validatorRules.resourceDesc]"
               :placeholder="$t('common.pleaseDesc')"/>
    </a-form-item>
    <a-form-item :label="$t('common.resourceLink')">
      <a-input v-decorator="['link', validatorRules.resourceDesc]"
               :placeholder="$t('common.pleaseLink')"/>
    </a-form-item>

    <a-form-item :label="$t('common.resourceLogo')">
      <ImageUpload
          ref="child"
          :resourceLogo="resourceLogo"
          @resourceLogoFn="resourceLogoFn"/>
    </a-form-item>
    <a-divider style="margin: 10px 0;"></a-divider>
    <a-form-item class="form-item-submit">
      <a-button type="primary" html-type="submit">{{ $t('common.sureAndAdd') }}</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import resourceService from "@/service/resourceService";
import ImageUpload from "@/components/resource/ImageUpload";

export default {
  components: {ImageUpload},

  props: {

    resourceId: {type: Number, default: 0},

    resourceName: {type: String, default: ''},

    category: {type: String, default: ''},

    desc: {type: String, default: ''},

    link: {type: String, default: ''},

    resourceLogoInit: {type: String, default: null},
  },

  data() {
    return {

      resourceLogo: this.resourceLogoInit,
      form: this.$form.createForm(this, {name: 'coordinated'}),

      validatorRules: {
        resourceName: {

          rules: [

            {required: true, message: this.$t('common.pleaseResource')}
          ]
        },
        resourceCategory: {

          rules: [

            {required: true, message: this.$t('common.resourceCategory')}
          ]
        },
        resourceDesc: {

          rules: [

            {required: true, message: this.$t('common.resourceDesc')}
          ]
        },
        resourceLink: {
    
          rules: [
     
            {required: true, message: this.$t('common.resourceLink')}
          ]
        },
      }
    }
  },

  methods: {
    handleSubmit(e) {
      e.preventDefault();

      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            "logo": this.resourceLogo,
            "resourceName": values.resourceName,
            "category": values.category,
            "desc": values.desc,
            "link": values.link,
          };
          if (this.resourceLogo) {
    
            if (this.resourceId) {
              data.id = this.resourceId;
              this.resourceUpdate(data);
       
            } else {
              this.resourceCreate(data);
            }
          } else {
            this.$message.warning('Please upload Logo');
          }
        }
      });
    },

 
    resourceCreate(data) {
      resourceService.resourceCreate(data)
          .then(res => {
           
            this.form.resetFields();
         
            this.$refs.child.clearFileList();
            
            this.$emit("hideResourceVisibleFn");
            this.refresh();
          })
          .catch(err => {
            this.$message.error(err.desc);
          });
    },

    
    resourceUpdate(data) {
      resourceService.resourceUpdate(data)
          .then(res => {
            this.$emit("hideResourceVisibleFn", data.id);
            this.refresh();
          })
          .catch(err => {
            this.$message.error(err.desc);
          });
    },

    resourceLogoFn(logo) {
      this.resourceLogo = logo;
    },

    refresh() {
      this.$emit("refresh");
    },
  },

  mounted() {
    // solution of conflict between v-mode and v-decorator
    this.form.setFieldsValue({
      resourceName: this.resourceName,
      category: this.category,
      desc: this.desc,
      link: this.link,
    })
  }

}
</script>

<style scoped>

.form-item-submit {
  display: flex;
  text-align: right;
  justify-content: right;
  margin-bottom: 0;
}
</style>