<template>
	<view style="padding:10px">
		<u-toast ref="uToast" />
		<u-search placeholder="请输入用户名" v-model="searchValue" :show-action="false" @search="init"></u-search>
		<u-swipe-action :show="item.show" :index="index" v-for="(item, index) in list" :key="item.id" @click="click" @open="open"
		 :options="options">
			<view class="item u-border-bottom">
				<image mode="aspectFill" :src="item.avatarUrl" />
				<!-- 此层wrap在此为必写的，否则可能会出现标题定位错误 -->
				<view class="title-wrap">
					<text class="title u-line-2">{{ item.nickName }}</text>
				</view>
			</view>
		</u-swipe-action>
	</view>
</template>

<script>
	export default {
		components: {},
		data() {
			return {
				searchValue: "",
				list: [{
						id: 1,
						title: '长安回望绣成堆，山顶千门次第开，一骑红尘妃子笑，无人知是荔枝来',
						images: 'https://cdn.uviewui.com/uview/common/logo.png',
						show: false
					},
					{
						id: 2,
						title: '新丰绿树起黄埃，数骑渔阳探使回，霓裳一曲千峰上，舞破中原始下来',
						images: 'https://cdn.uviewui.com/uview/common/logo.png',
						show: false
					},
					{
						id: 3,
						title: '登临送目，正故国晚秋，天气初肃。千里澄江似练，翠峰如簇',
						images: 'https://cdn.uviewui.com/uview/common/logo.png',
						show: false,
					}
				],
				disabled: false,
				btnWidth: 180,
				show: false,
				options: [{
					text: '删除',
					style: {
						backgroundColor: '#dd524d'
					}
				}]

			};
		},
		mounted() {
			this.init()
		},
		computed: {

		},
		methods: {
			init() {
				this.getUserList()
			},
			click(index, index1) {
				uni.request({
					url: this.baseUrl + '/user/' + this.list[index].id, //仅为示例，并非真实接口地址。
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
			// 如果打开一个的时候，不需要关闭其他，则无需实现本方法
			open(index) {
				// 先将正在被操作的swipeAction标记为打开状态，否则由于props的特性限制，
				// 原本为'false'，再次设置为'false'会无效
				this.list[index].show = true;
				this.list.map((val, idx) => {
					if (index != idx) this.list[idx].show = false;
				})
			},
			getUserList() {
				uni.request({
					url: this.baseUrl + '/user/allUser', //仅为示例，并非真实接口地址。
					data: {
						name: this.searchValue
					},
					method: "GET",
					success: (res) => {
						this.list = res.data.data
					}
				});
			}
		}
	};
</script>


<style lang="scss" scoped>
	.item {
		display: flex;
		padding: 20rpx;
	}

	image {
		width: 120rpx;
		flex: 0 0 120rpx;
		height: 120rpx;
		margin-right: 20rpx;
		border-radius: 12rpx;
	}

	.title {
		text-align: left;
		font-size: 28rpx;
		color: $u-content-color;
		margin-top: 20rpx;
	}
</style>
