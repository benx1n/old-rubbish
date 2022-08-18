<template>
	<view>
		<u-toast ref="uToast" />
		<view style="padding:15px 10px">
			<u-button type="success" size="mini" @click="modalShow=true">新增座位</u-button>
		</view>
		<u-cell-group>
			<!-- <u-cell-item v-for="(item,index) in list" :key="item.id" icon="integral-fill" :title="item.level+'-'+item.name"> -->
			<u-cell-item v-for="(item, index) in list" :key="item.id" icon="integral-fill" :title="item.level+'-'+item.name">
				<u-row gutter="16">
					<u-col offset="8" span="1">
						<view class="demo-layout bg-purple">
							-
						</view>
					</u-col>
					<u-col span="2">
						<view class="demo-layout bg-purple-light">
							<u-button type="error" size="mini" @click="del(item)">删除</u-button>
						</view>
					</u-col>
				</u-row>
			</u-cell-item>

		</u-cell-group>
		<u-modal v-model="modalShow" @confirm="add">
			<view class="slot-content" style="padding:15px">
				<u-form :model="form" ref="uForm">
					<u-form-item label="楼层">
						<u-input v-model="form.level" />
					</u-form-item>
					<u-form-item label="座位名">
						<u-input v-model="form.name" />
					</u-form-item>
				</u-form>
			</view>
		</u-modal>
	</view>
</template>
<script>
	export default {
		components: {

		},
		data() {
			return {
				list: [],
				modalShow: false,
				form: {
					name: "",
					level: ""

				}
			};
		},
		mounted() {
			this.init()
		},
		computed: {

		},
		methods: {
			init() {
				this.getSit()
			},
			getSit() {
				let that = this
				uni.request({
					url: this.baseUrl + '/sit/sit', //仅为示例，并非真实接口地址。
					method: "GET",
					success: (res) => {
						that.list = res.data.data || []
					}
				});
			},
			del(e) {
				uni.request({
					url: this.baseUrl + '/sit/' + e.id, //仅为示例，并非真实接口地址。
					method: "DELETE",

					success: (res) => {
						if (res.data.code === 200) {
							this.$refs.uToast.show({
								title: '删除成功',
								type: 'success'

							})
						}
						this.init()
					}
				});
			},
			add(){
				uni.request({
				    url: this.baseUrl+'/sit/sit', //仅为示例，并非真实接口地址。
				    data: {
				        status: "0",
						name:this.form.name,
						level:this.form.level
				    },
				    method:"POST",
				    success: (res) => {
				        if(res.data.code===200){
							this.$refs.uToast.show({
								title: '新增成功',
								type: 'success'
							
							})
						}
						this.init()
				    }
				});
			}

		}
	};
</script>

<style lang="scss" scoped>


</style>
