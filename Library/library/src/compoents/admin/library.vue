<template>
	<view style="padding:10px">
		<u-toast ref="uToast" />
		<u-modal v-model="modalShow" show-confirm-button="true" show-cancel-button="true" content="确认删除吗" @confirm="del"></u-modal>
		<view>
			<u-row gutter="16">
				<u-col span="9">
					<view class="demo-layout bg-purple">
						<u-search placeholder="书名/出版社名/类别/作者" v-model="searchValue" :show-action="false" @search="init"></u-search>
					</view>
				</u-col>
				<u-col span="3">
					<view class="demo-layout bg-purple-light">
						<u-button type="success" @click="add" size="mini">添加书籍</u-button>
					</view>
				</u-col>
			</u-row>

		</view>
		<view>
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
							<view>
								<u-tag style="padding: 3px;" v-for="(tag,tagIndex) in item.tags" :key="tag" :text="tag" mode="plain" />
							</view>
							<view style="width:400rpx" class="evaluate btn right">
								<u-button v-if="item.status==='7'" type="success" size="mini" @click="libraryRefound(item)">归还</u-button>
								<u-button type="error" size="mini" @click="sureDel(item)">删除</u-button>
								<u-button type="error" size="mini" @click="edit(item)">修改</u-button>
							</view>

						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>



<script>
	export default {
		components: {},
		data() {
			return {
				searchValue: "",
				allLibrarayList: [],
				item: null,
				modalShow: false,
				url: ""
			};
		},
		mounted() {
			this.url = this.baseUrl
			this.init()
		},
		computed: {

		},
		methods: {
			status(status) {
				let json = this.getStatus(status)
				return json
			},
			del() {
				uni.request({
					url: this.baseUrl + '/library/' + this.item.id, //仅为示例，并非真实接口地址。
					method: "DELETE",
					success: (res) => {
						this.$refs.uToast.show({
							title: '删除成功',
							type: 'success'
						})
						this.init()
					}
				});
			},
			sureDel(e) {
				this.item = e
				this.modalShow = true
			},
			add() {
				uni.navigateTo({
					url: "../../pages/addBooks/addBooks"
				})
			},
			init() {
				this.getLibrary()
			},
			getLibrary() {
				let that = this
				uni.request({
					url: this.baseUrl + '/library/library', //仅为示例，并非真实接口地址。
					data: {
						search: that.searchValue,
						status: ""
					},
					method: "GET",
					success: (res) => {
						that.allLibrarayList = res.data.data
					}
				});
			},
			libraryRefound(e) {
				let that = this
				console.log(e);
				uni.request({
					url: this.baseUrl + '/userLibrary/libraryRefound', //仅为示例，并非真实接口地址。
					data: {
						libraryId: e.id
					},
					method: "POST",
					success: (res) => {
						if (res.data.code === 200) {
							that.$refs.uToast.show({
								title: '归还成功',
								type: 'success'
							})
						}
						that.init()
					}
				});
			},
			edit(e) {
				uni.navigateTo({
					url: "/pages/editBooks/editBooks?item=" + JSON.stringify(e)
				})
			}
		}
	};
</script>

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
