<template>
	<view>
		<view style="padding:15px">
			<u-toast ref="uToast" />
			<view>
				<u-row gutter="16">
					<u-col span="5">
						<view class="demo-layout bg-purple">
							<image style="height:180px;width: 100%;" :src="item.imageUrl">

							</image>
						</view>
					</u-col>
					<u-col span="7">
						<view class="demo-layout bg-purple-light">
							<view style="font-size:30px;font-weight: bold;">
								{{item.name}}
							</view>
							<view>
								<text>作者：</text><text style="color:#929292">{{item.author}}</text>
							</view>
							<view>
								<text>类别：</text><text style="color:#929292">{{item.catory}}</text>
							</view>
						</view>
					</u-col>
				</u-row>
			</view>
			<view>
				<view class="blue-title">
					简介
				</view>
				<view style="padding:10px 10px;color:#848484">
					{{item.intro}}
				</view>
			</view>
			<view>
				<u-calendar v-model="endDatetimeShow" mode="date" @change="endDatetimeClick" :max-date="maxDate"></u-calendar>
				<view style="padding:0px 10px" @click="endDatetimeShow=true" >
					还书时间:{{endDatetime===null?'':endDatetime}}
				</view>
			</view>
			<view style="padding:10px 50px">
				<u-button type="success" @click="borrow" :loading="loading">借阅</u-button>
			</view>
		</view>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item: null,
				endDatetime: null,
				endDatetimeShow: false,
				loading:false,
				maxDate:""
			}
		},
		onLoad(e) {
			this.item = JSON.parse(decodeURIComponent(e.item));
			this.maxDate=this.dayjs().add(1, 'month').format('YYYY-MM-DD')
		},
		methods: {
			borrow() {
				if (this.endDatetime == null) {
					this.$refs.uToast.show({
						title: '请选择还书时间',
						type: 'warning'
					})
					return
				}
				let that = this
				this.loading=true
				let userInfo = JSON.parse(uni.getStorageSync("userInfo"))
				uni.request({
					url: this.baseUrl + '/library/borrow', //仅为示例，并非真实接口地址。
					data: {
						libraryId: that.item.id,
						userId: userInfo.id,
						endDatetime: that.endDatetime,
						status: "1"
					},
					method: "POST",
					success: (res) => {
						that.$refs.uToast.show({
							title: '借阅成功',
							type: 'success'

						})
						setTimeout(() => {
							uni.redirectTo({
								url: "/pages/index/index"
							})
						}, 1000)
					}
				});

			},

			endDatetimeClick(e) {
				this.endDatetime = e.result
			}
		}
	}
</script>

<style lang="scss" scoped>
	.blue-title {
		margin: 5px 10px;
		padding-left: 10px;
		border-left: 3px solid #45bede;
		color: #45bede
	}
</style>
