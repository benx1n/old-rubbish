<template>
	<view>
		<view style="padding:15px">
			<u-divider>第一次登录请完善信息</u-divider>
			<u-form :model="form" ref="uForm">
				<u-form-item label="姓名">
					<u-input v-model="form.name" placeholder="请输入姓名" />
				</u-form-item>
				<u-form-item label="班级">
					<u-input v-model="form.grade" placeholder="请输入班级" />
				</u-form-item>
				<u-form-item label="学号">
					<u-input v-model="form.studentId" placeholder="请输入学号" />
				</u-form-item>
			</u-form>
			<u-button type="primary" @click="sure">确定</u-button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					name: '',
					grade: '',
					studentId:""
				}
			};
		},
		methods: {
			sure() {
				let that = this
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/user/user', //仅为示例，并非真实接口地址。
					data: {
						id: userInfo.id,
						name: that.form.name,
						grade: that.form.grade,
						studentId:that.form.studentId,
						type:"0",
						status:"1"
					},
					method: "POST",
					success: (res) => {
						uni.redirectTo({
							url: "/pages/index/index"
						})
					}
				});
			}
		}
	};
</script>

<style>

</style>
