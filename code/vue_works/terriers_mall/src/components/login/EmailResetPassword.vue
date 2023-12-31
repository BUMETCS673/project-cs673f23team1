<template>
  <div>
    <a-modal v-model="$store.state.emailResetPasswordVisible" @ok="handleOk" :footer="null" :width="'320px'">
      <a-form-model ref="ruleForm" :model="ruleForm" :rules="rules" v-bind="layout" id="emailResetPassword-form-content">
        <h1 class="title">{{ $t("common.emailResetPassword") }}</h1>
        <a-form-model-item has-feedback prop="email">
          <a-input v-model="ruleForm.email"
                   :placeholder="$t('common.email')"
                   size="large">
            <a-icon slot="prefix" type="mail" style="color: rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item prop="code">
          <a-input-search v-model="ruleForm.code" :placeholder="$t('common.validateCode')" size="large" @search="onSearch">
            <a-button slot="enterButton">
              {{ $t('common.getValidateCode') }}
            </a-button>
            <a-icon slot="prefix" type="safety-certificate" style="color: rgba(0,0,0,.25)"/>
          </a-input-search>
        </a-form-model-item>
        <a-form-model-item has-feedback prop="newPassword">
          <a-input v-model="ruleForm.newPassword" type="password"
                   :placeholder="$t('common.newPassword')" size="large">
            <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item style="padding-top: 5px;">
          <a-button type="primary" html-type="submit" class="login-form-button" size="large" @click="submitForm('ruleForm')">
            {{ $t("common.saveChanges") }}
          </a-button>
          <a @click="mobileResetPassword">
            {{ $t("common.mobileResetPassword") }}
          </a>
          <a @click="login" style="float: right;">
            {{ $t("common.loginNow") }}
          </a>
        </a-form-model-item>
      </a-form-model>

    </a-modal>
  </div>
</template>

<script>
  import userService from "@/service/userService";

  export default {
    data() {
      // Valid email address
      let validateEmail = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('common.pleaseInputYourEmail')));
        } else {
          if (!this.emailReg.test(value)) {
            callback(this.$t("common.emailInvalid"));
          } else {
            callback();
          }
        }
      };

      // Valid verify code
      let validateCode = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('common.needVerifyCode')));
        } else {
          callback();
        }
      };

      // Valid password
      let validateNewPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('common.pleaseInputYourNewPassword')));
        } else {
          callback();
        }
      };

      return {
        emailReg: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
        ruleForm: {
          email: '',
          code: '',
          newPassword: '',
        },
        rules: {
          email: [{validator: validateEmail, trigger: 'change'}],
          code: [{validator: validateCode, trigger: 'change'}],
          newPassword: [{validator: validateNewPassword, trigger: 'change'}],
        },
        layout: {
          labelCol: {span: 0},
          wrapperCol: {span: 24},
        },
      };
    },

    methods: {
      onSearch(value) {
        if (this.emailReg.test(this.ruleForm.email)) {
          this.isEmailExist(this.ruleForm.email);
        } else {
          this.$message.warning(this.$t("common.emailInvalid"));
        }
      },

      // check if the email already exist in db
      isEmailExist(email) {
        return userService.isEmailExist(email)
            .then((res) => {
              if (res.data) {
                // send verify code
                this.sendEmailVerifyCode(email);
              } else {
                this.$message.warning(this.$t("common.emailNumberNotBound"));
              }
            })
            .catch(err => {
              this.$message.error(err.desc);
            });
      },

      // send verify code
      sendEmailVerifyCode(email) {
        userService.sendEmailVerifyCode({ email: email })
            .then(() => {
              this.$message.success(this.$t("common.verifyCodeSendSuccessed"));
            })
            .catch(err => {
              this.$message.error(err.desc);
            });
      },

      // high login panel
      handleOk() {
        this.$store.state.emailResetPasswordVisible = false;
      },

      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            userService.emailResetPassword({email:this.ruleForm.email, code: this.ruleForm.code, newPassword: this.ruleForm.newPassword})
                .then(res => {
                  if (res.data) {
                    // Password reset successfully
                    this.$message.success(this.$t("common.pleaseLoginAgain"));
                    this.login();
                  } else {
                    this.$message.warning(err.desc);
                  }
                })
                .catch(err => {
                  this.$message.error(err.desc);
                });
          } else {
            return false;
          }
        });
      },

      // Reset password by phone
      mobileResetPassword() {
        this.$store.state.emailResetPasswordVisible = false;
        this.$store.state.mobileResetPasswordVisible = true;
        this.$store.state.loginVisible = false;
      },

      // Straightly login
      login() {
        this.$store.state.emailResetPasswordVisible = false;
        this.$store.state.mobileResetPasswordVisible = false;
        this.$store.state.loginVisible = true;
      },
    },

  }
</script>

<style>
  #emailResetPassword-form-content .title {
    font-size: 20px;
    font-weight: bold;
    padding-bottom: 15px;
  }

  #emailResetPassword-form-content .ant-input-affix-wrapper .ant-input {
    font-size: 14px;
  }

  #emailResetPassword-form-content .login-form-forgot {
    float: right;
  }

  #emailResetPassword-form-content .login-form-button {
    width: 100%;
  }

  #emailResetPassword-form-content .ant-form-item {
    margin-bottom: 10px;
  }
</style>