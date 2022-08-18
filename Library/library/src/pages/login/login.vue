<template>
	<view>
		<u-toast ref="uToast" />
		<view style="padding:100px 50px;text-align: center;">
			<view style="height:80px;width:80px;margin-left:35%">
				<open-data type="userAvatarUrl"></open-data>
			</view>
			<open-data type="userNickName" lang="zh_CN"></open-data>
			<view style="margin-top:200px">
				<u-button :loading="loading" type="primary" @click="loginTest">{{title}}</u-button>

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: "登录",
				loading: false

			}
		},
		methods: {
			loginTest() { // 获取用户信息
				let that = this
				that.title = "正在登录"
				that.loading = true
				uni.getUserProfile({
					desc: '登录',
					success: (res) => {
						console.log(res);
						uni.login({
							provider: 'weixin',
							success: function(loginRes) {
								console.log(loginRes);
								that.loginPro(loginRes.code, res)
							}
						});
					},
					fail: (res) => {
						that.title = "登录"
						that.loading = false
					}
				});

			},
			loginPro(code, res) {
				let that = this

				uni.request({
					url: that.baseUrl + `/wx/user/${that.appid}/login`, //仅为示例，并非真实接口地址。
					data: {
						code: code
					},
					method: "GET",
					success: (sessionKeyRes) => {
						console.log(sessionKeyRes);
						uni.request({
							url: that.baseUrl + `/wx/user/${that.appid}/wxLogin`, //仅为示例，并非真实接口地址。
							data: {
								gender: res.userInfo.gender,
								nickName: res.userInfo.nickName,
								openId: sessionKeyRes.data.openid,
								province: res.userInfo.province,
								avatarUrl: res.userInfo.avatarUrl
							},
							method: "POST",
							success: (res) => {
								if (res.data.code === 200) {
									console.log("登录成功")
									uni.setStorageSync("userInfo", JSON.stringify(res.data.data))
									that.$refs.uToast.show({
										title: '登录成功',
										type: 'success'
									})
									console.log(res);
									if (res.data.data.status === "0") {
										//跳转填写数据界面
										uni.redirectTo({
											url: "/pages/perfectInformation/perfectInformation"
										})
									} else {
										if (res.data.data.type === "0") {
											//用户
											uni.redirectTo({
												url: "/pages/index/index"
											})
										} else {
											//管理员
											uni.redirectTo({
												url: "/pages/admin/admin"
											})
										}
									}

								}
							}
						});
					}
				});
			}
		},

	}
</script>

<style>

</style>
