<template>
	<view>
		<u-modal v-model="modalShow" content="确定归还吗？" show-confirm-button="true" show-cancel-button="false" @confirm="refund"></u-modal>
		<u-toast ref="uToast" />
		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false"
				 swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view style="padding:15px">
							<u-search placeholder="书名/出版社名/类别/作者" v-model="libraryName" :show-action="false" @search="init"></u-search>
						</view>
						<view v-for="(item,index) in allLibrarayList" :key="index" class="page-box">
							<view class="order">
								<view class="top">
									<view class="left">
										<u-icon name="order" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{item.name}}</view>
									</view>
									<view class="right" :style="{'color':status(item.status).color}">{{status(item.status).statusName }}</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="url+'/files/'+item.imageUrl" mode="aspectFill"></image>
									</view>
									<view style="line-height: 20px;">
										<view class="price" style="font-size:13px">
											作者：{{item.author}} | 类别：{{item.catory}}
										</view>
										<view class="intro">
											简介：{{item.intro}}
										</view>
										<view class="intro" v-if="item.status!='0'">
											预定归还日期：{{item.date}}
										</view>
										<view>
											<u-tag style="padding: 3px;" v-for="(tag,tagIndex) in item.tags" :key="tag" :text="tag" mode="plain" />
										</view>
										<view style="width:400rpx" class="evaluate btn right">
											<u-button v-if="item.status==='0'" type="warning" size="mini" @click="borrow(item)">借阅</u-button>
										</view>

									</view>
								</view>
							</view>
						</view>

					</scroll-view>
				</swiper-item>
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view v-for="(item,index) in myLibraryList" :key="index" class="page-box">
							<view class="order">
								<view class="top">
									<view class="left">
										<u-icon name="order" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{item.library.name}}</view>
									</view>
									<view class="right" :style="{'color':status(item.status).color}">{{status(item.status).statusName }}</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="url+'/files/'+item.library.imageUrl" mode="aspectFill"></image>
									</view>
									<view style="line-height: 20px;">
										<view class="price" style="font-size:13px">
											作者：{{item.library.author}} | 类别：{{item.library.catory}}
										</view>
										<view class="intro">
											简介：{{item.library.intro}}
										</view>
										<view v-if="item.status=='2'||item.status=='3'" class="intro" style="color:red">
											逾期金额：{{item.price}} 元
										</view>

										<view>
											<u-tag style="padding: 3px;" v-for="(tag,tagIndex) in item.tags" :key="tag" :text="tag" mode="plain" />
										</view>
										<view v-if="item.status==='1'||item.status==='2'" style="width:400rpx" class="evaluate btn right">
											<u-button type="success" size="mini" @click="sureFefund(item)">归还</u-button>
										</view>

									</view>
								</view>
							</view>
						</view>


					</scroll-view>
				</swiper-item>
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;">
						<view class="page-box">
							sss
						</view>
					</scroll-view>
				</swiper-item>

			</swiper>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderList: [
					[],
					[],
					[],
					[]
				],
				list: [{
						name: '书库'
					},
					{
						name: '图书借还'
					}

				],
				current: 0,
				swiperCurrent: 0,
				tabsHeight: 0,
				dx: 0,
				loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore'],
				libraryName: "",
				allLibrarayList: [],
				myLibraryList: [],
				modalShow: false,
				item: null,
				url: ""
			};
		},
		onLoad() {

		},
		mounted() {
			this.init()
			this.url = this.baseUrl
		},
		computed: {

		},
		methods: {
			init() {
				this.getLibrary()
				this.getUserLibrary()
			},
			sureFefund(e) {
				this.item = e
				this.modalShow = true
			},
			refund() {
				let that = this
				uni.request({
					url: this.baseUrl + '/userLibrary/returnBooks', //仅为示例，并非真实接口地址。
					data: {
						id: that.item.id,
						libraryId: that.item.library.id
					},
					method: "POST",
					success: (res) => {
						if (res.data.code === 200) {
							that.$refs.uToast.show({
								title: '归还成功,等待管理员确认',
								type: 'success',
								url: '/pages/user/index'
							})
						}
						that.init()
					}
				});

			},
			getUserLibrary() {
				let that = this
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/userLibrary/userLibrary', //仅为示例，并非真实接口地址。
					data: {
						userId: userInfo.id
					},
					method: "GET",
					success: (res) => {
						that.myLibraryList = res.data.data
					}
				});
			},
			reachBottom() {

			},
			// tab栏切换
			change(index) {
				this.swiperCurrent = index;
			},
			status(status) {
				let json = this.getStatus(status)
				return json
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
			borrow(item) {
				this.$emit("update")
				uni.navigateTo({
					url: "/pages/details/details?item=" + encodeURIComponent(JSON.stringify(item))
				})
			},
			getLibrary() {
				let that = this
				uni.request({
					url: this.baseUrl + '/library/library', //仅为示例，并非真实接口地址。
					data: {
						search: that.libraryName
					},
					method: "GET",
					success: (res) => {
						that.allLibrarayList = res.data.data
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
