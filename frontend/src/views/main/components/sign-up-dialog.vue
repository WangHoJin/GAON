<template>
  <el-dialog
    custom-class="sign-up-dialog"
    title="회원 가입"
    v-model="state.dialogVisible"
    @close="handleClose"
  >
    <el-form
      :model="state.form"
      :rules="state.rules"
      ref="signUpForm"
      :label-position="state.form.align"
    >
      <el-form-item
        prop="department"
        label="소속"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.department" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="position"
        label="직책"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.position" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="name"
        label="이름"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="id"
        label="아이디"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="password"
        label="비밀번호"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.password"
          autocomplete="off"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="repassword"
        label="비밀번호 확인"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.repassword"
          autocomplete="off"
          show-password
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickSignUp">가입하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style>
.sign-up-dialog {
  width: 400px !important;
  height: 550px;
}
.sign-up-dialog .el-dialog__headerbtn {
  float: right;
}
.sign-up-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.sign-up-dialog .el-form-item {
  margin-bottom: 20px;
}
.sign-up-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.sign-up-dialog .el-input__suffix {
  display: none;
}
.sign-up-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.sign-up-dialog .dialog-footer .el-button {
  width: 120px;
}
</style>
<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "sign-up-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const signUpForm = ref(null);

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        id: "",
        password: "",
        name: "",
        department: "",
        repassword: "",
        align: "left"
      },
      rules: {
        department: [
          {
            required: true,
            message: "Please input department",
            trigger: "blur"
          }
        ],
        position: [
          { required: true, message: "Please input position", trigger: "blur" }
        ],
        name: [
          { required: true, message: "Please input name", trigger: "blur" }
        ],
        id: [{ required: true, message: "Please input ID", trigger: "blur" }],
        password: [
          { required: true, message: "Please input password", trigger: "blur" }
        ],
        repassword: [
          {
            required: true,
            message: "Please input repassword",
            trigger: "blur"
          }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px"
    });

    onMounted(() => {
      // console.log(SignUpForm.value)
    });

    const clickSignUp = function() {
      // 회원가입 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      signUpForm.value.validate(valid => {
        if (valid) {
          console.log("submit");
          store
            .dispatch("root/requestSignUp", {
              department: state.form.department,
              position: state.form.position,
              name: state.form.name,
              id: state.form.id,
              password: state.form.password
            })
            .then(function(result) {
              alert("accessToken: " + result.data.accessToken);
              emit("closeSignUpDialog");
            })
            .catch(function(err) {
              alert(err);
            });
        } else {
          alert("Validate error!");
        }
      });
    };

    const handleClose = function() {
      state.form.id = "";
      state.form.password = "";
      state.form.repassword = "";
      state.form.name = "";
      state.form.department = "";
      state.form.position = "";
      emit("closeSignUpDialog");
    };

    return { signUpForm, state, clickSignUp, handleClose };
  }
};
</script>
