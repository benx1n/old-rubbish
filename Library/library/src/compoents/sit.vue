<template>
	<view>
		<!-- <view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false"
				 swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
					</scroll-view>
				</swiper-item>
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order">
								<view class="top">
									<view class="left">
										<u-icon name="order" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">XXXXXXX书名</view>
									</view>
									<view class="right">借阅中</view>
								</view>
								<view class="item">
									<view class="left">
										<image src="https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2496571732,442429806&fm=26&gp=0.jpg"
										 mode="aspectFill"></image>
									</view>
									<view style="line-height: 20px;">
										<view class="price" style="font-size:13px">
											作者：aaaa | 类别：言情
										</view>
										<view class="intro">
											简介：啊实打实打算大苏打阿松大阿松大啊实打实大叔大叔
										</view>
										<view>
											<u-tag text="热门" mode="plain" />
										</view>
										<view class="evaluate btn right">
											<u-button type="warning" size="mini" @click="borrow">归还</u-button>
										</view>

									</view>
								</view>
							</view>
						</view>
						<view class="page-box">
							<view class="order">
								<view class="top">
									<view class="left">
										<u-icon name="order" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">XXXXXXX书名</view>
									</view>
									<view class="right" style="color:green">已归还</view>
								</view>
								<view class="item">
									<view class="left">
										<image src="https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2496571732,442429806&fm=26&gp=0.jpg"
										 mode="aspectFill"></image>
									</view>
									<view style="line-height: 20px;">
										<view class="price" style="font-size:13px">
											作者：aaaa | 类别：言情
										</view>
										<view class="intro">
											简介：啊实打实打算大苏打阿松大阿松大啊实打实大叔大叔
										</view>
										<view>
											<u-tag text="热门" mode="plain" />
										</view>
									</view>
								</view>
							</view>
						</view>
						<view class="page-box">
							<view class="order">
								<view class="top">
									<view class="left">
										<u-icon name="order" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">XXXXXXX书名</view>
									</view>
									<view class="right" style="color:red">已逾期</view>
								</view>
								<view class="item">
									<view class="left">
										<image src="https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2496571732,442429806&fm=26&gp=0.jpg"
										 mode="aspectFill"></image>
									</view>
									<view style="line-height: 20px;">
										<view class="price" style="font-size:13px">
											作者：aaaa | 类别：言情
										</view>
										<view class="intro">
											简介：啊实打实打算大苏打阿松大阿松大啊实打实大叔大叔
										</view>
										<view>
											<u-tag text="热门" mode="plain" />
										</view>
									</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view> -->
		<sit-reservation></sit-reservation>
	</view>
</template>

<script>
	import sitReservation from "../compoents/sitReservation.vue"
	export default {
		components: {
			sitReservation
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
						name: '座位预约'
					},
					{
						name: '图书借还预约'
					}
				],
				current: 0,
				swiperCurrent: 0,
				tabsHeight: 0,
				dx: 0,
				loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore'],
				libraryName: ""
			};
		},
		onLoad() {
			this.getOrderList(0);
			this.getOrderList(1);
			this.getOrderList(3);
		},
		computed: {

		},
		methods: {
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
