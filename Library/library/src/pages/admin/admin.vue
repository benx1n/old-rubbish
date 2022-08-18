<template>
	<view>
		<user v-if="current===0"></user>
		<library v-if="current===1"></library>
		<sit v-if="current===2"></sit>
		<u-tabbar v-model="current" :list="list"></u-tabbar>
	</view>
</template>

<script>
	import user from "../../compoents/admin/user.vue"
	import library from "../../compoents/admin/library.vue"
	import sit from "../../compoents/admin/sit.vue"
	export default {
		components: {
			user,
			library,
			sit
		},
		data() {
			return {
				list: [{
						iconPath: "man-add",
						selectedIconPath: "man-add-fill",
						text: '用户管理',
						count: 2,
						isDot: true,
						customIcon: false,
					},
					{
						iconPath: "bookmark",
						selectedIconPath: "bookmark-fill",
						text: '书籍管理',
						customIcon: false,
					},
					{
						iconPath: "bag",
						selectedIconPath: "bag-fill",
						text: '座位管理',
						midButton: true,
						customIcon: false,
					}
				],
				current:0
			}
		},
		methods: {

		}
	}
</script>

<style>

</style>
