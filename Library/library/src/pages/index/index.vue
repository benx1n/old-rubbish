<template>
	<view class="content">
		<books-borrow style="width:100%" v-if="current===0" @update="updateToRemind"></books-borrow>
		<sit style="width:100%" v-if="current===1"></sit>
		<remind style="width:100%" v-if="current===2"></remind>
		<u-tabbar v-model="current" :list="list"></u-tabbar>
	</view>
</template>

<script>
	import booksBorrow from "../../compoents/booksBorrow.vue"
	import sit from "../../compoents/sit.vue"
	import remind from "../../compoents/remind.vue"
	export default {
		components: {
			booksBorrow,
			sit,
			remind
		},
		data() {
			return {
				list: [{
						iconPath: "home",
						selectedIconPath: "home-fill",
						text: '书架',
						count: 2,
						isDot: true,
						customIcon: false,
					},
					{
						iconPath: "photo",
						selectedIconPath: "photo-fill",
						text: '预约',
						customIcon: false,
					},
					{
						iconPath: "bell",
						selectedIconPath: "bell-fill",
						text: '提醒',
						midButton: true,
						customIcon: false,
						count: 0
					}
				],
				current: 0,

			}
		},
		mounted() {

			let that = this
			setInterval(() => {
				// that.list[2].count = that.list[2].count + 1
				this.updateToRemind()
			}, 1000)
		},
		methods: {
			updateToRemind() {
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/remind/remind', //仅为示例，并非真实接口地址。
					data: {
						userId: userInfo.id,
						status: "0"
					},
					method: "GET",
					success: (res) => {
						this.list[2].count = res.data.data.length
					}
				});

			}

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin: 200rpx auto 50rpx auto;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
