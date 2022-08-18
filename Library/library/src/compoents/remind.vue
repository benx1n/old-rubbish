<template>
	<view>
		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false"
				 swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item @touchmove.stop="stop" class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<seat-to-remind :list="libraryList" @itemClick="readClick"></seat-to-remind>
					</scroll-view>
				</swiper-item>
				<swiper-item @touchmove.stop="stop" class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<seat-to-remind :list="seatList" @itemClick="readClick"></seat-to-remind>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
	import seatToRemind from "../compoents/seatToRemind.vue"
	export default {
		components: {
			seatToRemind
		},
		data() {
			return {
				orderList: [
					[],
					[],
					[],
					[]
				],
				list: [{
						name: '图书归还',
						count: 0
					},
					{
						name: '座位保留',
						count: 0
					}
				],
				seatList: [],
				libraryList: [],
				current: 0,
				swiperCurrent: 0,
				tabsHeight: 0,
				dx: 0,
				loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore'],
				libraryName: "",
			};
		},
		mounted() {
			this.init()
		},
		computed: {

		},
		methods: {
			init() {
				setInterval(() => {
					this.getLibraryListRemind()
					this.list[0].count = this.libraryList.length
					this.getSitRemind()
					this.list[1].count = this.seatList.length
				}, 1000)
			},
			stop() {
				return true
			},
			reachBottom() {

			},
			// tab栏切换
			change(index) {
				this.swiperCurrent = index;
			},
			transition({
				detail: {
					dx
				}
			}) {
				this.$refs.tabs.setDx(dx);
			},
			animationfinish({
				detail: {
					current
				}
			}) {
				this.$refs.tabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},
			borrow() {
				uni.navigateTo({
					url: "/pages/details/details"
				})
			},
			readClick(item) {
				console.log(item);
				item.status = "1"
				this.updateStatus(item)
			},
			getLibraryListRemind() {
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/remind/remind', //仅为示例，并非真实接口地址。
					data: {
						userId: userInfo.id,
						type: "6",
						status: "0"
					},
					method: "GET",
					success: (res) => {
						this.libraryList = res.data.data || []
					}
				});
			},
			getSitRemind() {
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/remind/remind', //仅为示例，并非真实接口地址。
					data: {
						userId: userInfo.id,
						type: "5",
						status: "0"
					},
					method: "GET",
					success: (res) => {
						this.seatList = res.data.data || []
					}
				});
			},
			updateStatus(item) {
				uni.request({
					url: this.baseUrl + '/remind/remind', //仅为示例，并非真实接口地址。
					data: {
						id: item.id,
						status: item.status
					},
					method: "POST",
					success: (res) => {
						this.init()
					}
				});
			}
		}
	};
</script>

<style>
	/* #ifndef H5 */
	page {
		height: 100%;
		background-color: #eaeaea;
	}

	/* #endif */
</style>

<style lang="scss" scoped>
	.intro {
		font-size: 13px;
		color: #8e8d8d
	}

	.order {
		width: 710rpx;
		background-color: #ffffff;
		margin: 20rpx auto;
		border-radius: 20rpx;
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;

		.top {
			display: flex;
			justify-content: space-between;

			.left {
				display: flex;
				align-items: center;

				.store {
					margin: 0 10rpx;
					font-size: 32rpx;
					font-weight: bold;
				}
			}

			.right {
				color: $u-type-warning-dark;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0;

			.left {
				margin-right: 20rpx;

				image {
					width: 200rpx;
					height: 200rpx;
					border-radius: 10rpx;
				}
			}

			.content {
				.title {
					font-size: 28rpx;
					line-height: 50rpx;
				}

				.type {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}

				.delivery-time {
					color: #e5d001;
					font-size: 24rpx;
				}
			}

			.right {
				margin-left: 10rpx;
				padding-top: 20rpx;
				text-align: right;

				.decimal {
					font-size: 24rpx;
					margin-top: 4rpx;
				}

				.number {
					color: $u-tips-color;
					font-size: 24rpx;
				}
			}
		}

		.total {
			margin-top: 20rpx;
			text-align: right;
			font-size: 24rpx;

			.total-price {
				font-size: 32rpx;
			}
		}

		.bottom {
			display: flex;
			margin-top: 40rpx;
			padding: 0 10rpx;
			justify-content: space-between;
			align-items: center;

			.btn {
				line-height: 52rpx;
				width: 160rpx;
				border-radius: 26rpx;
				border: 2rpx solid $u-border-color;
				font-size: 26rpx;
				text-align: center;
				color: $u-type-info-dark;
			}

			.evaluate {
				color: $u-type-warning-dark;
				border-color: $u-type-warning-dark;
			}
		}
	}

	.centre {
		text-align: center;
		margin: 200rpx auto;
		font-size: 32rpx;

		image {
			width: 164rpx;
			height: 164rpx;
			border-radius: 50%;
			margin-bottom: 20rpx;
		}

		.tips {
			font-size: 24rpx;
			color: #999999;
			margin-top: 20rpx;
		}

		.btn {
			margin: 80rpx auto;
			width: 200rpx;
			border-radius: 32rpx;
			line-height: 64rpx;
			color: #ffffff;
			font-size: 26rpx;
			background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
		}
	}

	.wrap {
		display: flex;
		flex-direction: column;
		height: calc(100vh - var(--window-top));
		width: 100%;
	}

	.swiper-box {
		flex: 1;
	}

	.swiper-item {
		height: 100%;
	}
</style>
