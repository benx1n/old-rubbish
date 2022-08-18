<template>
	<view class="wrap">
		<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">
			<u-form-item :label-position="labelPosition" prop="photo" label-width="150">
				<u-upload ref="uUpload" :action="action" width="160" height="160" upload-text="上传书籍封面"></u-upload>
			</u-form-item>
			<u-form-item :leftIconStyle="{color: '#888', fontSize: '32rpx'}" label-width="120" :label-position="labelPosition"
			 label="书籍名称" prop="bookName">
				<u-input :border="border" placeholder="请输入书籍名称" v-model="model.bookName" type="text"></u-input>
			</u-form-item>
			<u-form-item :label-position="labelPosition" label="简介" prop="intro">
				<u-input type="textarea" :border="border" placeholder="请填写简介" v-model="model.intro" />
			</u-form-item>
			<u-form-item :leftIconStyle="{color: '#888', fontSize: '32rpx'}" label-width="120" :label-position="labelPosition"
			 label="类别名称" prop="category">
				<u-input :border="border" placeholder="请输入类别" v-model="model.category" type="text"></u-input>
			</u-form-item>
			<u-form-item :leftIconStyle="{color: '#888', fontSize: '32rpx'}" label-width="120" :label-position="labelPosition"
			 label="作者名称" prop="author">
				<u-input :border="border" placeholder="请输入作者名" v-model="model.author" type="text"></u-input>
			</u-form-item>
			<u-form-item :leftIconStyle="{color: '#888', fontSize: '32rpx'}" label-width="120" :label-position="labelPosition"
			 label="出版社" prop="press">
				<u-input :border="border" placeholder="请输入出版社" v-model="model.press" type="text"></u-input>
			</u-form-item>


		</u-form>

		<u-button @click="submit">提交</u-button>
		<u-action-sheet :list="actionSheetList" v-model="actionSheetShow" @click="actionSheetCallback"></u-action-sheet>
		<u-select mode="single-column" :list="selectList" v-model="selectShow" @confirm="selectConfirm"></u-select>
		<u-picker mode="region" v-model="pickerShow" @confirm="regionConfirm"></u-picker>
		<u-verification-code seconds="60" ref="uCode" @change="codeChange"></u-verification-code>

	</view>
</template>

<script>
	export default {
		data() {
			let that = this;
			return {
				model: {
					bookName: '',
					intro: '',
					category: '',
					author: "",
					press: ""
				},
				action: null,
				rules: {
					bookName: [{
							required: true,
							message: '请输入姓名',
							trigger: 'blur',
						},
						{
							min: 1,
							message: '书名不能为空',
							trigger: ['change', 'blur'],
						}
					],
					intro: [{
							required: true,
							message: '请填写简介'
						},
						{
							min: 5,
							message: '简介不能少于5个字',
							trigger: 'change',
						},
					],
					category: [{
							required: true,
							message: '请输入类别',
							trigger: 'blur',
						},
						{
							min: 1,
							message: '类别不能为空',
							trigger: ['change', 'blur'],
						}
					],
					author: [{
							required: true,
							message: '请输入作者',
							trigger: 'blur',
						},
						{
							min: 1,
							message: '作者不能为空',
							trigger: ['change', 'blur'],
						}
					],
				},
				border: false,
				check: false,
				selectStatus: 'close',
				actionSheetShow: false,
				pickerShow: false,
				selectShow: false,
				radioCheckWidth: 'auto',
				radioCheckWrap: false,
				labelPosition: 'top',
				codeTips: '',
				errorType: ['message'],
			};
		},
		onLoad() {
			this.action = this.baseUrl + "/file/fileUpload"

		},
		computed: {
			borderCurrent() {
				return this.border ? 0 : 1;
			}
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			submit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						console.log('验证通过');
						let files = [];
						// 通过filter，筛选出上传进度为100的文件(因为某些上传失败的文件，进度值不为100，这个是可选的操作)
						files = this.$refs.uUpload.lists.filter(val => {
							return val.progress == 100;
						})
						// 如果您不需要进行太多的处理，直接如下即可
						// files = this.$refs.uUpload.lists;
						let json = {}
						json.imageUrl = files[0].response
						json.author = this.model.author
						json.catory = this.model.category
						json.intro = this.model.intro
						json.name = this.model.bookName
						json.status = "0"
						json.press = this.model.press
						console.log(files)
						this.saveBook(json)

					} else {
						console.log('验证失败');
					}
				});
			},
			// 点击actionSheet回调
			actionSheetCallback(index) {
				uni.hideKeyboard();
				this.model.sex = this.actionSheetList[index].text;
			},
			// checkbox选择发生变化
			checkboxGroupChange(e) {
				this.model.likeFruit = e;
			},
			// radio选择发生变化
			radioGroupChange(e) {
				this.model.payType = e;
			},
			// 勾选版权协议
			checkboxChange(e) {
				this.model.agreement = e.value;
			},
			// 选择地区回调
			regionConfirm(e) {
				this.model.region = e.province.label + '-' + e.city.label + '-' + e.area.label;
			},
			// 选择商品类型回调
			selectConfirm(e) {
				this.model.goodsType = '';
				e.map((val, index) => {
					this.model.goodsType += this.model.goodsType == '' ? val.label : '-' + val.label;
				})
			},
			borderChange(index) {
				this.border = !index;
			},
			radioCheckboxChange(index) {
				if (index == 0) {
					this.radioCheckWrap = false;
					this.radioCheckWidth = 'auto';
				} else if (index == 1) {
					this.radioCheckWrap = true;
					this.radioCheckWidth = 'auto';
				} else if (index == 2) {
					this.radioCheckWrap = false;
					this.radioCheckWidth = '50%';
				}
			},
			labelPositionChange(index) {
				this.labelPosition = index == 0 ? 'left' : 'top';
			},
			codeChange(text) {
				this.codeTips = text;
			},
			// 获取验证码
			getCode() {
				if (this.$refs.uCode.canGetCode) {
					// 模拟向后端请求验证码
					uni.showLoading({
						title: '正在获取验证码',
						mask: true
					})
					setTimeout(() => {
						uni.hideLoading();
						// 这里此提示会被this.start()方法中的提示覆盖
						this.$u.toast('验证码已发送');
						// 通知验证码组件内部开始倒计时
						this.$refs.uCode.start();
					}, 2000);
				} else {
					this.$u.toast('倒计时结束后再发送');
				}
			},
			errorChange(index) {
				if (index == 0) this.errorType = ['message'];
				if (index == 1) this.errorType = ['toast'];
				if (index == 2) this.errorType = ['border-bottom'];
				if (index == 3) this.errorType = ['border'];
			},
			saveBook(model) {
				uni.request({
					url: this.baseUrl+ '/library/add', //仅为示例，并非真实接口地址。
					data: model,
					method: "POST",
					success: (res) => {
						uni.showToast({
							title: "新增成功"
						})
						setTimeout(()=>{
							uni.redirectTo({
								url: "/pages/admin/admin"
							})
						},1000)
						
					}
				});
			}
		}
	};
</script>

<style scoped lang="scss">
	.wrap {
		padding: 30rpx;
	}

	.agreement {
		display: flex;
		align-items: center;
		margin: 40rpx 0;

		.agreement-text {
			padding-left: 8rpx;
			color: $u-tips-color;
		}
	}
</style>
