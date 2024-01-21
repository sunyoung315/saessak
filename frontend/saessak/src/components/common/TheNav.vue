<template>
	<div id="nav-layout">
		<h3>Nav</h3>
		<div class="flex flex-col">
			<RouterLink to="/">Home</RouterLink>
			<RouterLink to="/notice">공지사항</RouterLink>
			<RouterLink to="/board">알림장</RouterLink>
			<RouterLink to="/album">앨범</RouterLink>
			<RouterLink to="/document">동의서</RouterLink>
			<RouterLink to="/menu">식단표</RouterLink>
			<RouterLink to="/attendance">출석부</RouterLink>
		</div>
	</div>
</template>

<script>
export default {
	setup() {
		return {};
	},
};
</script>

<style scoped>
#nav-layout {
	border: solid 1px black;
	width: 15%;
	height: 500px;
}
</style>
